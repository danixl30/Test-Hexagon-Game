package com.example.testhexagongame.piece;

import static com.example.testhexagongame.ui.theme.ColorKt.TRANSPARENT;

import com.example.testhexagongame.Color.ColorGenerator;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.functions.Function2;

public class HexagonPiece implements PieceFlippable<Triangle, String, String> {

    private Box<Triangle, String, String> triangle = null;
    private final ColorGenerator<String> colorGenerator;
    private final Function2<Integer, Integer, Integer> getRandom;
    private final ArrayList<ArrayList<Integer>> options;
    private final DataChecker<Triangle, String, String> colorChecker;
    private final DataTransfer<Triangle, String, String> dataTransfer;

    public HexagonPiece(ColorGenerator<String> colorGenerator, Function2<Integer, Integer, Integer> getRandom, ArrayList<ArrayList<Integer>> options, DataChecker<Triangle, String, String> colorChecker, DataTransfer<Triangle, String, String> dataTransfer) {
        this.colorGenerator = colorGenerator;
        this.getRandom = getRandom;
        this.options = options;
        this.colorChecker = colorChecker;
        this.dataTransfer = dataTransfer;
        create();
    }

    Box<Triangle, String, String> getTriangles() {
        return triangle;
    }

    private Pair<Box<Triangle, String, String>, ArrayList<Box<Triangle, String, String>>> setTriangleRow(
            ArrayList<Box<Triangle, String, String>> tops,
            Box<Triangle, String, String> prev,
            Integer current,
            Integer last,
            Boolean invertedType
    ) {
        Box<Triangle, String, String> triangle1 = new Box<>(new Triangle(), TRANSPARENT);
        if (prev != null) triangle1.setAdjacent("left", prev);
        triangle1.setRotation(!invertedType ? 0 : 180);
        if (invertedType && tops.size() > 0) {
            Box<Triangle, String, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle1.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle1);
        }
        ArrayList<Box<Triangle, String, String>> list = new ArrayList<>();
        if (!invertedType) list.add(triangle1);
        if (Objects.equals(current, last) && !invertedType) return new Pair<>(triangle1, list);
        if (Objects.equals(current, last) && invertedType) return new Pair<>(triangle1, list);
        Box<Triangle, String, String> triangle2 = new Box<>(new Triangle(), TRANSPARENT);
        triangle2.setAdjacent("left", triangle1);
        triangle1.setAdjacent("right", triangle2);
        triangle2.setRotation((invertedType) ? 0 : 180);
        if (!invertedType && tops.size() > 0) {
            Box<Triangle, String, String> top = tops.get(0);
            tops.remove(0);
            if (top != null) { triangle2.setAdjacent("base", top); }
            if (top != null) top.setAdjacent("base", triangle2);
        }
        Pair<Box<Triangle, String, String>, ArrayList<Box<Triangle, String, String>>> res = setTriangleRow(tops, triangle2, current+1, last, invertedType);
        triangle2.setAdjacent("right", res.component1());
        if (invertedType) list.add(triangle2);
        list.addAll(res.component2());
        return new Pair<>(triangle1, list);
    }

    private void setTriangleCol() {
        Pair<Box<Triangle, String, String>, ArrayList<Box<Triangle, String, String>>> temp = setTriangleRow(new ArrayList<>(), null, 1, 2 , false);
        setTriangleRow(temp.component2(), null, 1, 2, true);
        triangle = temp.component1();
    }

    private void setColor() {
        String color = colorGenerator.getColorRandom();
        ArrayList<String> path = new ArrayList<>();
        path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
        ArrayList<Box<Triangle, String, String>> triangles = triangle.getByRoute(path);
        ArrayList<Integer> option = options.get(getRandom.invoke(1, options.size())-1);
        for (int i = 0; i < option.size(); i++) {
            if (option.get(i) == 1) triangles.get(i).setData(color);
        }
    }

    @Override
    public void create() {
        setTriangleCol();
        setColor();
        for(int c = 0; c < getRandom.invoke(0, 6); c++) flip();
    }

    @Override
    public Boolean putPiece(Box<Triangle, String, String> current, Integer orientation) {
        Triple<Boolean, ArrayList<Box<Triangle, String, String>>, ArrayList<Box<Triangle, String, String>>> res = checkAll(current, triangle.getAdjacent("right"));
        if (res.component1()) {
            colorAll(res.component2(), res.component3());
            return true;
        }
        if (current.getRotation() == 180 || orientation == 180) return false;
        if (current.getAdjacent("left") == null || current.getAdjacent("left").getAdjacent("base") == null || current.getAdjacent("right") == null) {
            res = checkAll(current, triangle.getByRoute(new ArrayList<>(Arrays.asList("base", "right"))).get(2));
            if (res.component1()) {
                colorAll(res.component2(), res.component3());
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isFit(Box<Triangle, String, String> current) {
        return checkAll(current, triangle.getAdjacent("right")).component1() || checkAll(current, triangle.getByRoute(new ArrayList<>(Arrays.asList("base", "right"))).get(2)).component1();
    }

    private Triple<Boolean, ArrayList<Box<Triangle, String, String>>, ArrayList<Box<Triangle, String, String>>>
    checkAll(Box<Triangle, String, String> current, Box<Triangle, String, String> piece) {
        if (!Objects.equals(current.getRotation(), piece.getRotation())) return new Triple<>(false, null, null);
        ArrayList<String> route1 = new ArrayList<>(Arrays.asList("right", "base", "left", "left", "base"));
        ArrayList<String> route2 = new ArrayList<>(Arrays.asList("left", "base", "right", "right", "base"));
        ArrayList<String> route3 = new ArrayList<>(Arrays.asList("left", "right", "right", "base", "left", "left"));
        Triple<Boolean, ArrayList<Box<Triangle, String, String>>, ArrayList<Box<Triangle, String, String>>> res1 = validatePath(current, piece, route1);
        if (res1.component1()) return res1;
        Triple<Boolean, ArrayList<Box<Triangle, String, String>>, ArrayList<Box<Triangle, String, String>>> res2 = validatePath(current, piece, route2);
        if (res2.component1()) return res2;
        Triple<Boolean, ArrayList<Box<Triangle, String, String>>, ArrayList<Box<Triangle, String, String>>> res3 = validatePath(current, piece, route3);
        if (res3.component1()) return res3;
        return new Triple<>(false, null, null);
    }


    @Override
    public void flip() {
        String color = "";
        ArrayList<String> path = new ArrayList<>(Arrays.asList("right", "right", "base", "left", "left"));
        ArrayList<Box<Triangle, String, String>> triangles = triangle.getByRoute(path);
        for (Box<Triangle, String, String> triangleItem : triangles) {
            String temp = triangleItem.getData();
            if (!Objects.equals(color, "")) triangleItem.setData(color);
            color = temp;
        }
        triangles.get(0).setData(color);
    }

    private Triple<Boolean, ArrayList<Box<Triangle, String, String>>, ArrayList<Box<Triangle, String, String>>> validatePath(Box<Triangle, String, String> current, Box<Triangle, String, String> piece, ArrayList<String> path) {
        ArrayList<String> path1 = new ArrayList<>(path);
        ArrayList<Box<Triangle, String, String>> trianglesPiece = piece.getByRoute(path1);
        path1.addAll(path);
        ArrayList<Box<Triangle, String, String>> trianglesBoard = current.getByRoute(path1);
        while (trianglesBoard.size() < trianglesPiece.size()) trianglesBoard.add(null);
        for (int i = 0; i < trianglesPiece.size(); i++) {
            Boolean res = colorChecker.check(trianglesBoard.get(i), trianglesPiece.get(i));
            if (!res) return new Triple<>(false, trianglesBoard, trianglesPiece);
        }
        return new Triple<>(true, trianglesBoard, trianglesPiece);
    }

    private void colorAll(ArrayList<Box<Triangle, String, String>> trianglesBoard, ArrayList<Box<Triangle, String, String>> trianglesPiece) {
        for (int i = 0; i < trianglesPiece.size(); i++) {
            dataTransfer.transfer(trianglesBoard.get(i), trianglesPiece.get(i));
        }
    }
}
