package com.madrapps.floatingactionmenu

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.madrapps.floatingactionmenu.animators.Animator
import com.madrapps.floatingactionmenu.layouts.Layout
import com.madrapps.floatingactionmenu.util.Size

class FloatingActionMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private lateinit var layout: Layout
    private lateinit var animator: Animator

    private val parentPosition = Rect()
    private val anchorPosition = Rect(0, 0, 0, 0)
    private val anchorSize = Size(0, 0)

    private var anchorView: View? = null

    fun configure(layout: Layout, animator: Animator, anchor: View? = anchorView) {
        this.layout = layout
        this.animator = animator
        setAnchor(anchor)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        parentPosition.setEmpty()
        anchorPosition.setEmpty()

        parentPosition.left = paddingLeft
        parentPosition.top = paddingTop
        parentPosition.right = r - l - paddingRight
        parentPosition.bottom = b - t - paddingBottom

        val anchor = anchorView
        if (anchor != null) {
            anchorPosition.left = anchor.left - l - paddingLeft
            anchorPosition.top = anchor.top - t - paddingTop
            anchorPosition.right = anchor.right - l - paddingLeft
            anchorPosition.bottom = anchor.bottom - t - paddingTop

            val lp = anchor.layoutParams
            if (lp is MarginLayoutParams) {
                anchorPosition.left -= lp.leftMargin
                anchorPosition.top -= lp.topMargin
                anchorPosition.right += lp.rightMargin
                anchorPosition.bottom += lp.bottomMargin
            }
        }

        layout.position(children(), parentPosition, anchorPosition)
        animator.configure(children(), parentPosition, anchorPosition)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        children().forEach {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        val (width, height) = layout.measure(children(), getAnchorSize())
        setMeasuredDimension(width, height)
    }

    fun open() = animator.show()

    fun close() = animator.hide()

    fun setAnchor(anchor: View?) {
        anchorView = anchor
    }


    private fun getAnchorSize(): Size {
        anchorSize.width = anchorView?.measuredWidth ?: 0
        anchorSize.height = anchorView?.measuredHeight ?: 0
        return anchorSize
    }

    private fun children(): List<View> {
        return (0 until childCount)
                .map { getChildAt(it) }
                .filter { it.visibility != GONE }
    }

    override fun generateLayoutParams(attrs: AttributeSet?) = MarginLayoutParams(context, attrs)
    override fun generateDefaultLayoutParams() = MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams
    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)
    override fun shouldDelayChildPressedState() = false
}