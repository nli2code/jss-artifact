package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;
import lombok.Getter;

import java.util.Set;

@Getter
public class IRThis extends IRExpression {
    private final String ty;

    public IRThis(String ty, Node from) {
        super(Set.of(from));
        this.ty = ty;
    }

    @Override
    public String toString() {
        return "this";
    }
}
