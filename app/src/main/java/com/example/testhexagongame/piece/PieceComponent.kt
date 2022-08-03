package com.example.testhexagongame.piece

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.dragAndDrop.DragTarget
import com.example.testhexagongame.tiles.tile.RenderTriangle2

@Composable
fun RenderPiece(piece: HexagonPiece, position: Int, deletePiece: ((HexagonPiece) -> Unit)?) {
    @Composable
    fun PieceComp() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { if (deletePiece == null) piece.flip() else deletePiece(piece)}
        ) {
            Row() {
                RenderTriangle2(piece.triangles!!)
                RenderTriangle2(piece.triangles?.getAdjacent("right")!!)
                RenderTriangle2(
                    piece.triangles?.getAdjacent("right")?.getAdjacent("right")!!,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row() {
                RenderTriangle2(piece.triangles?.getAdjacent("base")!!)
                RenderTriangle2(
                    piece.triangles?.getAdjacent("base")?.getAdjacent("right")!!,
                )
                RenderTriangle2(
                    piece.triangles?.getAdjacent("base")?.getAdjacent("right")?.getAdjacent("right")!!,
                )
            }
        }
    }
    DragTarget(dataToDrop = piece, modifier = Modifier.size(110.dp), elPos = position) {
        PieceComp()
    }
}
