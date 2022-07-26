package com.example.testhexagongame.hexagon.center;

import com.example.testhexagongame.Factories.Factory;
import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;

public class HexagonCenterFactory implements Factory<ArrayList<HexagonCenter<Triangle>>> {
    private final Box<Triangle, String> triangle;

    private final ArrayList<HexagonCenter<Triangle>> centers = new ArrayList<>();

    private void search(Box<Triangle, String> current, ArrayList<Box<Triangle, String>> visited) {
        if (visited.contains(current)) return;
        visited.add(current);
        if (current.getRotation() == 0) {
            ArrayList<String> path = new ArrayList<>();
            path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
            ArrayList<Box<Triangle, String>> triangles = current.getByRoute(path);
            if (triangles.size() == 6) {
                HexagonCenter<Triangle> center = new HexagonCenter<>();
                triangles.forEach(center::addTriangle);
                centers.add(center);
            }
        }
        current.getAdjacents().forEach(e -> search(e, visited));
    }

    public HexagonCenterFactory(Box<Triangle, String> triangle) {
        this.triangle = triangle;
    }

    @Override
    public ArrayList<HexagonCenter<Triangle>> create() {
        search(triangle, new ArrayList<>());
        return centers;
    }
}
