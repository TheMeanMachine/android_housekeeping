package com.example.a300cemandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class passwordValidation {
    private static int passwordMinimumLength = 8;



    public int getPasswordMinimumLength(){
        return passwordMinimumLength;
    }

    public static int passwordStrength(String password){
        int len = password.length();
        Boolean strong = (len >= 12);
        Boolean okay = (len >= passwordMinimumLength && len < 12);
        Boolean weak = (len > 0 && len < passwordMinimumLength);
        Boolean hasSymbol = containsSymbol(password);

        if((weak || okay || strong) && !hasSymbol) {
            return 1;
        }else if(okay && hasSymbol){
            return 2;
        }else if(strong && hasSymbol){
            return 3;
        }

        return 0;
    }

    public static boolean containsSymbol(String target){
        String expression = "[$&+,:;=?@#|'<>.^*()%!-]";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

}
