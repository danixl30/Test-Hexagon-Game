package com.example.testhexagongame.BoardCheck;

import com.example.testhexagongame.piece.PieceFlippable;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;

public class HexagonBoardChecker<T extends PieceFlippable<Triangle, String>> implements BoardChecker<Triangle, String, T> {

    private Boolean checkBox(Box<Triangle, String> current, T piece, ArrayList<Box<Triangle, String>> visited) {
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
        for (Box<Triangle, String> side: current.getAdjacents()) {
            Boolean res = checkBox(side, piece, visited);
            if (res) return true;
        }
        return false;
    }

    private Boolean checkPieces(Box<Triangle, String> current, ArrayList<T> pieces) {
        for (T piece : pieces) {
            ArrayList<Box<Triangle, String>> visited = new ArrayList<>();
            Boolean res = checkBox(current, piece, visited);
            if (res) return true;
        }
        return false;
    }
    @Override
    public Boolean check(Box<Triangle, String> current, ArrayList<T> pieces) {
        return checkPieces(current, pieces);
    }
}
