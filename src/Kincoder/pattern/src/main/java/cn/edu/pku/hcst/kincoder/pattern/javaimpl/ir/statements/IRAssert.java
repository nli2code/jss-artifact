package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import cn.edu.pku.hcst.kincoder.pattern.utils.IRUtil.UsesBuilder;
import com.github.javaparser.ast.Node;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class IRAssert extends IRStatement {
    private final IRExpression check;
    @Nullable
    private final IRExpression message;

    public IRAssert(Set<Node> from, IRExpression check, @Nullable IRExpression message) {
        super(from);
        this.check = check;
        this.message = message;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return new UsesBuilder()
            .add(check)
            .add(message)
            .build();
    }
}
