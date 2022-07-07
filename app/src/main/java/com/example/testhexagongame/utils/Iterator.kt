package com.example.testhexagongame.utils

class Iterator<T>(private val elements: List<T>) {
    private var index = 0
    fun hasNext(): Boolean {
        return elements.size > index
    }

    fun getNext(): T {
        return elements[index++]
    }
    fun reset() {
        index = 0
    }
}