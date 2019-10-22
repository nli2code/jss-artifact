package cn.edu.pku.hcst.kincoder.common.utils;

public final class StringUtil {
    private StringUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String decapitalize(String s) {
        if (s == null) return null;
        if (s.length() == 0) return s;
        if (s.charAt(0) >= 'a' && s.charAt(0) <= 'z') return s;
        var chars = s.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
