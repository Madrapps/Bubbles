package com.madrapps.floatingactionmenu.layouts

import android.graphics.Rect
import android.view.View
import com.madrapps.floatingactionmenu.util.Size

class VerticalStackedLayout : Layout {
    override fun measure(children: List<View>, anchorSize: Size): Size {
        return Size(0, 0)
    }

    override fun position(children: List<View>, anchorPosition: Rect) {

    }
}