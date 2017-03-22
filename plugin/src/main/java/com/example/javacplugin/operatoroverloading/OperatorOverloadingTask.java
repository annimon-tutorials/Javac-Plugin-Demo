package com.example.javacplugin.operatoroverloading;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.tree.JCTree;

public final class OperatorOverloadingTask implements TaskListener {

    private final OperatorOverloadingVisitor visitor;

    public OperatorOverloadingTask(JavacTask task) {
        this.visitor = new OperatorOverloadingVisitor(task);
    }

    @Override
    public void started(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.ANALYZE) {
            CompilationUnitTree compilationUnit = event.getCompilationUnit();
            ((JCTree.JCCompilationUnit) compilationUnit).accept(visitor);
        }
    }

    @Override
    public void finished(TaskEvent event) {
    }

}
