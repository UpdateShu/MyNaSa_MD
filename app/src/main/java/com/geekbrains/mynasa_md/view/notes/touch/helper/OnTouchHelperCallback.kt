package com.geekbrains.mynasa_md.view.notes.touch.helper

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.mynasa_md.view.notes.adapter.NotesAdapter

class OnTouchHelperCallback(private val notesAdapter: NotesAdapter) : ItemTouchHelper.Callback()
{
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var dragFlag: Int? = null
        var swipeFlag: Int? = null
        var makeMovementFlag: Int
        if (viewHolder !is NotesAdapter.HeaderViewHolder) {
            dragFlag = ItemTouchHelper.DOWN or ItemTouchHelper.UP
            swipeFlag = ItemTouchHelper.END or ItemTouchHelper.START
        }
        if (dragFlag != null && swipeFlag != null) {
            makeMovementFlag = makeMovementFlags(dragFlag, swipeFlag)
        } else {
            makeMovementFlag = ItemTouchHelper.ACTION_STATE_IDLE
        }
        return makeMovementFlag
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,    // Положение начала движения
        target: RecyclerView.ViewHolder            // Положение окончания движения
    ): Boolean {
        notesAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.START) {
            notesAdapter.onItemDismiss(viewHolder.adapterPosition)
        } else if (direction == ItemTouchHelper.END) {
            notesAdapter.addNote(viewHolder.adapterPosition)
        }

    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            (it as NotesAdapter.BaseViewHolder).onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        when (viewHolder) {
            is NotesAdapter.NoteViewHolder -> {
                viewHolder.onItemClear()
            }
        }
    }
}