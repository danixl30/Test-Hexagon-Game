package com.example.testhexagongame.utils;

import java.util.List;

public class Iterator2<T> {
    private final List<T> elements;
    private int index;

    public Iterator2(List<T> elements) {
        this.elements = elements;
        this.index = 0;
    }

    public boolean hasNext() {
        return elements.size() > index;
    }
    public T getNext() {
        return elements.get(index++);
    }
    public void reset(){
        index = 0;
    }
}
