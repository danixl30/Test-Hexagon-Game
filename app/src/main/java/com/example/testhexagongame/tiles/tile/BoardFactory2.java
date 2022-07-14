package com.example.testhexagongame.tiles.tile;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import com.example.testhexagongame.Factories.Factory;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Pair;

public class BoardFactory2 implements Factory<Box2<Triangle, String>> {
    private Pair<Box2<Triangle, String>, ArrayList<Box2<Triangle, String>>> createRow(
            ArrayList<Box2<Triangle, String>> tops,
            Box2<Triangle, String> prev,
            Integer current,
            Integer last,
            Boolean invertedType
    ) {
        Box2<Triangle, String> triangle1 = new Box2<>(new Triangle(), GRAY_BASE);
        if (prev != null) triangle1.setAdjacent("left", prev);
        triangle1.setRotation(!invertedType ? 0 : 180);
        if (invertedType && tops.size() > 0) {
            Box2<Triangle, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle1.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle1);
        }
        ArrayList<Box2<Triangle, String>> list = new ArrayList<>();
        if (!invertedType) list.add(triangle1);
        if (Objects.equals(current, last) && !invertedType) return new Pair<>(triangle1, list);
        if (Objects.equals(current, last) && invertedType) return new Pair<>(triangle1, list);
        Box2<Triangle, String> triangle2 = new Box2<>(new Triangle(), GRAY_BASE);
        triangle2.setAdjacent("left", triangle1);
        triangle1.setAdjacent("right", triangle2);
        triangle2.setRotation((invertedType) ? 0 : 180);
        if (!invertedType && tops.size() > 0) {
            Box2<Triangle, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle2.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle2);
        }
        Pair<Box2<Triangle, String>, ArrayList<Box2<Triangle, String>>> res = createRow(tops, triangle2, current+1, last, invertedType);
        triangle2.setAdjacent("right", res.component1());
        if (invertedType) list.add(triangle2);
        list.addAll(res.component2());
        return new Pair<>(triangle1, list);
    }

    private Box2<Triangle, String> setTriangleCol() {
        Box2<Triangle, String> first = null;
        Pair<Box2<Triangle, String>, ArrayList<Box2<Triangle, String>>> temp = new Pair<>(new Box2<Triangle, String>(new Triangle(), ""), new ArrayList<>());
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
    public Box2<Triangle, String> create() {
        return setTriangleCol();
    }
}
