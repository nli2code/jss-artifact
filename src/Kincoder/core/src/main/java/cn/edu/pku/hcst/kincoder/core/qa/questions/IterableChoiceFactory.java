package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.IterableChoice;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IterableChoiceFactory {
    IterableChoice create(List<TypeEntity> path, @Nullable String recommendVar, boolean recommend);
}
