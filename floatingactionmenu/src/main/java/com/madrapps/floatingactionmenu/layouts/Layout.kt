package com.madrapps.floatingactionmenu.layouts

import android.graphics.Rect
import android.view.View
import com.madrapps.floatingactionmenu.util.Size

interface Layout {

    /**
     * Calculate the size of the action menu
     *
     * @param anchor size of the anchor view
     * @return the size of the action menu
     */
    fun measure(children: List<View>, anchor: Size): Size

    /**
     * Position all children within the parent
     *
     * @param parent relative position of the action menu, i.e the parent
     * @param anchor relative position of the anchor with respect to the parent
     */
    fun position(children: List<View>, parent: Rect, anchor: Rect)
}