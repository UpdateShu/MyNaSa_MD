package com.geekbrains.mynasa_md.view.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import coil.load
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentEarthBinding
import com.geekbrains.mynasa_md.viewmodel.AppState
import com.geekbrains.mynasa_md.viewmodel.EarthViewModel
import com.geekbrains.mynasa_md.viewmodel.utils.showSnackBarAction

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    companion object {
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

    private var flag : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
        viewModel.sendRequest(R.id.earth_progress)

        binding.fEarthCoordinator.also { coord ->
            coord.earthImageView.setOnClickListener {
                // добавляем анимацию (трансформацию) изображения
                val changeBounds = ChangeImageTransform()
                TransitionManager.beginDelayedTransition(
                    coord.coordinatorCollapsing, changeBounds)

                flag = !flag
                // без анимации:
                coord.earthImageView.scaleType = if (flag)
                    ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.CENTER_INSIDE
            }
        }
    }

    @SuppressLint("NewApi") // TODO HW не потерять пользователя 24-27 sdk версии
    private fun renderData(state: AppState) {
        binding.fEarthCoordinator.also { coor ->
            when (state) {
                is AppState.Error -> {
                    coor.earthImageView.isVisible = false
                    coor.earthProgress.isVisible = false
                    coor.earthProgress.showSnackBarAction(
                        state.error.toString(),
                        R.string.reload.toString(),
                        {
                            viewModel.sendRequest(R.id.earth_progress)
                        })
                }

                is AppState.Loading -> {
                    coor.earthImageView.isVisible = false
                    coor.earthProgress.isVisible = true
                }

                is AppState.Success -> {
                    val data = state.responseData.earth
                    coor.earthImageView.isVisible = true
                    data?.let {
                        if (data.isNotEmpty()) {
                            coor.earthImageView.isVisible = true
                            coor.earthProgress.isVisible = false

                            val first = data.get(0)
                            coor.earthImageView.load(first.getImageUrl())
                        }
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