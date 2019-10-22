package cn.edu.pku.hcst.kincoder.core.qa;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpContext;

import java.util.Set;

public interface ContextFactory {
    Context create(String query, Set<Pair<String, Type>> variables, Skeleton skeleton, Set<String> extendedTypes, NlpContext nlpCtx);
}
