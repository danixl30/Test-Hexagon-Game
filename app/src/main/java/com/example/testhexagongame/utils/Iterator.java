package com.example.testhexagongame.utils;

import java.util.ArrayList;

public class Iterator<T> {
    private ArrayList<T> elements;
    private int index;

    public Iterator(ArrayList<T> elements) {
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
