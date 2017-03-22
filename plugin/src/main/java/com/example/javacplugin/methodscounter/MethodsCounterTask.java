package com.example.javacplugin.methodscounter;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import java.util.concurrent.atomic.AtomicInteger;

public class MethodsCounterTask implements TaskListener {

    @Override
    public void started(TaskEvent taskEvent) {

    }

    @Override
    public void finished(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.PARSE) {
            CompilationUnitTree compilationUnit = event.getCompilationUnit();
            AtomicInteger counter = new AtomicInteger(0);
            compilationUnit.accept(new MethodsVisitor(), counter);

            System.out.format("%s: %d methods%n", event.getSourceFile().getName(), counter.get());
        }
    }
}
