package com.geekbrains.mynasa_md.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentPictureBinding
import com.geekbrains.mynasa_md.utils.Constants.DAY_BEFORE_YESTERDAY
import com.geekbrains.mynasa_md.utils.Constants.TAG_BS
import com.geekbrains.mynasa_md.utils.Constants.TODAY
import com.geekbrains.mynasa_md.utils.Constants.WIKI_URI
import com.geekbrains.mynasa_md.utils.Constants.YESTERDAY
import com.geekbrains.mynasa_md.utils.getDate
import com.geekbrains.mynasa_md.utils.showSnackBarAction
import com.geekbrains.mynasa_md.utils.showSnackBarNoAction
import com.geekbrains.mynasa_md.viewmodel.AppState
import com.geekbrains.mynasa_md.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PictureOfTheDayFragment : Fragment() {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.fpicturesTextInputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(WIKI_URI + binding.fpicturesTextInputEditText.text.toString())
            })
        }

        initBottomSheet()
        //viewModel.sendRequest(date = getDate(1))

        binding.chips3.setOnClickListener{
            viewModel.sendRequest(date = getDate(DAY_BEFORE_YESTERDAY))
        }
        binding.chips2.setOnClickListener{
            viewModel.sendRequest(date = getDate(YESTERDAY))
        }
        binding.chips1.setOnClickListener{
            viewModel.sendRequest(date = getDate(TODAY))
        }
    }

    private fun showFragment(fragment: Fragment, backstack: Boolean) {
        val sfm = parentFragmentManager.beginTransaction()
            .replace(R.id.a_frame_container, fragment)
        // Проверка необходимости положить предыдущий фрагмент в бэкстэк
        if (backstack) {
            sfm.addToBackStack(fragment.toString())
        }
        sfm.commit()
    }

    private fun initBottomSheet() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.fPicturesBottomSheet.fdialogContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // todo обработка состояния открытия BottomSheet
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // todo обработка состояния "STATE" BottomSheet
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d(TAG_BS, "onStateChanged: STATE_COLLAPSED $newState")
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d(TAG_BS, "onStateChanged: STATE_DRAGGING $newState")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.d(TAG_BS, "onStateChanged: STATE_EXPANDED $newState")
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.d(TAG_BS, "onStateChanged: STATE_HALF_EXPANDED $newState")
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d(TAG_BS, "onStateChanged: STATE_HIDDEN $newState")
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        Log.d(TAG_BS, "onStateChanged: STATE_SETTLING $newState")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //todo возвращает значение, на сколько сейчас BottomSheet открыт
                Log.d(TAG_BS, "onSlide: $slideOffset")
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { state ->
            renderData(state)
        })
        viewModel.sendRequest(R.id.fpictures_progress, getDate(TODAY))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                binding.fpicturesImageview.isVisible = false
                binding.fpicturesProgress.isVisible = false
                binding.fpicturesProgress.showSnackBarAction(
                    state.error.toString(),
                    R.string.reload.toString(),
                    {
                        viewModel.sendRequest(R.id.fpictures_progress, getDate(TODAY))
                    })
            }
            is AppState.Loading -> {
                binding.fpicturesImageview.isVisible = false
                binding.fpicturesProgress.isVisible = true
            }
            is AppState.Success -> {
                binding.fpicturesImageview.isVisible = true
                binding.fpicturesProgress.isVisible = false
                if (state.pictureOfTheDayResponseData.url.toString().takeLast(4) == ".jpg") {
                    if (state.pictureOfTheDayResponseData.hdurl == null) {
                        binding.fpicturesImageview.load(state.pictureOfTheDayResponseData.url)
                    } else {
                        binding.fpicturesImageview.load(state.pictureOfTheDayResponseData.hdurl)
                    }
                } else {
                    binding.fpicturesImageview.setImageDrawable(R.drawable.ic_outline_image_24.toDrawable())
                    binding.fpicturesProgress.showSnackBarNoAction(R.string.video_info.toString())
                }

                with(binding.fPicturesBottomSheet) {
                    fBottomSheetTitle.text = state.pictureOfTheDayResponseData.title
                    fBottomSheetDescription.text = state.pictureOfTheDayResponseData.explanation
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}