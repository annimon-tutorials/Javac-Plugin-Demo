package com.example.javacplugin.methodscounter;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class MethodsCounterPlugin implements Plugin {

    @Override
    public String getName() {
        return "MethodsCount";
    }

    @Override
    public void init(JavacTask task, String... args) {
        MethodsCounterTask counterTask = new MethodsCounterTask(new ConcurrentHashMap<>());
        task.addTaskListener(counterTask);
        counterTask.onComplete(stat -> {
            System.out.println("Methods count statistics: ");
            System.out.format("\t processed %d files%n", stat.size());
            System.out.format("\t summary %d methods%n", stat.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum());
            stat.entrySet().stream()
                    .map(e -> new MethodStatistics(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparingInt(MethodStatistics::getMethodsCount).reversed())
                    .forEach(System.out::println);
        });
    }
}
