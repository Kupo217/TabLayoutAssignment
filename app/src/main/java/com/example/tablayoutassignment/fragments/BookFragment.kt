package com.example.tablayoutassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tablayoutassignment.R
import com.example.tablayoutassignment.adapters.BookAdapter
import com.example.tablayoutassignment.database.DatabaseHelper

class BookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_book, container, false)

        val rvBooks: RecyclerView = view.findViewById(R.id.rvBooks)
        val swipeLayout: SwipeRefreshLayout = view.findViewById(R.id.swipeLayout)
        val dbHelper = DatabaseHelper(view.context)

        val bookList = dbHelper.getBooks()

        if (bookList.size > 0)
        {
            rvBooks.visibility = View.VISIBLE
            rvBooks.layoutManager = LinearLayoutManager(view.context)
            val bookAdapter = BookAdapter(bookList)
            rvBooks.adapter = bookAdapter
        }else
        {
            rvBooks.visibility = View.GONE
        }

        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = false
            val bookListRefreshed = dbHelper.getBooks()

            if (bookListRefreshed.size > 0)
            {
                rvBooks.visibility = View.VISIBLE
                rvBooks.layoutManager = LinearLayoutManager(view.context)
                val bookAdapter = BookAdapter(bookListRefreshed)
                rvBooks.adapter = bookAdapter
            }
        }

        return view
    }

}