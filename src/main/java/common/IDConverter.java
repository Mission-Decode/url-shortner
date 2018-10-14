package common;

import java.util.*;

public class IDConverter {

    private static Map<Character, Integer> charToIntegerMapping;

    private static List<Character> integerToCharMapping;

    private IDConverter() {
    }

    static {
        init();
    }

    private static void init() {
        charToIntegerMapping = new HashMap<>();
        integerToCharMapping = new ArrayList<>();

        char c;
        for (int i = 0; i < 26; i++) {
            c = 'a';
            c = (char) (c + i);
            charToIntegerMapping.put(c, i);
            integerToCharMapping.add(c);
        }

        for (int i = 26; i < 52; i++) {
            c = 'A';
            c = (char) (c + (i - 26));
            charToIntegerMapping.put(c, i);
            integerToCharMapping.add(c);
        }

        for (int i = 52; i < 62; i++) {
            c = '0';
            c = (char) (c + (i - 52));
            charToIntegerMapping.put(c, i);
            integerToCharMapping.add(c);
        }
    }

    public static String createUniqueId(Long id) {
        Stack<Integer> digits = convertBase10ToBase62Digits(id);
        StringBuilder sb = new StringBuilder();

        while (!digits.isEmpty()) {
            Integer digit = digits.pop();
            sb.append(integerToCharMapping.get(digit));
        }
        return sb.toString();
    }

    public static Long getIdForUrl(String url) {
        List<Character> base62Digits = new LinkedList<>();

        for (int i = 0; i < url.length(); i++) {
            base62Digits.add(url.charAt(i));
        }

        return convertBase62ToBase10Digits(base62Digits);
    }

    private static Long convertBase62ToBase10Digits(List<Character> base62Digits) {
        long id = 0L;
        int exp = base62Digits.size() - 1;

        for (char c : base62Digits) {
            int base10 = charToIntegerMapping.get(c);
            id += base10 * Math.pow(62.0, exp--);
        }

        return id;
    }

    private static Stack<Integer> convertBase10ToBase62Digits(Long id) {
        Stack<Integer> s = new Stack<>();

        StringBuilder sb = new StringBuilder();

        while (id > 0) {
            int remainder = (int) (id % 62);
            s.push(remainder);
            id /= 62;
        }

        return s;
    }
}
