package dataStructure;

import javafx.util.Pair;
import utils.MostFrequentStringFinder;

import java.util.ArrayList;
import java.util.List;

public class AcceptedPattern {
    private PatternInstance instance;
    private List<String> recommendation;
    private List<String> pathRecommendation;
    private List<String> groupTypes;
    private List<List<Integer>> groups;
    private List<Pair<Integer,Integer>> referencePairs;

    public AcceptedPattern(PatternInstance instance) {
        this.instance = (PatternInstance) instance.clone();
    }

    private boolean isIntermediateVar(List<Integer> group) {
        List<Integer> intermediateHoles = instance.getIntermediateHoles();
        for (int groupID : group) {
            for (int intermediateHole : intermediateHoles) {
                if (groupID == intermediateHole) {
                    return true;
                }
            }
        }
        return false;
    }

    private void countGroupTypes() {
        groupTypes = new ArrayList<>();
        List<String> types = instance.typeSerialize();
        for (List<Integer> group : groups) {
            List<String> groupFillerTypes = new ArrayList<>();
            for (int groupMember : group) {
                groupFillerTypes.add(types.get(groupMember));
            }
            groupTypes.add(MostFrequentStringFinder.find(groupFillerTypes));
        }
    }


    public void accept(List<String> recommendation, List<String> pathRecommendation, List<List<Integer>> groups, List<Pair<Integer,Integer>> referencePairs) {
        this.recommendation = recommendation;
        this.groups = groups;
        this.referencePairs = referencePairs;
        countGroupTypes();
        this.pathRecommendation = new ArrayList<>();
        this.pathRecommendation.addAll(pathRecommendation);
        for (Pair<Integer,Integer> referencePair : referencePairs){
            String completePath = recommendation.get(referencePair.getValue()) + pathRecommendation.get(referencePair.getKey());
            this.pathRecommendation.set(referencePair.getKey(),completePath);
        }

        String[] placeholders = new String[instance.serialize().size()];
        int counter = 0;
        for (int i = 0; i < groups.size(); i++) {
            if (isIntermediateVar(groups.get(i))) {
                for (int position : groups.get(i)) {
                    placeholders[position] = recommendation.get(i);
                }
            } else {
                for (int position : groups.get(i)) {
                    placeholders[position] = "<" + ((Integer) counter).toString() + ">";
                }
                counter++;
            }
        }
        List<String> fillers = new ArrayList<>();
        for (String placeholder : placeholders) {
            fillers.add(placeholder);
        }
        instance.deserialize(fillers);
    }

    public String toString() {
        String sketch = "------------------------------------\n";
        sketch += instance.toString() + '\n';

        int counter = 0;
        for (int i = 0; i < recommendation.size(); i++) {
            if (!isIntermediateVar(groups.get(i))) {
                sketch += "<" + ((Integer) counter++).toString() + ">  :  ";
                sketch += recommendation.get(i);
                sketch += "  ( Path = " + pathRecommendation.get(i) +" )";
                sketch += "  ( Type = " + groupTypes.get(i) + " )\n";

            }
        }

        sketch += "\n";
        for (String symbol : instance.getSymbols()) {
            sketch += symbol + "\n";
        }
        sketch += "------------------------------------\n";

        return sketch;
    }

}
