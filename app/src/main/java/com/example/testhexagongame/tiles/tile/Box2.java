package com.example.testhexagongame.tiles.tile;

import com.example.testhexagongame.tiles.tile.Shape.Shape2;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Box2<T extends Shape2, U> {
    private final T shape;
    private final Observer<U> data;

    public Box2(T shape, U data) {
        this.shape = shape;
        this.data = new Observer<>(data);
    }

    public U getData() {
        return data.getValue();
    }

    public void setData(U data) {
        this.data.setValue(data);
    }

    public void subscribeOnColorChange(Function1<U, Unit> callback) {
        data.subscribe(callback);
    }

    public void setRotation(Integer rotation) {
        shape.setRotation(rotation);
    }

    public Integer getRotation() {
        return shape.getCurrentRotation();
    }

    public void setAdjacent(String side, Box2<T, U> node) {
        shape.setAdjacent(side, node);
    }

    public Box2<T, U> getAdjacent(String side) {
        return shape.getSide(side);
    }

    public ArrayList<Box2<T, U>> getByRoute(ArrayList<String> path) {
        ArrayList<Box2<T, U>> list = new ArrayList<Box2<T, U>>();
        list.add(this);
        if (path.size() == 0) return list;
        String current = path.get(0);
        path.remove(0);
        Box2<T, U> next = shape.getSide(current);
        if (next == null) return list;
        ArrayList nodes = next.getByRoute(path);
        list.addAll(nodes);
        return list;
    }
}
