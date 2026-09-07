package com.irfan.cleaningserviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.cleaningserviceapp.R
import com.irfan.cleaningserviceapp.model.ServiceCategory

class CategoryAdapter(
    private val categoryList: List<ServiceCategory>,
    private val onCategoryClick: (ServiceCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.categoryIcon)
        val name: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.icon.setImageResource(category.iconResId)
        holder.name.text = category.name

        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}