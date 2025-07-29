package com.pangea.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pangea.weather.R
import com.pangea.weather.interfaces.ClickListener
import com.pangea.weather.models.City

class CityAdapter(items: ArrayList<City>, private var listener: ClickListener): RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private var items: ArrayList<City>? = items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_adapter, parent, false)
        val viewHolder = ViewHolder(view, listener)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return items?.count()?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.name.text = item?.name

    }

    class ViewHolder(view: View, private var listener: ClickListener): RecyclerView.ViewHolder(view), View.OnClickListener {
        val name = view.findViewById<TextView>(R.id.tvName)

        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            v?.let {
                listener.onClick(it, adapterPosition)
            }
        }

    }
}