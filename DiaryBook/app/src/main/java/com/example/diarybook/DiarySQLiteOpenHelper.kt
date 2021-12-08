package com.example.diarybook

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DiarySQLiteOpenHelper(
    val context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    private val createDiary = "create table Diary (" +
            "id integer primary key autoincrement," +
            "title text," +
            "author text," +
            "date text," +
            "content text," +
            "image_left text," +
            "image_right text)"
    private val dropBook = "drop table  if exists \"Book\""

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createDiary)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }



}