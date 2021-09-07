package com.example.whattowear.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowear.databinding.FragmentForecastsListBinding


/**
 * [Fragment] subclass.
 */
class ForecastsListFragment : Fragment() {
    private lateinit var viewModel: ForecastViewModel

    private var _binding: FragmentForecastsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    /**
     * Retrieve existing [ForecastViewModel] or instantiate new one if there is none
     * Inflate layout
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        _binding = FragmentForecastsListBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.futureForecasts

        /** Setting up LiveData observation relationship **/
        viewModel.subForecasts.observe(viewLifecycleOwner, { newStatus ->
            recyclerView.adapter = ForecastAdapter(context, viewModel.subForecasts.value)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}