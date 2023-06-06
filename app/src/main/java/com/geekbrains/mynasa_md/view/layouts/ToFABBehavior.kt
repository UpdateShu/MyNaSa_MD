package com.geekbrains.mynasa_md.view.layouts

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class ToFABBehavior(context: Context, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean = dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val appBarLayout = dependency as AppBarLayout

        val barHeight = appBarLayout.height.toFloat()
        val barWidth = appBarLayout.width.toFloat()
        val barY = appBarLayout.y
        val barX = appBarLayout.x



        if (Math.abs(barY) > (barHeight * 0.67)) {
            child.visibility = View.GONE
            Log.d("ToFABBehavior.if", "Y:$barY  X: $barX")
        } else {
            child.visibility = View.VISIBLE
            child.alpha = (((barHeight * 0.67) - Math.abs(barY)) / (barHeight * 0.67)).toFloat()
            child.x = barWidth * 0.85F - Math.abs(barY)
            Log.d("ToFABBehavior.else", "Y:$barY  X: ${child.x}")
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }
}