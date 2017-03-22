package com.example.javacplugin.methodscounter;

import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreeScanner;

public class MethodsVisitor extends TreeScanner<Void, MutableInteger> {

    @Override
    public Void visitMethod(MethodTree methodTree, MutableInteger input) {
        input.increment();
        return super.visitMethod(methodTree, input);
    }
}
