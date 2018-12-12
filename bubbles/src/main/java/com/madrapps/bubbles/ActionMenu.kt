package com.madrapps.bubbles

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.madrapps.bubbles.animators.Animator
import com.madrapps.bubbles.layouts.Layout
import com.madrapps.bubbles.util.Size

class ActionMenu @JvmOverloads constructor(
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
    private var anchorId: Int = -1

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionMenu, defStyleAttr, 0)
        with(typedArray) {
            anchorId = getResourceId(R.styleable.ActionMenu_anchor, -1)
            setLayout(getString(R.styleable.ActionMenu_menu_layout))
            setAnimator(getString(R.styleable.ActionMenu_menu_animator))
            recycle()
        }
    }

    private fun setAnimator(animatorString: String?) {
        if (animatorString != null) {
            val instance = getClazz(animatorString).newInstance()
            this.animator = instance as Animator
        }
    }

    private fun setLayout(layoutString: String?) {
        if (layoutString != null) {
            val instance = getClazz(layoutString).newInstance()
            this.layout = instance as Layout
        }
    }

    private fun getClazz(classString: String?): Class<*> {
        try {
            return javaClass.classLoader.loadClass(classString)
        } catch (e: Exception) {
            throw IllegalArgumentException("$classString doesn't exist", e)
        }
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
        layout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        children().forEach {
            measureChildWithMargins(it, widthMeasureSpec, 0, heightMeasureSpec, 0)
        }
        setAnchor(anchorId)

        val (width, height) = measure()
        setMeasuredDimension(width, height)
    }

    fun open() = animator.show()

    fun close() = animator.hide()

    @Suppress("unused")
    fun configure(layout: Layout, animator: Animator, anchor: View? = anchorView) {
        this.layout = layout
        this.animator = animator
        setAnchor(anchor)
    }

    @Suppress("unused")
    fun setLayout(layout: Layout) {
        this.layout = layout
        invalidate()
    }

    @Suppress("unused")
    fun setAnimator(animator: Animator) {
        this.animator = animator
        invalidate()
    }

    @Suppress("MemberVisibilityCanPrivate")
    fun setAnchor(anchor: View?) {
        anchorView = anchor
        invalidate()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun measure(): Size {
        if (isInEditMode && layout == null) { // layout may be null in EditMode
            return Size(100, 100)
        }
        return layout.measure(children(), getAnchorSize())
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun layout() {
        if (isInEditMode && (layout == null || animator == null)) return // layout/animator may be null in edit mode

        layout.position(children(), parentPosition, anchorPosition)
        animator.configure(context, children(), parentPosition, anchorPosition)
    }

    private fun setAnchor(id: Int) {
        if (id != -1 && anchorView == null) {
            val anchorView = (parent as ViewGroup).findViewById<View>(id)
            setAnchor(anchorView)
        }
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