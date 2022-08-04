package com.example.testhexagongame.tiles.tile.Graphics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testhexagongame.dragAndDrop.DropTarget
import com.example.testhexagongame.piece.HexagonPiece
import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.RenderTriangle2
import com.example.testhexagongame.tiles.tile.Shape.Triangle
import com.example.testhexagongame.ui.theme.GRAY_BASE
import com.example.testhexagongame.utils.Iterator

@Composable
fun RenderRow(
    triangles: Iterator<Box<Triangle, String, String>>,
    removePiece: (piece: HexagonPiece) -> Unit,
    onClickItem: ((Box<Triangle, String, String>) -> Unit)?,
    triangleInsideIcon: (@Composable BoxScope.() -> Unit)
) {
    while (triangles.hasNext()) {
        val triangle = triangles.next
        DropTarget<HexagonPiece>(modifier = Modifier) { isInBound, data, isInverted ->
            if (data != null) println(isInverted)
            if (isInBound && data != null && data.putPiece(triangle, if (isInverted) 0 else 180)){
                removePiece(data)
            }
            Box(modifier = if (onClickItem == null)
                Modifier else Modifier.clickable { onClickItem(triangle) }) {
                RenderTriangle2(triangle, if (triangle.data != GRAY_BASE) triangleInsideIcon else null)
            }
        }
    }
}
