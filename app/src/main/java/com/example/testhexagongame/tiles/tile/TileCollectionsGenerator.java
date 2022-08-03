package com.example.testhexagongame.tiles.tile;

import java.util.ArrayList;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;
import com.example.testhexagongame.utils.Iterator;

public class TileCollectionsGenerator {
    private final Box<Triangle,String, String> triangle;

    public TileCollectionsGenerator(Box<Triangle,String, String> triangle){
        this.triangle = triangle;
    }

    private Iterator<Box<Triangle, String, String>> setRow(Box<Triangle,String, String> triangle){
        Box<Triangle, String, String> temp = triangle;
        ArrayList<Box<Triangle, String, String>> list = new ArrayList<>();
        while(temp != null){
            list.add(temp);
            if(temp.getAdjacent("right") == null) break;
            temp = temp.getAdjacent("right");
        }
        return new Iterator<>(list);
    }
    private Iterator<Iterator<Box<Triangle, String, String>>> setCol(){
        Box<Triangle, String, String> temp = triangle;
        ArrayList<Iterator<Box<Triangle, String, String>>> inters = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            Iterator<Box<Triangle, String, String>> inter = setRow(temp);
            inters.add(inter);
            if (temp.getAdjacent("base").getAdjacent("left") == null) {
                temp = temp.getAdjacent("base");
                break;
            }
            temp = temp.getAdjacent("base").getAdjacent("left");
        }
        for (int i = 1; i <= 3 && temp != null; i++) {
            Iterator<Box<Triangle, String, String>> inter = setRow(temp);
            inters.add(inter);
            if (temp.getAdjacent("right") != null)
                temp = temp.getAdjacent("right").getAdjacent("base");
        }
        return new Iterator<>(inters);
    }
    public Iterator<Iterator<Box<Triangle, String, String>>> createCollection() {
        return setCol();
    }
}