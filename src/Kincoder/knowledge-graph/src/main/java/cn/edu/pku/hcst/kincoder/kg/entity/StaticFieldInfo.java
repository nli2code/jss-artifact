package cn.edu.pku.hcst.kincoder.kg.entity;

import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class StaticFieldInfo {
    private Map<String, Set<String>> fieldsInfo;

    public StaticFieldInfo() {

    }

    private StaticFieldInfo(ResolvedReferenceTypeDeclaration decl) {
        this.fieldsInfo = decl.getDeclaredFields().stream()
            .filter(ResolvedFieldDeclaration::isStatic)
            .collect(
                groupingBy(
                    (ResolvedFieldDeclaration f) -> f.getType().describe(),
                    mapping(ResolvedFieldDeclaration::getName, toSet())
                )
            );
    }

    public static StaticFieldInfo from(ResolvedReferenceTypeDeclaration decl) {
        try {
            return new StaticFieldInfo(decl);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Set<String>> getFieldsInfo() {
        return fieldsInfo;
    }
}
