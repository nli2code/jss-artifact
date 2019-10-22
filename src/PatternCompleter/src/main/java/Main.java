import com.sun.tools.hat.internal.model.ReferenceChain;
import dataStructure.AcceptedPattern;
import dataStructure.PatternInstance;
import extractor.PatternInstanceExtractor;
import extractor.PatternInstanceFilter;
import javafx.util.Pair;
import recommendator.FillerRecommendator;
import relationshipsFinder.ReferenceFinder;
import relationshipsFinder.SimilarGroupFinder;
import utils.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (!Config.batchMode()) {
            handleSinglePattern(Config.getFoucsPatternDir());
        } else {
            File allPatternDir = new File(Config.getAllPatternDir());
            for (File patternDir : allPatternDir.listFiles()) {
                if (patternDir.isDirectory()) {
                    System.out.println("------------------------------------");
                    System.out.println("Patten : " + patternDir);
                    handleSinglePattern(patternDir.getPath());
                }
            }
        }
    }

    public static void handleSinglePattern(String patternDir) {
        PatternInstanceExtractor extractor = PatternInstanceExtractor.getInstance();
        List<PatternInstance> patternInstances = extractor.extract(
                patternDir + "/pattern",
                patternDir + "/corpus"
        );
        patternInstances = PatternInstanceFilter.filter(patternInstances);
        recordPatternInstances(patternInstances, patternDir + "/instances");


        SimilarGroupFinder groupFinder = SimilarGroupFinder.getInstance();
        List<List<Integer>> groups = groupFinder.find(patternInstances, Config.getThreshold());
        patternInstances = PatternInstanceFilter.groupFilter(patternInstances,groups);

        ReferenceFinder referenceFinder = ReferenceFinder.getInstance();
        List<Pair<Integer,Integer>> referencePairs = referenceFinder.find(patternInstances,groups,Config.getThreshold());
        patternInstances = PatternInstanceFilter.referenceFilter(patternInstances,groups,referencePairs);


        FillerRecommendator fillerRecommendator = FillerRecommendator.getInstance();
        List<String> fillerRecommendation = fillerRecommendator.recommend(groups, patternInstances, false);
        List<String> fillerPathRecommendation = fillerRecommendator.recommend(groups, patternInstances, true);


        AcceptedPattern acceptedPattern = new AcceptedPattern(patternInstances.get(0));
        acceptedPattern.accept(fillerRecommendation, fillerPathRecommendation, groups, referencePairs);
        System.out.println(acceptedPattern);

        try {
            FileWriter writer = new FileWriter(patternDir + "/recommendation");
            writer.write(acceptedPattern.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void recordPatternInstances(List<PatternInstance> patternInstances, String file) {
        try {
            FileWriter writer = new FileWriter(file);
            for (PatternInstance instance : patternInstances) {
                writer.write(instance.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
