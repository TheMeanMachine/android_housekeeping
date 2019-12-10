package com.example.a300cemandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class emailValidation {

    /**
     * Checks if email matches regex which validates email addresses
     * @param email - String value to check against
     * @return Boolean (true if email is correct)
     */
    public static boolean isEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
