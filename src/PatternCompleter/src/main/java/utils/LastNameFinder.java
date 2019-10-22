package utils;

public class LastNameFinder {
    public static String getLastName(String name){
        int lastDotPosition = name.lastIndexOf(".");
        if (lastDotPosition != -1) {
            return name.substring(lastDotPosition + 1);
        } else {
            return name;
        }
    }
}
