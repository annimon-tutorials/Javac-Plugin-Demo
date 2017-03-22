package com.example.javacplugin.methodscounter;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

public class MethodsCounterPlugin implements Plugin {

    @Override
    public String getName() {
        return "MethodsCount";
    }

    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new MethodsCounterTask());
    }
}
