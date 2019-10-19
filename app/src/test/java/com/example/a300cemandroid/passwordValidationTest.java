package com.example.a300cemandroid;


import org.junit.Test;

import static com.example.a300cemandroid.passwordValidation.containsSymbol;
import static com.example.a300cemandroid.passwordValidation.passwordStrength;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class passwordValidationTest {

    //containsSymbol
    @Test
    public void containsSymbol_emptyString() {
        String testString = "";
        assertFalse(containsSymbol(testString));
    }

    @Test
    public void containsSymbol_noSymbols() {
        String testString = "asdsajknduwhaije92em9ep10oj0di3p2ke";
        assertFalse(containsSymbol(testString));
    }

    @Test
    public void containsSymbol_justSymbols() {
        String testString = "$)(*)()#'#'/]''#'#'./'[;;]]']";
        assertTrue(containsSymbol(testString));
    }

    @Test
    public void containsSymbol_symbolsAndOthers() {
        String testString = "auuhadsu&aij*90)()($£!%`jas";
        assertTrue(containsSymbol(testString));
    }

    //passwordStrength

    @Test
    public void passwordStrength_emptyString() {
        String testString = "";
        assertEquals(0, passwordStrength(testString));
    }

    @Test
    public void passwordStrength_lessThan8() {
        //Weak
        String testString = "1234567";
        assertEquals(1, passwordStrength(testString));
    }

    @Test
    public void passwordStrength_equal8_noSymbols() {
        //Weak
        String testString = "12345678";
        assertEquals(1, passwordStrength(testString));
    }

    @Test
    public void passwordStrength_equal8_symbols() {
        //Okay
        String testString = "1234567$";
        assertEquals(2, passwordStrength(testString));
    }

    @Test
    public void passwordStrength_moreThan8_symbols() {
        //Okay
        String testString = "123456789$";
        assertEquals(2, passwordStrength(testString));
    }

    @Test
    public void passwordStrength_equal12_symbols() {
        //Strong
        String testString = "12345678911$";
        assertEquals(3, passwordStrength(testString));
    }

    @Test
    public void passwordStrength_moreThan12_noSymbols() {
        //Strong
        String testString = "123456789ABCDE";
        assertEquals(1, passwordStrength(testString));
    }



}