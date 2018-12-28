package com.example.anilyilmaz.post_try

import android.content.Intent
import android.os.AsyncTask
import khttp.get
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.StrictMode
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import org.json.JSONArray
import org.json.JSONObject

val GOOGLE_API_KEY = "AIzaSyCtbwIANm51Acc9KEr9ybfXbtkoNWCQ1cY"
var SEARCH_COUNT = "15"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
    fun search(view: View){
        var videoIdList: MutableList<String> = mutableListOf<String>()
        val searchWord = aramaText.text.toString()
        val results = get("https://www.googleapis.com/youtube/v3/search?q=$searchWord&part=snippet&key=${GOOGLE_API_KEY}&maxResults=$SEARCH_COUNT")
        Thread.sleep(2_000)
        val datas = JsonArray(results.jsonObject["items"])
        for (i in 1..10) {
            val oneDatum = (datas[0] as JSONArray).get(i)
            val getId = (oneDatum as JSONObject).getJSONObject("id")
            val videoId = getId.get("videoId").toString()
            videoIdList.add(videoId)
        }
        textView.text = videoIdList.toString()
    }

}


