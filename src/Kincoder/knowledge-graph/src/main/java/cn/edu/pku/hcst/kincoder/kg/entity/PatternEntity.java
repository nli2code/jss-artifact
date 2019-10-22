package cn.edu.pku.hcst.kincoder.kg.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@NodeEntity
public class PatternEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String skeleton;

    @Relationship(type = "HAS_TYPE")
    private Set<TypeEntity> hasTypes = new HashSet<>();

    @Relationship(type = "HAS_METHOD")
    private Set<MethodEntity> hasMethods = new HashSet<>();

    public static PatternEntity create(String skeleton) {
        return new PatternEntity(skeleton);
    }

    protected PatternEntity() {
    }

    protected PatternEntity(String skeleton) {
        this.skeleton = skeleton;
    }

    public void addHasType(TypeEntity typeEntity) {
        hasTypes.add(typeEntity);
    }

    public void addHasTypes(Collection<TypeEntity> typeEntitys) {
        hasTypes.addAll(typeEntitys);
    }

    public void addHasMethod(MethodEntity methodEntity) {
        hasMethods.add(methodEntity);
    }

    public void addHasMethods(Collection<MethodEntity> methodEntitys) {
        hasMethods.addAll(methodEntitys);
    }

    public String getSkeleton() {
        return skeleton;
    }

    public Set<TypeEntity> getHasTypes() {
        return Collections.unmodifiableSet(hasTypes);
    }

    public Set<MethodEntity> getHasMethods() {
        return Collections.unmodifiableSet(hasMethods);
    }

}
