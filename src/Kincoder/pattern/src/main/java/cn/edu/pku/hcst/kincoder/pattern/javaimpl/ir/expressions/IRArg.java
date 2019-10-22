package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import lombok.Getter;

import java.util.Set;

@Getter
public class IRArg extends IRExpression {
    private final String name;
    private final String ty;

    public IRArg(String name, String ty) {
        super(Set.of());
        this.name = name;
        this.ty = ty;
    }

    @Override
    public String toString() {
        return name;
    }
}
