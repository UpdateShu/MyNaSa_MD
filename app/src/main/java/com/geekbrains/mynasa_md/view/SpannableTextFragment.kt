package com.geekbrains.mynasa_md.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.databinding.FragmentNotesBinding
import com.geekbrains.mynasa_md.databinding.FragmentSpannableTextBinding

class SpannableTextFragment : Fragment() {

    companion object {
        fun newInstance() = SpannableTextFragment()
    }

    private var _binding : FragmentSpannableTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSpannableTextBinding.inflate(inflater, container, false)
        return binding.root
    }
}