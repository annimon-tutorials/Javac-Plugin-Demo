package com.example.javacplugin.extensionmethods;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

public class ExtensionMethodsPlugin implements Plugin {

    @Override
    public String getName() {
        return "ExtensionMethods";
    }

    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new ExtensionMethodsTask(task));
    }
}
