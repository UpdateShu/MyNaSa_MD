package com.geekbrains.mynasa_md.model.repo

import androidx.fragment.app.Fragment
import com.geekbrains.mynasa_md.view.navigation.EarthFragment
import com.geekbrains.mynasa_md.view.navigation.MarsFragment
import com.geekbrains.mynasa_md.view.navigation.SolarFlaresFragment

class InMemoryRepository : InMemoryRepositoryInterface {

    private val fragmentListInMemory = listOf<Fragment>(
        EarthFragment(),
        MarsFragment(),
        SolarFlaresFragment()
    )

    override fun getFragmentListInMemory(): List<Fragment> = fragmentListInMemory
}