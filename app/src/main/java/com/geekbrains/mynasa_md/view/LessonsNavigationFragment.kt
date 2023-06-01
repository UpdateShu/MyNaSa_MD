package com.geekbrains.mynasa_md.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.LessonsNavigationFragmentBinding
import com.geekbrains.mynasa_md.view.notes.NotesFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LessonsNavigationFragment : BottomSheetDialogFragment() {

    private var _binding: LessonsNavigationFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LessonsNavigationFragmentBinding.inflate(inflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lessonsNavView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.menu_nav_lesson_1 -> {
                    showLesson(PictureOfTheDayFragment.newInstance())
                }
                R.id.menu_nav_lesson_6 -> {
                    showLesson(NotesFragment.newInstance())
                }
                R.id.menu_nav_lesson_7 -> {
                    //showLesson(SpannableTextFragment.newInstance())
                }
            }
            dismiss()
            return@setNavigationItemSelectedListener false
        }
    }

    fun showLesson(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.lessons_view_container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}