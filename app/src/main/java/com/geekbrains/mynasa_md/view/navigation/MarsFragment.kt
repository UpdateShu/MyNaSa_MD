package com.geekbrains.mynasa_md.view.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.geekbrains.mynasa_md.R
import com.geekbrains.mynasa_md.databinding.FragmentMarsBinding
import com.geekbrains.mynasa_md.viewmodel.AppState
import com.geekbrains.mynasa_md.viewmodel.EarthViewModel
import com.geekbrains.mynasa_md.viewmodel.MarsViewModel
import com.geekbrains.mynasa_md.viewmodel.utils.showSnackBarAction

class MarsFragment : Fragment() {

    private var _binding : FragmentMarsBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = MarsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
        viewModel.sendRequest(R.id.mars_progress)
    }

    @SuppressLint("NewApi") // TODO HW не потерять пользователя 24-27 sdk версии
    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                binding.marsImageView.isVisible = false
                binding.marsProgress.isVisible = false
                binding.marsProgress.showSnackBarAction(
                    state.error.toString(),
                    R.string.reload.toString(),
                    {
                        viewModel.sendRequest(R.id.mars_progress)
                    })
            }

            is AppState.Loading -> {
                binding.marsImageView.isVisible = false
                binding.marsProgress.isVisible = true
            }

            is AppState.Success -> {
                val data = state.responseData.mars
                binding.marsImageView.isVisible = true
                data?.photos?.let {
                    if (it.isNotEmpty()) {
                        binding.marsImageView.isVisible = true
                        binding.marsProgress.isVisible = false

                        val first = it.get(0)
                        try {
                            //Не грузится изображение с Марса?!
                            binding.marsImageView.load(first.imgSrc)
                            //val `in` = java.net.URL("http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.jpg").openStream()
                            //binding.fpicturesImageview.setImageBitmap(BitmapFactory.decodeStream(`in`))
                        }
                        catch (e: Exception) {
                            Log.e("imgload", e.message.toString())
                            e.printStackTrace()
                        }
                        /*try
                        {
                            Glide.with(context)
                                .load(image)
                                .fitCenter()
                                .into(imageView) // the view in which the image will be loaded
                        }
                        catch (e: IOException)
                        {
                            e.printStackTrace()
                        }*/
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