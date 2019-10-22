package recommendator;

import dataStructure.CreationPath.CreationPath;
import dataStructure.PatternInstance;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FillerRecommendator {
    //Singleton
    private static FillerRecommendator instance;

    private FillerRecommendator() {
    }

    public static FillerRecommendator getInstance() {
        if (instance == null) {
            instance = new FillerRecommendator();
        }
        return instance;
    }

    //Data & initialization
    private Map<Integer, Integer> groupMapping;
    private List<Map<String, Integer>> fillerFrequencyCounters;
    private List<String> recommendation;


    private void initialize(List<List<Integer>> groups) {
        groupMapping = new HashMap<>();
        for (int i = 0; i < groups.size(); i++) {
            for (Integer integer : groups.get(i)) {
                groupMapping.put(integer, i);
            }
        }

        fillerFrequencyCounters = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            Map<String, Integer> fillerFrequencyCounter = new HashMap<>();
            fillerFrequencyCounters.add(fillerFrequencyCounter);
        }

        recommendation = new ArrayList<>();
    }

    //The only exposed method
    public List<String> recommend(List<List<Integer>> groups, List<PatternInstance> patternInstances, boolean fullPath) {
        initialize(groups);
        countFillerFrequency(patternInstances,fullPath);
        recommendFiller();
        return recommendation;
    }

    private void countFillerFrequency(List<PatternInstance> patternInstances, boolean fullPath) {
        for (PatternInstance instance : patternInstances) {
            List<String> fillers;
            if (fullPath){
                fillers = instance.getCreationPathsAsString();
            }else {
                fillers = instance.serialize();
            }
            for (int i = 0; i < fillers.size(); i++) {
                Map<String, Integer> fillerCounter = fillerFrequencyCounters.get(groupMapping.get(i));

                String filler = fillers.get(i);
                if (fillerCounter.containsKey(filler)) {
                    fillerCounter.put(filler, fillerCounter.get(filler) + 1);
                } else {
                    fillerCounter.put(filler, 1);
                }
            }
        }
    }

    private void recommendFiller() {
        for (Map<String, Integer> fillerFrequencyCounter : fillerFrequencyCounters) {
            int fillerFrequency = 0;
            String mostFrequentFiller = "";
            for (String filler : fillerFrequencyCounter.keySet()) {
                if (fillerFrequencyCounter.get(filler) > fillerFrequency) {
                    fillerFrequency = fillerFrequencyCounter.get(filler);
                    mostFrequentFiller = filler;
                }
            }
            recommendation.add(mostFrequentFiller);
        }
    }

}
