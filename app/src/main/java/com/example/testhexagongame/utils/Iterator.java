package com.example.testhexagongame.utils;

import java.util.List;

public class Iterator<T> {
    private List<T> elements;
    private int index;

    public Iterator(List<T> elements) {
        this.elements = elements;
        this.index = 0;
    }

    boolean hasNext() {
        return elements.size() > index;
    }
    T getNext() {
        return elements.get(index++);
    }
    void reset(){
        index = 0;
    }
}
