package com.example.test.extensionmethods;

public class IntPair<T> {
    final int first;
    final T second;

    public IntPair(int first, T second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}