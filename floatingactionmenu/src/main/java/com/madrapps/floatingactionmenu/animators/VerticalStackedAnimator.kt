package com.madrapps.floatingactionmenu.animators

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import com.madrapps.floatingactionmenu.R

class VerticalStackedAnimator(context: Context) : Animator {

    private var openAnimator = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator1 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator2 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var openAnimator3 = AnimatorInflater.loadAnimator(context, R.animator.open) as AnimatorSet
    private var closeAnimator = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator1 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator2 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet
    private var closeAnimator3 = AnimatorInflater.loadAnimator(context, R.animator.close) as AnimatorSet

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

    override fun configure(children: List<View>) {
        openAnimator.setTarget(children[0])
        openAnimator1.setTarget(children[1])
        openAnimator2.setTarget(children[2])
        openAnimator3.setTarget(children[3])
        closeAnimator.setTarget(children[0])
        closeAnimator1.setTarget(children[1])
        closeAnimator2.setTarget(children[2])
        closeAnimator3.setTarget(children[3])
    }

    override fun show() {
        openAnimator.start()
        openAnimator1.start()
        openAnimator2.start()
        openAnimator3.start()
    }

    override fun hide() {
        closeAnimator.start()
        closeAnimator1.start()
        closeAnimator2.start()
        closeAnimator3.start()
    }
}