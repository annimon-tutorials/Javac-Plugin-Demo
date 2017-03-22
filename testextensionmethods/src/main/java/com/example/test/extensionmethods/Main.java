package com.example.test.extensionmethods;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println("JAVA IS AWESOME".toTitleCase());
    }

    public static String toTitleCase(String s) {
        return Arrays.stream(s.toLowerCase().split(" "))
                .map(str -> String.valueOf(Character.toTitleCase(str.charAt(0))).concat(str.substring(1)))
                .collect(Collectors.joining(" "));
    }
}
