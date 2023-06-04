package com.geekbrains.mynasa_md.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentViewPagerBinding
import com.geekbrains.mynasa_md.model.repo.InMemoryRepository
import com.geekbrains.mynasa_md.model.repo.InMemoryRepositoryInterface
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.EARTH_KEY
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.MARS_KEY
import com.geekbrains.mynasa_md.viewmodel.utils.Constants.SOLAR_SYSTEM_KEY
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    private val repo: InMemoryRepositoryInterface = InMemoryRepository()

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    class ViewPagerAdapter(fa: FragmentActivity, fragmentList: List<Fragment>)
        : FragmentStateAdapter(fa) {
        private val fragmentListAdapter = fragmentList
        override fun getItemCount(): Int = fragmentListAdapter.size

        override fun createFragment(position: Int): Fragment = fragmentListAdapter[position]

        //override fun getCount(): Int
        //override fun getItem(position: Int): Fragment =
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), repo.getFragmentListInMemory())
        TabLayoutMediator(binding.nasaTabLayout, binding.viewPager) {
            tab, position -> tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        binding.nasaTabLayout.getTabAt(EARTH_KEY)?.setCustomView(R.layout.fragment_view_pager_item_earth)
        binding.nasaTabLayout.getTabAt(MARS_KEY)?.setCustomView(R.layout.fragment_view_pager_item_mars)
        binding.nasaTabLayout.getTabAt(SOLAR_SYSTEM_KEY)?.setCustomView(R.layout.fragment_view_pager_item_solar_flares)
    }
}
