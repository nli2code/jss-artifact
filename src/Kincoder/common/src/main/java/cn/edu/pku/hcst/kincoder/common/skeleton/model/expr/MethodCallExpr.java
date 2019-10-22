package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Wither
public class MethodCallExpr implements Expr<MethodCallExpr>, Callable {
    private final Pair<ReferenceType, Expr<?>> receiver;
    private final String name;
    private final List<Arg> args;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> MethodCallExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    public String getQualifiedSignature() {
        var argStr = args.stream().map(Arg::getType).map(Type::describe).collect(Collectors.joining(", "));
        return String.format(
            "%s.%s(%s)",
            receiver.getLeft().describe(),
            name,
            argStr
        );
    }

    @Override
    public int findArgIndex(Arg arg) {
        return args.indexOf(arg);
    }

}
