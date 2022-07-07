package com.example.testhexagongame.piece

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.dragAndDrop.DragTarget
import com.example.testhexagongame.tiles.tile.RenderTriangle

@Composable
fun RenderPiece(piece: Piece2, position: Int, deletePiece: ((Piece2) -> Unit)?) {
    @Composable
    fun PieceComp() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { if (deletePiece == null) piece.flip() else deletePiece(piece)}
        ) {
            Row() {
                RenderTriangle(piece.getTriangles()!!)
                RenderTriangle(piece.getTriangles()?.getAdjacent("right")!!)
                RenderTriangle(piece.getTriangles()?.getAdjacent("right")?.getAdjacent("right")!!)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row() {
                RenderTriangle(piece.getTriangles()?.getAdjacent("base")!!)
                RenderTriangle(piece.getTriangles()?.getAdjacent("base")?.getAdjacent("right")!!)
                RenderTriangle(piece.getTriangles()?.getAdjacent("base")?.getAdjacent("right")?.getAdjacent("right")!!)
            }
        }
    }
    DragTarget(dataToDrop = piece, modifier = Modifier.size(110.dp), elPos = position) {
        PieceComp()
    }
}
