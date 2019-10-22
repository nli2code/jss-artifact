package cn.edu.pku.hcst.kincoder.core.rules;

import cn.edu.pku.hcst.kincoder.core.nlp.javadoc.JavadocProcessor;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import com.google.inject.Inject;

import java.util.List;

public class GetMethodJudger extends PositiveJudger<MethodEntity> {

	@Inject
	public GetMethodJudger(JavadocProcessor javadocProcessor) {
		// 方法的简单名以load, read, open开头，之后是一个大写字母或结尾
		Rule<MethodEntity> nameRule = methodEntity -> methodEntity.getSimpleName().matches("^(get)([A-Z].*|$)");

		this.rules = List.of(nameRule);
	}
}
