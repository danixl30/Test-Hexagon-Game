package com.example.testhexagongame.tiles.tile

import android.graphics.Color
import androidx.compose.runtime.Composable
import com.example.testhexagongame.utils.Observer

interface TileModel{
    var right: TileModel?
    var left: TileModel?
    var base: TileModel?
    var isFlipped: Boolean

    @Composable
    fun GraphicItem(onChage: (() -> Unit)?)
}