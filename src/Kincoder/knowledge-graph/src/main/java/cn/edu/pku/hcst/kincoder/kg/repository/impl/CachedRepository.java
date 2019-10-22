package cn.edu.pku.hcst.kincoder.kg.repository.impl;

import cn.edu.pku.hcst.kincoder.kg.entity.EnumEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.PatternEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.inject.Inject;
import org.jetbrains.annotations.Nullable;
import org.neo4j.ogm.session.Session;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CachedRepository implements Repository {
    private final Session session;
    private final ConcurrentMap<String, TypeEntity> types = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, MethodEntity> methods = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, EnumEntity> enums = new ConcurrentHashMap<>();
    private Collection<PatternEntity> patterns;

    @Inject
    public CachedRepository(Session session) {
        this.session = session;
    }

    @Override
    public @Nullable TypeEntity getTypeEntity(String qualifiedName) {
        if (types.containsKey(qualifiedName)) {
            return types.get(qualifiedName);
        }
        var enumEntity = session.load(EnumEntity.class, qualifiedName);
        if (enumEntity != null) {
            types.putIfAbsent(qualifiedName, enumEntity);
            return enumEntity;
        }
        var typeEntity = session.load(TypeEntity.class, qualifiedName);
        types.putIfAbsent(qualifiedName, typeEntity);
        return typeEntity;
    }

    @Override
    public @Nullable MethodEntity getMethodEntity(String qualifiedSignature) {
        if (methods.containsKey(qualifiedSignature)) {
            return methods.get(qualifiedSignature);
        }
        var methodEntity = session.load(MethodEntity.class, qualifiedSignature);
        methods.putIfAbsent(qualifiedSignature, methodEntity);
        return methodEntity;
    }

    @Override
    public @Nullable EnumEntity getEnumEntity(String qualifiedName) {
        if (enums.containsKey(qualifiedName)) {
            return enums.get(qualifiedName);
        }
        var enumEntity = session.load(EnumEntity.class, qualifiedName);
        enums.putIfAbsent(qualifiedName, enumEntity);
        return enumEntity;
    }

    @Override
    public Collection<PatternEntity> getAllPatterns() {
        if (patterns == null) {
            patterns = session.loadAll(PatternEntity.class);
        }
        return patterns;
    }
}
