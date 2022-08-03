package com.example.testhexagongame.piece;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;
import static com.example.testhexagongame.ui.theme.ColorKt.TRANSPARENT;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.Objects;

public class PieceColorChecker implements DataChecker<Triangle, String, String> {
    @Override
    public Boolean check(Box<Triangle, String, String> triangle1, Box<Triangle, String, String> triangle2) {
        if (Objects.equals(triangle2.getData(), TRANSPARENT)) return true;
        return triangle1 != null && Objects.equals(triangle1.getData(), GRAY_BASE);
    }
}
