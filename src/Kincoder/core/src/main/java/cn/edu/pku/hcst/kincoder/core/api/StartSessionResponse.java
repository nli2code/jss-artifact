package cn.edu.pku.hcst.kincoder.core.api;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import lombok.Value;

import java.util.List;

@Value
public class StartSessionResponse {
    private long id;
    private List<Pair<Skeleton, Double>> skeletons;
}
