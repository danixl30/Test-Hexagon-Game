package com.example.testhexagongame.utils

class Observer<T>(private var data: T) {
    private val subscribers = arrayListOf<(T) -> Unit>()

    fun subscribe(callBack: (T) -> Unit) {
        subscribers.add(callBack)
    }

    fun getValue(): T {
        return data
    }

    fun setValue(value: T) {
        data = value
        publish()
    }

    private fun publish() {
        subscribers.forEach { callBack -> callBack(data) }
    }
}