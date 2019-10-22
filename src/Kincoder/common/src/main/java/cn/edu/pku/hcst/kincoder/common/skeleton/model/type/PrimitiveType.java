package cn.edu.pku.hcst.kincoder.common.skeleton.model.type;

public enum PrimitiveType implements Type {
    BOOLEAN("boolean"),
    BYTE("byte"),
    SHORT("short"),
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    CHAR("char")
    ;
    private final String name;

    PrimitiveType(String name) {
        this.name = name;
    }

    @Override
    public String describe() {
        return name;
    }
}
