package com.example.testhexagongame.BoardCheck;

import com.example.testhexagongame.piece.PieceFlippable2;
import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Shape2;

import java.util.ArrayList;

public interface BoardChecker<T extends Shape2, U, W extends PieceFlippable2> {
    public Boolean check(Box2<T, U> current, ArrayList<W> pieces);
}
