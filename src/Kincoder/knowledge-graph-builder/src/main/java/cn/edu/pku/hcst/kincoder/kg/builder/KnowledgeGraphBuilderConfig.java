package cn.edu.pku.hcst.kincoder.kg.builder;

import lombok.Data;

import java.util.List;

@Data
public class KnowledgeGraphBuilderConfig {
    private String uri;
    private String username;
    private String password;

    private String jdkSrcCodeDir;
    private List<String> projectSrcCodeDirs;

    private boolean printUnsolvedSymbol = false;
}
