package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ArrayType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.*;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.common.collect.Streams;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Slf4j
public class ChoiceQuestionFactory {
    private final Repository repository;
    private final CodeUtil codeUtil;
    private final MethodCategorizer categorizer;

    @Inject
    public ChoiceQuestionFactory(Repository repository, CodeUtil codeUtil, MethodCategorizer categorizer) {
        this.repository = repository;
        this.codeUtil = codeUtil;
        this.categorizer = categorizer;
    }

    // type非基本类型
    public ChoiceQuestion forType(Context ctx, Type type, Boolean recommend) {
        if (type instanceof ReferenceType) {
            var vars = ctx.findVariables(type);
            var producers = codeUtil.producers(type);

            // t是枚举类型，则添加枚举选项
            var enumEntity = repository.getEnumEntity(type.describe());
            var enumChoice = enumEntity != null ? List.<Choice>of(new EnumChoice(enumEntity)) : List.<Choice>of();
            var simpleName = ElementUtil.qualifiedName2Simple(type.describe()).toLowerCase();
            var content = String.format("Which %s?", simpleName);

            if (recommend) {
                var choices = Streams.concat(
                    vars.stream().map(VariableChoice::new),
                    enumChoice.stream(),
                    producers.stream().map(MethodChoice::new)
                ).collect(Collectors.toList());
                return new ChoiceQuestion(content, choices);
            } else {
                var cases = producers.stream().collect(groupingBy(categorizer::category, toSet()));

                var methodCategoryChoices = cases.entrySet().stream().flatMap(e -> {
                    var category = e.getKey();
                    var ms = e.getValue();
                    if (category == MethodCategory.UNKNOWN) {
                        log.debug("----- UnCategorised -----");
                        ms.forEach(m -> {
                            log.debug(m.getQualifiedSignature());
                            if (m.getJavadoc() != null) {
                                log.debug(m.getJavadoc().getDescription());
                            }
                        });
                        log.debug("-------------------------");
                        return Stream.of();
                    }

                    return Stream.of(new MethodCategoryChoice((ReferenceType) type, category, ms));
                });

                var choices = Streams.concat(
                    vars.stream().map(VariableChoice::new),
                    enumChoice.stream(),
                    methodCategoryChoices
                ).collect(Collectors.toList());
                return new ChoiceQuestion(content, choices);
            }
        } else {
            // TODO: nested ArrayType
            var vars = ctx.findVariables(type);
            var simpleName = ElementUtil.qualifiedName2Simple(((ArrayType) type).getComponentType().describe()).toLowerCase();
            var content = String.format("Which %s?", simpleName);

            var choices = Streams.concat(
                vars.stream().map(VariableChoice::new),
                Stream.of(new CreateArrayChoice(((ArrayType) type)))
            ).collect(Collectors.toList());

            return new ChoiceQuestion(content, choices);
        }
    }
}
