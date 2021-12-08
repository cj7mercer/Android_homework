package com.example.diarybook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_write_diary.*
import kotlinx.android.synthetic.main.activity_write_diary.image_left_button
import kotlinx.android.synthetic.main.activity_write_diary.image_right_button
import kotlinx.android.synthetic.main.activity_write_diary.submit
import java.time.LocalDate

class WriteDiaryActivity : AppCompatActivity() {

    private var image_left = false
    private var image_left_str: String = ""
    private var image_right_str: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_diary)

        val sqlUtils = SqlUtils(this, "Diary")

        image_left_button.setOnClickListener {
            ImageUtils.show_number_popupmenu(this, it)
        }

        image_right_button.setOnClickListener {
            ImageUtils.show_number_popupmenu(this, it)
        }

        submit.setOnClickListener {
            val author = intent.getStringExtra("account")!!
            val title = insert_title.text.toString()
            val content = insert_content.text.toString()
            val date = LocalDate.now().toString().replace("-", "/")
            sqlUtils.insert(title, author, date, content, image_left_str, image_right_str)

            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ImageUtils.takePhoto -> if (resultCode == Activity.RESULT_OK ) {
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(ImageUtils.imageUri))
                set_image(bitmap, ImageUtils.imageUri)
            } else {
                ImageUtils.outputImage?.delete()
            }
            ImageUtils.fromAlum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        Log.d("uri", uri.toString())
                        val bitmap = ImageUtils.getBitmapFromUri(this, uri)
                        set_image(bitmap, uri)
                        ImageUtils.outputImage = null
                    }
                }
            }
        }
    }

    private fun set_image(bitmap: Bitmap, uri: Uri) {
        image_left = if (!image_left) {
            image_left_button.setImageBitmap(ImageUtils.rotateIfRequired(bitmap))
            image_left_str = uri.toString()
            true
        } else{
            image_right_button.setImageBitmap(ImageUtils.rotateIfRequired(bitmap))
            image_right_str = uri.toString()
            false
        }
    }
}