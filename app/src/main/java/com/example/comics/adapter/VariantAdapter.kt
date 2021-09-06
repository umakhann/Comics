package com.example.comics.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.databinding.ItemBookMoreBookBinding
import com.example.comics.databinding.ItemVariantBinding
import com.example.comics.model.Book
import com.squareup.picasso.Picasso

class VariantAdapter(private val list: List<Book.Name>, private val listener: Click) : RecyclerView.Adapter<VariantAdapter.ViewHolder>() {

    private val TAG = "variantadapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemVariantBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item.name)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemVariantBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{



        fun bind(title: String){
            binding.apply {

                itemView.setOnClickListener(this@ViewHolder)

                variantName.text = title
            }
        }

        override fun onClick(v: View?) {
            listener.onClick(adapterPosition)
        }
    }
}