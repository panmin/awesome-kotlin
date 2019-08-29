package com.panmin.awesomekotlin.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    /*println("start coroutine time ${System.currentTimeMillis()}")
    GlobalScope.launch {
        println("init coroutine end time ${System.currentTimeMillis()}")
        for (i in 1..3){
            println("one coroutine $i time ${System.currentTimeMillis()}")
        }
        delay(500)
        for (i in 1..3){
            println("tow coroutine $i time ${System.currentTimeMillis()}")
        }
    }
    Thread.sleep(1000)
    for (i in 1..3){
        println("three coroutine $i time ${System.currentTimeMillis()}")
    }*/

    val testArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    GlobalScope.launch {
        testArray.map { it * 2 }
            .map {
                println(it)
            }
    }
    Thread.sleep(1000)
    println("the end")


}