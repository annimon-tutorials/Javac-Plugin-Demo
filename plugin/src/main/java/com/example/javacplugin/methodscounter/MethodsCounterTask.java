package com.example.javacplugin.methodscounter;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import java.util.Map;
import java.util.function.Consumer;

public class MethodsCounterTask implements TaskListener {

    private final Map<String, Integer> stat;
    private Consumer<Map<String, Integer>> consumer;

    public MethodsCounterTask(Map<String, Integer> stat) {
        this.stat = stat;
    }

    public void onComplete(Consumer<Map<String, Integer>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void started(TaskEvent taskEvent) {

    }

    @Override
    public void finished(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.PARSE) {
            CompilationUnitTree compilationUnit = event.getCompilationUnit();
            MutableInteger counter = new MutableInteger();
            compilationUnit.accept(new MethodsVisitor(), counter);
            stat.put(event.getSourceFile().getName(), counter.get());
        }
        if (event.getKind() == TaskEvent.Kind.GENERATE) {
            if (consumer != null) {
                consumer.accept(stat);
                consumer = null;
            }
        }
    }
}
