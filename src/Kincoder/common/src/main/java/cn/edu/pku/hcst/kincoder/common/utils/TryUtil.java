package cn.edu.pku.hcst.kincoder.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class TryUtil {
    public <T> Optional<T> optionalTry(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (Throwable e) {
            return Optional.empty();
        }
    }
}
