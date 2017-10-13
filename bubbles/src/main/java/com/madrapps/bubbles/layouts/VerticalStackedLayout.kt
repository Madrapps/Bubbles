package com.madrapps.bubbles.layouts

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import com.madrapps.bubbles.util.Size

class VerticalStackedLayout : Layout {

    override fun position(children: List<View>, parent: Rect, anchor: Rect) {
        var top = parent.top
        children.reversed()
                .forEach { child ->
                    val lp = child.layoutParams as ViewGroup.MarginLayoutParams

                    val childLeft = parent.left + ((parent.right - parent.left) - child.measuredWidth) / 2
                    val childRight = childLeft + child.measuredWidth
                    val childTop = top + lp.topMargin
                    val childBottom = childTop + child.measuredHeight

                    child.layout(childLeft, childTop, childRight, childBottom)

                    top = childBottom + lp.bottomMargin
                }
    }

    override fun measure(children: List<View>, anchor: Size): Size {
        var maxWidth = 0
        var maxHeight = 0
        children.forEach { child ->
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            maxWidth = Math.max(maxWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
            maxHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
        }
        return Size(Math.max(maxWidth, anchor.width), maxHeight + anchor.height)
    }

}