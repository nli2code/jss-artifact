package cn.edu.pku.hcst.kincoder.core.rules;

import cn.edu.pku.hcst.kincoder.core.nlp.javadoc.JavadocProcessor;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodJavadocEntity;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;

public class CreateMethodJudger extends PositiveJudger<MethodEntity> {

	@Inject
	public CreateMethodJudger(JavadocProcessor javadocProcessor) {
		Rule<MethodEntity> nameRule = methodEntity -> methodEntity.getSimpleName().matches("^(create|new)([A-Z].*|$)");

		// javadoc的第一句话中包含create
		Rule<MethodEntity> javadocRule = methodEntity -> Optional.ofNullable(methodEntity.getJavadoc())
			.map(MethodJavadocEntity::getDescription)
			.map(javadocProcessor::extractFirstSentence)
			.orElse("")
			.toLowerCase()
			.contains("create");

		this.rules = List.of(nameRule, javadocRule);
	}
}
