package com.geekbrains.mynasa_md.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import com.geekbrains.mynasa_md.databinding.FragmentEarthCoordinatorBinding
import com.geekbrains.mynasa_md.view.layouts.ToFABBehavior

class EarthCoordinatorFragment : Fragment() {

    private var _binding: FragmentEarthCoordinatorBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo задать behavior с кода
        val behavior = ToFABBehavior(requireContext())
        (binding.fCoordinatorFab.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}