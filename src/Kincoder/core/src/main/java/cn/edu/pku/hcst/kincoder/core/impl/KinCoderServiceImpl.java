package cn.edu.pku.hcst.kincoder.core.impl;

import cn.edu.pku.hcst.kincoder.core.api.*;
import cn.edu.pku.hcst.kincoder.core.impl.session.Session;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpService;
import cn.edu.pku.hcst.kincoder.core.nlp.PatternMatcher;
import cn.edu.pku.hcst.kincoder.core.qa.ContextFactory;
import cn.edu.pku.hcst.kincoder.core.qa.QuestionGenerator;
import com.google.inject.Inject;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class KinCoderServiceImpl implements KinCoderService {
    private final ConcurrentMap<Long, Session> sessions = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong();
    private final ContextFactory contextFactory;
    private final NlpService nlpService;
    private final QuestionGenerator questionGenerator;
    private final PatternMatcher patternMatcher;
    private final KinCoderConfig config;

    @Inject
    public KinCoderServiceImpl(ContextFactory contextFactory, NlpService nlpService, QuestionGenerator questionGenerator, PatternMatcher patternMatcher, KinCoderConfig config) {
        this.contextFactory = contextFactory;
        this.nlpService = nlpService;
        this.questionGenerator = questionGenerator;
        this.patternMatcher = patternMatcher;
        this.config = config;
    }

    @Override
    public StartSessionResponse startSession(String query, Map<String, String> variables, Set<String> extendedTypes) {
        var results = patternMatcher.match(query, config.getPatternLimit());
        var session = new Session(contextFactory, nlpService, questionGenerator, query, variables, extendedTypes, results);
        var sessionId = nextId.getAndIncrement();
        sessions.put(sessionId, session);
        return new StartSessionResponse(nextId.getAndIncrement(), results);
    }

    @Override
    public QAResponse selectPattern(long sessionId, int id) {
        var session = sessions.get(sessionId);
        if (session == null) return new ErrorResponse(String.format("No session with id %d", sessionId));
        return session.selectSkeleton(id);
    }

    @Override
    public QAResponse response(long sessionId, String answer) {
        var session = sessions.get(sessionId);
        if (session == null) return new ErrorResponse(String.format("No session with id %d", sessionId));
        return session.answer(answer);
    }

    @Override
    public UndoResponse undo(long sessionId) {
        var session = sessions.get(sessionId);
        if (session == null) return new ErrorResponse(String.format("No session with id %d", sessionId));
        return session.undo();
    }
}
