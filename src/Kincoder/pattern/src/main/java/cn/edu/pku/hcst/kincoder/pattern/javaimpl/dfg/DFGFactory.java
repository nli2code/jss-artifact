package cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFGStatements;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRDefStatement;
import com.google.inject.Inject;
import de.parsemis.graph.Edge;
import de.parsemis.graph.Node;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

public class DFGFactory {
    private final DFGNodeFactory dfgNodeFactory;

    @Inject
    public DFGFactory(DFGNodeFactory dfgNodeFactory) {
        this.dfgNodeFactory = dfgNodeFactory;
    }

    @Value
    private class StmtMapping {
        private final IRStatement stmt;
        private final Node<DFGNode, DFGEdge> defNode;
        private final Set<Node<DFGNode, DFGEdge>> typeNode;
    }

    @Value
    private class DefUse {
        private final IRExpression def;
        private final IRStatement use;
    }

    public DFG create(CFG cfg) {
        var dfg = new DFG(cfg);

        var statements = cfg.getBlocks().stream()
            .filter(CFGStatements.class::isInstance)
            .map(CFGStatements.class::cast)
            .map(CFGStatements::statements)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        List<StmtMapping> mappings = statements.stream()
            .map(s -> {
                var opNode = dfg.addNode(dfgNodeFactory.statement2OpNode(s));
                if (s instanceof IRDefStatement) {
                    var tyNodes = dfgNodeFactory.statement2Type(s)
                        .stream()
                        .map(n -> {
                            var tyNode = dfg.addNode(n);
                            dfg.addEdge(opNode, tyNode, DFGEdge.INSTANCE, Edge.OUTGOING);
                            return tyNode;
                        }).collect(Collectors.toSet());
                    return new StmtMapping(s, opNode, tyNodes);
                } else return new StmtMapping(s, opNode, Set.of());
            })
            .collect(Collectors.toList());

        Map<IRStatement, Node<DFGNode, DFGEdge>> opMap = mappings.stream().collect(Collectors.toMap(StmtMapping::getStmt, StmtMapping::getDefNode));
        Map<IRStatement, Set<Node<DFGNode, DFGEdge>>> tyMap = mappings.stream().collect(Collectors.toMap(StmtMapping::getStmt, StmtMapping::getTypeNode));
        Map<Node<DFGNode, DFGEdge>, Set<com.github.javaparser.ast.Node>> result = new HashMap<>();

        List<DefUse> defUses = statements.stream()
            .flatMap(s -> s.uses().stream().map(use -> new DefUse(use, s)))
            .collect(Collectors.toList());

        Map<IRExpression, Set<Node<DFGNode, DFGEdge>>> dataNodes = new HashMap<>();

        for (DefUse defUse : defUses) {
            var from = defUse.def;
            var to = defUse.use;

            if (from.def().isPresent()) {
                tyMap.get(from.def().get()).forEach(d -> dfg.addEdge(d, opMap.get(to), DFGEdge.INSTANCE, Edge.OUTGOING));
            } else {
                Set<Node<DFGNode, DFGEdge>> tyNodes;
                if (dataNodes.containsKey(from)) {
                    tyNodes = dataNodes.get(from);
                } else {
                    var dataNode = dfg.addNode(dfgNodeFactory.expression2DataNode(from));
                    tyNodes = dfgNodeFactory.expression2Type(from)
                        .stream()
                        .map(n -> {
                            var tyNode = dfg.addNode(n);
                            dfg.addEdge(dataNode, tyNode, DFGEdge.INSTANCE, Edge.OUTGOING);
                            return tyNode;
                        })
                        .collect(Collectors.toSet());
                    dataNodes.put(from, tyNodes);
                    result.put(dataNode, from.getFrom());
                }

                tyNodes.forEach(tyNode -> dfg.addEdge(tyNode, opMap.get(to), DFGEdge.INSTANCE, Edge.OUTGOING));
            }
        }

        opMap.forEach((a, b) -> result.put(b, a.getFrom()));
        dfg.setMap(result);
        return dfg;
    }
}
