package com.tttsaurus.fluidintetweaker.common.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RomanNumberUtils
{
    private static final List<String> romanLetterList = new ArrayList<>(Arrays.asList(
            "M",   // 1000
            "CM",  // 900
            "D",   // 500
            "CD",  // 400
            "C",   // 100
            "XC",  // 90
            "L",   // 50
            "XL",  // 40
            "X",   // 10
            "IX",  // 9
            "V",   // 5
            "IV",  // 4
            "I")); // 1
    private static final List<Integer> romanLetterValues = new ArrayList<>(Arrays.asList(
            1000,
            900,
            500,
            400,
            100,
            90,
            50,
            40,
            10,
            9,
            5,
            4,
            1));

    public static String toRomanNotation(int number)
    {
        if (number > 5999 || number < 1) return "";
        int index = 0;
        while (number / romanLetterValues.get(index) == 0) index++;
        return romanLetterList.get(index) + toRomanNotation(number - romanLetterValues.get(index));
    }
}