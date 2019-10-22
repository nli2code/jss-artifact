package cn.edu.pku.hcst.kincoder.kg.repository;

import cn.edu.pku.hcst.kincoder.kg.entity.EnumEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.PatternEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Repository {
    @Nullable
    TypeEntity getTypeEntity(String qualifiedName);

    @Nullable
    MethodEntity getMethodEntity(String qualifiedSignature);

    @Nullable
    EnumEntity getEnumEntity(String qualifiedName);

    Collection<PatternEntity> getAllPatterns();
}
