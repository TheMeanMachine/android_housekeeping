package com.example.a300cemandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class nameValidation {
    public static boolean isFirstName( String firstName )
    {
        return firstName.matches( "[A-Z][a-zA-Z]*" ) && firstName.length() > 1;
    }


    public static boolean isLastName( String lastName )
    {
        return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*") && lastName.length() > 1;
    }
}
