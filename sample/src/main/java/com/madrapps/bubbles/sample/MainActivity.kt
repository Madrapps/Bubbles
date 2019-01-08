package com.madrapps.bubbles.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.madrapps.bubbles.ActionMenu

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menu = findViewById<ActionMenu>(R.id.floatingActionMenu)

        findViewById<View>(R.id.floatingActionButton).setOnClickListener {
            menu.switchMenuState()
        }
    }


}
