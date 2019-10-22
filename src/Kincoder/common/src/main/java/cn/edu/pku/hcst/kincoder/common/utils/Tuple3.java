package cn.edu.pku.hcst.kincoder.common.utils;

public class Tuple3<V1, V2, V3> {
    private final V1 v1;
    private final V2 v2;
    private final V3 v3;

    private Tuple3(V1 v1, V2 v2, V3 v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public static <V1, V2, V3> Tuple3<V1, V2, V3> of(V1 v1, V2 v2, V3 v3) {
        return new Tuple3<V1, V2, V3>(v1, v2, v3);
    }

    public V1 getV1() {
        return this.v1;
    }

    public V2 getV2() {
        return this.v2;
    }

    public V3 getV3() {
        return this.v3;
    }
}
