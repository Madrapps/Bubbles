package com.madrapps.floatingactionmenu

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import com.madrapps.floatingactionmenu.layouts.Layout
import com.madrapps.floatingactionmenu.util.Size

class FloatingActionMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {


    private var openAnimator = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator1 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator2 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator3 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var closeAnimator = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator1 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator2 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator3 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet

    private lateinit var layout: Layout

    private val parentPosition = Rect()
    private val anchorPosition = Rect()
    private val anchorSize = Size()

    init {
        openAnimator1.startDelay = 30
        openAnimator2.startDelay = 60
        openAnimator3.startDelay = 90
        openAnimator.interpolator = OvershootInterpolator()
        openAnimator1.interpolator = OvershootInterpolator()
        openAnimator2.interpolator = OvershootInterpolator()
        openAnimator3.interpolator = OvershootInterpolator()

        closeAnimator.startDelay = 90
        closeAnimator1.startDelay = 60
        closeAnimator2.startDelay = 30
        closeAnimator.interpolator = AnticipateInterpolator()
        closeAnimator1.interpolator = AnticipateInterpolator()
        closeAnimator2.interpolator = AnticipateInterpolator()
        closeAnimator3.interpolator = AnticipateInterpolator()
    }

    fun configure(layout: Layout) {
        this.layout = layout
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

        openAnimator.setTarget(getChildAt(0))
        openAnimator1.setTarget(getChildAt(1))
        openAnimator2.setTarget(getChildAt(2))
        openAnimator3.setTarget(getChildAt(3))
        closeAnimator.setTarget(getChildAt(0))
        closeAnimator1.setTarget(getChildAt(1))
        closeAnimator2.setTarget(getChildAt(2))
        closeAnimator3.setTarget(getChildAt(3))
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

    fun open() {
        openAnimator.start()
        openAnimator1.start()
        openAnimator2.start()
        openAnimator3.start()
    }

    fun close() {
        closeAnimator.start()
        closeAnimator1.start()
        closeAnimator2.start()
        closeAnimator3.start()

    }
}