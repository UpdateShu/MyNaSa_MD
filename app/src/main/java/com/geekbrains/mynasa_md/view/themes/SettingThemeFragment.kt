package com.geekbrains.mynasa_md.view.themes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.mynasa_md.databinding.FragmentSettingThemeBinding
import com.geekbrains.mynasa_md.model.theme.Theme
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.ARG_CLICK_SAVE_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.ARG_THEME
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.KEY_CLICK_SAVE_THEME

class SettingThemeFragment : Fragment() {

    interface OnClickTheme {
        fun saveClickTheme(theme: Theme)
    }

    companion object {

        fun newInstance(key: Int) = SettingThemeFragment().apply {
            arguments.apply {
                Bundle().apply {
                    putInt(ARG_THEME, key)
                }
            }
        }
    }

    private var _binding: FragmentSettingThemeBinding? = null
    private val binding get() = _binding!!

    private val adapter: SettingThemeAdapter =
        SettingThemeAdapter(object : OnClickTheme {
            override fun saveClickTheme(theme: Theme) {
                Toast.makeText(requireContext(), theme.nameTheme, Toast.LENGTH_SHORT).show()
                val data: Bundle = Bundle().apply {
                    putInt(ARG_CLICK_SAVE_THEME, theme.key)
                }
                parentFragmentManager.setFragmentResult(KEY_CLICK_SAVE_THEME, data)
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ARG_THEME).let {
            if (it != null) {
                adapter.getKeyThemeSaved(it)
            }
        }
        val recyclerView: RecyclerView = binding.fSettingThemeContainer
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

    }
}