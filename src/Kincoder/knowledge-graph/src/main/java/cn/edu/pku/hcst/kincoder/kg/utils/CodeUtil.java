package cn.edu.pku.hcst.kincoder.kg.utils;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ArrayType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.PrimitiveType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.FList;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.resolution.types.ResolvedTypeVariable;
import com.google.inject.Inject;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CodeUtil {
    private final Repository repository;

    private final Map<Type, Set<MethodEntity>> producersCache = new HashMap<>();

    @Inject
    public CodeUtil(Repository repository) {
        this.repository = repository;
    }

    public Set<FList<TypeEntity>> getIterablePaths(ReferenceType referenceType) {
        var typeEntity = repository.getTypeEntity(referenceType.describe());
        if (typeEntity == null) return Set.of();

        Set<FList<TypeEntity>> result = new HashSet<>();
        var path = FList.of(typeEntity);
        Queue<FList<TypeEntity>> q = new LinkedList<>();
        q.add(path);
        while (!q.isEmpty()) {
            var front = q.poll();
            result.add(front);
            q.addAll(
                front.getHead().getIterables().stream()
                    .map(i -> FList.cons(i, front))
                    .collect(Collectors.toList())
            );
        }
        return result;
    }

    private boolean isAccessible(MethodEntity entity) {
        // TODO: 考虑继承和protected
        return entity.getAccessSpecifier() == AccessSpecifier.PUBLIC ||
            entity.getDeclareType().isInterface() && entity.getAccessSpecifier() == AccessSpecifier.PACKAGE_PRIVATE;
    }

    private boolean isAssignableRec(Type source, Type target) {
        if (source instanceof PrimitiveType && target instanceof PrimitiveType) {
            return source == target;
        } else if (source instanceof ArrayType && target instanceof ArrayType) {
            return isAssignableRec(((ArrayType) source).getComponentType(), ((ArrayType) target).getComponentType());
        } else if (source instanceof ReferenceType && target instanceof ReferenceType) {
            var sourceEntity = repository.getTypeEntity(((ReferenceType) source).getQualifiedName());
            var targetEntity = repository.getTypeEntity(((ReferenceType) target).getQualifiedName());
            if (sourceEntity == null || targetEntity == null) return false;
            Set<TypeEntity> visited = new HashSet<>();
            Queue<TypeEntity> queue = new LinkedList<>();
            queue.add(sourceEntity);
            while (!queue.isEmpty()) {
                var front = queue.poll();
                if (visited.contains(front)) continue;
                if (front == targetEntity) return true;
                visited.add(front);
                queue.addAll(front.getExtendedTypes());
            }
        }
        return false;
    }

    public boolean isAssignable(Type source, Type target) {
        return isAssignableRec(source, target);
    }

    private Set<MethodEntity> producers(TypeEntity entity, boolean multiple) {
        if (entity == null) return Set.of();

        Queue<TypeEntity> queue = new LinkedList<>();
        Map<MethodEntity, Set<MethodEntity>> deleteMap = new HashMap<>();
        Set<MethodEntity> visited = new HashSet<>();
        Set<MethodEntity> result = new HashSet<>();
        Set<TypeEntity> processedType = new HashSet<>();
        queue.add(entity);
        while (!queue.isEmpty()) {
            var front = queue.poll();

            if (processedType.contains(front)) continue;

            var frontEntity = repository.getTypeEntity(front.getQualifiedName());
            if (frontEntity == null) continue;

            (multiple ? frontEntity.getMultipleProducers() : frontEntity.getProducers()).stream()
                .filter(this::isAccessible)
                .forEach(method -> {
                    var extendedMethods = method.getExtendedMethods().stream().filter(this::isAccessible).collect(Collectors.toList());
                    if (extendedMethods.stream().anyMatch(visited::contains)) {
                        visited.add(method);
                    } else {
                        extendedMethods.forEach(extendedMethod -> {
                            deleteMap.putIfAbsent(extendedMethod, new HashSet<>());
                            var deletes = deleteMap.get(extendedMethod);
                            deletes.add(method);
                        });
                        deleteMap.remove(method);
                        visited.add(method);
                        result.add(method);
                        result.removeAll(deleteMap.getOrDefault(method, Set.of()));
                    }
                });

            queue.addAll(front.getExtendedTypes());
            processedType.add(frontEntity);
        }

        return result;
    }

    public Set<MethodEntity> producers(Type type) {
        var result = producersCache.get(type);
        if (result != null) return result;

        if (type instanceof ReferenceType) {
            var typeEntity = repository.getTypeEntity(((ReferenceType) type).getQualifiedName());
            result = producers(typeEntity, false).stream().filter(Predicate.not(MethodEntity::isDeprecated)).collect(Collectors.toSet());
            producersCache.put(type, result);
            return result;
        } else if (type instanceof ArrayType && ((ArrayType) type).getComponentType() instanceof ReferenceType) {
            var typeEntity = repository.getTypeEntity(((ReferenceType) ((ArrayType) type).getComponentType()).getQualifiedName());
            result =  producers(typeEntity, true).stream().filter(Predicate.not(MethodEntity::isDeprecated)).collect(Collectors.toSet());
            producersCache.put(type, result);
            return result;
        }

        throw new UnsupportedOperationException(String.format("Producers not supported for type %s", type.describe()));
    }

    public Set<String> staticFields(ReferenceType receiverType, Type targetType){
        var typeEntity = repository.getTypeEntity(receiverType.describe());
        if (typeEntity == null) {
            return Set.of();
        }
        return typeEntity.getStaticFields().getFieldsInfo().getOrDefault(targetType.describe(), Set.of());
    }

    private String getMethodProtoRec(MethodEntity method) {
        return method.getExtendedMethods().stream().findAny().map(this::getMethodProtoRec).orElse(method.getQualifiedSignature());
    }

    public Optional<String> getMethodProto(String method) {
        var entity = repository.getMethodEntity(method);
        if (entity == null) return Optional.empty();
        return Optional.of(getMethodProtoRec(entity));
    }

    private void getAllParentTypes(TypeEntity typeEntity, Set<String> result) {
        result.add(typeEntity.getQualifiedName());
        typeEntity.getExtendedTypes().forEach(t -> getAllParentTypes(t, result));
    }

    public Set<String>  getAllParentTypes(String qualifiedName){
        var entity = repository.getTypeEntity(qualifiedName);
        if (entity == null) return Set.of(qualifiedName);
        Set<String> result = new HashSet<>();
        getAllParentTypes(entity, result);
        return result;
    }

    public Type coreType(Type type) {
        if (type instanceof ReferenceType || type instanceof PrimitiveType) {
            return type;
        }
        return coreType(((ArrayType) type).getComponentType());
    }

    public Type resolvedTypeToType(ResolvedType resolvedType) {
        if (resolvedType instanceof ResolvedTypeVariable) {
            return Type.fromString("java.lang.Object");
        }
        return Type.fromString(resolvedType.describe());
    }
}
