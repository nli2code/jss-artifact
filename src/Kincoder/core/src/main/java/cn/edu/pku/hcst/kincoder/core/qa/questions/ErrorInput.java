package cn.edu.pku.hcst.kincoder.core.qa.questions;

import lombok.Value;

@Value
public class ErrorInput implements QuestionResult {
    private final String message;
}
