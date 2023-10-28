package ru.kryu.playlistmaker.createplaylist.data.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import ru.kryu.playlistmaker.createplaylist.data.ImageStorage
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ExternalPrivateStorage(private val context: Context): ImageStorage {
    override fun saveImage(path: String?) {
        val filePath =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "covers")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "cover${UUID.randomUUID()}.jpg")
        val inputStream = context.contentResolver?.openInputStream(Uri.parse(path))
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
    }
}