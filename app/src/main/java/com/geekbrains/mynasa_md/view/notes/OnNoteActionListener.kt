package com.geekbrains.mynasa_md.view.notes

import com.geekbrains.mynasa_md.model.MyNote
import com.geekbrains.mynasa_md.view.notes.adapter.NotesAdapter

interface OnNoteActionListener {
    fun onNoteEdited(note: MyNote)
    fun onNoteClick(note: MyNote)
    fun onNoteDrag(viewHolder: NotesAdapter.BaseViewHolder)
}