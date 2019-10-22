package cn.edu.pku.hcst.kincoder.core.rules;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.core.nlp.javadoc.JavadocProcessor;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodJavadocEntity;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;

public class LoadMethodJudger extends PositiveJudger<MethodEntity> {
	@Inject
	public LoadMethodJudger(JavadocProcessor javadocProcessor, CodeUtil codeUtil) {
		// 方法的简单名以load, read, open开头，之后是一个大写字母或结尾
		Rule<MethodEntity> nameRule = methodEntity -> methodEntity.getSimpleName().matches("^(load|read|open)([A-Z].*|$)");

		// javadoc的第一句话中包含load, read, open
		Rule<MethodEntity> javadocRule = methodEntity -> {
			var javadoc = Optional.ofNullable(methodEntity.getJavadoc())
				.map(MethodJavadocEntity::getDescription)
				.map(javadocProcessor::extractFirstSentence)
				.orElse("")
				.toLowerCase();
			return javadoc.contains("create") || javadoc.contains("read") || javadoc.contains("open");
		};

		// 参数中有java.io.InputStream的子类
		Rule<MethodEntity> paramRule = methodEntity ->
			ElementUtil.methodParams(methodEntity.getQualifiedSignature())
				.stream()
				.anyMatch(param -> codeUtil.isAssignable(param, Type.fromString("java.io.InputStream")));

		this.rules = List.of(nameRule, javadocRule, paramRule);
	}
}
