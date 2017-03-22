package com.example.javacplugin.mapaccessoperator;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

public class MapAccessOperatorPlugin implements Plugin {

    @Override
    public String getName() {
        return "MapAccess";
    }

    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new MapAccessOperatorTask(task));
    }
}
