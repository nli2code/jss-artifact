package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.common.skeleton.HoleFactory;
import cn.edu.pku.hcst.kincoder.common.skeleton.NameFinder;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameOrHole;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.Stmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.common.utils.Tuple3;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.DFG2PatternExpressionVisitor.GenExprResult;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.GenericVisitorWithDefaults;
import com.github.javaparser.resolution.declarations.ResolvedParameterDeclaration;
import com.google.common.collect.Streams;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.*;
import static cn.edu.pku.hcst.kincoder.common.utils.CollectionUtil.concat;
import static cn.edu.pku.hcst.kincoder.common.utils.CollectionUtil.cons;

@Slf4j
public class DFG2PatternExpressionVisitor extends GenericVisitorWithDefaults<GenExprResult, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>>> {
    @Value
    static class GenExprResult {
        private final Expr<?> e;
        private final Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx;
        private final List<Stmt<?>> added;
    }

    class SimpleFindNameContext implements NameFinder {
        private ConcurrentMap<Type, AtomicInteger> numbering = new ConcurrentHashMap<>();

        @Override
        public int nextIdForType(Type ty) {
            var atomic = new AtomicInteger(1);
            var id = numbering.putIfAbsent(ty, atomic);
            if (id != null) atomic = id;
            return atomic.getAndIncrement();
        }
    }

    private final CodeUtil codeUtil;
    private final Set<Node> nodes;
    private final HoleFactory holeFactory;
    private final NameFinder nameFinder;

    public DFG2PatternExpressionVisitor(CodeUtil codeUtil, Set<Node> nodes, HoleFactory holeFactory) {
        this.codeUtil = codeUtil;
        this.nodes = nodes;
        this.holeFactory = holeFactory;
        this.nameFinder = new SimpleFindNameContext();
    }

    private GenExprResult holeResult(Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx) {
        return new GenExprResult(holeFactory.create(), ctx, List.of());
    }

    private Tuple3<List<Arg>, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>>, List<Stmt<?>>> processParams(List<Pair<String, Expression>> args, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx) {
        List<Arg> result = new ArrayList<>();
        List<Stmt<?>> added = new ArrayList<>();

        for (Pair<String, Expression> arg : args) {
            var ty = Type.fromString(arg.getLeft());
            var expr = arg.getRight();
            var r = expr.accept(this, ctx);
            if (r.getE() instanceof HoleExpr || r.getE() instanceof cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr) {
                ctx = r.getCtx();
                added.addAll(r.getAdded());
                result.add(new Arg(ty, r.getE()));
            } else {
                var n = name(ty, nameFinder);
                ctx = r.getCtx();
                added.addAll(r.getAdded());
                added.add(expr2stmt(v(ty, n, r.getE())));
                result.add(new Arg(ty, n));
            }
        }
        return Tuple3.of(result, ctx, added);
    }

    private GenExprResult rs0(Node n, Supplier<Expr<?>> gen, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx) {
        return nodes.contains(n) ? new GenExprResult(gen.get(), ctx, List.of()) : holeResult(ctx);
    }

    private GenExprResult rs1(Node n, Expression ast1, Function<Expr<?>, Expr<?>> gen, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx) {
        var result = ast1.accept(this, ctx);
        return nodes.contains(n)
            ? new GenExprResult(gen.apply(result.getE()), result.getCtx(), result.getAdded())
            : new GenExprResult(holeFactory.create(), result.getCtx(), result.getAdded());
    }

    private GenExprResult rs2(Node n, Expression ast1, Expression ast2, BiFunction<Expr<?>, Expr<?>, Expr<?>> gen, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> ctx) {
        var result1 = ast1.accept(this, ctx);
        var result2 = ast2.accept(this, result1.getCtx());
        return nodes.contains(n)
            ? new GenExprResult(gen.apply(result1.getE(), result2.getE()), result2.getCtx(), concat(result1.getAdded(), result2.getAdded()))
            : new GenExprResult(holeFactory.create(), result2.getCtx(), concat(result1.getAdded(), result2.getAdded()));
    }

    @Override
    public GenExprResult visit(AssignExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        if (n.getTarget().isNameExpr()) {
            return rs1(n, n.getValue(), e -> assign(str2name(n.getTarget().asNameExpr().getNameAsString()), e), arg);
        }

        return super.visit(n, arg);
    }

    @Override
    public GenExprResult visit(ArrayCreationExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return new GenExprResult(holeFactory.create(), arg, List.of());
    }

    @Override
    public GenExprResult visit(BinaryExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return rs2(n, n.getLeft(), n.getRight(), (left, right) -> binary(n.getOperator().asString(), left, right), arg);
    }

    @Override
    public GenExprResult visit(BooleanLiteralExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return rs0(n, () -> new BooleanLiteral(n.getValue()), arg);
    }

    @Override
    public GenExprResult visit(CastExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return n.getExpression().accept(this, arg);
    }

    @Override
    public GenExprResult visit(EnclosedExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return n.getInner().accept(this, arg);
    }

    @Override
    public GenExprResult visit(FieldAccessExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        if (n.getScope().isNameExpr() && n.getScope().asNameExpr().getNameAsString().matches("^[A-Z].*")) {
            NameOrHole<?> constant = nodes.contains(n.getName()) ? str2name(n.getNameAsString()) : holeFactory.create();
            try {
                var ty = (ReferenceType) codeUtil.resolvedTypeToType(n.getScope().asNameExpr().calculateResolvedType());
                return rs0(n, () -> enumerate(ty, constant), arg);
            } catch (Throwable e) {
                return holeResult(arg);
            }
        } else {
            NameOrHole<?> constant = nodes.contains(n.getName()) ? str2name(n.getNameAsString()) : holeFactory.create();
            var result = n.getScope().accept(this, arg);
            try {
                var ty = (ReferenceType) codeUtil.resolvedTypeToType(n.getScope().calculateResolvedType());
                return nodes.contains(n)
                    ? new GenExprResult(field(ty, result.getE(), constant), result.getCtx(), result.getAdded())
                    : new GenExprResult(holeFactory.create(), result.getCtx(), result.getAdded());
            } catch (Throwable e) {
                return new GenExprResult(holeFactory.create(), result.getCtx(), result.getAdded());
            }
        }
    }

    @Override
    public GenExprResult visit(IntegerLiteralExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return rs0(n, () -> new IntLiteral(n.asInt()), arg);
    }

    @Override
    public GenExprResult visit(LongLiteralExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return rs0(n, () -> new LongLiteral(n.asLong()), arg);
    }

    @Override
    public GenExprResult visit(MethodCallExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        try {
            if (n.getScope().isPresent() && !n.resolve().isStatic()) {
                var scope = n.getScope().get();
                var r1 = scope.accept(this, arg);
                var resolved = n.resolve();
                var paramTypes = IntStream.range(0, resolved.getNumberOfParams())
                    .mapToObj(resolved::getParam)
                    .map(ResolvedParameterDeclaration::describeType);
                var args = Streams.zip(paramTypes, n.getArguments().stream(), Pair::of)
                    .collect(Collectors.toList());
                var r = processParams(args, r1.getCtx());
                var qualifiedSignature = codeUtil.getMethodProto(n.resolve().getQualifiedSignature());
                return nodes.contains(n)
                    ? new GenExprResult(call(r1.getE(), ElementUtil.methodReceiverType(qualifiedSignature.get()).get(), n.getNameAsString(), r.getV1().toArray(new Arg[0])), r.getV2(), r.getV3())
                    : new GenExprResult(holeFactory.create(), r.getV2(), r.getV3());
            }

            var resolved = n.resolve();
            var paramTypes = IntStream.range(0, resolved.getNumberOfParams())
                .mapToObj(resolved::getParam)
                .map(ResolvedParameterDeclaration::describeType);
            var args = Streams.zip(paramTypes, n.getArguments().stream(), Pair::of)
                .collect(Collectors.toList());
            var r = processParams(args, arg);
            var qualifiedSignature = codeUtil.getMethodProto(n.resolve().getQualifiedSignature());
            return nodes.contains(n)
                ? new GenExprResult(call(ElementUtil.methodReceiverType(qualifiedSignature.get()).get(), n.getNameAsString(), r.getV1().toArray(new Arg[0])), r.getV2(), r.getV3())
                : new GenExprResult(holeFactory.create(), r.getV2(), r.getV3());
        } catch (Throwable e) {
            return holeResult(arg);
        }
    }

    @Override
    public GenExprResult visit(NameExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        var name = n.getNameAsString();
        return nodes.contains(n) ? new GenExprResult(str2name(name), arg, List.of()) : (
            arg.containsKey(name) ? new GenExprResult(arg.get(name), arg, List.of()) : new GenExprResult(holeFactory.create(), arg, List.of())
        );
    }

    @Override
    public GenExprResult visit(NullLiteralExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return rs0(n, NullLiteral::new, arg);
    }

    @Override
    public GenExprResult visit(ObjectCreationExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        try {
        var receiverType = (ReferenceType) codeUtil.resolvedTypeToType(n.getType().resolve());
        var resolved = n.resolve();
        var paramTypes = IntStream.range(0, resolved.getNumberOfParams())
            .mapToObj(resolved::getParam)
            .map(ResolvedParameterDeclaration::describeType);
        var args = Streams.zip(paramTypes, n.getArguments().stream(), Pair::of)
            .collect(Collectors.toList());
        var r = processParams(args, arg);
        var qualifiedSignature = codeUtil.getMethodProto(n.resolve().getQualifiedSignature());
        return nodes.contains(n)
            ? new GenExprResult(create(receiverType, r.getV1().stream().map(Arg::getValue).collect(Collectors.toList())), r.getV2(), r.getV3())
            : new GenExprResult(holeFactory.create(), r.getV2(), r.getV3());
        } catch (Throwable e) {
            return holeResult(arg);
        }
    }

    @Override
    public GenExprResult visit(StringLiteralExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return rs0(n, () -> new StringLiteral(n.asString()), arg);
    }

    @Override
    public GenExprResult visit(VariableDeclarationExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        return n.getVariable(0).accept(this, arg);
    }

    @Override
    public GenExprResult visit(VariableDeclarator n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        try {
            var ty = codeUtil.resolvedTypeToType(n.getType().resolve());
            if (n.getInitializer().isPresent()) {
                var result = n.getInitializer().get().accept(this, arg);
                if (result.getE() instanceof HoleExpr) {
                    return new GenExprResult(holeFactory.create(), arg, List.of());
                } else {
                    var newName = name(ty, nameFinder);
                    return new GenExprResult(v(ty, newName, result.getE()), cons(result.getCtx(), n.getNameAsString(), newName), result.getAdded());
                }
            } else {
                return new GenExprResult(holeFactory.create(), arg, List.of());
            }

        } catch (Throwable e) {
            return new GenExprResult(holeFactory.create(), arg, List.of());
        }
    }

    @Override
    public GenExprResult visit(UnaryExpr n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        var op = n.getOperator();

        return rs1(n, n.getExpression(), e -> unary(op.asString(), e, op.isPrefix()), arg);
    }

    @Override
    public GenExprResult defaultAction(Node n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        log.warn("Not implemented {}", n.getClass());
        return holeResult(arg);
    }

    @Override
    public GenExprResult defaultAction(NodeList n, Map<String, cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr<?>> arg) {
        log.warn("Not implemented {}", n.getClass());
        return holeResult(arg);
    }
}
