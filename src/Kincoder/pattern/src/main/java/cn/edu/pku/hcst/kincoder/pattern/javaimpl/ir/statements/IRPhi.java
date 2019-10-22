package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFGBlock;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class IRPhi extends IRDefStatement {
    @Getter
    private final CFGBlock block;
    private final List<IRExpression> operands = new ArrayList<>();

    public IRPhi(CFG cfg, String ty, CFGBlock block) {
        super(cfg, ty, Set.of());
        this.block = block;
        block.addPhi(this);
        init();
    }

    public void appendOperand(IRExpression ope) {
        operands.add(ope);
        ope.addUse(this);
    }

    public List<IRExpression> getOperands() {
        return Collections.unmodifiableList(operands);
    }

    @Override
    public List<IRExpression> uses() {
        return Collections.unmodifiableList(operands);
    }

    public void replaceBy(IRExpression variable) {
        block.removePhi(this);
        operands.forEach(ope -> ope.removeUse(this));
        getTarget().setReplaced(variable);
    }
}

