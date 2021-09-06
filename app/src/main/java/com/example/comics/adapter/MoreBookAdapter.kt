package com.example.comics.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.databinding.ItemBookMoreBookBinding
import com.example.comics.model.Book
import com.squareup.picasso.Picasso

class MoreBookAdapter(private val list: List<Book>, private val listener: ClickBook) : RecyclerView.Adapter<MoreBookAdapter.ViewHolder>() {

    private val TAG = "morebookadapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBookMoreBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item.title, item.thumbnail.path + "." + item.thumbnail.extension)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemBookMoreBookBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{



        fun bind(title: String, image: String){
            binding.apply {

                itemView.setOnClickListener(this@ViewHolder)

                var imageUrl = image
//                Log.d(TAG, image)


                if(image[4] != 's')
                    imageUrl = image.substring(0, 4) + "s" + image.substring(4, image.length)

//                Log.d(TAG, imageUrl)

                if(imageUrl == "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                    imageUrl = "https://c8.alamy.com/comp/WEJWDG/vintage-comic-book-cover-artwork-WEJWDG.jpg"

                Picasso.get()
                    .load(imageUrl)
                    .into(imageOfBook)


//                Glide.with(itemView)
//                    .load(imageUrl)
//                    .into(imageOfBook)


                nameOfBook.setText("Lorem Ipsum")
                titleOfBook.setText(title)
            }
        }

        override fun onClick(v: View?) {
            listener.onClickBook(list[adapterPosition])
        }
    }
}