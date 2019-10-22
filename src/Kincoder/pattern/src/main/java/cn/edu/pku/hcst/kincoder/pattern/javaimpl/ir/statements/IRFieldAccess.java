package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class IRFieldAccess extends IRDefStatement {
    private final IRExpression receiver;
    @Getter
    private final String field;

    public IRFieldAccess(CFG cfg, String ty, Set<Node> from, IRExpression receiver, String field) {
        super(cfg, ty, from);
        this.receiver = receiver;
        this.field = field;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(receiver);
    }
}
