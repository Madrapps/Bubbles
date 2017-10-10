package com.madrapps.floatingactionmenu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT

class FloatingActionMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = paddingLeft
        val right = r - l - paddingRight
        var top = paddingTop
//        val bottom = b - t - paddingBottom

        (0 until childCount)
                .map { getChildAt(it) }
                .filter { it.visibility != GONE }
                .reversed()
                .forEach { child ->

                    val lp = child.layoutParams as MarginLayoutParams

                    val childLeft = left + lp.leftMargin
                    val childRight = right - lp.rightMargin
                    val childTop = top + lp.topMargin
                    val childBottom = childTop + child.measuredHeight

                    child.layout(childLeft, childTop, childRight, childBottom)

                    top = childBottom + lp.bottomMargin
                }
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

        // TODO We need to obtain this ANCHOR ID from the anchor tag
        val anchorView = (parent as ViewGroup).findViewById<View>(R.id.floatingActionButton)
        setMeasuredDimension(maxWidth, maxHeight + (anchorView?.measuredHeight ?: 0))
    }

    override fun generateLayoutParams(attrs: AttributeSet?) = MarginLayoutParams(context, attrs)
    override fun generateDefaultLayoutParams() = MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams
    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)
    override fun shouldDelayChildPressedState() = false
}
