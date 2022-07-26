package com.example.testhexagongame.piece;

import static com.example.testhexagongame.ui.theme.ColorKt.GRAY_BASE;
import static com.example.testhexagongame.ui.theme.ColorKt.TRANSPARENT;

import androidx.annotation.NonNull;

import com.example.testhexagongame.Color.ColorGenerator;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Objects;

import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.functions.Function2;

public class Piece implements PieceFlippable<Triangle, String> {

    private Box<Triangle, String> triangle = null;
    private final ColorGenerator colorGenerator;
    private final Function2<Integer, Integer, Integer> getRandom;
    private final ArrayList<ArrayList<Integer>> options;

    public Piece(ColorGenerator colorGenerator, Function2<Integer, Integer, Integer> getRandom, ArrayList<ArrayList<Integer>> options) {
        this.colorGenerator = colorGenerator;
        this.getRandom = getRandom;
        this.options = options;
        create();
    }

    Box<Triangle, String> getTriangles() {
        return triangle;
    }

    @NonNull
    @Contract("_, _, _, _, _ -> new")
    private Pair<Box<Triangle, String>, ArrayList<Box<Triangle, String>>> setTriangleRow(
            ArrayList<Box<Triangle, String>> tops,
            Box<Triangle, String> prev,
            Integer current,
            Integer last,
            Boolean invertedType
    ) {
        Box<Triangle, String> triangle1 = new Box<>(new Triangle(), TRANSPARENT);
        if (prev != null) triangle1.setAdjacent("left", prev);
        triangle1.setRotation(!invertedType ? 0 : 180);
        if (invertedType && tops.size() > 0) {
            Box<Triangle, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle1.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle1);
        }
        ArrayList<Box<Triangle, String>> list = new ArrayList<>();
        if (!invertedType) list.add(triangle1);
        if (Objects.equals(current, last) && !invertedType) return new Pair<>(triangle1, list);
        if (Objects.equals(current, last) && invertedType) return new Pair<>(triangle1, list);
        Box<Triangle, String> triangle2 = new Box<>(new Triangle(), TRANSPARENT);
        triangle2.setAdjacent("left", triangle1);
        triangle1.setAdjacent("right", triangle2);
        triangle2.setRotation((invertedType) ? 0 : 180);
        if (!invertedType && tops.size() > 0) {
            Box<Triangle, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle2.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle2);
        }
        Pair<Box<Triangle, String>, ArrayList<Box<Triangle, String>>> res = setTriangleRow(tops, triangle2, current+1, last, invertedType);
        triangle2.setAdjacent("right", res.component1());
        if (invertedType) list.add(triangle2);
        list.addAll(res.component2());
        return new Pair<>(triangle1, list);
    }

    private void setTriangleCol() {
        Pair<Box<Triangle, String>, ArrayList<Box<Triangle, String>>> temp = setTriangleRow(new ArrayList<>(), null, 1, 2 , false);
        setTriangleRow(temp.component2(), null, 1, 2, true);
        triangle = temp.component1();
    }

    private void setColor() {
        String color = colorGenerator.getColorRandom();
        ArrayList<String> path = new ArrayList<>();
        path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
        ArrayList<Box<Triangle, String>> triangles = triangle.getByRoute(path);
        ArrayList<Integer> option = options.get(getRandom.invoke(0, options.size()-1));
        for (int i = 0; i < option.size(); i++) {
            if (option.get(i) == 1) triangles.get(i).setData(color);
        }
    }

    @Override
    public void create() {
        setTriangleCol();
        setColor();
    }

    @Override
    public Boolean putPiece(Box<Triangle, String> current) {
        Triple<Boolean, ArrayList<Box<Triangle, String>>, ArrayList<Box<Triangle, String>>> res = checkAll(current, triangle.getAdjacent("right"));
        if (res.component1()) {
            colorAll(res.component2(), res.component3());
            return true;
        }
        return false;
    }

    @Override
    public Boolean isFit(Box<Triangle, String> current) {
        return checkAll(current, triangle.getAdjacent("right")).component1();
    }


    @Override
    public void flip() {
        String color = "";
        ArrayList<String> path = new ArrayList<>();
        path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
        ArrayList<Box<Triangle, String>> triangles = triangle.getByRoute(path);
        for (Box<Triangle, String> triangleItem : triangles) {
            String temp = triangleItem.getData();
            if (!Objects.equals(color, "")) triangleItem.setData(color);
            color = temp;
        }
        triangles.get(0).setData(color);
    }

    private Boolean checkSingle(Box<Triangle, String> triangle1, Box<Triangle, String> triangle2) {
        if (Objects.equals(triangle2.getData(), TRANSPARENT)) return true;
        return triangle1 != null && Objects.equals(triangle1.getData(), GRAY_BASE);
    }

    private Triple<Boolean, ArrayList<Box<Triangle, String>>, ArrayList<Box<Triangle, String>>> validatePath(Box<Triangle, String> current, Box<Triangle, String> piece, ArrayList<String> path) {
        ArrayList<String> path1 = new ArrayList<>(path);
        ArrayList<Box<Triangle, String>> trianglesPiece = piece.getByRoute(path1);
        path1.addAll(path);
        ArrayList<Box<Triangle, String>> trianglesBoard = current.getByRoute(path1);
        while (trianglesBoard.size() < 6) trianglesBoard.add(null);
        for (int i = 0; i < trianglesPiece.size(); i++) {
            Boolean res = checkSingle(trianglesBoard.get(i), trianglesPiece.get(i));
            if (!res) return new Triple<>(false, trianglesBoard, trianglesPiece);
        }
        return new Triple<>(true, trianglesBoard, trianglesPiece);
    }

    private Triple<Boolean, ArrayList<Box<Triangle, String>>, ArrayList<Box<Triangle, String>>> checkAll(Box<Triangle, String> current, Box<Triangle, String> piece) {
        if (!Objects.equals(current.getRotation(), piece.getRotation())) return new Triple<>(false, null, null);
        ArrayList<String> route1 = new ArrayList<>();
        route1.add("right");route1.add("base");route1.add("left");route1.add("left");route1.add("base");
        ArrayList<String> route2 = new ArrayList<>();
        route2.add("left");route2.add("base");route2.add("right");route2.add("right");route2.add("base");
        Triple<Boolean, ArrayList<Box<Triangle, String>>, ArrayList<Box<Triangle, String>>> res1 = validatePath(current, piece, route1);
        if (res1.component1()) return res1;
        Triple<Boolean, ArrayList<Box<Triangle, String>>, ArrayList<Box<Triangle, String>>> res2 = validatePath(current, piece, route2);
        if (res2.component1()) return res2;
        return new Triple<>(false, null, null);
    }

    private void colorSingle(Box<Triangle, String> current, Box<Triangle, String> piece) {
        if (Objects.equals(piece.getData(), TRANSPARENT)) return;
        if (current != null) current.setData(piece.getData());
    }

    private void colorAll(ArrayList<Box<Triangle, String>> trianglesBoard, ArrayList<Box<Triangle, String>> trianglesPiece) {
        for (int i = 0; i < trianglesPiece.size(); i++) {
            colorSingle(trianglesBoard.get(i), trianglesPiece.get(i));
        }
    }
}
