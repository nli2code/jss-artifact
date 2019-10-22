package extractor;

import dataStructure.CreationPath.CreationPath;
import dataStructure.CreationPath.CreationPathNode;
import dataStructure.CreationPath.CreationPathReferenceNode;
import dataStructure.PatternInstance;
import dataStructure.PatternInstanceLine;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternInstanceFilter {

    private static List<PatternInstance> illegalInstanceFilter(List<PatternInstance> patternInstances) {
        List<PatternInstance> legalPatternInstances = new ArrayList<>();

        for (PatternInstance instance : patternInstances) {
            if (!instance.isIllegalPattern()) {
                legalPatternInstances.add(instance);
            }
        }

        return legalPatternInstances;
    }

    private static List<PatternInstance> normalFilter(List<PatternInstance> patternInstances) {
        Map<String, Integer> signatureCounts = new HashMap<>();

        for (PatternInstance instance : patternInstances) {
            String signature = instance.getSignature();
            if (signatureCounts.containsKey(signature)) {
                signatureCounts.put(signature, signatureCounts.get(signature) + 1);
            } else {
                signatureCounts.put(signature, 1);
            }
        }

        String normalSignature = null;
        int normalSignatureCount = 0;
        for (String signature : signatureCounts.keySet()) {
            int signatureCount = signatureCounts.get(signature);
            if (signatureCount > normalSignatureCount) {
                normalSignatureCount = signatureCount;
                normalSignature = signature;
            }
        }

        List<PatternInstance> normalPatternInstances = new ArrayList<>();
        for (PatternInstance instance : patternInstances) {
            if (instance.getSignature().equals(normalSignature)) {
                normalPatternInstances.add(instance);
            }
        }
        return normalPatternInstances;
    }

    private static boolean matchGroups(PatternInstance patternInstance, List<List<Integer>> groups){
        List<String> fillers = patternInstance.serialize();
        for (List<Integer> group : groups){
            String content = null;
            for (int i : group){
                if (content == null){
                    content = fillers.get(i);
                }else if (content.equals(fillers.get(i))){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public static List<PatternInstance> groupFilter(List<PatternInstance> patternInstances, List<List<Integer>> groups){
        System.out.println("Before Filter : PatternInstance Number = " + patternInstances.size());
        List<PatternInstance> legalPatternInstances = new ArrayList<>();
        for (PatternInstance patternInstance : patternInstances){
            if (matchGroups(patternInstance,groups)){
                legalPatternInstances.add(patternInstance);
            }
        }
        System.out.println("After Filter : PatternInstance Number = " + legalPatternInstances.size());
        return legalPatternInstances;
    }

    private static boolean matchReference(PatternInstance instance,
                                          List<List<Integer>> groups,
                                          List<Pair<Integer,Integer>> referencePairs){
        List<CreationPath> creationPaths = instance.getCreationPaths();
        if (!matchGroups(instance,groups)){
            return false;
        }
        for (Pair<Integer,Integer> referencePair : referencePairs){
            boolean passReferenceCheck = false;
            CreationPath sourceCreationPath = creationPaths.get(groups.get(referencePair.getKey()).get(0));
            CreationPathNode sourceCreationPathFirstNode = sourceCreationPath.get(0);
            if (sourceCreationPathFirstNode instanceof CreationPathReferenceNode){
                int targetPosition = ((CreationPathReferenceNode) sourceCreationPathFirstNode).referencePosition;
                if (groups.get(referencePair.getValue()).contains(targetPosition)){
                    passReferenceCheck = true;
                }
            }
            if (!passReferenceCheck){
                return false;
            }
        }
        return true;
    }

    public static List<PatternInstance> referenceFilter(
            List<PatternInstance> patternInstances,
            List<List<Integer>> groups,
            List<Pair<Integer,Integer>> referencePairs){
        System.out.println("Before Filter : PatternInstance Number = " + patternInstances.size());
        List<PatternInstance> legalPatternInstances = new ArrayList<>();
        for (PatternInstance patternInstance : patternInstances){
            if (matchReference(patternInstance,groups,referencePairs)){
                legalPatternInstances.add(patternInstance);
            }
        }
        System.out.println("After Filter : PatternInstance Number = " + legalPatternInstances.size());
        return legalPatternInstances;
    }

    public static List<PatternInstance> filter(List<PatternInstance> patternInstances) {
        System.out.println("------------------------------------");
        System.out.println("Before Filter : PatternInstance Number = " + patternInstances.size());

        List<PatternInstance> legalPatternInstances = illegalInstanceFilter(patternInstances);
        System.out.println("After Illegal Filter : PatternInstance Number = " + legalPatternInstances.size());

        List<PatternInstance> normalPatternInstances = normalFilter(legalPatternInstances);
        System.out.println("After Normal Filter : PatternInstance Number = " + normalPatternInstances.size());

        System.out.println("Filter Process Done");
        System.out.println("------------------------------------");
        return normalPatternInstances;
    }
}
