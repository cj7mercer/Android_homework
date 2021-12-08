package com.example.diarybook

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.util.ArrayList

class SqlUtils(val context: Context, val dbName: String) {

    private lateinit var db: android.database.sqlite.SQLiteDatabase

    init {
        val dbHelper = DiarySQLiteOpenHelper(context, dbName, null, 1)
        db = dbHelper.writableDatabase
    }

    fun delete(id: String) {
        db.delete(dbName, "id = ?", arrayOf(id))
        Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()
    }

    fun insert(title: String, author: String, date: String, content: String, image_left: String, image_right: String) {
        val value = ContentValues().apply {
            put("title", title)
            put("author", author)
            put("date", date)
            put("content", content)
            put("image_left", image_left)
            put("image_right", image_right)
        }
        db.insert(dbName, null, value)
        Toast.makeText(context, "Insert Success", Toast.LENGTH_SHORT).show()
    }

    fun update_title(id: String, new_title: String) {
        val values = ContentValues().apply {
            put("title", new_title)
        }
        db.update(dbName, values, "id = ?", arrayOf(id))
        Toast.makeText(context, "Update Title Success", Toast.LENGTH_SHORT).show()
    }

    fun update_author(old_author: String, new_author: String) {
        val values = ContentValues().apply {
            put("author", new_author)
        }
        db.update(dbName, values, "author = ?", arrayOf(old_author))
        Toast.makeText(context, "Update Author Success", Toast.LENGTH_SHORT).show()
    }

    fun update_content(id: String, new_title: String) {
        val values = ContentValues().apply {
            put("content", new_title)
        }
        db.update(dbName, values, "id = ?", arrayOf(id))
        Toast.makeText(context, "Update Content Success", Toast.LENGTH_SHORT).show()
    }

    fun update_image_left(id: String, new_image: String) {
        val values = ContentValues().apply {
            put("image_left", new_image)
        }
        db.update(dbName, values, "id = ?", arrayOf(id))
        Toast.makeText(context, "Update Content Success", Toast.LENGTH_SHORT).show()
    }

    fun update_image_right(id: String, new_image: String) {
        val values = ContentValues().apply {
            put("image_right", new_image)
        }
        db.update(dbName, values, "id = ?", arrayOf(id))
        Toast.makeText(context, "Update Content Success", Toast.LENGTH_SHORT).show()
    }

    fun query_id(id: String): ContentValues{
        val contentValues = ContentValues()
        val cursor = db.query(dbName, null, "id = ?", arrayOf(id), null, null, "id")
        var title = ""
        var content = ""
        var image_right = ""
        var image_left = ""
        if (cursor.moveToFirst()) {
            title = cursor.getString(cursor.getColumnIndex("title"))
            content = cursor.getString(cursor.getColumnIndex("content"))
            image_left = cursor.getString(cursor.getColumnIndex("image_left"))
            image_right = cursor.getString(cursor.getColumnIndex("image_right"))
        }

        contentValues.apply {
            put("title", title)
            put("content", content)
            put("image_left", image_left)
            put("image_right", image_right)
        }

        return contentValues
    }

    fun query_author(accout: String): ArrayList<DiaryItem> {
        val list = ArrayList<DiaryItem>()

        val cursor = db.query(dbName, null, "author = ?", arrayOf(accout), null, null, "id")
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val date = cursor.getString(cursor.getColumnIndex("date"))
                val content = cursor.getString(cursor.getColumnIndex("content"))
                val diary = DiaryItem(id, title, date, author, content)
                list.add(diary)
            } while (cursor.moveToNext())
        }

        return list
    }

}