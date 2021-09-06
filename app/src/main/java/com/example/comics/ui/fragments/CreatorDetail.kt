package com.example.comics.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.R
import com.example.comics.ui.fragments.BaseFragment
import com.example.comics.adapter.Click
import com.example.comics.adapter.ClickBook
import com.example.comics.adapter.TemporaryLayedBookAdapter
import com.example.comics.databinding.FragmentCreatorBinding
import com.example.comics.model.Book
import com.example.comics.source.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatorDetail : BaseFragment(R.layout.fragment_creator), ClickBook {

    val TAG = "creatordetails"
    private lateinit var list : List<Book>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCreatorBinding.bind(view)
        val id = arguments?.getInt("id")
        Log.d(TAG, id.toString())
        val bookViewModel : BookViewModel by viewModels()

        binding.apply {

            if (id != null && isInternetOn()) {
                bookViewModel.getCreator(id).observe(this@CreatorDetail) { comics ->

                    creatorName.text = arguments?.getString("name")
                    creatorRecycler.layoutManager = LinearLayoutManager(context)
                    list = comics.data.results
                    creatorRecycler.adapter = TemporaryLayedBookAdapter(list,
                        this@CreatorDetail, context!!)


                    lifecycleScope.launch() {
                        delay(1000)
                        progressBar.visibility = View.GONE
                        creatorRecycler.visibility = View.VISIBLE
                    }




                }
            } else if(!isInternetOn()) {
                progressBar.visibility = View.GONE
                creatorRecycler.visibility = View.GONE
                noInternet.visibility = View.VISIBLE
            }

            moreBookBack.setOnClickListener(){
                findNavController().navigateUp()
            }
        }
    }

    override fun onClickBook(book: Book) {
        val action = CreatorDetailDirections.actionCreatorDetailToMoreBook2(book)
        findNavController().navigate(action)
    }
}