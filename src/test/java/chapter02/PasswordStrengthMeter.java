package chapter02;

import static chapter02.PasswordStrength.*;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String s) {
        if(s==null || s.isEmpty()) return INVALID;
        boolean lengthEnough = s.length() >= 8;
        boolean containsNum = meetsContainingNumberCriteria(s);
        boolean containsUpp = meetsContainingUppercaseCriteria(s);
        if (lengthEnough && !containsNum && !containsUpp) {
            return WEAK;
        }
        if (!lengthEnough && containsNum && !containsUpp) {
            return WEAK;
        }
        if (!lengthEnough && !containsNum && containsUpp) {
            return WEAK;
        }
        if (!lengthEnough) {
            return NORMAL;
        }
        if(!containsNum) return NORMAL;
        if(!containsUpp) return NORMAL;
        return STRONG;
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        for(char ch : s.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }
}
