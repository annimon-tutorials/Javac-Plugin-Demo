package com.example.javacplugin.characterscounter;

import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import javax.tools.JavaFileObject;

public class CharactersCounterTask implements TaskListener {

    private final Map<String, Integer> stat;
    private Consumer<Map<String, Integer>> consumer;

    public CharactersCounterTask(Map<String, Integer> stat) {
        this.stat = stat;
    }

    public void onComplete(Consumer<Map<String, Integer>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void started(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.PARSE) {
            final JavaFileObject source = event.getSourceFile();
            final String name = source.getName();
            try {
                stat.put(name, source.getCharContent(true).length());
                System.out.println(name + " processed");
            } catch (IOException ex) {
                System.out.println(name + " fail");
            }
        }
    }

    @Override
    public void finished(TaskEvent event) {
        if (event.getKind() == TaskEvent.Kind.GENERATE) {
            if (consumer != null) {
                consumer.accept(stat);
                consumer = null;
            }
        }
    }

}
