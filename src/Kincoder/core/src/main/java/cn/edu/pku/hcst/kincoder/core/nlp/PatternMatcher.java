package cn.edu.pku.hcst.kincoder.core.nlp;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.PatternEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.google.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PatternMatcher {
    private final Repository repository;
    private final ObjectMapper objectMapper;

    @Inject
    public PatternMatcher(Repository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public List<Pair<Skeleton, Double>> match(String query, int limit) {
        var patterns = repository.getAllPatterns();
        return patterns.stream()
            .map(pattern -> evaluate(query, pattern))
            .sorted(Comparator.<Pair<Skeleton, Double>, Double>comparing(Pair::getRight).reversed())
            .limit(limit)
            .collect(Collectors.toList());
    }

    @SneakyThrows
    public Pair<Skeleton, Double> evaluate(String query, PatternEntity pattern) {
        var queryWords = Arrays.stream(query.split(" ")).map(String::toLowerCase).collect(Collectors.toList());
        var methods = pattern.getHasMethods();
        var types = pattern.getHasTypes();

        var ite = queryWords.iterator();
        while (ite.hasNext()) {
            var word = ite.next();

            if (methods.stream().map(MethodEntity::getSimpleName).anyMatch(name -> cover(name, word))) {
                ite.remove();
            } else if (types.stream().map(TypeEntity::getSimpleName).anyMatch(name -> cover(name, word))) {
                ite.remove();
            }
        }

        val score = 1.0 - (double) queryWords.size() / (double) queryWords.size();

        var skeleton = objectMapper.readValue(pattern.getSkeleton(), Skeleton.class);
        return Pair.of(skeleton, score);
    }

    private boolean cover(String simpleName, String word) {
        var methodWords = List.of(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, simpleName).split("_"));
        return methodWords.contains(word);
    }
}
