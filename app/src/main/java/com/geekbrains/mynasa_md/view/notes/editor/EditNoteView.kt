package com.geekbrains.mynasa_md.view.notes.editor

import com.geekbrains.mynasa_md.model.MyNote

interface EditNoteView {
    fun initEditView(resId: Int, editText: String)
    fun onNoteSaved(note: MyNote)
    fun getSavedNote() : MyNote
}