package cn.edu.pku.hcst.kincoder.core.nlp.javadoc;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JavadocDescriptionPreFilters {
	public JavadocDescriptionPreFilter pTagReplace = javadoc -> javadoc.replaceAll("<p>", ".");
	public JavadocDescriptionPreFilter joinLines = javadoc -> javadoc.replaceAll("\n", " ");
	public JavadocDescriptionPreFilter joinSpaces = javadoc -> javadoc.replaceAll("[ ]+", " ");
}
