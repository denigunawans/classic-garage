package com.dng.classicgarage.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dng.classicgarage.R
import com.dng.classicgarage.model.Car
import com.dng.classicgarage.view.DetailCarActivity

class GridCarAdapter(private val listCar: ArrayList<Car>) : RecyclerView.Adapter<GridCarAdapter.GridViewHolder>() {
    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txBrand: TextView = itemView.findViewById(R.id.tx_brandGrid)
        val txProduction: TextView = itemView.findViewById(R.id.tx_yearProductionGrid)
        val imgCar: ImageView = itemView.findViewById(R.id.img_carItemGrid)
        val progressBar: ProgressBar = itemView.findViewById(R.id.loading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int = listCar.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val (brand, photo,_, production) = listCar[position]
        Glide.with(holder.itemView.context)
            .load(photo).circleCrop()
            .error(Glide.with(holder.itemView.context)
                .load(R.drawable.no_image)
                .circleCrop())
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

            })
            .into(holder.imgCar)
        holder.txBrand.text = brand
        holder.txProduction.text = production

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCarActivity::class.java)
            intent.putExtra("DATA", listCar[position])
            holder.itemView.context.startActivity(intent)
        }
    }

}