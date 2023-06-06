package com.geekbrains.mynasa_md.view.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class ToNestedScrollBehavior(context: Context, attr: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attr) {
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
        child.y = appBarLayout.height.toFloat()+appBarLayout.y
        return super.onDependentViewChanged(parent, child, dependency)
    }
}