package cn.edu.pku.hcst.kincoder.pattern.api.filter;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFG;

public class DFGSizeFilter implements ListFilter<DFG> {
    private final int size;

    public DFGSizeFilter(int size) {
        this.size = size;
    }

    @Override
    public boolean valid(DFG dfg) {
        return dfg.getNodeCount() <= size;
    }
}
