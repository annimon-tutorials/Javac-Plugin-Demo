package com.example.javacplugin.extensionmethods;

import javax.lang.model.element.ExecutableElement;

public class ExtensionMethod {

    private final String name;
    private final int parametersCount;
    private final ExecutableElement element;

    public ExtensionMethod(ExecutableElement element) {
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.parametersCount = element.getParameters().size();
    }

    public String getName() {
        return name;
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public ExecutableElement getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "ExtensionMethod{" + "name=" + name + ", element=" + element + '}';
    }
}
