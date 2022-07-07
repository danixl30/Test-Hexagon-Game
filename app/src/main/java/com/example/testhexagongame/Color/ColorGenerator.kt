package com.example.testhexagongame.Color

class ColorGenerator(
    private val listColor: List<String>,
    private val getRandom: (Int, Int) -> Int
    ) {
    fun getColorRandom(): String {
        return listColor[getRandom(0, listColor.size-1)]
    }
}