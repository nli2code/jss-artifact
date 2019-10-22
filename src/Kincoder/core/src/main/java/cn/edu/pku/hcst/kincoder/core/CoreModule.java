package cn.edu.pku.hcst.kincoder.core;

import cn.edu.pku.hcst.kincoder.core.api.KinCoderConfig;
import cn.edu.pku.hcst.kincoder.core.api.KinCoderService;
import cn.edu.pku.hcst.kincoder.core.impl.KinCoderServiceImpl;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpContextFactory;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpServerConfig;
import cn.edu.pku.hcst.kincoder.core.nlp.javadoc.JavadocDescriptionPreFilter;
import cn.edu.pku.hcst.kincoder.core.nlp.javadoc.JavadocDescriptionPreFilters;
import cn.edu.pku.hcst.kincoder.core.qa.ContextFactory;
import cn.edu.pku.hcst.kincoder.core.qa.hole_resolver.ArgumentHoleResolver;
import cn.edu.pku.hcst.kincoder.core.qa.hole_resolver.HoleResolver;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphConfig;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphSessionFactory;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import cn.edu.pku.hcst.kincoder.kg.repository.impl.PreloadRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import org.neo4j.ogm.session.Session;

public class CoreModule extends AbstractModule {
    private final KinCoderConfig kinCoderConfig;
    private final NlpServerConfig nlpServerConfig;
    private final KnowledgeGraphConfig knowledgeGraphConfig;

    public CoreModule(KinCoderConfig kinCoderConfig, NlpServerConfig nlpServerConfig, KnowledgeGraphConfig knowledgeGraphConfig) {
        this.kinCoderConfig = kinCoderConfig;
        this.nlpServerConfig = nlpServerConfig;
        this.knowledgeGraphConfig = knowledgeGraphConfig;
    }

    @Override
    protected void configure() {
        bind(KinCoderConfig.class).toInstance(kinCoderConfig);
        bind(NlpServerConfig.class).toInstance(nlpServerConfig);
        bind(KnowledgeGraphConfig.class).toInstance(knowledgeGraphConfig);
        bind(Session.class).toProvider(KnowledgeGraphSessionFactory.class).in(Scopes.SINGLETON);
        bind(KinCoderService.class).to(KinCoderServiceImpl.class);
        bind(Repository.class).to(PreloadRepository.class);
        install(new FactoryModuleBuilder().build(NlpContextFactory.class));
        install(new FactoryModuleBuilder().build(ContextFactory.class));
        var javadocDescriptionPreFilters = Multibinder.newSetBinder(binder(), JavadocDescriptionPreFilter.class);
        javadocDescriptionPreFilters.addBinding().toInstance(JavadocDescriptionPreFilters.pTagReplace);
        javadocDescriptionPreFilters.addBinding().toInstance(JavadocDescriptionPreFilters.joinLines);
        javadocDescriptionPreFilters.addBinding().toInstance(JavadocDescriptionPreFilters.joinSpaces);

        var holeResolverBinder = Multibinder.newSetBinder(binder(), HoleResolver.class);
        holeResolverBinder.addBinding().to(ArgumentHoleResolver.class);
    }
}
