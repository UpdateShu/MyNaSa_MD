package com.geekbrains.mynasa_md.view.notes.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.createSavedStateHandle
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.mynasa_md.R

class RecyclerItemDecoration(
    val context: Context,
    val headerHeight: Int,
    val isSticky: Boolean,
    val callback: SectionCallback
) : RecyclerView.ItemDecoration() {

    private var headerView: View? = null
    private var tvTitle: TextView? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (callback.isSectionHeader(position)) {
            outRect.top = headerHeight
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (headerView == null) {
            headerView = inflateHeader(parent)
        }
        headerView?.let {
            tvTitle = it.findViewById(R.id.header_title_text_view)
            fixLayoutSize(it, parent)

            var prevTitle = ""
            for (i in 0..parent.childCount - 1) {
                val child = parent.getChildAt(i)
                val childPos = parent.getChildAdapterPosition(child)
                val title = callback.getSectionHeaderName(childPos)
                tvTitle?.setText(title)
                if (!prevTitle.equals(title, ignoreCase = true) ||
                    callback.isSectionHeader(childPos))
                {
                    drawHeader(c, child, it)
                    prevTitle = title
                }
            }
        }
    }

    private fun drawHeader(c: Canvas, child: View, headerView: View) {
        c.save()
        if (isSticky) {
            c.translate(.0f, Math.max(0, child.top - headerView.height).toFloat())
        } else {
            c.translate(.0f, (child.top - headerView.height).toFloat())
        }
        headerView.draw(c)
        c.restore()
    }

    private fun fixLayoutSize(headerView: View, parent: ViewGroup) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            headerView.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            headerView.layoutParams.height
        )

        headerView.measure(childWidth, childHeight)
        headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)
    }

    fun inflateHeader(recyclerView: RecyclerView) = LayoutInflater.from(context).inflate(
        R.layout.item_header,
        recyclerView, false
    )

    interface SectionCallback {
        fun isSectionHeader(position: Int): Boolean
        fun getSectionHeaderName(position: Int): String
    }
}

