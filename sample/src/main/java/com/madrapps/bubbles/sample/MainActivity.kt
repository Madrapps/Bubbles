package com.madrapps.bubbles.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.madrapps.bubbles.ActionMenu

class MainActivity : AppCompatActivity() {

    lateinit var menu: ActionMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menu = findViewById<ActionMenu>(R.id.floatingActionMenu)
        menu.close()

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

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
        menu.open()
        menu.close()
    }
}
