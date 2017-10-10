package com.madrapps.floatingactionmenu

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT

class FloatingActionMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d("Layout", "width = " + width)
        Log.d("Layout", "height = " + height)
        Log.d("Layout", "measuredWidth = " + measuredWidth)
        Log.d("Layout", "measuredHeight = " + measuredHeight)

        Log.d("Layout", "Parent:left = " + left)
        Log.d("Layout", "Parent:right = " + right)
        Log.d("Layout", "Parent:top = " + top)
        Log.d("Layout", "Parent:bottom = " + bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var maxWidth = 0
        var maxHeight = 0
        (0 until childCount)
                .map { getChildAt(it) }
                .filter { it.visibility != GONE }
                .forEach { child ->

                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)

                    val lp: MarginLayoutParams = child.layoutParams as MarginLayoutParams

                    maxWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
                    maxHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
                }
        val anchorView = (parent as ViewGroup).findViewById<View>(R.id.floatingActionButton)
        Log.d("Layout", "anchorView:height = " + anchorView?.measuredHeight)
        Log.d("Layout", "anchorView:width = " + anchorView?.measuredWidth)

        setMeasuredDimension(maxWidth, maxHeight + (anchorView?.measuredHeight ?: 0))
    }

    override fun generateLayoutParams(attrs: AttributeSet?) = MarginLayoutParams(context, attrs)
    override fun generateDefaultLayoutParams() = MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams
    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)
    override fun shouldDelayChildPressedState() = false
}
