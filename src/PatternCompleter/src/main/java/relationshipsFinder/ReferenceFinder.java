package relationshipsFinder;

import dataStructure.CreationPath.CreationPath;
import dataStructure.CreationPath.CreationPathNode;
import dataStructure.CreationPath.CreationPathReferenceNode;
import dataStructure.PatternInstance;
import javafx.util.Pair;
import org.omg.PortableInterceptor.INACTIVE;
import utils.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReferenceFinder {
    //Singleton
    private static ReferenceFinder instance;

    private ReferenceFinder() {
    }

    public static ReferenceFinder getInstance() {
        if (instance == null) {
            instance = new ReferenceFinder();
        }
        return instance;
    }

    //Data Structure & Their Initialization
    private int patternInstanceNumber;
    private int groupNumber;
    private int[][] referenceFrequencyCounter;
    private double threshold;
    private List<Pair<Integer,Integer>> referencePairs;
    private Map<Integer, Integer> groupMapping;



    private void initialize(List<PatternInstance> patternInstances, List<List<Integer>> groups, double threshold){
        patternInstanceNumber = patternInstances.size();

        groupNumber = groups.size();
        referenceFrequencyCounter = new int[groupNumber][groupNumber];
        for (int i = 0; i < groupNumber; i++) {
            for (int j = 0; j < groupNumber; j++) {
                referenceFrequencyCounter[i][j] = 0;
            }
        }

        this.threshold = threshold;
        referencePairs = new ArrayList<>();

        groupMapping = new HashMap<>();
        for (int i = 0; i < groupNumber; i++) {
            for (Integer integer : groups.get(i)) {
                groupMapping.put(integer, i);
            }
        }
    }

    //The only exposed method
    public List<Pair<Integer,Integer>> find(List<PatternInstance> patternInstances, List<List<Integer>> groups, double threshold){
        initialize(patternInstances,groups,threshold);
        countReferenceFrequency(patternInstances,groups);
        findReferencePairs();
        if (Config.debug()){
            printAllPairs();
        }
        return referencePairs;
    }

    private void printAllPairs(){
        System.out.println("------------------------------------");
        System.out.println("All Pairs:");
        for (Pair<Integer, Integer> pair : referencePairs){
            System.out.println(pair.getKey() + "-->" + pair.getValue());
        }
        System.out.println("------------------------------------");
    }

    private void findReferencePairs(){
        for (int i=0;i<groupNumber;i++){
            for (int j=0;j<groupNumber;j++){
                if (referenceFrequencyCounter[i][j] >= threshold * patternInstanceNumber){
                    referencePairs.add(new Pair<>(i,j));
                }
            }
        }
    }

    private void countReferenceFrequency(List<PatternInstance> patternInstances, List<List<Integer>> groups){
        for (PatternInstance patternInstance : patternInstances){
            List<CreationPath> creationPaths = patternInstance.getCreationPaths();
            for (int i=0;i<groupNumber;i++){
                for (int j=0;j<groupNumber;j++){
                    int sourceGroupMember = groups.get(i).get(0);
                    CreationPathNode firstNode = creationPaths.get(sourceGroupMember).get(0);
                    if (firstNode instanceof CreationPathReferenceNode){
                        if (groups.get(j).contains(((CreationPathReferenceNode) firstNode).referencePosition)){
                            referenceFrequencyCounter[i][j] += 1;
                        }
                    }
                }
            }
        }
    }
}
