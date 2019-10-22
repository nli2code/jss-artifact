package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.Set;

public class IRTypeObject extends IRExpression {
    private final String ty;

    public IRTypeObject(String ty, Node from) {
        super(Set.of(from));
        this.ty = ty;
    }

    @Override
    public String toString() {
        return ty + ".class";
    }
}
