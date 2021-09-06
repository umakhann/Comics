package com.example.comics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.databinding.ItemTypeBinding

class TypesAdapter(private val list: ArrayList<String>) : RecyclerView.Adapter<TypesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemTypeBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(a: String){
            binding.apply {
                typeOnlyText.setText(a)
            }
        }
    }
}