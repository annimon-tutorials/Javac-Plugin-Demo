package com.example.javacplugin.extensionmethods;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;

public final class ExtensionMethodsTask implements TaskListener {

     private final ExtensionMethodVisitor visitor;

    public ExtensionMethodsTask(JavacTask task) {
        this.visitor = new ExtensionMethodVisitor(task);
    }

    @Override
    public void started(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.ANALYZE) {
            CompilationUnitTree compilationUnit = event.getCompilationUnit();
            visitor.scan(compilationUnit, null);
        }
    }

    @Override
    public void finished(TaskEvent event) {
    }
}
