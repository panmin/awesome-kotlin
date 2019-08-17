package com.panmin.awesomekotlin.kotlin

import android.app.Activity
import android.widget.Toast

class KotlinClass(
    p1: String,
    p2: String,
    var p3: String,
    val p4: String,
    private var p5: String,
    private val p6: String
) {
    private var merge: String? = null

    init {
        merge = p1 + p2
    }



    fun f() {
//        p1，p2 无法访问
//        //p3，p6 无法赋值
//        p4，p5 正常赋值
    }
}


fun main(args: Array<String>) {
    val kotlinClass = KotlinClass("p1", "p2", "p3", "p4", "p5", "p6")
    kotlinClass.p3 = "aaa"
    //kotlinClass.p4 = "bbb"

    KotlinClass2("a","b")
    KotlinClass3()
    KotlinClass3("")
    KotlinClass3(p1 = "")
}


class KotlinClass2(var p1:String){
    private var p2:String? = null
    constructor(p1: String,p2: String):this(p1){
        this.p2 = p2
    }
}

class KotlinClass3(var p1:String="a")


fun Activity.show(msg:String,duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, duration).show()
}