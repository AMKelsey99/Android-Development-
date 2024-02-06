package com.example.cs414final.ui.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs414final.R


class MyAdapter2(private val myDataSet: ArrayList<RowItem2>):
    RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dailyTime = itemView.findViewById<TextView>(R.id.dailyTimeValue)
        val dailyMini = itemView.findViewById<TextView>(R.id.dailyMinValue)
        val dailyMaxi = itemView.findViewById<TextView>(R.id.dailyMaxValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myDataSet[position]
        holder.dailyTime.text = currentItem.dailyTimeValue
        holder.dailyMini.text = currentItem.dailyMinValue
        holder.dailyMaxi.text = currentItem.dailyMaxValue
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }
}