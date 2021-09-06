package com.example.comics.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.R
import com.example.comics.adapter.ClickBook
import com.example.comics.adapter.LayedBookAdapter
import com.example.comics.adapter.MainBookAdapter
import com.example.comics.adapter.TypesAdapter
import com.example.comics.databinding.FragmentMainPageBinding
import com.example.comics.model.Book
import com.example.comics.util.TemporaryUtils
import com.example.comics.source.viewmodel.BookViewModel
import com.example.comics.source.viewmodel.DbViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainPage : BaseFragment(R.layout.fragment_main_page), ClickBook {

    companion object {
        var moreRecommendedIsSmall = true
        val TAG = "mainpage"
        lateinit var list : List<Book>
        var randomNumber: Int = (0..40000).random()

    }

//    override fun onResume() {
//        super.onResume()
//
//        FragmentMainPageBinding.inflate(layoutInflater).scrollview.visibility = View.GONE
//        FragmentMainPageBinding.inflate(layoutInflater).toolbar.visibility = View.GONE
//        FragmentMainPageBinding.inflate(layoutInflater).progressBar.visibility = View.VISIBLE
//
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookViewModel : BookViewModel by viewModels()
        val dbViewModel : DbViewModel by viewModels()
        val binding = FragmentMainPageBinding.bind(view)


        binding.apply {



            typesRec.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = TypesAdapter(TemporaryUtils.list_type)
            }


            booksRec.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                val random = randomNumber
                Log.d(TAG + "_random", random.toString())


                if(isInternetOn()) {
                    bookViewModel.getComics(random)
                        .observe(this@MainPage) { response ->

                            Log.d(TAG, "observe")
                            list = response.data.results
//                            Collections.shuffle(list)
                            adapter = MainBookAdapter(list, this@MainPage)

                           runBlocking {
                               delay(250)
                           }
                            progressBar.visibility = View.GONE
                            scrollview.visibility = View.VISIBLE
//                            toolbar.visibility = View.VISIBLE

                        }
                }

            }


            lifecycleScope.launch {
                withContext(Dispatchers.Main){

                    layedRec.apply {

                        var bookmarksList : List<Book> = ArrayList()

                        dbViewModel.getComics().observe(this@MainPage) { bookmarks ->
                            layoutManager = LinearLayoutManager(context)
                            if(bookmarks.size>0)
                                adapter = LayedBookAdapter(arrayListOf(bookmarks[0]), this@MainPage)


                            bookmarksList = bookmarks
                        }

                        swipeRefresh.setOnRefreshListener {

                            progressBar.visibility = View.VISIBLE
                            scrollview.visibility = View.GONE

                            changeBookmarksPosition(binding, bookmarksList)
//                toolbar.visibility = View.GONE

                            randomNumber = (0..40000).random()
                            this@MainPage.onViewCreated(view, savedInstanceState)
                            swipeRefresh.isRefreshing = false
                        }




                        upbutton.setOnClickListener {

                            if(bookmarksList.isEmpty()){
                                Snackbar.make(binding.root, "No bookmarks yet!", Snackbar.LENGTH_SHORT)
                                    .setAction("CLOSE") { }
                                    .setActionTextColor(resources.getColor(R.color.purple_700))
                                    .show()
                            } else
                                changeBookmarksPosition(binding, bookmarksList)

                        }
                    }
                }
            }
        }
    }

    override fun onClickBook(book: Book) {
           val action = MainPageDirections.actionMainPageToMoreBook(book)
            findNavController().navigate(action)
    }

    fun changeBookmarksPosition(binding: FragmentMainPageBinding, bookmarksList: List<Book>){

        binding.apply {

            if(moreRecommendedIsSmall)
                upbutton.animate().rotation(180F).start()

            menuButton.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            userFace.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            cardView.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            textView.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            typesRec.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            trending.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            booksRec.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            edgeLeftTop.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE
            edgeRightTop.visibility = if (moreRecommendedIsSmall) View.GONE else View.VISIBLE

            if(bookmarksList.size>0) {
                layedRec.apply {
                    if (!moreRecommendedIsSmall)

                        adapter = LayedBookAdapter(
                            arrayListOf(bookmarksList[0]),
                            this@MainPage)

                    else adapter = LayedBookAdapter(
                        bookmarksList.let { bookmarksList ->
                            bookmarksList
                        },
                        this@MainPage)
                }
            }

            if(!moreRecommendedIsSmall)
            upbutton.animate().rotation(0F).start()

        }


        moreRecommendedIsSmall = moreRecommendedIsSmall != true
    }


}