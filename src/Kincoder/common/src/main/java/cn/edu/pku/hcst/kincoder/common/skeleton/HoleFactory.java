package cn.edu.pku.hcst.kincoder.common.skeleton;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HoleFactory {
    private int nextId = 0;

    public HoleExpr create() {
        return new HoleExpr(nextId++);
    }
}
