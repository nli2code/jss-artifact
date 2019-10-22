package cn.edu.pku.hcst.kincoder.core.nlp.javadoc;

@FunctionalInterface
public interface JavadocDescriptionPreFilter {
	String filter(String javadoc);
}
