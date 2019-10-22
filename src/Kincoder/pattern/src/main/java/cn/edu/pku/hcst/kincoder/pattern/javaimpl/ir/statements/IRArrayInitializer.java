package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.utils.IRUtil.UsesBuilder;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRArrayInitializer extends IRDefStatement {
    private final List<IRExpression> inits;

    public IRArrayInitializer(CFG cfg, String ty, Set<Node> from, List<IRExpression> inits) {
        super(cfg, ty, from);
        this.inits = inits;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return new UsesBuilder()
            .addAll(inits)
            .build();
    }
}

