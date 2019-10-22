package cn.edu.pku.hcst.kincoder.core.qa.questions;

import java.util.function.Function;

import static cn.edu.pku.hcst.kincoder.common.utils.ElementUtil.qualifiedName2Simple;

public enum MethodCategory {
    CREATE(type -> String.format("Create a new %s", qualifiedName2Simple(type))),
    LOAD(type -> String.format("Load a new %s", qualifiedName2Simple(type))),
    GET(type -> String.format("Get a new %s", qualifiedName2Simple(type))),
    UNKNOWN(type -> "Other");

    private final Function<String, String> questionGenerator;

    MethodCategory(Function<String, String> questionGenerator) {
        this.questionGenerator = questionGenerator;
    }
}
