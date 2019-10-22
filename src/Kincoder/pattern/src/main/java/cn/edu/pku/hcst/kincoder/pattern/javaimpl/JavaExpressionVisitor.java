package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.common.utils.Function3;
import cn.edu.pku.hcst.kincoder.common.utils.Function4;
import cn.edu.pku.hcst.kincoder.common.utils.TryUtil;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFGStatements;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions.*;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.*;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorWithDefaults;
import com.github.javaparser.resolution.declarations.ResolvedEnumConstantDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class JavaExpressionVisitor extends GenericVisitorWithDefaults<Optional<IRExpression>, CFGStatements> {
    private final CodeUtil codeUtil;
    private final PatternConfig config;
    private final CFG cfg;

    public JavaExpressionVisitor(CodeUtil codeUtil, PatternConfig config, CFG cfg) {
        this.codeUtil = codeUtil;
        this.config = config;
        this.cfg = cfg;
    }

    private Optional<String> typeOf(Expression expr) {
        return TryUtil.optionalTry(() -> expr.calculateResolvedType().describe());
    }

    private Optional<String> resolve(Type type) {
        return TryUtil.optionalTry(() -> type.resolve().describe());
    }

    private Optional<String> resolveObjectCreationExpr(ObjectCreationExpr objectCreationExpr) {
        return TryUtil.optionalTry(() -> {
            var resolved = objectCreationExpr.resolve();
            return resolved.getQualifiedSignature();
        });
    }

    private Optional<String> resolveMethodCallExpr(MethodCallExpr methodCallExpr) {
        try {
            return codeUtil.getMethodProto(methodCallExpr.resolve().getQualifiedSignature());
        } catch (Throwable e) {
            return Optional.empty();
        }
    }

    private BinaryExpr.Operator assignOpe2BinaryOpe(AssignExpr.Operator ope) {
        switch (ope) {
            case PLUS:
                return BinaryExpr.Operator.PLUS;
            case MINUS:
                return BinaryExpr.Operator.MINUS;
            case MULTIPLY:
                return BinaryExpr.Operator.MULTIPLY;
            case DIVIDE:
                return BinaryExpr.Operator.DIVIDE;
            case BINARY_AND:
                return BinaryExpr.Operator.BINARY_AND;
            case BINARY_OR:
                return BinaryExpr.Operator.BINARY_OR;
            case XOR:
                return BinaryExpr.Operator.XOR;
            case REMAINDER:
                return BinaryExpr.Operator.REMAINDER;
            case LEFT_SHIFT:
                return BinaryExpr.Operator.LEFT_SHIFT;
            case SIGNED_RIGHT_SHIFT:
                return BinaryExpr.Operator.SIGNED_RIGHT_SHIFT;
            case UNSIGNED_RIGHT_SHIFT:
                return BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT;
            case ASSIGN:
                throw new RuntimeException("Cannot convert assign operator to binary operator!");
        }
        throw new RuntimeException("Unknown operator!");
    }

    private String posInfo(Node n) {
        return n.getBegin().map(pos -> String.format(" line: %d, col: %d.", pos.line, pos.column)).orElse(".");
    }

    private void unimplemented(Node n) {
        if (config.isDebug()) {
            log.warn("{} not supported{}", n.getClass(), posInfo(n));
        }
    }

    private List<IRExpression> acceptList(CFGStatements arg, NodeList<?> nodes) {
        return nodes.stream()
            .map(n -> n.accept(this, arg))
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }

    private <T extends Node, N extends Node> List<IRExpression> acceptList(CFGStatements arg, Stream<T> nodes, Function<T, Stream<N>> getter) {
        return nodes
            .flatMap(getter)
            .map(n -> n.accept(this, arg))
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }

    private <N extends Expression> Optional<IRExpression> acceptExpr(CFGStatements arg, N n, Node node1, BiFunction<IRExpression, String, IRExpression> gen) {
        return node1.accept(this, arg).flatMap(n1 ->
            typeOf(n).map(ty ->
                gen.apply(n1, ty)
            )
        );
    }

    private <N extends Expression> Optional<IRExpression> accept(CFGStatements arg, N n, Node node1, BiFunction<IRExpression, String, IRDefStatement> gen) {
        return node1.accept(this, arg).flatMap(n1 ->
            typeOf(n).map(ty ->
                arg.addStatement(gen.apply(n1, ty)).getTarget()
            )
        );
    }

    private <N extends Expression> Optional<IRExpression> accept(CFGStatements arg, N n, Node node1, Node node2, Function3<IRExpression, IRExpression, String, IRDefStatement> gen) {
        return node1.accept(this, arg).flatMap(n1 ->
            node2.accept(this, arg).flatMap(n2 ->
                typeOf(n).map(ty ->
                    arg.addStatement(gen.apply(n1, n2, ty)).getTarget()
                )
            )
        );
    }

    private <N extends Expression> Optional<IRExpression> accept(CFGStatements arg, N n, Node node1, Node node2, Node node3, Function4<IRExpression, IRExpression, IRExpression, String, IRDefStatement> gen) {
        return node1.accept(this, arg).flatMap(n1 ->
            node2.accept(this, arg).flatMap(n2 ->
                node3.accept(this, arg).flatMap(n3 ->
                    typeOf(n).map(ty ->
                        arg.addStatement(gen.apply(n1, n2, n3, ty)).getTarget()
                    )
                )
            )
        );
    }

    @Override
    public Optional<IRExpression> defaultAction(Node n, CFGStatements arg) {
        throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
    }

    @Override
    public Optional<IRExpression> defaultAction(NodeList n, CFGStatements arg) {
        throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
    }

    @Override
    public Optional<IRExpression> visit(ArrayAccessExpr n, CFGStatements arg) {
        return accept(arg, n, n.getName(), n.getIndex(), (name, index, ty) -> new IRArrayAccess(cfg, ty, Set.of(n), name, index));
    }

    @Override
    public Optional<IRExpression> visit(ArrayCreationExpr n, CFGStatements arg) {
        var levels = acceptList(arg, n.getLevels().stream(), l -> l.getDimension().stream());
        var initializers = n.getInitializer().flatMap(i -> i.accept(this, arg)).orElse(null);
        return typeOf(n).map(ty ->
            arg.addStatement(new IRArrayCreation(cfg, ty, Set.of(n), levels, initializers)).getTarget()
        );
    }

    @Override
    public Optional<IRExpression> visit(ArrayInitializerExpr n, CFGStatements arg) {
        var values = acceptList(arg, n.getValues());
        return typeOf(n).map(ty ->
            arg.addStatement(new IRArrayInitializer(cfg, ty, Set.of(n), values)).getTarget()
        );
    }

    @Override
    public Optional<IRExpression> visit(AssignExpr n, CFGStatements arg) {
        var target = n.getTarget();
        if (target.isNameExpr()) {
            return acceptExpr(arg, n, n.getValue(), (source, ty) -> {
                if (n.getOperator() == AssignExpr.Operator.ASSIGN) {
                    cfg.writeVar(target.asNameExpr().getNameAsString(), arg, source);
                    return source;
                }

                var value = cfg.readVar(ty, target.asNameExpr().getNameAsString(), arg);
                var newTarget = arg.addStatement(new IRBinaryOperation(cfg, ty, Set.of(n), value, source, assignOpe2BinaryOpe(n.getOperator()))).getTarget();
                cfg.writeVar(target.asNameExpr().getNameAsString(), arg, newTarget);
                return newTarget;
            });
        }
        if (target.isFieldAccessExpr() && target.asFieldAccessExpr().getScope().isThisExpr()) {
            return acceptExpr(arg, n, n.getValue(), (source, ty) -> {
                if (n.getOperator() == AssignExpr.Operator.ASSIGN) {
                    cfg.writeVar(target.asFieldAccessExpr().getNameAsString(), arg, source);
                    return source;
                }

                var value = cfg.readVar(ty, target.asFieldAccessExpr().getNameAsString(), arg);
                var newTarget = arg.addStatement(new IRBinaryOperation(cfg, ty, Set.of(n), value, source, assignOpe2BinaryOpe(n.getOperator()))).getTarget();
                cfg.writeVar(target.asFieldAccessExpr().getNameAsString(), arg, newTarget);
                return newTarget;
            });
        }


        // TODO: left hand side
        unimplemented(n);
        return n.getValue().accept(this, arg);
    }

    @Override
    public Optional<IRExpression> visit(BinaryExpr n, CFGStatements arg) {
        return accept(arg, n, n.getLeft(), n.getRight(), (lhs, rhs, ty) -> new IRBinaryOperation(cfg, ty, Set.of(n), lhs, rhs, n.getOperator()));
    }

    @Override
    public Optional<IRExpression> visit(BooleanLiteralExpr n, CFGStatements arg) {
        return Optional.of(new IRBoolean(n.getValue(), n));
    }

    @Override
    public Optional<IRExpression> visit(CastExpr n, CFGStatements arg) {
        return n.getExpression().accept(this, arg);
    }

    @Override
    public Optional<IRExpression> visit(CharLiteralExpr n, CFGStatements arg) {
        return Optional.of(new IRChar(n.asChar(), n));
    }

    @Override
    public Optional<IRExpression> visit(ClassExpr n, CFGStatements arg) {
        return resolve(n.getType()).map(ty -> new IRTypeObject(ty, n));
    }

    @Override
    public Optional<IRExpression> visit(ConditionalExpr n, CFGStatements arg) {
        return accept(arg, n, n.getCondition(), n.getThenExpr(), n.getElseExpr(), (cond, thenExpr, elseExpr, ty) -> new IRConditional(cfg, ty, Set.of(n), cond, thenExpr, elseExpr));
    }

    @Override
    public Optional<IRExpression> visit(DoubleLiteralExpr n, CFGStatements arg) {
        return Optional.of(new IRDouble(n.asDouble(), n));
    }

    @Override
    public Optional<IRExpression> visit(EnclosedExpr n, CFGStatements arg) {
        return n.getInner().accept(this, arg);
    }

    @Override
    public Optional<IRExpression> visit(FieldAccessExpr n, CFGStatements arg) {
        return typeOf(n).flatMap(ty -> {
            try {
                var resolved = n.resolve();

                if (resolved instanceof ResolvedFieldDeclaration && ((ResolvedFieldDeclaration) resolved).isStatic()) {
                    var receiverTy = ((ResolvedFieldDeclaration) resolved).declaringType().getQualifiedName();
                    return Optional.of(arg.addStatement(new IRStaticFieldAccess(cfg, ty, Set.of(n), receiverTy, n.getNameAsString())).getTarget());
                }
                if (resolved instanceof ResolvedEnumConstantDeclaration) {
                    var constant = new IREnum(n, ty, resolved.getName());
                    return Optional.of(arg.addStatement(new IREnumAccess(cfg, ty, Set.of(n), constant)).getTarget());
                }

                return n.getScope().accept(this, arg).map(receiver -> arg.addStatement(new IRFieldAccess(cfg, ty, Set.of(n), receiver, n.getNameAsString())).getTarget());
            } catch (Throwable e) {
                return Optional.empty();
            }
        });
    }

    @Override
    public Optional<IRExpression> visit(InstanceOfExpr n, CFGStatements arg) {
        return n.getExpression().accept(this, arg).flatMap(src ->
            resolve(n.getType()).map(ty ->
                arg.addStatement(new IRInstanceOf(cfg, "boolean", Set.of(n), src, ty)).getTarget()
            )
        );
    }

    @Override
    public Optional<IRExpression> visit(IntegerLiteralExpr n, CFGStatements arg) {
        return Optional.of(new IRInteger(n.asInt(), n));
    }

    @Override
    public Optional<IRExpression> visit(LongLiteralExpr n, CFGStatements arg) {
        return Optional.of(new IRLong(n.asLong(), n));
    }

    @Override
    public Optional<IRExpression> visit(MethodCallExpr n, CFGStatements arg) {
        return typeOf(n).flatMap(ty -> resolveMethodCallExpr(n).flatMap(qualifiedSignature -> {
            var args = acceptList(arg, n.getArguments());

            if (n.getScope().isEmpty()) {
                return Optional.of(arg.addStatement(
                    new IRMethodInvocation(cfg, ty, Set.of(n), qualifiedSignature, new IRThis(n.resolve().declaringType().getQualifiedName(), n), args)).getTarget()
                );
            } else {
                return n.getScope().get().accept(this, arg).map(receiver -> arg.addStatement(new IRMethodInvocation(cfg, ty, Set.of(n), qualifiedSignature, receiver, args)).getTarget());
            }
        }));
    }

    @Override
    public Optional<IRExpression> visit(LambdaExpr n, CFGStatements arg) {
        unimplemented(n); // TODO: lambda expr
        return Optional.empty();
    }

    @Override
    public Optional<IRExpression> visit(MethodReferenceExpr n, CFGStatements arg) {
        unimplemented(n); // TODO: MethodReferenceExpr
        return Optional.empty();
    }

    @Override
    public Optional<IRExpression> visit(NameExpr n, CFGStatements arg) {
        var name = n.getName().asString();
        return typeOf(n).map(ty -> cfg.readVar(ty, name, arg));
    }

    @Override
    public Optional<IRExpression> visit(NullLiteralExpr n, CFGStatements arg) {
        return typeOf(n).map(ty -> new IRNull(ty, n));
    }

    @Override
    public Optional<IRExpression> visit(ObjectCreationExpr n, CFGStatements arg) {
        return resolveObjectCreationExpr(n).flatMap(qualifiedSignature -> typeOf(n).map(ty -> {
            var args = acceptList(arg, n.getArguments());
            return arg.addStatement(new IRMethodInvocation(cfg, ty, Set.of(n), qualifiedSignature, null, args)).getTarget();
        }));
    }

    @Override
    public Optional<IRExpression> visit(StringLiteralExpr n, CFGStatements arg) {
        return Optional.of(new IRString(n.asString(), n));
    }

    @Override
    public Optional<IRExpression> visit(SuperExpr n, CFGStatements arg) {
        return Optional.empty(); // TODO
    }

    @Override
    public Optional<IRExpression> visit(ThisExpr n, CFGStatements arg) {
        return typeOf(n).map(ty -> new IRThis(ty, n));
    }

    @Override
    public Optional<IRExpression> visit(UnaryExpr n, CFGStatements arg) {
        switch (n.getOperator()) {
            case PREFIX_INCREMENT:
            case POSTFIX_INCREMENT:
            case PREFIX_DECREMENT:
            case POSTFIX_DECREMENT: {
                String name;

                if (n.getExpression().isNameExpr()) {
                    name = n.getExpression().asNameExpr().getNameAsString();
                } else if (n.getExpression().isFieldAccessExpr() && n.asFieldAccessExpr().getScope().isThisExpr()) {
                    name = n.getExpression().asFieldAccessExpr().getNameAsString();
                } else {
                    unimplemented(n);
                    return Optional.empty();
                }

                return typeOf(n.getExpression()).map(ty -> {
                    var source = cfg.readVar(ty, name, arg);
                    var ope = (n.getOperator() == UnaryExpr.Operator.PREFIX_INCREMENT || n.getOperator() == UnaryExpr.Operator.POSTFIX_INCREMENT) ?
                        BinaryExpr.Operator.PLUS :
                        BinaryExpr.Operator.MINUS;

                    var target = arg.addStatement(new IRBinaryOperation(cfg, ty, Set.of(n), source, new IRInteger(1, n), ope)).getTarget();
                    cfg.writeVar(name, arg, target);
                    return n.getOperator().isPrefix() ? target : source;
                });
            }
            default: {
                return accept(arg, n, n.getExpression(), (source, ty) -> new IRUnaryOperation(cfg, ty, Set.of(n), source, n.getOperator()));
            }
        }
    }

    @Override
    public Optional<IRExpression> visit(VariableDeclarationExpr n, CFGStatements arg) {
        n.getVariables().forEach(v -> v.accept(this, arg));
        return Optional.empty();
    }

    @Override
    public Optional<IRExpression> visit(VariableDeclarator n, CFGStatements arg) {
        return resolve(n.getType()).flatMap(ty ->
            n.getInitializer().map(i -> i.accept(this, arg)).orElse(Optional.of(new IRNull(ty, n))).map(initValue -> {
                var name = n.getName().asString();
                if (initValue instanceof IRTemp) {
                    cfg.writeVar(name, arg, initValue);
                    return initValue;
                } else {
                    var target = arg.addStatement(new IRAssignment(cfg, ty, Set.of(n), initValue)).getTarget();
                    cfg.writeVar(name, arg, target);
                    return target;
                }
            })
        );
    }

    @Override
    public Optional<IRExpression> visit(TypeExpr n, CFGStatements arg) {
        return Optional.empty(); // TODO
    }
}
