package com.example.comics.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.databinding.ItemBookLayedBinding
import com.example.comics.model.Book
import com.example.comics.ui.fragments.createLink
import com.squareup.picasso.Picasso

class LayedBookAdapter(private val list: List<Book>, private val listener: ClickBook) : RecyclerView.Adapter<LayedBookAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBookLayedBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemBookLayedBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{


        fun bind(book: Book){
            binding.apply {
                Picasso.get().load(createLink(book.thumbnail.path, book.thumbnail.extension))
                    .into(layedImage)
//                layedName.text = book.title
                layedTitle.text = book.title

                itemView.setOnClickListener(this@ViewHolder)
            }
        }

        override fun onClick(v: View?) {
            listener.onClickBook(list[adapterPosition])
        }
    }
}