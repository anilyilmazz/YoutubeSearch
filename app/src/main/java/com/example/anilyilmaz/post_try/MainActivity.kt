package com.example.anilyilmaz.post_try

import android.content.Context
import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.AsyncTask
import khttp.get

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.StrictMode
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import org.json.JSONArray
import org.json.JSONObject

val GOOGLE_API_KEY = "AIzaSyCtbwIANm51Acc9KEr9ybfXbtkoNWCQ1cY"
var SEARCH_COUNT = "15"

class MainActivity : AppCompatActivity() {

    var listView : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        listView = findViewById(R.id.liste)

    }



    fun search(view: View){

        var videoIdList: MutableList<String> = mutableListOf<String>()
        var titleList : MutableCollection<String> = mutableListOf<String>()
        var imageList : MutableCollection<String> = mutableListOf<String>()
        var searchWord = aramaText.text.toString()
        try{
            searchWord = searchWord.replace(" ", "%20")
            val results = get("https://www.googleapis.com/youtube/v3/search?q=$searchWord&part=snippet&key=${GOOGLE_API_KEY}&maxResults=$SEARCH_COUNT&type=video")
            val datas = JsonArray(results.jsonObject["items"])
            for (i in 1..10) {
                val oneDatum = (datas[0] as JSONArray).get(i)
                val getId = (oneDatum as JSONObject).getJSONObject("id")
                val videoId = getId.get("videoId")
                videoIdList.add(videoId.toString())

                val getTitle = (oneDatum as JSONObject).getJSONObject("snippet")
                val videoTitle = getTitle.get("title")
                titleList.add(videoTitle.toString())

                val getImage = (oneDatum as JSONObject).getJSONObject("snippet")
                val getThumbnail = getImage.getJSONObject("thumbnails")
                val getMediumImage = getThumbnail.getJSONObject("medium")
                val videoMediumImageUrl = getMediumImage.get("url")
                imageList.add(videoMediumImageUrl.toString())
            }

            listView?.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleList.toTypedArray())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }catch (e: Exception) {
            Log.d("TAG",e.message);

        }
    }



}


