package com.example.tablayoutassignment.fragments

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tablayoutassignment.BookModel
import com.example.tablayoutassignment.MainActivity
import com.example.tablayoutassignment.R
import com.example.tablayoutassignment.database.DatabaseHelper


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val etTitle: EditText = view.findViewById(R.id.etTitle)
        val etAuthor: EditText = view.findViewById(R.id.etAuthor)
        val etPages: EditText = view.findViewById(R.id.etPages)
        val btnAddBook: Button = view.findViewById(R.id.btnAddBook)

        btnAddBook.setOnClickListener {
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val pages = etPages.text.toString()

            val dbHelper = DatabaseHelper(view.context)
            if (title.isNotEmpty() && author.isNotEmpty() && pages.isNotEmpty())
            {
                val status = dbHelper.addBook(BookModel(title, author, pages))
                if (status > -1)
                {
                    Toast.makeText(view.context, "Book Saved", Toast.LENGTH_LONG).show()
                    etTitle.text.clear()
                    etAuthor.text.clear()
                    etPages.text.clear()
                }else
                {
                    Toast.makeText(view.context, "Error acquired", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(view.context, "Fill all fields", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }


}