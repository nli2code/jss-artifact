package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphConfig;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphSessionFactory;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import cn.edu.pku.hcst.kincoder.kg.repository.impl.PreloadRepository;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.neo4j.ogm.session.Session;

public class MinerModule extends AbstractModule {
    private final PatternMiningConfig config;

    public MinerModule(PatternMiningConfig config) {
        this.config = config;
    }

    @Override
    public void configure() {
        bind(PatternConfig.class).toInstance(config);
        bind(Repository.class).to(PreloadRepository.class);
        bind(Session.class).toProvider(KnowledgeGraphSessionFactory.class).in(Singleton.class);
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
