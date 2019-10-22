package cn.edu.pku.hcst.kincoder.core.api;

import lombok.Value;

@Value
public class ErrorResponse implements QAResponse, UndoResponse {
    private String message;
}
