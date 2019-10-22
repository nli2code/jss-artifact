package relationshipsFinder;

import dataStructure.PatternInstance;

import java.util.ArrayList;
import java.util.List;

public class SimilarGroupFinder {
    //Singleton
    private static SimilarGroupFinder instance;

    private SimilarGroupFinder() {
    }

    public static SimilarGroupFinder getInstance() {
        if (instance == null) {
            instance = new SimilarGroupFinder();
        }
        return instance;
    }

    //Data Structure & Their Initialization
    private int patternInstanceNumber;
    private int patternInstanceDimension;
    private int[][] equalityFrequencyCounter;
    private double threshold;
    private List<List<Integer>> similarGroups;


    private void initialization(List<PatternInstance> patternInstances, double threshold) {
        patternInstanceNumber = patternInstances.size();

        patternInstanceDimension = patternInstances.get(0).serialize().size();

        equalityFrequencyCounter = new int[patternInstanceDimension][patternInstanceDimension];
        for (int i = 0; i < patternInstanceDimension; i++) {
            for (int j = 0; j < patternInstanceDimension; j++) {
                equalityFrequencyCounter[i][j] = 0;
            }
        }

        this.threshold = threshold;
        similarGroups = new ArrayList<>();
    }

    //The only exposed method
    public List<List<Integer>> find(List<PatternInstance> patternInstances, double threshold) {
        initialization(patternInstances, threshold);
        countEqualityFrequency(patternInstances);
        findSimilarGroups();
        return similarGroups;
    }


    private void countEqualityFrequency(List<PatternInstance> patternInstances) {
        for (PatternInstance instance : patternInstances) {
            List<String> fillers = instance.serialize();
            for (int i = 0; i < patternInstanceDimension; i++) {
                for (int j = i + 1; j < patternInstanceDimension; j++) {
                    if (fillers.get(i).equals(fillers.get(j))) {
                        equalityFrequencyCounter[i][j] += 1;
                    }
                }
            }
        }
    }

    private boolean similar2AllMember(int j, List<Integer> group) {
        for (int i : group) {
            if (equalityFrequencyCounter[i][j] < patternInstanceNumber * threshold) {
                return false;
            }
        }
        return true;
    }

    private void findSimilarGroups() {
        Boolean[] grouped = new Boolean[patternInstanceDimension];
        for (int i = 0; i < patternInstanceDimension; i++) {
            grouped[i] = false;
        }

        for (int i = 0; i < patternInstanceDimension; i++) {
            if (grouped[i]) {
                continue;
            }
            List<Integer> newGroup = new ArrayList<>();
            newGroup.add(i);
            grouped[i] = true;

            for (int j = i + 1; j < patternInstanceDimension; j++) {
                if (grouped[j]) {
                    continue;
                }
                if (similar2AllMember(j, newGroup)) {
                    newGroup.add(j);
                    grouped[j] = true;
                }
            }
            similarGroups.add(newGroup);
        }
    }

}
