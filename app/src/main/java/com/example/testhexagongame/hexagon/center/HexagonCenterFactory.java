package com.example.testhexagongame.hexagon.center;

import com.example.testhexagongame.Factories.Factory;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;

public class HexagonCenterFactory implements Factory<ArrayList<HexagonCenter<Triangle>>> {
    private final Box<Triangle, String> triangle;

    private final ArrayList<HexagonCenter<Triangle>> centers = new ArrayList<>();

    private void checkRow(Box<Triangle, String> triangle) {
        ArrayList<String> path = new ArrayList<>();
        path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
        ArrayList<Box<Triangle, String>> triangles = triangle.getByRoute(path);
        if (triangles.size() == 6 && triangle.getRotation() == 0) {
            HexagonCenter<Triangle> center = new HexagonCenter<>();
            triangles.forEach(center::addTriangle);
            centers.add(center);
        }
        if (triangle.getAdjacent("right") != null) checkRow(triangle.getAdjacent("right"));
    }

    private void searchCol() {
        Box<Triangle, String> temp = triangle;
        for (int i = 1; i <= 3; i++) {
            checkRow(temp);
            if (temp.getAdjacent("base").getAdjacent("left") == null) {
                temp = temp.getAdjacent("base");
                break;
            }
            temp = temp.getAdjacent("base").getAdjacent("left");
        }
        for (int i = 1; i <= 3; i++) {
            checkRow(temp.getAdjacent("right"));
            temp = temp.getAdjacent("right").getAdjacent("base");
        }
    }

    public HexagonCenterFactory(Box<Triangle, String> triangle) {
        this.triangle = triangle;
    }

    @Override
    public ArrayList<HexagonCenter<Triangle>> create() {
        searchCol();
        return centers;
    }
}
