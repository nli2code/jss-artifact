package cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFGSwitch extends CFGBlock {
    public interface SwitchLabel {

    }

    @Value
    public static class ExpressionLabel implements SwitchLabel {
        private final IRExpression expr;
    }

    public static class DefaultLabel implements SwitchLabel {

    }

    private final IRExpression selector;
    private final Map<SwitchLabel, CFGBlock> blocks = new HashMap<>();

    public CFGSwitch(CFG cfg, IRExpression selector) {
        super(cfg);
        this.selector = selector;
    }

    public void update(SwitchLabel label, CFGBlock block) {
        blocks.put(label, block);
    }

    @Override
    public void setNext(CFGBlock next) {
        throw new UnsupportedOperationException("Cannot set next block of switch");
    }

    @Override
    public List<? extends IRStatement> statements() {
        return phis;
    }
}
