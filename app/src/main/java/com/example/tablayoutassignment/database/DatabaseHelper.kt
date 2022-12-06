package com.example.tablayoutassignment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.tablayoutassignment.BookModel

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME($KEY_ID INTEGER PRIMARY KEY, $KEY_TITLE TEXT, $KEY_AUTHOR TEXT, $KEY_PAGES INTEGER)")
        db!!.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addBook(book: BookModel): Long {
        val values = ContentValues()

        values.put(KEY_TITLE, book.title)
        values.put(KEY_AUTHOR, book.author)
        values.put(KEY_PAGES, book.pages)

        val db = this.writableDatabase
        return db.insertOrThrow(TABLE_NAME, null, values)
    }

    fun getBooks(): ArrayList<BookModel> {
        val bookList = ArrayList<BookModel>()

        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var title: String
        var author: String
        var pages: String

        if (cursor!!.moveToFirst())
        {
            do {
                title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE))
                author = cursor.getString(cursor.getColumnIndexOrThrow(KEY_AUTHOR))
                pages = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PAGES))

                val book = BookModel(title, author, pages)
                bookList.add(book)
            }while (cursor.moveToNext())
        }

        return bookList
    }


    companion object {
        const val DATABASE_VERSION = 1

        const val DATABASE_NAME = "BOOK-SHELF"

        const val TABLE_NAME = "book_shelf"

        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_AUTHOR = "author"
        const val KEY_PAGES = "pages"
    }
}