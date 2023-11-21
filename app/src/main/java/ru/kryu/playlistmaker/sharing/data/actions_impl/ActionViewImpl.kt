package ru.kryu.playlistmaker.sharing.data.actions_impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import ru.kryu.playlistmaker.sharing.data.ActionView
import javax.inject.Inject

class ActionViewImpl @Inject constructor(private val context: Context) : ActionView {
    override fun share(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("myTag", e.stackTraceToString())
        }
    }
}