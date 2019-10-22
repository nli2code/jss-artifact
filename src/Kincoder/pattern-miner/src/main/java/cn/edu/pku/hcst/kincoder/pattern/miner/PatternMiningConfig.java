package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import lombok.Data;

import java.util.List;

@Data
public class PatternMiningConfig implements PatternConfig {
    public String uri;
    public String username;
    public String password;

    public String clientCodeDir;

    public Integer nodeSizeLimit = null;
    public List<String> dfgNodeFilters = List.of("org.apache.poi.ss.usermodel.Cell.setHyperlink(org.apache.poi.ss.usermodel.Hyperlink)");
    public List<String> sourceCodeDirs;

    public boolean debug = false;

}
