package cn.edu.pku.hcst.kincoder.kg.builder;

import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class BuilderModule extends AbstractModule {
    private final KnowledgeGraphBuilderConfig config;

    public BuilderModule(KnowledgeGraphBuilderConfig config) {
        this.config = config;
    }

    @Override
    public void configure() {
        bind(KnowledgeGraphBuilderConfig.class).toInstance(this.config);
    }

    @Provides
    @Singleton
    public KnowledgeGraphConfig knowledgeGraphConfig() {
        return KnowledgeGraphConfig.builder()
            .uri(config.getUri())
            .username(config.getUsername())
            .password(config.getPassword())
            .build();
    }
}
