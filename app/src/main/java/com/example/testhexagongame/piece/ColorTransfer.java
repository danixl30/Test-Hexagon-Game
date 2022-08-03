package com.example.testhexagongame.piece;

import static com.example.testhexagongame.ui.theme.ColorKt.TRANSPARENT;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.Objects;

public class ColorTransfer implements DataTransfer<Triangle, String, String> {
    @Override
    public void transfer(Box<Triangle, String, String> triangle1, Box<Triangle, String, String> triangle2) {
        if (!Objects.equals(triangle2.getData(), TRANSPARENT) && triangle1 != null) triangle1.setData(triangle2.getData());
    }
}
