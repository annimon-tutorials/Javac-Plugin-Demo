package com.example.javacplugin.operatoroverloading;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

public class OperatorOverloadingPlugin implements Plugin {

    @Override
    public String getName() {
        return "OperatorOverloading";
    }

    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new OperatorOverloadingTask(task));
    }

}
