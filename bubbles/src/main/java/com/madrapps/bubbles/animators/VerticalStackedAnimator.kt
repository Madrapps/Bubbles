package com.madrapps.bubbles.animators

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import com.madrapps.bubbles.R

class VerticalStackedAnimator(private val context: Context) : Animator {

    private val showAnimators = ArrayList<AnimatorSet>()
    private val hideAnimators = ArrayList<AnimatorSet>()

    override fun configure(children: List<View>, parent: Rect, anchor: Rect) {
        reconfigureAnimatorsOnModification(children, showAnimators, false) { showAnimator() }
        reconfigureAnimatorsOnModification(children, hideAnimators, true) { hideAnimator() }
        children.forEachIndexed { i, child ->
            showAnimators[i].setTarget(child)
            hideAnimators[i].setTarget(child)
        }
    }

    // TODO not comfortable have the reversed parameter. Let's remove it once all done
    private fun reconfigureAnimatorsOnModification(children: List<View>, animators: MutableList<AnimatorSet>, reversed: Boolean, animator: () -> AnimatorSet) {
        val size = children.size
        if (animators.size != size) {
            animators.clear()
            children.forEachIndexed { i, _ ->
                val anim = animator()
                anim.startDelay = 30L * if (reversed) size - i else i
                animators.add(anim)
            }
        }
    }

    override fun show() = showAnimators.forEach(AnimatorSet::start)
    override fun hide() = hideAnimators.forEach(AnimatorSet::start)

    private fun showAnimator(): AnimatorSet {
        val animator = AnimatorInflater.loadAnimator(context, R.animator.vertical_stacked_show) as AnimatorSet
        animator.interpolator = OvershootInterpolator()
        return animator
    }

    private fun hideAnimator(): AnimatorSet {
        val animator = AnimatorInflater.loadAnimator(context, R.animator.vertical_stacked_hide) as AnimatorSet
        animator.interpolator = AnticipateInterpolator()
        return animator
    }
}