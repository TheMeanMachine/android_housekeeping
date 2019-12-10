package com.example.a300cemandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class nameValidation {
    /**
     * Checks if first name is valid against regex
     * @param firstName - string to check
     * @return boolean based on success
     */
    public static boolean isFirstName( String firstName )
    {
        return firstName.matches( "[A-Z][a-zA-Z]*" ) && firstName.length() > 1;
    }


    /**
     * Checks if last name is valid against regex
     * @param lastName - String to check
     * @return Boolean based on success
     */
    public static boolean isLastName( String lastName )
    {
        return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") && lastName.length() > 1;
    }
}
