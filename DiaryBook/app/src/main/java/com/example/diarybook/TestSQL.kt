package com.example.diarybook

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_sql.*

class TestSQL : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_sql)
        val dbHelper = DiarySQLiteOpenHelper(this, "diary", null, 1)
        add.setOnClickListener {
            val db = dbHelper.writableDatabase
            val value1 = ContentValues().apply {
                put("title", "This is a title")
                put("author", "zrzs")
                put("date", "2021/11/2")
                put("content", "给给给给")
            }

            db.insert("Diary", null, value1)

            Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show()
        }

        upgrade.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("author", "xvzh")
            db.update("Diary", values, "id = ?", arrayOf("2"))
        }

        delete.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Diary", "id = ?", arrayOf("2"))
        }

        select.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Diary", null, null, null,null,null,"id")
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val date = cursor.getString(cursor.getColumnIndex("date"))
                    val content = cursor.getString(cursor.getColumnIndex("content"))
                    Log.d("TestSQL", "id is $id")
                    Log.d("TestSQL", "author is $author")
                    Log.d("TestSQL", "date is $date")
                    Log.d("TestSQL", "content is $content")
                } while (cursor.moveToNext())
            }
        }

    }
}