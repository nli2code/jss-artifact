package cn.edu.pku.hcst.kincoder.pattern.api.filter;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode;

public class DFGNodeFilter implements ListFilter<DFG> {
    private final DFGNode dfgNode;

    public DFGNodeFilter(DFGNode dfgNode) {
        this.dfgNode = dfgNode;
    }

    @Override
    public boolean valid(DFG dfg) {
        var nodeIte = dfg.nodeIterator();
        while (nodeIte.hasNext()) {
            var node = nodeIte.next();
            if (node.getLabel().equals(dfgNode)) return true;
        }
        return false ;
    }
}
