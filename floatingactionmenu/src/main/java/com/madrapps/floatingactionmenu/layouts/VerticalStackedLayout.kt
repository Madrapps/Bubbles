package com.madrapps.floatingactionmenu.layouts

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import com.madrapps.floatingactionmenu.util.Size

class VerticalStackedLayout : Layout {

    override fun measure(children: List<View>, anchorSize: Size): Size {
        var maxWidth = 0
        var maxHeight = 0
        children.forEach { child ->
            val lp: ViewGroup.MarginLayoutParams = child.layoutParams as ViewGroup.MarginLayoutParams

            maxWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            maxHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
        }
        return Size(maxWidth, maxHeight + anchorSize.height)
    }

    override fun position(children: List<View>, anchorPosition: Rect) {

    }
}