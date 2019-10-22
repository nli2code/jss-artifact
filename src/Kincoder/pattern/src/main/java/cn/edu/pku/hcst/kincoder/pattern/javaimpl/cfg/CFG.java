package cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg;

import cn.edu.pku.hcst.kincoder.common.collection.ImmutableStack;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions.IRArg;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions.IRTemp;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRDefStatement;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRPhi;
import com.github.javaparser.ast.Node;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CFG {
    @Nullable
    private final Path path;
    private final String methodName;
    @Getter
    private final Node decl;

    private final AtomicInteger tempId = new AtomicInteger(0);
    private final List<CFGBlock> cfgBlocks = new ArrayList<>();
    @Getter
    private final CFGStatements entry = new CFGStatements(this);
    @Getter
    private final CFGExit exit = new CFGExit(this);

    public CFG(@Nullable Path path, String methodName, Node decl) {
        this.path = path;
        this.methodName = methodName;
        this.decl = decl;
    }

    @Getter
    public class Context {
        private final CFGStatements block;
        private final ImmutableStack<CFGBlock> breakStack;
        private final ImmutableStack<CFGBlock> continueStack;

        public Context(CFGStatements block) {
            this.block = block;
            this.breakStack = ImmutableStack.of();
            this.continueStack = ImmutableStack.of();
        }

        private Context(CFGStatements block, ImmutableStack<CFGBlock> breakStack, ImmutableStack<CFGBlock> continueStack) {
            this.block = block;
            this.breakStack = breakStack;
            this.continueStack = continueStack;
        }

        public Context change(CFGStatements b) {
            return new Context(b, breakStack, continueStack);
        }

        public Context push(CFGStatements b, CFGBlock br, CFGBlock c) {
            return new Context(b, breakStack.push(br), continueStack.push(c));
        }

        public Context pop(CFGStatements b) {
            return new Context(b, breakStack.pop().getRight(), continueStack.pop().getRight());
        }
    }

//    object Context {
//        def apply(block: CFGStatements): Context = Context(block, Nil, Nil)
//    }


    public List<CFGBlock> getBlocks() {
        return Collections.unmodifiableList(cfgBlocks);
    }

    public void addCfgBlock(CFGBlock block) {
        cfgBlocks.add(block);
    }

    public IRTemp createTempVar(IRDefStatement def) {
        return new IRTemp(tempId.getAndIncrement(), def, def.getTy());
    }

    public CFGStatements createStatements() {
        return new CFGStatements(this);
    }

    public CFGBranch createBranch(@Nullable IRExpression condition, CFGBlock thenBlock, CFGBlock elseBlock) {
        return new CFGBranch(this, condition, thenBlock, elseBlock);
    }

    public CFGSwitch createSwitch(IRExpression selector) {
        return new CFGSwitch(this, selector);
    }

    public void writeVar(String name, CFGBlock block, IRExpression value) {
        block.updateDef(name, value);
    }

    public IRExpression readVar(String ty, String name, CFGBlock block) {
        var expr = block.getDefs().get(name);
        if (expr != null) return expr;
        return readVarRec(ty, name, block);
    }

    private IRExpression readVarRec(String ty, String name, CFGBlock block) {
        IRExpression v;
        if (!block.isSealed()) {
            var phi = new IRPhi(block.cfg, ty, block);
            block.updateIncompletePhi(name, phi);
            v = phi.getTarget();
        } else if (block.getPreds().size() == 1) {
            v = readVar(ty, name, block.getPreds().get(0));
        } else {
            var phi = new IRPhi(block.cfg, ty, block);
            writeVar(name, block, phi.getTarget());
            v = addPhiOperands(name, phi);
        }
        writeVar(name, block, v);
        return v;
    }

    public IRExpression addPhiOperands(String name, IRPhi phi) {
        phi.getBlock().getPreds().forEach(pred -> phi.appendOperand(readVar(phi.getTy(), name, pred)));
        return tryRemoveTrivialPhi(name, phi);
    }

    private IRExpression tryRemoveTrivialPhi(String name, IRPhi phi) {
        IRExpression same = null;

        for (IRExpression op : phi.getOperands()) {
            if (!exprEquals(op, same) && !exprEquals(op, phi.getTarget())) {
                if (same != null) return phi.getTarget();
                same = op;
            }
        }

        var result = same == null ? new IRArg(phi.getTy(), name) : same;

        phi.replaceBy(result);

        phi.getTarget().getUses().stream()
            .filter(p -> p != phi)
            .forEach(p -> {
                if (p instanceof IRPhi) tryRemoveTrivialPhi(name, (IRPhi) p);
            });
        return result;
    }

    private boolean exprEquals(IRExpression a, IRExpression b) {
        if (a == null || b == null) return a == null && b == null;
        if (a instanceof IRTemp && ((IRTemp) a).getReplaced() != null) return exprEquals(((IRTemp) a).getReplaced(), b);
        if (b instanceof IRTemp && ((IRTemp) b).getReplaced() != null) return exprEquals(a, ((IRTemp) b).getReplaced());
        return a.equals(b);
    }
}
