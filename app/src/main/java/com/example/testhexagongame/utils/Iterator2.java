package com.example.testhexagongame.utils;

import java.util.ArrayList;

public class Iterator2<T> {
    private ArrayList<T> elements;
    private int index;

    public Iterator2(ArrayList<T> elements) {
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
