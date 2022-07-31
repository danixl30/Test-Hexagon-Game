package com.example.testhexagongame.dragAndDrop

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    elPos: Int,
    content: @Composable (() -> Unit),
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current
    val offsetBase = Offset.Zero

    if (currentState.isDragging && currentState.elIndex == elPos && currentState.dataToDrop != dataToDrop) currentState.dataToDrop = dataToDrop

    Box(modifier = modifier
        .onGloballyPositioned {
            currentPosition = it.localToWindow(Offset.Zero)
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(onDragStart = {
                currentState.subscribers.keys.forEach { e -> currentState.subscribers[e] = 0 }
                currentState.dataToDrop = dataToDrop
                currentState.isDragging = true
                currentState.isElement = false
                currentState.elIndex = elPos
                currentState.dragPosition = currentPosition + it
                currentState.draggableComposable = content
            }, onDrag = { change, dragAmount ->
                currentState.subscribers.keys.forEach { e -> currentState.subscribers[e] = 0 }
                if (dragAmount.x > 1 || dragAmount.x < -1 || dragAmount.y > 1 || dragAmount.y < -1) currentState.offsetZone = Offset(0f, -300f)
                change.consumeAllChanges()
                currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
            }, onDragEnd = {
                currentState.subscribers.keys.forEach { e -> currentState.subscribers[e] = 0 }
                currentState.isDragging = false
                currentState.elIndex = 0
                currentState.dragOffset = offsetBase
                currentState.isElement = false
            }, onDragCancel = {
                currentState.subscribers.keys.forEach { e -> currentState.subscribers[e] = 0 }
                currentState.isDragging = false
                currentState.elIndex = 0
                currentState.dragOffset = offsetBase
                currentState.isElement = false
            })
        }) {
        content()
    }
}