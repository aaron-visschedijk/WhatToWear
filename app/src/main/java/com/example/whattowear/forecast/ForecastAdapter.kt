package com.example.whattowear.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowear.R
import com.example.whattowear.model.WearForecast

/**
 * This class implements a [RecyclerView] which uses Data Binding to present [List] [WearForecast]
 * data
 */
class ForecastAdapter(private val context: Context?, private val dataset: List<WearForecast>?) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    /**
     * Viewholder class containing the [TextView] and [ImageView]
     */
    class ForecastViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.forecast_text)
        val imageView: ImageView = view.findViewById(R.id.forecast_image)
    }

    /**
     * Inflates the layout and dynamically changes the width of each list item
     * TODO: see if it is possible to change the recyclerview itemwidth in XML
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast, parent, false)

        adapterLayout.layoutParams.width = (parent.measuredWidth * 0.42f).toInt()
        return ForecastViewHolder(adapterLayout)
    }

    /**
     * Bind contents of the view to the dataset
     */
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = dataset?.get(position)

        if (forecast != null) {
            holder.textView.text = forecast.day
            holder.imageView.setImageResource(forecast.drawable)
        }
    }

    /**
     * Return number of items in dataset
     */
    override fun getItemCount(): Int {
        if (dataset != null) {
            return dataset.size
        }
        return 0
    }

}