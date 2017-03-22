package com.example.test.extensionmethods;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        System.out.println("JAVA IS AWESOME".toTitleCase());

        Stream.of("sdo".reverse(), "ogvj".rotate(-2), " ", "[\\V`[RaeR".rotate(19).reverse())
                .reverse()
                .forEach(System.out::print);
    }

    public static String toTitleCase(String s) {
        return Arrays.stream(s.toLowerCase().split(" "))
                .map(str -> String.valueOf(Character.toTitleCase(str.charAt(0))).concat(str.substring(1)))
                .collect(Collectors.joining(" "));
    }

    public static String rotate(String input, int value) {
        return input.chars()
                .mapToObj(i -> String.valueOf((char)(i + value)))
                .collect(Collectors.joining());
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static <T> Stream<T> reverse(Stream<T> stream) {
        return stream
                .indexed()
                .sortBy(p -> -p.getFirst())
                .map(p -> p.getSecond());
    }

    public static <T, R extends Comparable<? super R>> Stream<T> sortBy(
            Stream<T> stream, Function<? super T, ? extends R> f) {
        return stream.sorted(Comparator.comparing(f::apply));
    }

    public static <T> Stream<IntPair<T>> indexed(Stream<T> stream) {
        return stream.map(new Function<T, IntPair<T>>() {

            private int counter = 0;

            @Override
            public IntPair<T> apply(T t) {
                return new IntPair<>(counter++, t);
            }
        });
    }
}
