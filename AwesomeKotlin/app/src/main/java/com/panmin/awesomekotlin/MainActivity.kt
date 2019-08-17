package com.panmin.awesomekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panmin.awesomekotlin.kotlin.show
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.text = "aaaaaaa"
        show("aaaaa")


    }
}

