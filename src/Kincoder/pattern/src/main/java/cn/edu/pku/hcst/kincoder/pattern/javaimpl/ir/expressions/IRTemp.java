package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class IRTemp extends IRExpression {
    private final int id;
    private final IRStatement def;
    private final String ty;
    @Getter @Setter
    private @Nullable IRExpression replaced;

    public IRTemp(int id, IRStatement def, String ty) {
        super(Set.of());
        this.id = id;
        this.def = def;
        this.ty = ty;
    }

    @Override
    public Optional<IRStatement> def() {
        return replaced == null ? Optional.of(this.def) : replaced.def();
    }

    public int getId() {
        if (replaced == null) return this.id;
        assert replaced instanceof IRTemp;
        return ((IRTemp) replaced).getId();
    }

    @Override
    public String toString() {
        return replaced == null ? "#" + this.id : replaced.toString();
    }
}
