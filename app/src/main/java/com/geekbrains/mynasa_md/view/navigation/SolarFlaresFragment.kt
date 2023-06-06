package com.geekbrains.mynasa_md.view.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentSolarFlaresBinding
import com.geekbrains.mynasa_md.viewmodel.AppState
import com.geekbrains.mynasa_md.viewmodel.SolarFlareViewModel
import com.geekbrains.mynasa_md.viewmodel.utils.showSnackBarAction
import java.lang.StringBuilder

class SolarFlaresFragment : Fragment() {

    private var _binding : FragmentSolarFlaresBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = SolarFlaresFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolarFlaresBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(SolarFlareViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
        viewModel.sendRequest(R.id.solar_flares_progress)
    }

    @SuppressLint("NewApi") // TODO HW не потерять пользователя 24-27 sdk версии
    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                binding.solarFlaresDescription.isVisible = false
                binding.solarFlaresProgress.isVisible = false
                binding.solarFlaresProgress.showSnackBarAction(
                    state.error.toString(),
                    R.string.reload.toString(),
                    {
                        viewModel.sendRequest(R.id.solar_flares_progress)
                    })
            }

            is AppState.Loading -> {
                binding.solarFlaresDescription.isVisible = false
                binding.solarFlaresProgress.isVisible = true
            }

            is AppState.Success -> {
                val data = state.responseData.solarFlares
                binding.solarFlaresDescription.isVisible = true
                data?.let {
                    if (data.isNotEmpty()) {
                        binding.solarFlaresDescription.isVisible = true
                        binding.solarFlaresProgress.isVisible = false

                        val descriptions = StringBuilder()
                        for (flare in data) {
                            descriptions.append(flare.toString())
                            descriptions.append('\n')
                        }
                        binding.solarFlaresDescription.text = descriptions
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}