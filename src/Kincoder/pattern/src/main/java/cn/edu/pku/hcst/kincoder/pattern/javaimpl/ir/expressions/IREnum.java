package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;
import lombok.Getter;

import java.util.Set;

@Getter
public class IREnum extends IRExpression {
    private final String ty;
    private final String constant;

    public IREnum(Node from, String ty, String constant) {
        super(Set.of(from));
        this.ty = ty;
        this.constant = constant;
    }

    @Override
    public String toString() {
        return constant;
    }
}
