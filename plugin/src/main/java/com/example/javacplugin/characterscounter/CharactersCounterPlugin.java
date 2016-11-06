package com.example.javacplugin.characterscounter;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import java.util.concurrent.ConcurrentHashMap;

public class CharactersCounterPlugin implements Plugin {

    @Override
    public String getName() {
        return "CharStat";
    }

    @Override
    public void init(JavacTask task, String... args) {
        final CharactersCounterTask counterTask = new CharactersCounterTask(new ConcurrentHashMap<>());
        task.addTaskListener(counterTask);
        counterTask.onComplete(classCharsCount -> {
            System.out.println("Characters count statistics: ");
            System.out.format("\t processed %d files%n", classCharsCount.size());
            System.out.format("\t summary %d chars%n", classCharsCount.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum());
        });
    }

}
