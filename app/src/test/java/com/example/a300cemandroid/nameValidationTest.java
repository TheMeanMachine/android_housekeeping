package com.example.a300cemandroid;

import org.junit.Test;

import static com.example.a300cemandroid.nameValidation.isFirstName;
import static com.example.a300cemandroid.nameValidation.isLastName;
import static org.junit.Assert.*;

public class nameValidationTest {


    //isFirstName
    @Test
    public void isFirstName_emptyString() {
        String testString = "";
        assertFalse(isFirstName(testString));
    }

    @Test
    public void isFirstName_oneChar() {
        String testString = "A";
        assertFalse(isFirstName(testString));
    }

    @Test
    public void isFirstName_symbols() {
        String testString = "~?=-*(@";
        assertFalse(isFirstName(testString));
    }

    @Test
    public void isFirstName_numbers() {
        String testString = "341412412";
        assertFalse(isFirstName(testString));
    }

    @Test
    public void isFirstName_nameWithNumber() {
        String testString = "Aaron1";
        assertFalse(isFirstName(testString));
    }

    @Test
    public void isFirstName_name() {
        String testString = "Aaron";
        assertTrue(isFirstName(testString));
    }

    //isLastName

    @Test
    public void isLastName_emptyString() {
        String testString = "";
        assertFalse(isLastName(testString));
    }

    @Test
    public void isLastName_oneChar() {
        String testString = "A";
        assertFalse(isLastName(testString));
    }

    @Test
    public void isLastName_symbols() {
        String testString = "~?=-*(@";
        assertFalse(isLastName(testString));
    }

    @Test
    public void isLastName_numbers() {
        String testString = "341412412";
        assertFalse(isLastName(testString));
    }

    @Test
    public void isLastName_nameWithNumber() {
        String testString = "Machin1";
        assertFalse(isLastName(testString));
    }

    @Test
    public void isLastName_name() {
        String testString = "Machin";
        assertTrue(isLastName(testString));
    }
}