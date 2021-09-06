package com.example.comics.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.R
import com.example.comics.adapter.Click
import com.example.comics.ui.fragments.BaseFragment
import com.example.comics.adapter.ClickBook
import com.example.comics.adapter.MoreBookAdapter
import com.example.comics.adapter.VariantAdapter
import com.example.comics.databinding.FragmentMoreBookBinding
import com.example.comics.model.Book
import com.example.comics.source.viewmodel.BookViewModel
import com.example.comics.source.viewmodel.DbViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class MoreBook : BaseFragment(R.layout.fragment_more_book), ClickBook, Click {

    companion object{

        val TAG = "morebook"
        lateinit var listStatic : List<Book>
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookViewModel : BookViewModel by viewModels()
        val dbViewModel : DbViewModel by viewModels()
        val binding = FragmentMoreBookBinding.bind(view)



        binding.apply {



            moreBookBack.setOnClickListener(){
                findNavController().navigateUp()
            }



            val book = arguments?.getParcelable<Book>("book")


            moreBookShare.setOnClickListener(){
                book?.let {
                    lifecycleScope.launch(Dispatchers.IO){
                        dbViewModel.rowExists(book.id).observe(this@MoreBook) { result ->
                            if(!result) {

                                dbViewModel.insert(book)

                                Snackbar.make(
                                binding.root, "Bookmarked!",
                                Snackbar.LENGTH_SHORT
                            )
                                .setAction("CLOSE") { }
                                .setActionTextColor(resources.getColor(R.color.purple_700))
                                .show()


                            }
                            else {
                                dbViewModel.delete(book)

                                Snackbar.make(
                                    binding.root, "Removed from Bookmarks!",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .setAction("CLOSE") { }
                                    .setActionTextColor(resources.getColor(R.color.purple_700))
                                    .show()
                            }
                        }

                    }

                }
            }

            moreTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
            moreTitle.isSelected = true
            moreTitle.isSingleLine = true
            moreTitle.text = book?.title

            if(book?.series?.name != null){
                series.text = "Series: " + book!!.series.name

//                series.setOnClickListener {
//                    val action = MoreBookDirections.actionMoreBookToCreatorDetail(book.id, book.creators.items.get(0).name)
//                    findNavController().navigate(action)
//                }

            } else series.text = ""


            var imageUrl = book?.thumbnail?.path + "." + book?.thumbnail?.extension

            Log.d(TAG, book?.id.toString())


            if(imageUrl[4] != 's')
                imageUrl = imageUrl.substring(0, 4) + "s" + imageUrl.substring(4, imageUrl.length)

//            Log.d(TAG, imageUrl)

            if(imageUrl == "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
                imageUrl = "https://c8.alamy.com/comp/WEJWDG/vintage-comic-book-cover-artwork-WEJWDG.jpg"

            Picasso.get()
                .load(imageUrl)
                .into(moreBookImage)

            val random = (0..40000).random()
            Log.d(TAG + "_random", random.toString())
//            48058
//            43719
            if(isInternetOn())
            bookViewModel.getComics(random).observe(this@MoreBook) {

                val list = it.data.results
                Collections.shuffle(list)
                listStatic = list

                // price, pagecount, description
                book?.let {
                    price.text = if(book.prices[0].price.toDouble() < 1) "No price information" else "Price: $${book.prices[0].price}"
                    pageCount.text = if(book.pageCount < 1) "No page count information" else "Page Count: ${book.pageCount}"
                    description.text = if(book.description == null) "No description" else book.description
                }


                moreBookVariantRecyclerView.apply {

                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                    if(book?.variants?.size!! > 1)
                    adapter = VariantAdapter(book.variants, this@MoreBook)

                    progressBar.visibility = View.GONE
                    moreBookRecyclerView.visibility = View.VISIBLE


                }


                moreBookRecyclerView.apply {

                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = MoreBookAdapter(list, this@MoreBook)

                    progressBar.visibility = View.GONE
                    moreBookRecyclerView.visibility = View.VISIBLE


                }





            } else {
                progressBar.visibility = View.GONE
                moreBookRecyclerView.visibility = View.GONE
                noInternet.visibility = View.VISIBLE
            }


        }



    }

    override fun onClickBook(book: Book) {
        val action = MoreBookDirections.actionMoreBookSelf(book)
        findNavController().navigate(action)
    }

    override fun onClick(position: Int) {
//        val action = MoreBookDirections.actionMoreBookSelf(book)
//        findNavController().navigate(action)
    }
}