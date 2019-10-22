package cn.edu.pku.hcst.kincoder.kg.builder;

import cn.edu.pku.hcst.kincoder.kg.entity.EnumEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodJavadocEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.*;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class EntityManager {
    private static Logger logger = LoggerFactory.getLogger(EntityManager.class);

    private final Map<String, MethodEntity> methodMapping = new HashMap<>();
    private final Map<String, TypeEntity> typeMapping = new HashMap<>();
    private final List<MethodJavadocEntity> javadocs = new ArrayList<>();

    private final KnowledgeGraphBuilderConfig config;
    private final JavaParserTypeSolver jdkSolver;

    @Inject
    public EntityManager(KnowledgeGraphBuilderConfig config) {
        this.jdkSolver = new JavaParserTypeSolver(config.getJdkSrcCodeDir());
        this.config = config;
    }

    private MethodJavadocEntity createJavadocEntity(Javadoc javadoc) {
        var javadocEntity = new MethodJavadocEntity(javadoc);
        javadocs.add(javadocEntity);
        return javadocEntity;
    }

    public TypeEntity createTypeEntity(ResolvedReferenceTypeDeclaration resolved) {
        TypeEntity typeEntity;
        if (resolved instanceof JavaParserClassDeclaration) {
            typeEntity = TypeEntity.fromDeclaration((JavaParserClassDeclaration) resolved);
        } else if (resolved instanceof JavaParserInterfaceDeclaration) {
            typeEntity = TypeEntity.fromDeclaration((JavaParserInterfaceDeclaration) resolved);
        } else if (resolved instanceof JavaParserEnumDeclaration) {
            typeEntity = EnumEntity.fromDeclaration((JavaParserEnumDeclaration) resolved);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported %s", resolved.getClass().getCanonicalName()));
        }

        typeMapping.put(typeEntity.getQualifiedName(), typeEntity);

        resolved.getDeclaredMethods().forEach(m -> {
            try {
                var astNode = ((JavaParserMethodDeclaration) m).getWrappedNode();
                var isDeprecated = astNode.getAnnotationByClass(Deprecated.class).isPresent();
                var javadoc = astNode.getJavadoc().map(this::createJavadocEntity);
                var methodEntity = new MethodEntity(m, isDeprecated, typeEntity, javadoc.orElse(null));
                methodMapping.put(methodEntity.getQualifiedSignature(), methodEntity);
            } catch (UnsolvedSymbolException e) {
                if (config.isPrintUnsolvedSymbol()) {
                    log.warn("Unsolved Symbol", e);
                }
            } catch (UnsupportedOperationException e) {
                logger.warn("Unsupported Operation", e);
            } catch (Throwable e) {
                logger.error("Unknown Error", e);
            }
        });

        resolved.getConstructors().forEach(m -> {
            try {
                MethodEntity methodEntity;
                if (m instanceof JavaParserConstructorDeclaration) {
                    var astNode = ((JavaParserConstructorDeclaration) m).getWrappedNode();
                    var isDeprecated = astNode.getAnnotationByClass(Deprecated.class).isPresent();
                    var javadoc = astNode.getJavadoc().map(this::createJavadocEntity);
                    methodEntity = new MethodEntity(m, isDeprecated, typeEntity, javadoc.orElse(null));
                } else if (m instanceof DefaultConstructorDeclaration) {
                    methodEntity = new MethodEntity(m, false, typeEntity, null);
                } else {
                    throw new UnsupportedOperationException(String.format("Not supported %s", m.getClass().getCanonicalName()));
                }
                methodMapping.put(methodEntity.getQualifiedSignature(), methodEntity);

            } catch (UnsolvedSymbolException e) {
                if (config.isPrintUnsolvedSymbol()) {
                    log.warn("Unsolved Symbol", e);
                }
            } catch (UnsupportedOperationException e) {
                logger.warn("Unsupported Operation", e);
            } catch (Throwable e) {
                logger.error("Unknown Error", e);
            }
        });

        return typeEntity;
    }

    public TypeEntity getTypeEntityOrCreate(String qualifiedName) {
        var typeEntity = typeMapping.get(qualifiedName);
        if (typeEntity == null) {
            typeEntity = createTypeEntity(jdkSolver.solveType(qualifiedName));
            typeMapping.put(qualifiedName, typeEntity);
        }
        return typeEntity;
    }

    public MethodEntity getMethodEntity(String qualifiedSignature) {
        return methodMapping.get(qualifiedSignature);
    }

    public void save(Session session) {
        logger.info(String.format("Javadoc Number: %d", javadocs.size()));
        logger.info(String.format("Method Number: %d", methodMapping.size()));
        logger.info(String.format("Type Number: %d", typeMapping.size()));
        try (var tx = session.beginTransaction()) {
            var entities = Stream.concat(typeMapping.values().stream(), methodMapping.values().stream()).collect(Collectors.toList());
            session.save(entities, 1);
            tx.commit();
        }
    }
}
