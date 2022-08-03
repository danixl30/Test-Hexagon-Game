package com.example.testhexagongame.BoardCheck;

import com.example.testhexagongame.piece.PieceFlippable;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

import java.util.ArrayList;

public interface BoardChecker<T extends Shape, U, Sides, W extends PieceFlippable> {
    public Boolean check(Box<T, U, Sides> current, ArrayList<W> pieces);
}
