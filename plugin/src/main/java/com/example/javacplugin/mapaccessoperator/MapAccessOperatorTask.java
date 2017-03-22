package com.example.javacplugin.mapaccessoperator;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.tree.JCTree;

public class MapAccessOperatorTask implements TaskListener {

    private final MapAccessReplacer replacer;

    public MapAccessOperatorTask(JavacTask task) {
        this.replacer = new MapAccessReplacer(task);
    }

    @Override
    public void started(TaskEvent taskEvent) {

    }

    @Override
    public void finished(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.PARSE) {
            CompilationUnitTree compilationUnit = event.getCompilationUnit();
            ((JCTree.JCCompilationUnit) compilationUnit).accept(replacer);
        }
    }
}
