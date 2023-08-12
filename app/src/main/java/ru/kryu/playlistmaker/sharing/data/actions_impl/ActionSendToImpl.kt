package ru.kryu.playlistmaker.sharing.data.actions_impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import ru.kryu.playlistmaker.sharing.data.ActionSendTo

class ActionSendToImpl(private val context: Context) : ActionSendTo {
    override fun share(email: Array<String>, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(
            Intent.EXTRA_EMAIL,
            email
        )
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            subject
        )
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("myTag", e.stackTraceToString())
        }
    }
}