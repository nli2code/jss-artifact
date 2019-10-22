package utils;

public class Config {

    public static Boolean batchMode() {
        return false;
    }

    public static String getAllPatternDir() {
        return "data";
    }

    public static String getFoucsPatternDir() {
        return "data/fill_color";
    }

    public static boolean debug() {
        return false;
    }

    public static double getThreshold() {
        return 0.95;
    }

}
