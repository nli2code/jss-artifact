package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.MethodCallExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.NodeCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.TypeCollector;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphSessionFactory;
import cn.edu.pku.hcst.kincoder.kg.entity.PatternEntity;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PatternSaver {
    private final ObjectMapper objectMapper;
    private final CodeUtil codeUtil;
    private final Repository repository;
    private final Session session;

    @Inject
    public PatternSaver(KnowledgeGraphSessionFactory sessionFactory, ObjectMapper objectMapper, CodeUtil codeUtil, Repository repository) {
        this.session = sessionFactory.get();
        this.objectMapper = objectMapper;
        this.codeUtil = codeUtil;
        this.repository = repository;
    }

    public void saveSkeleton(Collection<Skeleton> skeletons) {
        try (var tx = session.beginTransaction()) {
            session.save(
                skeletons.stream()
                    .map(this::toEntity)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()),
                1
            );
            tx.commit();
        }
    }

    private PatternEntity toEntity(Skeleton skeleton) {
        try {
            var entity = PatternEntity.create(objectMapper.writeValueAsString(skeleton));

            var types = TypeCollector.INSTANCE.collect(skeleton.getStmts())
                .stream()
                .map(codeUtil::coreType)
                .map(Type::describe)
                .map(repository::getTypeEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            entity.addHasTypes(types);

            var methods = NodeCollector.INSTANCE.collect(skeleton.getStmts(), MethodCallExpr.class)
                .stream()
                .map(MethodCallExpr::getQualifiedSignature)
                .map(repository::getMethodEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            entity.addHasMethods(methods);

            return entity;
        } catch (JsonProcessingException e) {
            log.error("Error!", e);
        }

        return null;
    }
}
