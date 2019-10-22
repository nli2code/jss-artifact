package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir;

import com.github.javaparser.ast.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class IRExpression {
    private final Set<Node> from;
    protected final Set<IRStatement> uses = new HashSet<>();

    protected IRExpression(Set<Node> from) {
        this.from = from;
    }

    public Set<Node> getFrom() {
        return Collections.unmodifiableSet(from);
    }

    public Optional<IRStatement> def() {
        return Optional.empty();
    }

    public void addUse(IRStatement use) {
        uses.add(use);
    }

    public void removeUse(IRStatement use) {
        uses.remove(use);
    }

    public Set<IRStatement> getUses() {
        return Collections.unmodifiableSet(uses);
    }
}


