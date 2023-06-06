package com.geekbrains.mynasa_md.view.notes.editor

import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.model.MyNote

class EditNotePresenter(val editNoteView: EditNoteView,
                        var editedNote: MyNote?) {

    init {
        if (editedNote != null) {
            editNoteView.initEditView(R.string.update_title, editedNote!!.text)
        }
    }

    fun saveNote(editedText: String) {
        editedNote?.let {
            it.text = editedText
            editNoteView.onNoteSaved(it)
        }
    }
}