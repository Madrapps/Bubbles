package com.madrapps.floatingactionmenu.animators

import android.view.View

interface Animator {

    fun configure(children: List<View>)

    fun show()

    fun hide()
}