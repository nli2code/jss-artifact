package cn.edu.pku.hcst.kincoder.pattern.api;

import java.util.List;

public interface PatternConfig {
    List<String> getSourceCodeDirs();
    boolean isDebug();
    List<String> getDfgNodeFilters();
    default Integer getNodeSizeLimit() {
        return null;
    }
}
