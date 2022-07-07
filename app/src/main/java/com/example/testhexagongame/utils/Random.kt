package com.example.testhexagongame.utils

import kotlin.random.Random

val randomNum: (Int, Int) -> Int = { initial: Int, last: Int -> (initial..last).random() }
