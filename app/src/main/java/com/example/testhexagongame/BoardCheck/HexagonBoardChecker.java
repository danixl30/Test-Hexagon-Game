package com.example.testhexagongame.BoardCheck;

import com.example.testhexagongame.piece.PieceFlippable2;
import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;

public class HexagonBoardChecker<T extends PieceFlippable2<Triangle, String>> implements BoardChecker<Triangle, String, T> {

    private Boolean checkBox(Box2<Triangle, String> current, T piece, ArrayList<Box2<Triangle, String>> visited) {
        if (visited.contains(current)) return false;
        visited.add(current);
        for (int i = 1; i <= 6; i++) {
            Boolean res = piece.isFit(current);
            if (res) {
                if (i != 1) for (int j = 1; j <= 6-i; j++) piece.flip();
                return true;
            }
            piece.flip();
        }
        for (Box2<Triangle, String> side: current.getAdjacents()) {
            Boolean res = checkBox(side, piece, visited);
            if (res) return true;
        }
        return false;
    }

    private Boolean checkPieces(Box2<Triangle, String> current, ArrayList<T> pieces) {
        for (T piece : pieces) {
            ArrayList<Box2<Triangle, String>> visited = new ArrayList<>();
            Boolean res = checkBox(current, piece, visited);
            if (res) return true;
        }
        return false;
    }
    @Override
    public Boolean check(Box2<Triangle, String> current, ArrayList<T> pieces) {
        return checkPieces(current, pieces);
    }
}
