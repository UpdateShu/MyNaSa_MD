package com.geekbrains.mynasa_md.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentBottomNavigationBinding
import com.google.android.material.badge.BadgeDrawable

class BottomNavigationFragment : Fragment() {
    companion object {
        fun newInstance() = BottomNavigationFragment()
    }

    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_earth -> {
                    showFragment(EarthFragment.newInstance())
                    binding.bottomNavigationView.getBadge(R.id.menu_earth)?.let {
                        it.isVisible = false
                        it.clearNumber()
                    }
                    true
                }
                R.id.menu_mars -> {
                    showFragment(MarsFragment.newInstance())
                    binding.bottomNavigationView.removeBadge(R.id.menu_mars) // todo удаление бейджа
                    true
                }
                R.id.menu_solar_flares -> {
                    showFragment(SolarFlaresFragment.newInstance())
                    true
                }
                else -> false
            }
        }
        // todo по умолчанию задать активное меню
        binding.bottomNavigationView.selectedItemId = R.id.menu_mars
        binding.bottomNavigationView.selectedItemId = R.id.menu_earth

        // todo добавление бейджа и задаем значение
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.menu_mars)
        badge?.number = 999                 // todo задать количественное значение бейджа
        badge.maxCharacterCount = 3         // todo задать максимальное количество выводимых знаков
        badge.badgeGravity =
            BadgeDrawable.TOP_START      // todo задать расположение бейджа относитень ItemBottomNavigationView

        val badgeVisible = binding.bottomNavigationView.getOrCreateBadge(R.id.menu_earth)
        badgeVisible?.isVisible = true
    }

    private fun showFragment(fragment: Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.nasa_view, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}