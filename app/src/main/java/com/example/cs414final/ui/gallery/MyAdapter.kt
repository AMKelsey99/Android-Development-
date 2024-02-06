package com.example.cs414final.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs414final.R


class MyAdapter(private val myDataSet: ArrayList<RowItem>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val hourlyTime = itemView.findViewById<TextView>(R.id.dailyTimeValue)
        val hourlyTemp = itemView.findViewById<TextView>(R.id.dailyMinValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myDataSet[position]
        holder.hourlyTime.text = currentItem.hourlyTimeValue
        holder.hourlyTemp.text = currentItem.hourlyTempValue
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }
}