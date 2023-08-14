package ru.kryu.playlistmaker.sharing.data.actions_impl

import android.content.Context
import android.content.Intent
import android.util.Log
import ru.kryu.playlistmaker.sharing.data.ActionSend

class ActionSendImpl(private val context: Context) : ActionSend {
    override fun share(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"
        val shareIntent = Intent.createChooser(intent, "Share via")
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            Log.e("myTag", e.stackTraceToString())
        }
    }
}