package com.geekbrains.mynasa_md.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.LessonsNavigationFragmentBinding
import com.geekbrains.mynasa_md.view.navigation.ViewPagerFragment
import com.geekbrains.mynasa_md.view.notes.NotesFragment
import com.geekbrains.mynasa_md.view.themes.SettingThemeFragment


class LessonsNavigationFragment(private var keyTheme: Int) : DialogFragment() {

    private var _binding: LessonsNavigationFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LessonsNavigationFragmentBinding.inflate(inflater, container,false)
        dialog?.window?.setGravity(Gravity.TOP or Gravity.LEFT)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lessonsNavView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.menu_nav_lesson_1 -> {
                    showLesson(PictureOfTheDayFragment.newInstance())
                }
                R.id.menu_nav_lesson_2 -> {
                    showLesson(SettingThemeFragment.newInstance(keyTheme))
                }
                R.id.menu_nav_lesson_3 -> {
                    showLesson(ViewPagerFragment.newInstance())
                }
                R.id.menu_nav_lesson_6 -> {
                    showLesson(NotesFragment.newInstance())
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