package pine.demo.recommend.utils;

import java.util.List;

public class StringUtils {
    public static String listToString(List<Character> list) {
        StringBuilder sb = new StringBuilder(list.size());
        for (Character ch : list) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
