package com.example.whattowear.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.whattowear.databinding.ForecastBinding
import com.example.whattowear.databinding.FragmentMainForecastBinding

class MainForecastFragment : Fragment() {

    private lateinit var viewModel: ForecastViewModel

    private var _binding: FragmentMainForecastBinding? = null
    private val binding get() = _binding!!

    private lateinit var forecast: ForecastBinding

    /**
     * Retrieve existing [ForecastViewModel] or instantiate new one if there is none
     * Inflate layout
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        _binding = FragmentMainForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        /** Setting up LiveData observation relationship **/
        viewModel.mainForecast.observe(viewLifecycleOwner, { newStatus ->
            binding.forecastToday.forecastText.text = newStatus.day
            binding.forecastToday.forecastImage.setImageResource(newStatus.drawable)
            binding.forecastToday.forecastTextLong.text = newStatus.subtitle
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}