package com.example.javacplugin.extensionmethods;

import java.util.ArrayList;
import java.util.stream.Stream;

public final class ExtensionMethods extends ArrayList<ExtensionMethod> {

    public Stream<String> names() {
        return stream().map(ExtensionMethod::getName);
    }
}
