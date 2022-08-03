package com.example.testhexagongame.tiles.tile;

import com.example.testhexagongame.tiles.tile.Shape.Shape;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Box<T extends Shape, U, Sides> {
    private final T shape;
    private final Observer<U> data;

    public Box(T shape, U data) {
        this.shape = shape;
        this.data = new Observer<>(data);
    }

    public U getData() {
        return data.getValue();
    }

    public void setData(U data) {
        this.data.setValue(data);
    }

    public void subscribeOnDataChange(Function1<U, Unit> callback) {
        data.subscribe(callback);
    }

    public void setRotation(Integer rotation) {
        shape.setRotation(rotation);
    }

    public Integer getRotation() {
        return shape.getCurrentRotation();
    }

    public void setAdjacent(String side, Box<T, U, Sides> node) {
        shape.setAdjacent(side, node);
    }

    public Box<T, U, Sides> getAdjacent(String side) {
        return shape.getSide(side);
    }

    public ArrayList<Box<T, U, Sides>> getByRoute(ArrayList<String> path) {
        ArrayList<Box<T, U, Sides>> list = new ArrayList<>();
        list.add(this);
        if (path.size() == 0) return list;
        String current = path.get(0);
        path.remove(0);
        Box<T, U, Sides> next = shape.getSide(current);
        if (next == null) return list;
        ArrayList nodes = next.getByRoute(path);
        list.addAll(nodes);
        return list;
    }

    public ArrayList<Box<T, U, Sides>> getAdjacents() {
        return shape.getAdjacents();
    }
}
