package com.example.diarybook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.diarybook.ImageUtils.outputImage
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_update.image_right_button
import kotlinx.android.synthetic.main.activity_update.update_content
import kotlinx.android.synthetic.main.activity_update.update_title


class EditDiaryActivity : AppCompatActivity() {

    private var image_left = false
    private var image_left_str: String = ""
    private var image_right_str: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val sqlUtils = SqlUtils(this, "Diary")
        val diary_id = intent.getStringExtra("id")!!

        val contentValues = sqlUtils.query_id(diary_id)
        update_title.setText(contentValues.getAsString("title"))
        update_content.setText(contentValues.getAsString("content"))
        image_left_str = contentValues.getAsString("image_left")
        image_right_str = contentValues.getAsString("image_right")
        if (image_left_str != "")
            image_left_button.setImageBitmap(ImageUtils.getBitmapFromUri(this, Uri.parse(image_left_str)))
        if (image_right_str != "")
            image_right_button.setImageBitmap(ImageUtils.getBitmapFromUri(this, Uri.parse(image_right_str)))

        image_left_button.setOnClickListener {
            ImageUtils.show_number_popupmenu(this, it)
        }

        image_right_button.setOnClickListener {
            ImageUtils.show_number_popupmenu(this, it)
        }

        submit.setOnClickListener {
            val sqlUtils =  SqlUtils(this, "Diary")
            sqlUtils.update_title(diary_id, update_title.text.toString())
            sqlUtils.update_content(diary_id, update_content.text.toString())
            sqlUtils.update_image_left(diary_id, image_left_str)
            sqlUtils.update_image_right(diary_id, image_right_str)

            val intent = Intent(this, ShowContentActivty::class.java)
            intent.putExtra("id", diary_id)
            startActivity(intent)

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
                outputImage?.delete()
            }
            ImageUtils.fromAlum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        Log.d("uri", uri.toString())
                        val bitmap = ImageUtils.getBitmapFromUri(this, uri)
                        set_image(bitmap, uri)
                        outputImage = null
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