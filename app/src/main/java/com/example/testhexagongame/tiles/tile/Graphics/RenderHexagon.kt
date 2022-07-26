package com.example.testhexagongame.tiles.tile.Graphics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testhexagongame.dragAndDrop.DropTarget
import com.example.testhexagongame.piece.Piece
import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.RenderTriangle2
import com.example.testhexagongame.tiles.tile.Shape.Triangle
import com.example.testhexagongame.utils.Iterator

@Composable
fun RenderRow(
    triangles: Iterator<Box<Triangle, String>>,
    removePiece: (piece: Piece) -> Unit,
    onClickItem: ((Box<Triangle, String>) -> Unit)?
) {
    while (triangles.hasNext()) {
        val triangle = triangles.next
        DropTarget<Piece>(modifier = Modifier) { isInBound, data ->
            if (isInBound && data != null && data.putPiece(triangle)){
                removePiece(data)
            }
            Box(modifier = if (onClickItem == null) if (!isInBound) Modifier else Modifier.background(
                Color.Red) else Modifier.clickable { onClickItem(triangle) }) {
                RenderTriangle2(triangle)
            }
        }
    }
}
