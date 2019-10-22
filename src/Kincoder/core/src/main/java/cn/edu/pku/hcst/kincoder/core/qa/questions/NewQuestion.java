package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.core.qa.Question;
import lombok.Value;

@Value
public class NewQuestion implements ChoiceResult {
    private final Question question;
}
