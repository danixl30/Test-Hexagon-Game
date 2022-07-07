package com.example.testhexagongame.piece

import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.Shape.Shape

interface PieceI<T: Shape> {
    fun create()
    fun putPiece(current: Box<T>?): Boolean
}

interface PieceFlippable<T: Shape>: PieceI<T> {
    fun flip()
}