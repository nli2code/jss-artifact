package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir;

import com.github.javaparser.ast.Node;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public abstract class IRStatement {
    @Getter
    private final Set<Node> from;

    abstract public List<IRExpression> uses();

    protected void init() {
        uses().forEach(use -> use.uses.add(this));
    }

    protected IRStatement(Set<Node> from) {
        this.from = from;
    }
}
