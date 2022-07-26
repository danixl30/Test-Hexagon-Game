package com.example.testhexagongame.hexagon.center;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

import java.util.ArrayList;
import java.util.Objects;

public class HexagonCenter<T extends Shape> implements Region {
    private final ArrayList<Box<T, String>> triangles = new ArrayList<>();
    @Override
    public boolean check() {
        String color = triangles.get(0).getData();
        if (Objects.equals(color, GRAY_BASE)) return false;
        for (Box<T, String> triangle : triangles) {
            if (!Objects.equals(triangle.getData(), color)) return false;
        }
        return true;
    }

    public void addTriangle(Box<T, String> triangle) {
        if (triangles.size() < 6) triangles.add(triangle);
    }

    @Override
    public void empty() {
        System.out.println("here");
        triangles.forEach(e -> e.setData(GRAY_BASE));
    }
}
