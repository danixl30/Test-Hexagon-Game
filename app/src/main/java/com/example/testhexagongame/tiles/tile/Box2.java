package com.example.testhexagongame.tiles.tile;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import androidx.compose.ui.text.android.style.PlaceholderSpan;

import com.example.testhexagongame.tiles.tile.Shape.Shape2;
import com.example.testhexagongame.utils.Observer;

import java.util.ArrayList;

import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Box2<T extends Shape2> {
    private final T shape;
    private final Observer<String> color = new Observer<>(GRAY_BASE);

    public Box2(T shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color.getValue();
    }

    public void setColor(String color) {
        this.color.setValue(color);
    }

    public void subscribeOnColorChange(Function1<? super String, Unit> callback) {
        color.subscribe(callback);
    }

    public void setRotation(Integer rotation) {
        shape.setRotation(rotation);
    }

    public Integer getRotation() {
        return shape.getCurrentRotation();
    }

    public void setAdjacent(String side, Box2<T> node) {
        shape.setAdjacent(side, node);
    }

    public Box2<T> getAdjacent(String side) {
        return shape.getSide(side);
    }

    public ArrayList<Box2<T>> getByRoute(ArrayList<String> path) {
        ArrayList<Box2<T>> list = new ArrayList<Box2<T>>();
        list.add(this);
        if (path.size() == 0) return list;
        String current = path.get(0);
        path.remove(0);
        Box2<T> next = shape.getSide(current);
        if (next == null) return list;
        ArrayList<Box2<T>> nodes = next.getByRoute(path);
        list.addAll(nodes);
        return list;
    }
}
