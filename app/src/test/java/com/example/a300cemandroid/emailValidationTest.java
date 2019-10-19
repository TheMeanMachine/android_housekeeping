package com.example.a300cemandroid;

import org.junit.Test;

import static com.example.a300cemandroid.emailValidation.isEmail;
import static org.junit.Assert.*;

public class emailValidationTest {

    //isEmail
    @Test
    public void isEmail_emptyString() {
        String testString = "";
        assertFalse(isEmail(testString));
    }

    @Test
    public void isEmail_notAnEmail() {
        String testString = "loremipsum";
        assertFalse(isEmail(testString));
    }

    @Test
    public void isEmail_isAnEmail() {
        String testString = "loremipsum@lorem.com";
        assertTrue(isEmail(testString));
    }
}