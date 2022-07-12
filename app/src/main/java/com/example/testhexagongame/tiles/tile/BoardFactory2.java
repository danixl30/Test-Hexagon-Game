package com.example.testhexagongame.tiles.tile;

import com.example.testhexagongame.Factories.Factory;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Pair;

public class BoardFactory2 implements Factory<Box2<Triangle>> {
    private Pair<Box2<Triangle>, ArrayList<Box2<Triangle>>> createRow(
            ArrayList<Box2<Triangle>> tops,
            Box2<Triangle> prev,
            Integer current,
            Integer last,
            Boolean invertedType
    ) {
        Box2<Triangle> triangle1 = new Box2<Triangle>(new Triangle());
        if (prev != null) triangle1.setAdjacent("left", prev);
        triangle1.setRotation(!invertedType ? 0 : 180);
        if (invertedType && tops.size() > 0) {
            Box2<Triangle> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle1.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle1);
        }
        ArrayList<Box2<Triangle>> list = new ArrayList<>();
        if (!invertedType) list.add(triangle1);
        if (Objects.equals(current, last) && !invertedType) return new Pair<Box2<Triangle>, ArrayList<Box2<Triangle>>>(triangle1, list);
        if (Objects.equals(current, last) && invertedType) return new Pair<Box2<Triangle>, ArrayList<Box2<Triangle>>>(triangle1, list);
        Box2<Triangle> triangle2 = new Box2<Triangle>(new Triangle());
        triangle2.setAdjacent("left", triangle1);
        triangle1.setAdjacent("right", triangle2);
        triangle2.setRotation((invertedType) ? 0 : 180);
        if (!invertedType && tops.size() > 0) {
            Box2<Triangle> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle2.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle2);
        }
        Pair<Box2<Triangle>, ArrayList<Box2<Triangle>>> res = createRow(tops, triangle2, current+1, last, invertedType);
        triangle2.setAdjacent("right", res.component1());
        if (invertedType) list.add(triangle2);
        list.addAll(res.component2());
        return new Pair<Box2<Triangle>, ArrayList<Box2<Triangle>>>(triangle1, list);
    }

    private Box2<Triangle> setTriangleCol() {
        Box2<Triangle> first = null;
        Pair<Box2<Triangle>, ArrayList<Box2<Triangle>>> temp = new Pair<>(new Box2<Triangle>(new Triangle()), new ArrayList<>());
        for (int i = 3; i <= 5; i++) {
            temp = createRow(temp.component2(), null, 1, i , false);
            if (first == null) first = temp.component1();
        }
        for (int i = 5; i >= 3; i--) {
            temp = createRow(temp.component2(), null, 1, i , true);
        }
        return first;
    }
    @Override
    public Box2<Triangle> create() {
        return setTriangleCol();
    }
}
