package com.example.testhexagongame.hexagon.center;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.testhexagongame.Factories.Factory;
import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;

import java.util.ArrayList;

public class HexagonCenterFactory implements Factory<ArrayList<HexagonCenter<Triangle>>> {
    private final Box2<Triangle, String> triangle;

    private final ArrayList<HexagonCenter<Triangle>> centers = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkRow(Box2<Triangle, String> triangle) {
        if (triangle.getAdjacent("right") == null || triangle.getRotation() == 180) { return; }
        HexagonCenter<Triangle> center = new HexagonCenter<>();
        ArrayList<String> path = new ArrayList<>();
        path.add("right");path.add("right");path.add("base");path.add("left");path.add("left");
        ArrayList<Box2<Triangle, String>> triangles = triangle.getByRoute(path);
        if (triangles.size() < 6) return;
        triangles.forEach(center::addTriangle);
        centers.add(center);
        checkRow(triangle.getAdjacent("right"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchCol() {
        Box2<Triangle, String> temp = triangle;
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

    public HexagonCenterFactory(Box2<Triangle, String> triangle) {
        this.triangle = triangle;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<HexagonCenter<Triangle>> create() {
        searchCol();
        return centers;
    }
}
