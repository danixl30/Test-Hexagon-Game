package com.example.testhexagongame.hexagon.center;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

import java.util.ArrayList;
import java.util.Objects;

public class HexagonCenter<T extends Shape> implements Region {
    private final ArrayList<Box<T, String, String>> boxes = new ArrayList<>();
    @Override
    public boolean check() {
        String color = boxes.get(0).getData();
        if (Objects.equals(color, GRAY_BASE)) return false;
        for (Box<T, String, String> triangle : boxes) {
            if (!Objects.equals(triangle.getData(), color)) return false;
        }
        return true;
    }

    public void addBox(Box<T, String, String> box) {
        if (boxes.size() < 6) boxes.add(box);
    }

    @Override
    public void empty() {
        boxes.forEach(e -> e.setData(GRAY_BASE));
    }
}
