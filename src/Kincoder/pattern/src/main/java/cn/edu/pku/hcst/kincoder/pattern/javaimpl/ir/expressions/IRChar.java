package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.Set;

public class IRChar extends IRExpression {
    private final char value;

    public IRChar(char value, Node from) {
        super(Set.of(from));
        this.value = value;
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }
}
