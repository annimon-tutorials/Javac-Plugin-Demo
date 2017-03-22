package com.example.javacplugin.methodscounter;

import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreeScanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MethodsVisitor extends TreeScanner<Void, AtomicInteger> {

    @Override
    public Void visitMethod(MethodTree methodTree, AtomicInteger input) {
        input.incrementAndGet();
        return super.visitMethod(methodTree, input);
    }
}
