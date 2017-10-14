package com.madrapps.bubbles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menu = findViewById<ActionMenu>(R.id.floatingActionMenu)

        var flag = false
        findViewById<View>(R.id.floatingActionButton).setOnClickListener {
            flag = if (flag) {
                menu.open()
                false
            } else {
                menu.close()
                true
            }
        }
    }
}
