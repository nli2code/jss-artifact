package cn.edu.pku.hcst.kincoder.common.skeleton.model.type;

import lombok.Value;

@Value
public class ArrayType implements Type {
    private final Type componentType;

    @Override
    public String describe() {
        return String.format("%s[]", componentType.describe());
    }
}
