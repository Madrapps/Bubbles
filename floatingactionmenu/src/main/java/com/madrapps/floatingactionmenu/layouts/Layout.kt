package com.madrapps.floatingactionmenu.layouts

import android.graphics.Rect
import android.view.View
import com.madrapps.floatingactionmenu.util.Size

interface Layout {

    fun measure(children: List<View>, anchorSize: Size): Size

    fun position(children: List<View>, anchorPosition: Rect)
}