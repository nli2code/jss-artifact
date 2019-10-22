package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostFrequentStringFinder {
    public static String find(List<String> strings) {
        Map<String, Integer> counter = new HashMap<>();
        for (String string : strings) {
            if (counter.containsKey(string)) {
                counter.put(string, counter.get(string) + 1);
            } else {
                counter.put(string, 1);
            }
        }
        String mostFrequentString = null;
        int maxFrequency = 0;
        for (String string : counter.keySet()) {
            if (counter.get(string) > maxFrequency) {
                maxFrequency = counter.get(string);
                mostFrequentString = string;
            }
        }
        return mostFrequentString;
    }
}
