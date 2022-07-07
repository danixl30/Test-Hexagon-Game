package com.example.testhexagongame.tiles.tile.Graphics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import com.example.testhexagongame.R
import com.example.testhexagongame.dragAndDrop.DropTarget
import com.example.testhexagongame.piece.Piece2
import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.RenderTriangle
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape
import com.example.testhexagongame.ui.theme.GRAY_BASE
import com.example.testhexagongame.utils.Iterator

@Composable
fun RenderRow2(
    triangles: Iterator<Box<TriangleShape>>,
    onChangeColor: () -> Unit,
    removePiece: (piece: Piece2) -> Unit,
    onClickItem: ((Box<TriangleShape>) -> Unit)?
) {
    while (triangles.hasNext()) {
        val triangle = triangles.getNext()
        DropTarget<Piece2>(modifier = Modifier) { isInBound, data ->
            if (isInBound && data != null && data.putPiece(triangle)){
                onChangeColor()
                removePiece(data)
            }
            Box(modifier = if (onClickItem == null) if (!isInBound) Modifier else Modifier.background(
                Color.Red) else Modifier.clickable { onClickItem(triangle) }) {
                RenderTriangle(triangle)
            }
        }
    }
}
