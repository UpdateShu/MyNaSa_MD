package com.geekbrains.mynasa_md.view.notes.editor

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentEditNoteBinding
import com.geekbrains.mynasa_md.model.MyNote

class EditNoteFragment : Fragment(), EditNoteView {
    companion object {
        const val MY_NOTE = "note"
        const val KEY_RESULT = "EditNoteFragment_KEY_RESULT"

        fun newInstance(bundle: Bundle): EditNoteFragment {
            val fragment = EditNoteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var editNotePresenter: EditNotePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editNotePresenter = EditNotePresenter(this,
            arguments?.getParcelable(MY_NOTE, MyNote::class.java))

        binding.btnSave.setOnClickListener {
            editNotePresenter.saveNote(binding.noteText.text.toString())
        }
    }

    override fun initEditView(resId: Int, editText: String) {
        binding.toolbar.setTitle(resId)
        binding.noteText.setText(editText)
    }

    override fun onNoteSaved(note: MyNote) {
        parentFragmentManager?.let {
            it.popBackStack()
        }
    }

    override fun getSavedNote(): MyNote {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parentFragmentManager?.let {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}