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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentEarthBinding
import com.geekbrains.mynasa_md.viewmodel.AppState
import com.geekbrains.mynasa_md.viewmodel.EarthViewModel
import com.geekbrains.mynasa_md.viewmodel.SolarFlareViewModel
import com.geekbrains.mynasa_md.viewmodel.utils.showSnackBarAction

class EarthFragment : Fragment() {

    private var _binding : FragmentEarthBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = EarthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
        viewModel.sendRequest(R.id.earth_progress)
    }

    @SuppressLint("NewApi") // TODO HW не потерять пользователя 24-27 sdk версии
    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                binding.earthImageView.isVisible = false
                binding.earthProgress.isVisible = false
                binding.earthProgress.showSnackBarAction(
                    state.error.toString(),
                    R.string.reload.toString(),
                    {
                        viewModel.sendRequest(R.id.earth_progress)
                    })
            }

            is AppState.Loading -> {
                binding.earthImageView.isVisible = false
                binding.earthProgress.isVisible = true
            }

            is AppState.Success -> {
                val data = state.responseData.earth
                binding.earthImageView.isVisible = true
                data?.let {
                    if (data.isNotEmpty()) {
                        binding.earthImageView.isVisible = true
                        binding.earthProgress.isVisible = false

                        val first = data.get(0)
                        binding.earthImageView.load(first.getImageUrl())
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