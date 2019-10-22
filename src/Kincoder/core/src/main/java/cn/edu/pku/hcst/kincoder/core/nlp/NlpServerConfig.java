package cn.edu.pku.hcst.kincoder.core.nlp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NlpServerConfig {
    @Builder.Default private final String host = "http://162.105.88.99";
    @Builder.Default private final int port = 9000;
}
