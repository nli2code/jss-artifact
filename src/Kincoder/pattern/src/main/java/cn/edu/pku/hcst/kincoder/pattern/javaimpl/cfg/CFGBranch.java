package cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CFGBranch extends CFGBlock {
    @Nullable
    private final IRExpression condition;
    private final CFGBlock thenBlock;
    private final CFGBlock elseBlock;

    public CFGBranch(CFG cfg, @Nullable IRExpression condition, CFGBlock thenBlock, CFGBlock elseBlock) {
        super(cfg);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;

        thenBlock.addPred(this);
        elseBlock.addPred(this);
    }

    @Override
    public void setNext(CFGBlock next) {
        throw new UnsupportedOperationException("Cannot set next block of branch");
    }

    @Override
    public List<? extends IRStatement> statements() {
        return phis;
    }
}
