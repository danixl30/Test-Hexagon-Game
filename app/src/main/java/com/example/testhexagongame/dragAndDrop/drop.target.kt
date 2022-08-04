package com.example.testhexagongame.dragAndDrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import java.util.*

@Composable
fun <T> DropTarget(
    modifier: Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?, isInverted: Boolean) -> Unit)
) {

    val id by remember {
        mutableStateOf(UUID.randomUUID().toString())
    }
    val dragInfo = LocalDragTargetInfo.current
    dragInfo.subscribers[id] = 0
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset + dragInfo.offsetZone)
            if (isCurrentDropTarget) dragInfo.subscribers[id] = 2
            else dragInfo.subscribers[id] = 1
            if (!dragInfo.subscribers.containsValue(0) && !dragInfo.subscribers.containsValue(2)) {
                dragInfo.offsetZone = Offset(0f, 100f)
                dragInfo.subscribers.keys.forEach { e -> dragInfo.subscribers[e] = 0 }
            }
        }
    }) {
        var data =
            if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop as T? else null
        if (isCurrentDropTarget && !dragInfo.isDragging && data != dragInfo.dataToDrop) data = dragInfo.dataToDrop as T? else null
        content(isCurrentDropTarget, data, dragInfo.offsetZone == Offset(0f, 100f))
    }
}