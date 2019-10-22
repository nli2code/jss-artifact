package cn.edu.pku.hcst.kincoder.kg.repository.impl;

import cn.edu.pku.hcst.kincoder.kg.entity.*;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.inject.Inject;
import org.jetbrains.annotations.Nullable;
import org.neo4j.ogm.session.Session;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PreloadRepository implements Repository {
    private final Map<String, TypeEntity> types;
    private final Map<String, MethodEntity> methods;
    private final Map<String, EnumEntity> enums;
    private final Collection<PatternEntity> patterns;

    @Inject
    public PreloadRepository(Session session) {
        this.types = session.loadAll(TypeEntity.class).stream().collect(Collectors.toMap(
            TypeEntity::getQualifiedName,
            Function.identity()
        ));
        this.methods = session.loadAll(MethodEntity.class).stream().collect(Collectors.toMap(
            MethodEntity::getQualifiedSignature,
            Function.identity()
        ));
        this.enums = session.loadAll(EnumEntity.class).stream().collect(Collectors.toMap(
            EnumEntity::getQualifiedName,
            Function.identity()
        ));
        session.loadAll(MethodJavadocEntity.class);
        session.loadAll(MethodParamJavadocEntity.class);
        this.patterns = session.loadAll(PatternEntity.class);
    }

    @Override
    public @Nullable TypeEntity getTypeEntity(String qualifiedName) {
        var enumEntity = enums.get(qualifiedName);
        if (enumEntity != null) return enumEntity;
        return types.get(qualifiedName);
    }

    @Override
    public @Nullable MethodEntity getMethodEntity(String qualifiedSignature) {
        return methods.get(qualifiedSignature);
    }

    @Override
    public @Nullable EnumEntity getEnumEntity(String qualifiedName) {
        return enums.get(qualifiedName);
    }

    @Override
    public Collection<PatternEntity> getAllPatterns() {
        return patterns;
    }
}
