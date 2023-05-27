package ru.kryu.playlistmaker

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var userText: String = ""
    private var lastRequest: String = ""
    private val editText: EditText by lazy { findViewById<EditText>(R.id.edit_text_search) }
    private val viewNotFound: LinearLayout by lazy { findViewById<LinearLayout>(R.id.not_found) }
    private val viewNoConnection: LinearLayout by lazy { findViewById<LinearLayout>(R.id.no_connection) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view_search) }
    private val buttonRefresh: Button by lazy { findViewById<Button>(R.id.button_refresh) }

    private val trackList = ArrayList<Track>()
    private val trackAdapter = TrackAdapter(trackList)

    private val iTunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesApiService = retrofit.create(ITunesApiService::class.java)

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val TRACKS = "TRACKS"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, userText)
        outState.putParcelableArrayList(TRACKS, trackList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(SEARCH_TEXT)){
            userText = savedInstanceState.getString(SEARCH_TEXT,"")
            editText.setText(userText)
            editText.setSelection(editText.text.length)
        }
        if (savedInstanceState.containsKey(TRACKS)){
            trackList.addAll(savedInstanceState.parcelableArrayList<Track>(TRACKS)!!)
        }
    }

    private inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val buttonBack = findViewById<ImageView>(R.id.search_arrow)
        buttonBack.setOnClickListener {
            finish()
        }

        val clearButton = findViewById<ImageView>(R.id.edit_text_clear)
        clearButton.setOnClickListener {
            clearButtonOnClick()
        }

        val editTextTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                userText = s.toString()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = trackAdapter

        editText.addTextChangedListener(editTextTextWatcher)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewNotFound.visibility = View.GONE
                viewNoConnection.visibility = View.GONE
                if (trackList.isNotEmpty()) {
                    trackList.clear()
                    trackAdapter.notifyDataSetChanged()
                }
                refreshTrackList(editText.text.toString())
                true
            }
            false
        }

        buttonRefresh.setOnClickListener {
            viewNoConnection.visibility = View.GONE
            refreshTrackList(lastRequest)
        }
    }

    private fun clearButtonOnClick() {
        editText.setText("")
        editText.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        viewNotFound.visibility = View.GONE
        viewNoConnection.visibility = View.GONE
        if (trackList.isNotEmpty()) {
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun refreshTrackList(requestText: String) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        lastRequest = requestText
        iTunesApiService.search(lastRequest).enqueue(
            object : Callback<ITunesResponce> {
                override fun onResponse(
                    call: Call<ITunesResponce>,
                    response: Response<ITunesResponce>
                ) {
                    progressBar.visibility = View.GONE
                    if (response.code() == 200) {
                        if (response.body()?.results?.isNotEmpty()!!) {
                            trackList.addAll(response.body()?.results!!)
                            trackAdapter.notifyDataSetChanged()
                        } else {
                            viewNotFound.visibility = View.VISIBLE
                        }
                    } else {
                        viewNoConnection.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ITunesResponce>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    viewNoConnection.visibility = View.VISIBLE
                }

            }
        )
    }
}
