package com.example.comics.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comics.databinding.ItemBookLayedBinding
import com.example.comics.model.Book

class TemporaryLayedBookAdapter(private val list: List<Book>, private val listener: ClickBook, val context: Context) : RecyclerView.Adapter<TemporaryLayedBookAdapter.ViewHolder>() {

    val TAG = "TemporaryLayedBookAdapter"

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



        @SuppressLint("SetTextI18n", "LongLogTag")
        fun bind(book: Book){
            binding.apply {

                itemView.setOnClickListener(this@ViewHolder)

                var image = book.thumbnail.path + "." + book.thumbnail.extension
                var imageUrl = image
//                 Log.d(TAG, image)


                if(image[4] != 's')
                    imageUrl = image.substring(0, 4) + "s" + image.substring(4, image.length)

//                Log.d(TAG, imageUrl)

                if(imageUrl == "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                    imageUrl = "https://c8.alamy.com/comp/WEJWDG/vintage-comic-book-cover-artwork-WEJWDG.jpg"

                Glide.with(context)
                    .load(imageUrl)
//                    .listener(object: RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//
//                    itemView.findViewById(R.id.progress_bar)progressBar.vis
//                    detailImage.isVisible = true
//
//                    if(photo.description != null)
//                        detailDescription.isVisible = true
//
//                    detailCreator.isVisible = true
//                    return false
//
//                }
//            })
                    .into(layedImage)


//                Picasso.get()
//                    .load(imageUrl)
//                    .into(layedImage)

               if(book.creators.available != 0)
                        layedName.text = "by " + book.creators.items[0].name
                else layedName.text = "by Author"


                layedTitle.text = book.title
                }
            }

       override fun onClick(v: View?) {
           listener.onClickBook(list[adapterPosition])
       }
    }
    }