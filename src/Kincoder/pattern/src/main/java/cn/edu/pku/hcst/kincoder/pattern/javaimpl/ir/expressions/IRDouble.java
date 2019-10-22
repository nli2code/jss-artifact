package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.Set;

public class IRDouble extends IRExpression {
    private final double value;

    public IRDouble(double value, Node from) {
        super(Set.of(from));
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
