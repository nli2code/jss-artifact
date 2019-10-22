package cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg;

import de.parsemis.parsers.LabelParser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode
public class DFGNode {
    public static class Parser implements LabelParser<DFGNode> {
        private final Pattern pattern = Pattern.compile("(#.*)(?:\\$$(.*))?");

        @Override
        public DFGNode parse(String s) {
            var matcher = pattern.matcher(s);
            var op = matcher.group(1);
            var info = matcher.group(2);
            return new DFGNode(Type.valueOf(op), info);
        }

        @Override
        public String serialize(DFGNode dfgNode) {
            return String.format("%s$%s", dfgNode.type, dfgNode.info);
        }
    }

    private final Type type;
    private final String info;

    public DFGNode(Type type, String info) {
        this.type = type;
        this.info = info;
    }

    public enum Type {
        OP("#OP"),
        DATA("#DATA"),
        ARG("#ARG"),
        TYPE("#TYPE"),
        METHOD_INVOCATION("#METHOD_INVOCATION"),
        REFERENCE("#REFERENCE"),
        BINARY("#BINARY"),
        UNARY("#UNARY"),
        FIELD_ACCESS("#FIELD_ACCESS"),
        ENUM_ACCESS("#ENUM_ACCESS"),
        ARRAY_CREATION("#ARRAY_CREATION"),
        INSTANCE_OF("#INSTANCE_OF"),
        EXTERN("#EXTERN")
        ;

        @Getter
        private final String name;

        Type(String name) {
            this.name = name;
        }
    }


}
