package com.asadevelopers.findmovies.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asadevelopers.findmovies.R
import com.asadevelopers.findmovies.models.Category
import com.google.android.material.button.MaterialButton


class CategoriesAdapter(private val list: ArrayList<Category>, private val listener: ButtonCLicked): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val button : MaterialButton = itemView.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_btn, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        val rnd = java.util.Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        holder.button.setBackgroundColor(color)
        holder.button.text = currentItem.name
        holder.itemView.setOnClickListener {
            listener.onItemClicked(currentItem)
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }
}

interface ButtonCLicked{
    fun onItemClicked(category: Category)
}