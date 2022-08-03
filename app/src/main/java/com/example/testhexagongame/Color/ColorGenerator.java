package com.example.testhexagongame.Color;

import java.util.ArrayList;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class ColorGenerator<T> {
    private final ArrayList<T> colors;
    private final Function2<Integer, Integer, Integer> randomNum;

    public ColorGenerator(ArrayList<T> colors, Function2<Integer, Integer, Integer> randomNum) {
        this.colors = colors;
        this.randomNum = randomNum;
    }

    public T getColorRandom() {
        return colors.get(randomNum.invoke(0, colors.size()-1));
    }
}
