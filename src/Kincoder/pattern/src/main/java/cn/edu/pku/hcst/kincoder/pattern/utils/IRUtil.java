package cn.edu.pku.hcst.kincoder.pattern.utils;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IRUtil {
    public static class UsesBuilder {
        private final ImmutableList.Builder<IRExpression> builder = ImmutableList.builder();

        public UsesBuilder addAll(Iterable<IRExpression> iterable) {
            builder.addAll(iterable);
            return this;
        }

        public UsesBuilder add(@Nullable IRExpression value) {
            if (value != null) {
                builder.add(value);
            }
            return this;
        }

        public List<IRExpression> build() {
            return builder.build();
        }
    }
}
