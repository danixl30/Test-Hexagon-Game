package com.example.testhexagongame.tiles.tile;

import java.util.ArrayList;
import com.example.testhexagongame.tiles.tile.Shape.Triangle;
import com.example.testhexagongame.utils.Iterator2;

public class TileCollectionsGenerator2 {
    private Box2<Triangle,String> triangle;

    public TileCollectionsGenerator2(Box2<Triangle,String> triangle){
        this.triangle = triangle;
    }

    private Iterator2<Box2<Triangle,String>> setRow(Box2<Triangle,String> triangle){
        Box2<Triangle,String> temp = triangle;
        ArrayList<Box2<Triangle,String>> list = new ArrayList<Box2<Triangle,String>>();
        while(temp != null){
            list.add(temp);
            if(temp.getAdjacent("right") == null) break;
            temp = temp.getAdjacent("right");
        }
        return new Iterator2(list);
    }
    private Iterator2<Iterator2<Box2<Triangle,String>>> setRow(){
        Box2<Triangle,String> temp = triangle;
        ArrayList<Iterator2<Box2<Triangle,String>>> inters = new ArrayList<Iterator2<Box2<Triangle,String>>>();
        for(int i = 1; i <= 3; i++){
            Iterator2<Box2<Triangle,String>> inter = setRow(temp);
            inters.add(inter);
            if (temp.getAdjacent("base").getAdjacent("left") == null) {
                temp = temp.getAdjacent("base");
                break;
            }
            temp = temp.getAdjacent("base");
        }
        for (int i = 1; i <= 3; i++) {
            Iterator2<Box2<Triangle,String>> inter = setRow(temp);
            inters.add(inter);
            temp = temp.getAdjacent("right").getAdjacent("base");
        }
        return new Iterator2(inters);
    }
    Iterator2<Iterator2<Box2<Triangle,String>>> createCollection() {
        return setRow();
    }
}