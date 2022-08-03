package com.example.testhexagongame.utils;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Observer<T> {
    private final ArrayList<Function1<T, Unit>> subscribers = new ArrayList<>();
    private T data;

    public Observer(T data) {
        this.data = data;
    }

    public void subscribe(Function1<T, Unit> callback) {
        subscribers.add(callback);
    }

    public T getValue() {
        return data;
    }

    public void setValue(T data) {
        this.data = data;
        publish();
    }

    private void publish() {
        subscribers.forEach(e -> e.invoke(data));
    }
}
