package cn.edu.pku.hcst.kincoder.common.utils;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import com.google.common.base.CaseFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ElementUtil {
    private final static Pattern methodPattern = Pattern.compile("(.*)\\(([a-zA-Z.,\\[\\] ]*)\\)");

    /**
     * 从类的全限定名称中提取简化名称，即以'.'分割后的最后一段字符串
     * 例：
     * Object => Object
     * java.lang.Object => Object
     * org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener => SheetRecordCollectingListener
     * @param qualifiedName 类的全限定名称
     * @return 类的简化名称
     */
    public static String qualifiedName2Simple(String qualifiedName) {
        var segments = qualifiedName.split("\\.");
        return segments[segments.length - 1];
    }

    /**
     * 从方法签名中提取参数类型列表
     * 例：
     * func() => []
     * func(int, long) => ["int", "long"]
     * org.test.func(java.lang.Object, float) => ["java.lang.Object", "float"]
     * @param signature 方法的签名
     * @return 参数类型列表
     */
    public static List<Type> methodParams(String signature) {
        var matcher = methodPattern.matcher(signature);
        if (matcher.find()) {
            var g = matcher.group(2);
            return Arrays.stream(g.split(", ")).map(Type::fromString).collect(Collectors.toList());
        } else {
            throw new RuntimeException(String.format("Given string %s isn't a valid method signature", signature));
        }
    }

    /**
     * 从方法签名中提取接受者类型的全限定名称
     * 例：
     * java.lang.Object.toString() => Some(java.lang.Object)
     * func(int, long) => None
     * @param signature 方法的签名
     * @return 接受者类型的全限定名称
     */
    public static Optional<ReferenceType> methodReceiverType(String signature) {
        var matcher = methodPattern.matcher(signature);
        if (matcher.find()) {
            var g = matcher.group(1);
            var index = g.lastIndexOf('.');
            return Optional.of(((ReferenceType) Type.fromString(g.substring(0, index))));
        }
        return Optional.empty();
    }

    public static String[] lowerCamelCaseToPhrase(String element) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, element).split("_");
    }
}
