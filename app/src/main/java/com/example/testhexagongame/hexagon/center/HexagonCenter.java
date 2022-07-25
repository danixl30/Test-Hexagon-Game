package com.example.testhexagongame.hexagon.center;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import android.os.Build;

import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Shape2;

import java.util.ArrayList;
import java.util.Objects;

public class HexagonCenter<T extends Shape2> implements Region {
    private final ArrayList<Box2<T, String>> triangles = new ArrayList<>();
    @Override
    public boolean check() {
        String color = triangles.get(0).getData();
        if (Objects.equals(color, GRAY_BASE)) return false;
        for (Box2<T, String> triangle : triangles) {
            if (!Objects.equals(triangle.getData(), color)) return false;
        }
        return true;
    }

    public void addTriangle(Box2<T, String> triangle) {
        if (triangles.size() < 6) triangles.add(triangle);
    }

    @Override
    public void empty() {
        System.out.println("here");
        triangles.forEach(e -> e.setData(GRAY_BASE));
    }
}
