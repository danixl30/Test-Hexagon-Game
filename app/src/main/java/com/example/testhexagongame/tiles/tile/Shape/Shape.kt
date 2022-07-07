package com.example.testhexagongame.tiles.tile.Shape

import com.example.testhexagongame.tiles.tile.Box

abstract class Shape() {
    protected val sides = mutableListOf<String>()
    protected var currentRotation = 0
    protected val rotations = mutableListOf<Int>()
    private val adjacent = HashMap<String, Box<Shape>>()

    init {
        this.setAdjacent()
        this.setRotationRules()
    }

    protected abstract fun setAdjacent()

    protected abstract fun setRotationRules()

    fun addAdjacent(side: String, piece: Box<Shape>) {
        if (!sides.contains(side)) return
        adjacent[side] = piece
    }

    fun getAdjacent(side: String): Box<Shape>? {
        if (!sides.contains(side)) return null
        return adjacent[side]
    }

    fun setRotation(rotation: Int) {
        if (!rotations.contains(rotation)) return
        currentRotation = rotation
    }

    fun getRotation(): Int {
        return currentRotation
    }
}