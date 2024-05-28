package Client.ClientHandlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {
    public static boolean StringForIntegerChecker(String input) {
        Pattern pat = Pattern.compile("^\\s*\\d+\\s*(?:\\s+\\d+\\s*)*$");
        Matcher match = pat.matcher(input);
        return match.matches();
    }

    public static boolean StringForNumberChecker(String input) {
        String[] nums = input.split(" ");
        for (String number : nums) {
            if (!number.matches("-?\\d+(\\.\\d+)?")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullChecker(Object obj) {
        return obj == null;
    }

    public static boolean ValidNameChecker(String str) {
        return !isNullChecker(str) && !str.isEmpty() && str.matches("^[^\\s].*");
    }

    public static <T extends Number> boolean CorrectNumberChecker(String str, Class<T> type) {
        try {
            T num = type.getConstructor(String.class).newInstance(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isSubstringChecker(String substring, String string) {
        return string.contains(substring);
    }


    public static boolean NullArrayChecker(Object[] array) {
        for (Object element : array) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean EmptyArrayChecker(Object[] args) {
        return args.length == 0;
    }
}