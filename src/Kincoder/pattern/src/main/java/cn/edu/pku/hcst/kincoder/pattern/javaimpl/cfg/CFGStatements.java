package cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CFGStatements extends CFGBlock {
    private final List<IRStatement> statements = new ArrayList<>();
    @Nullable
    private CFGBlock next;

    public CFGStatements(CFG cfg) {
        super(cfg);
    }

    @Override
    public void setNext(CFGBlock next) {
        this.next = next;
        next.addPred(this);
    }

    @Override
    public List<? extends IRStatement> statements() {
        return ImmutableList.<IRStatement>builder()
            .addAll(phis)
            .addAll(statements)
            .build();
    }

    public <S extends IRStatement> S addStatement(S statement) {
        statements.add(statement);
        return statement;
    }
}
