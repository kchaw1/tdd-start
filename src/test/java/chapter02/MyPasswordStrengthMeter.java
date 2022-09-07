package chapter02;

public class MyPasswordStrengthMeter {

    private static final int PASSWORD_MIN_LENGTH = 8;
    public static PasswordStrength validate(String password) {

        int passCnt = 0;

        if(isLongerThanMinLength(password)) passCnt++;
        if(isIncludeNumber(password)) passCnt++;
        if(isIncludeUpperCase(password)) passCnt++;

        return getPasswordStrength(passCnt);
    }

    private static PasswordStrength getPasswordStrength(int passCnt) {
        return switch (passCnt) {
            case 3 -> PasswordStrength.STRONG;
            case 2 -> PasswordStrength.NORMAL;
            default -> PasswordStrength.WEAK;
        };
    }

    private static boolean isLongerThanMinLength(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH;
    }

    private static boolean isIncludeNumber(String password) {
        return password.matches(".*[0-9].*");
    }

    private static boolean isIncludeUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

}
