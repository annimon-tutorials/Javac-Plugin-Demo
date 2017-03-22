package com.example.javacplugin.methodscounter;

public class MethodStatistics {

    private final String name;
    private final int methodsCount;

    public MethodStatistics(String name, int methodsCount) {
        this.name = name;
        this.methodsCount = methodsCount;
    }

    public String getName() {
        return name;
    }

    public int getMethodsCount() {
        return methodsCount;
    }

    @Override
    public String toString() {
        return name + ": " + methodsCount;
    }
}
