package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRArrayAccess extends IRDefStatement {
    private final IRExpression array;
    private final IRExpression index;

    public IRArrayAccess(CFG cfg, String ty, Set<Node> from, IRExpression array, IRExpression index) {
        super(cfg, ty, from);
        this.array = array;
        this.index = index;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(array, index);
    }
}
