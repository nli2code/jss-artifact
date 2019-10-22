package cn.edu.pku.hcst.kincoder.common.skeleton.model.type;

import java.util.Arrays;

public interface Type {
    String describe();

    static Type fromString(String type) {
        var primitiveOpt = Arrays.stream(PrimitiveType.values()).filter(t -> t.describe().equals(type)).findFirst();
        if (primitiveOpt.isPresent()) return primitiveOpt.get();
        if (type.endsWith("[]")) {
            return new ArrayType(fromString(type.substring(0, type.length() - 2)));
        }
        return new ReferenceType(type);
    }
}
