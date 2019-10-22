package cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.IRPhi;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public abstract class CFGBlock {
    protected final CFG cfg;
    private final Map<String, IRExpression> defs = new HashMap<>();
    private final int id;
    @Getter
    @Setter
    private boolean sealed = false;

    protected final List<IRPhi> phis = new ArrayList<>();
    private final Map<String, IRPhi> incompletePhis = new HashMap<>();

    private final List<CFGBlock> preds = new ArrayList<>();

    protected CFGBlock(CFG cfg) {
        this.cfg = cfg;
        this.id = cfg.getBlocks().size();
        cfg.addCfgBlock(this);
    }

    public abstract void setNext(CFGBlock next);

    public abstract List<? extends IRStatement> statements();

    public void seal() {
        incompletePhis.forEach(cfg::addPhiOperands);
        sealed = true;
    }

    public void updateDef(String name, IRExpression expr) {
        defs.put(name, expr);
    }

    public Map<String, IRExpression> getDefs() {
        return Collections.unmodifiableMap(defs);
    }

    public void updateIncompletePhi(String name, IRPhi phi) {
        incompletePhis.put(name, phi);
    }

    public List<CFGBlock> getPreds() {
        return Collections.unmodifiableList(preds);
    }

    public void addPhi(IRPhi phi) {
        phis.add(phi);
    }

    public void removePhi(IRPhi phi) {
        phis.remove(phi);
    }

    protected void addPred(CFGBlock pred) {
        preds.add(pred);
    }
}
