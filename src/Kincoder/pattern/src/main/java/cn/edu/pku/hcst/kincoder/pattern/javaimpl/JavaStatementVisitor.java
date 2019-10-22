package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.common.utils.TryUtil;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG.Context;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFGSwitch.DefaultLabel;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFGSwitch.ExpressionLabel;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRAssert;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRMethodInvocation;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRReturn;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRThrow;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.GenericVisitorWithDefaults;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class JavaStatementVisitor {
    private final CodeUtil codeUtil;
    private final PatternConfig config;
    private final JavaExpressionVisitor exprVisitor;
    @Getter
    private final GenericVisitorWithDefaults<CFG.Context, CFG.Context> visitor;

    public JavaStatementVisitor(CodeUtil codeUtil, PatternConfig config, CFG cfg) {
        this.codeUtil = codeUtil;
        this.config = config;
        this.exprVisitor = new JavaExpressionVisitor(codeUtil, config, cfg);
        this.visitor = new GenericVisitorWithDefaults<>() {

            private Optional<String> typeOf(Expression expr) {
                return TryUtil.optionalTry(() -> expr.calculateResolvedType().describe());
            }

            @Override
            public Context defaultAction(Node n, Context arg) {
                throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
            }

            @Override
            public Context defaultAction(NodeList n, Context arg) {
                throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
            }

            @Override
            public Context visit(AssertStmt n, Context arg) {
                n.getCheck().accept(exprVisitor, arg.getBlock()).flatMap(check ->
                    n.getMessage().map(m -> m.accept(exprVisitor, arg.getBlock()).orElse(null)).map(message ->
                        arg.getBlock().addStatement(new IRAssert(Set.of(n), check, message)))
                );
                return arg;
            }

            @Override
            public Context visit(BlockStmt n, Context arg) {
                return n.getStatements().stream().reduce(arg, (c, s) -> s.accept(this, c), (a, b) -> null);
            }

            @Override
            public Context visit(BreakStmt n, Context arg) {
                // TODO: change control flow
                if (config.isDebug()) log.warn("BreakStmt needs to be enhanced");
                return arg;
            }

            @Override
            public Context visit(ContinueStmt n, Context arg) {
                // TODO: change control flow
                if (config.isDebug()) log.warn("ContinueStmt needs to be enhanced");
                return arg;
            }

            @Override
            public Context visit(DoStmt n, Context arg) {
                arg.getBlock().seal();
                var entryBlock = cfg.createStatements();
                var bodyBlock = cfg.createStatements();
                var exitBlock = cfg.createStatements();
                var compareBlock = cfg.createStatements();
                arg.getBlock().setNext(entryBlock);
                entryBlock.setNext(bodyBlock);
                var newContext = n.getBody().accept(this, arg.push(bodyBlock, exitBlock, compareBlock));
                newContext.getBlock().seal();
                newContext.getBlock().setNext(compareBlock);
                var compareResult = n.getCondition().accept(exprVisitor, compareBlock).orElse(null);
                compareBlock.seal();
                var branch = cfg.createBranch(compareResult, bodyBlock, exitBlock);
                compareBlock.setNext(branch);
                branch.seal();
                entryBlock.seal();
                return newContext.pop(exitBlock);
            }

            @Override
            public Context visit(EmptyStmt n, Context arg) {
                return arg;
            }

            @Override
            public Context visit(ExpressionStmt n, Context arg) {
                n.getExpression().accept(exprVisitor, arg.getBlock());
                return arg;
            }

            @Override
            public Context visit(ForEachStmt n, Context arg) {
                var ty = typeOf(n.getVariable());
                arg.getBlock().seal();
                var entryBlock = cfg.createStatements();
                arg.getBlock().setNext(entryBlock);
                var iteExpr = n.getIterable().accept(exprVisitor, entryBlock);
                var tempIte = entryBlock.addStatement(new IRMethodInvocation(cfg, "Iterable", Set.of(n), "java.lang.Iterable.iterator()", iteExpr.orElse(null), List.of())).getTarget();
                var conditionBlock = cfg.createStatements();
                entryBlock.setNext(conditionBlock);
                entryBlock.seal();
                var condition = conditionBlock.addStatement(new IRMethodInvocation(cfg, "boolean", Set.of(n), "java.util.Iterator.hasNext()", tempIte, List.of())).getTarget();
                var thenBlock = cfg.createStatements();
                ty.ifPresent(t -> {
                    var next = thenBlock.addStatement(new IRMethodInvocation(cfg, t, Set.of(n), "java.util.Iterator.next()", tempIte, List.of())).getTarget();
                    cfg.writeVar(n.getVariableDeclarator().getNameAsString(), thenBlock, next);
                });
                var elseBlock = cfg.createStatements();
                var branch = cfg.createBranch(condition, thenBlock, elseBlock);
                branch.seal();
                conditionBlock.setNext(branch);
                // TODO next
                var newContext = n.getBody().accept(this, arg.push(thenBlock, elseBlock, entryBlock));
                newContext.getBlock().seal();
                newContext.getBlock().setNext(conditionBlock);
                conditionBlock.seal();
                return newContext.pop(elseBlock);
            }

            @Override
            public Context visit(ForStmt n, Context arg) {
                arg.getBlock().seal();
                var entryBlock = cfg.createStatements();
                n.getInitialization().forEach(i -> i.accept(exprVisitor, entryBlock));
                arg.getBlock().setNext(entryBlock);
                entryBlock.seal();
                var compareBlock = cfg.createStatements();
                entryBlock.setNext(compareBlock);
                var compareResult = n.getCompare().flatMap(c -> c.accept(exprVisitor, compareBlock));
                var thenBlock = cfg.createStatements();
                var elseBlock = cfg.createStatements();
                var updateBlock = cfg.createStatements();
                var branch = compareResult.map(r -> cfg.createBranch(r, thenBlock, elseBlock));
                if (branch.isPresent()) {
                    compareBlock.setNext(branch.get());
                    branch.get().seal();
                } else {
                    compareBlock.setNext(thenBlock);
                }
                var newContext = n.getBody().accept(this, arg.push(thenBlock, elseBlock, updateBlock));
                newContext.getBlock().seal();
                newContext.getBlock().setNext(updateBlock);
                n.getUpdate().forEach(u -> u.accept(exprVisitor, updateBlock));
                updateBlock.seal();
                updateBlock.setNext(compareBlock);
                compareBlock.seal();
                return newContext.pop(elseBlock);
            }

            @Override
            public Context visit(IfStmt n, Context arg) {
                var condition = n.getCondition().accept(exprVisitor, arg.getBlock());
                var thenBlock = cfg.createStatements();
                var elseBlock = cfg.createStatements();
                var endBlock = cfg.createStatements();
                var branch = cfg.createBranch(condition.orElse(null), thenBlock, elseBlock);
                arg.getBlock().seal();
                arg.getBlock().setNext(branch);
                branch.seal();
                var newContext = n.getThenStmt().accept(this, arg.push(thenBlock, endBlock, null));
                newContext.getBlock().seal();
                newContext.getBlock().setNext(endBlock);
                if (n.getElseStmt().isPresent()) {
                    newContext = n.getElseStmt().get().accept(this, newContext.change(elseBlock));
                    newContext.getBlock().seal();
                    newContext.getBlock().setNext(endBlock);
                } else {
                    elseBlock.seal();
                    elseBlock.setNext(endBlock);
                }
                return newContext.pop(endBlock);
            }

            @Override
            public Context visit(LabeledStmt n, Context arg) {
                // TODO: label
                if (config.isDebug()) log.warn("Label of LabelStmt not supported");
                return n.getStatement().accept(this, arg);
            }

            @Override
            public Context visit(ReturnStmt n, Context arg) {
                var value = n.getExpression().flatMap(expr -> expr.accept(exprVisitor, arg.getBlock())).orElse(null);
                arg.getBlock().addStatement(new IRReturn(Set.of(n), value));
                return arg;
            }

            @Override
            public Context visit(SwitchStmt n, Context arg) {
                var s = n.getSelector().accept(exprVisitor, arg.getBlock()).map(selector -> {
                    arg.getBlock().seal();
                    var switchBlock = cfg.createSwitch(selector);
                    arg.getBlock().setNext(switchBlock);
                    var exitBlock = cfg.createStatements();
                    // TODO: fall through
                    n.getEntries().forEach(e -> {
                        var labels = e.getLabels();
                        var stmts = e.getStatements();
                        var statements = cfg.createStatements();
                        if (labels.size() == 0) {
                            switchBlock.update(new DefaultLabel(), statements);
                        } else {
                            labels.stream().flatMap(l -> l.accept(exprVisitor, arg.getBlock()).stream()).map(ExpressionLabel::new).forEach(l -> switchBlock.update(l, statements));
                        }
                        var newContext = stmts.stream().reduce(arg.push(statements, exitBlock, null), (c, st) -> st.accept(this, c), (a, b) -> null);
                        newContext.getBlock().seal();
                        newContext.getBlock().setNext(exitBlock);
                    });
                    return arg.change(exitBlock);
                });
                return s.orElse(arg);
            }

            @Override
            public Context visit(SynchronizedStmt n, Context arg) {
                return n.getBody().accept(this, arg);
            }

            @Override
            public Context visit(ThrowStmt n, Context arg) {
                n.getExpression().accept(exprVisitor, arg.getBlock()).ifPresent(exception -> arg.getBlock().addStatement(new IRThrow(Set.of(n), exception)));
                return arg;
            }

            @Override
            public Context visit(TryStmt n, Context arg) {
                n.getResources().forEach(r -> r.accept(exprVisitor, arg.getBlock()));
                var newContext = n.getTryBlock().accept(this, arg);
                if (config.isDebug()) log.warn("Catch clause not supported");

                if (n.getFinallyBlock().isPresent()) {
                    newContext = n.getFinallyBlock().get().accept(this, newContext);
                }
                //  TODO: catch clause
                return newContext;
            }

            @Override
            public Context visit(LocalClassDeclarationStmt n, Context arg) {
                // TODO: Local class decl
                if (config.isDebug()) log.warn("LocalClassDeclarationStmt not supported");
                return arg;
            }

            @Override
            public Context visit(WhileStmt n, Context arg) {
                arg.getBlock().seal();
                var entryBlock = cfg.createStatements();
                arg.getBlock().setNext(entryBlock);
                var conditionBlock = cfg.createStatements();
                entryBlock.setNext(conditionBlock);
                var condition = n.getCondition().accept(exprVisitor, conditionBlock);
                var thenBlock = cfg.createStatements();
                var elseBlock = cfg.createStatements();
                var branch = cfg.createBranch(condition.orElse(null), thenBlock, elseBlock);
                conditionBlock.seal();
                branch.seal();
                conditionBlock.setNext(branch);
                var newContext = n.getBody().accept(this, arg.push(thenBlock, elseBlock, entryBlock));
                newContext.getBlock().seal();
                newContext.getBlock().setNext(entryBlock);
                entryBlock.seal();
                return newContext.pop(elseBlock);
            }

        };
    }
}
