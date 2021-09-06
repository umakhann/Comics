package com.example.comics.ui.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


    fun createLink(path: String, extension: String): String {

        var imageUrl = path + "." + extension

        if (imageUrl[4] != 's')
            imageUrl = imageUrl.substring(0, 4) + "s" + imageUrl.substring(4, imageUrl.length)

        if (imageUrl == "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg")
            imageUrl = "https://c8.alamy.com/comp/WEJWDG/vintage-comic-book-cover-artwork-WEJWDG.jpg"


        return imageUrl
    }


    fun createBitmap(link: String): Bitmap? {

        try {
            val url = URL(link)
            return BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            System.out.println(e)
        }

        return null

    }


    fun createDirectoryAndSaveFile(imageToSave: Bitmap, fileName: String) {
        val direct = File(Environment.getExternalStorageDirectory().toString() + "/imagesOfComics")
        if (!direct.exists()) {
            val wallpaperDirectory = File("/data/imagesOfComics/")
            wallpaperDirectory.mkdirs()
        }
        val file = File("/data/imagesOfComics/", fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }