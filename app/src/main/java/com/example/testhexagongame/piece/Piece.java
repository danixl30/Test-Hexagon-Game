package com.example.testhexagongame.piece;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;
import static com.example.testhexagongame.ui.theme.ColorKt.TRANSPARENT;

import androidx.annotation.Nullable;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Pair;
import kotlin.Triple;

public class Piece implements PieceFlippable2<Triangle, String> {

    private Box2<Triangle, String> triangle = null;

    public Piece() {
        create();
    }

    private Pair<Box2<Triangle, String>, ArrayList<Box2<Triangle, String>>> setTriangleRow(
            ArrayList<Box2<Triangle, String>> tops,
            Box2<Triangle, String> prev,
            Integer current,
            Integer last,
            Boolean invertedType
    ) {
        Box2<Triangle, String> triangle1 = new Box2<>(new Triangle(), TRANSPARENT);
        if (prev != null) triangle1.setAdjacent("left", prev);
        triangle1.setRotation(!invertedType ? 0 : 180);
        if (invertedType && tops.size() > 0) {
            Box2<Triangle, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle1.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle1);
        }
        ArrayList<Box2<Triangle, String>> list = new ArrayList<>();
        if (!invertedType) list.add(triangle1);
        if (Objects.equals(current, last) && !invertedType) return new Pair<>(triangle1, list);
        if (Objects.equals(current, last) && invertedType) return new Pair<>(triangle1, list);
        Box2<Triangle, String> triangle2 = new Box2<>(new Triangle(), TRANSPARENT);
        triangle2.setAdjacent("left", triangle1);
        triangle1.setAdjacent("right", triangle2);
        triangle2.setRotation((invertedType) ? 0 : 180);
        if (!invertedType && tops.size() > 0) {
            Box2<Triangle, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle2.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle2);
        }
        Pair<Box2<Triangle, String>, ArrayList<Box2<Triangle, String>>> res = setTriangleRow(tops, triangle2, current+1, last, invertedType);
        triangle2.setAdjacent("right", res.component1());
        if (invertedType) list.add(triangle2);
        list.addAll(res.component2());
        return new Pair<>(triangle1, list);
    }

    private void setTriangleCol() {
        Pair<Box2<Triangle, String>, ArrayList<Box2<Triangle, String>>> temp = setTriangleRow(new ArrayList<>(), null, 1, 2 , false);
        setTriangleRow(temp.component2(), null, 1, 2, true);
        triangle = temp.component1();
    }

    @Override
    public void create() {
        setTriangleCol();
    }

    @Override
    public Boolean putPiece(Box2<Triangle, String> current) {
        return checkAll(current, triangle.getAdjacent("right"));
    }

    @Override
    public void flip() {
        String color = "";
        ArrayList<String> path = new ArrayList<>();
        path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
        ArrayList<Box2<Triangle, String>> triangles = triangle.getByRoute(path);
        for (Box2<Triangle, String> triangleItem : triangles) {
            String temp = triangleItem.getData();
            if (!Objects.equals(color, "")) triangleItem.setData(color);
            color = temp;
        }
        triangles.get(0).setData(color);
    }

    private Boolean checkSingle(Box2<Triangle, String> triangle1, Box2<Triangle, String> triangle2) {
        if (Objects.equals(triangle2.getData(), TRANSPARENT)) return true;
        return triangle1 != null && Objects.equals(triangle1.getData(), GRAY_BASE);
    }

    private Triple<Boolean, ArrayList<Box2<Triangle, String>>, ArrayList<Box2<Triangle, String>>> validatePath(Box2<Triangle, String> current, Box2<Triangle, String> piece, ArrayList<String> path) {
        ArrayList<String> path1 = new ArrayList<String>(path);
        ArrayList<Box2<Triangle, String>> trianglesPiece = piece.getByRoute(path1);
        path1.addAll(path);
        ArrayList<Box2<Triangle, String>> trianglesBoard = current.getByRoute(path1);
        while (trianglesBoard.size() < 6) trianglesBoard.add(null);
        for (int i = 0; i < trianglesPiece.size(); i++) {
            Boolean res = checkSingle(trianglesBoard.get(i), trianglesPiece.get(i));
            if (!res) return new Triple<>(false, trianglesBoard, trianglesPiece);
        }
        return new Triple<>(true, trianglesBoard, trianglesPiece);
    }

    private Boolean checkAll(Box2<Triangle, String> current, Box2<Triangle, String> piece) {
        if (!Objects.equals(current.getRotation(), piece.getRotation())) return false;
        ArrayList<String> route1 = new ArrayList<>();
        route1.add("right");route1.add("right");route1.add("base");route1.add("left");route1.add("left");
        ArrayList<String> route2 = new ArrayList<>();
        route2.add("left");route2.add("base");route2.add("right");route2.add("right");route2.add("base");
        Triple<Boolean, ArrayList<Box2<Triangle, String>>, ArrayList<Box2<Triangle, String>>> res1 = validatePath(current, piece, route1);
        if (res1.component1()) {
            colorAll(res1.component2(), res1.component3());
            return true;
        }
        Triple<Boolean, ArrayList<Box2<Triangle, String>>, ArrayList<Box2<Triangle, String>>> res2 = validatePath(current, piece, route2);
        if (res2.component1()) {
            colorAll(res2.component2(), res2.component3());
            return true;
        }
        return false;
    }

    private void colorSingle(Box2<Triangle, String> current, Box2<Triangle, String> piece) {
        if (Objects.equals(piece.getData(), TRANSPARENT)) return;
                if (current != null ) current.setData(piece.getData());
    }

    private void colorAll(ArrayList<Box2<Triangle, String>> trianglesBoard, ArrayList<Box2<Triangle, String>> trianglesPiece) {
        for (int i = 0; i < trianglesPiece.size(); i++) {
            colorSingle(trianglesBoard.get(i), trianglesPiece.get(i));
        }
    }
}
