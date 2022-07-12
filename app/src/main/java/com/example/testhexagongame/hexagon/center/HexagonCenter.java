package com.example.testhexagongame.hexagon.center;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import android.os.Build;

import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Shape2;

import java.util.ArrayList;
import java.util.Objects;

public class HexagonCenter<T extends Shape2> implements Region {
    private ArrayList<Box2<T>> triangles = new ArrayList<>();
    @Override
    public boolean check() {
        String color = triangles.get(0).getColor();
        if (Objects.equals(color, GRAY_BASE)) return false;
        for (Box2<T> triangle : triangles) {
            if (!Objects.equals(triangle.getColor(), color)) return false;
        }
        return true;
    }

    public void addTriangle(Box2<T> triangle) {
        if (triangles.size() < 6) triangles.add(triangle);
    }

    @Override
    public void empty() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            triangles.forEach(e -> e.setColor(GRAY_BASE));
        }
    }
}
