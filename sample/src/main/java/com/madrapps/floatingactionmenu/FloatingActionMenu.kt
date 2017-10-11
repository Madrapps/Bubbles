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
    private val anchorPosition = Rect()
    private val anchorSize = Size()

    fun configure(layout: Layout, animator: Animator) {
        this.layout = layout
        this.animator = animator
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        parentPosition.left = paddingLeft
        parentPosition.top = paddingTop
        parentPosition.right = r - l - paddingRight
        parentPosition.bottom = b - t - paddingBottom

        // TODO We need to obtain this ANCHOR ID from the anchor tag
        val anchorView = getAnchorView()

        anchorPosition.left = anchorView.left - l - paddingLeft
        anchorPosition.top = anchorView.top - t - paddingTop
        anchorPosition.right = anchorView.right - l - paddingLeft
        anchorPosition.bottom = anchorView.bottom - t - paddingTop

        val lp = anchorView.layoutParams
        if (lp is MarginLayoutParams) {
            anchorPosition.left -= lp.leftMargin
            anchorPosition.top -= lp.topMargin
            anchorPosition.right += lp.rightMargin
            anchorPosition.bottom += lp.bottomMargin
        }

        layout.position(children(), parentPosition, anchorPosition)
        animator.configure(children())
    }

    private fun getAnchorView(): View {
        return (parent as ViewGroup).findViewById(R.id.floatingActionButton)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        children().forEach {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }

        // TODO We need to obtain this ANCHOR ID from the anchor tag
        val anchorView = getAnchorView()

        val (width, height) = layout.measure(children(), size(anchorView))
        setMeasuredDimension(width, height)
    }

    private fun size(view: View): Size {
        anchorSize.width = view.measuredWidth
        anchorSize.height = view.measuredHeight
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

    fun open() = animator.show()
    fun close() = animator.hide()
}