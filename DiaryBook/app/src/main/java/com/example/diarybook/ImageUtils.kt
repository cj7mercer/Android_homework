package com.example.diarybook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

object ImageUtils {

    val takePhoto = 1
    val fromAlum = 2

    var outputImage: File? = null
    lateinit var imageUri: Uri

    fun getBitmapFromUri(activity: Activity, uri: Uri) = activity.contentResolver.openFileDescriptor(uri, "r").use {
        BitmapFactory.decodeFileDescriptor(it?.fileDescriptor)
    }

    fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = outputImage?.let { ExifInterface(it.path) }
        val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotateBitmap
    }

    fun show_number_popupmenu(activity: Activity, view: View) {
        // 这里的view代表popupMenu需要依附的view
        val popupMenu = PopupMenu(activity, view)
        // 获取布局文件
        popupMenu.menuInflater.inflate(R.menu.image_button__menu, popupMenu.menu)
        popupMenu.show()
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.camera -> {
                    camera(activity)
                }
                R.id.galley -> {
                    galley(activity)
                }
            }
            false
        }
    }

    private fun camera(activity: Activity) {
            outputImage = File(activity.externalCacheDir, Date().time.toString() + ".jpg")
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(activity, "com.example.diarybook.fileprovider",
                    outputImage!!)
            } else {
                Uri.fromFile(outputImage)
            }

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            activity.startActivityForResult(intent, takePhoto)

        }

    private fun galley(activity: Activity) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        activity.startActivityForResult(intent, fromAlum)
    }

}