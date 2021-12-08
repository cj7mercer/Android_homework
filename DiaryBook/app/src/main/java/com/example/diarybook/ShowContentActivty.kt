package com.example.diarybook

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_content.*

class ShowContentActivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        val sqlUtils = SqlUtils(this, "Diary")
        val id = intent.getStringExtra("id")!!
        val contentValues = sqlUtils.query_id(id)

        diary_title.setText(contentValues.getAsString("title"))
        diary_content.setText(contentValues.getAsString("content"))

        val image_left_str = contentValues.getAsString("image_left")
        val image_right_str = contentValues.getAsString("image_right")
        if (image_left_str != "")
            image_left.setImageBitmap(ImageUtils.getBitmapFromUri(this, Uri.parse(image_left_str)))
        if (image_right_str != "")
            image_right.setImageBitmap(ImageUtils.getBitmapFromUri(this, Uri.parse(image_right_str)))

    }
}