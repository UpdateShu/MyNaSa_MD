package com.geekbrains.mynasa_md.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val splashAnimation = binding.splashImageView.animate()
        splashAnimation.rotationBy(720f)
        splashAnimation.duration = 7000L
        splashAnimation.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}