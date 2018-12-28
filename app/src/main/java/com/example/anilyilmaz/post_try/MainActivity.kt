package com.example.anilyilmaz.post_try

import android.content.Intent
import android.os.AsyncTask
import khttp.get

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
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
        var titleList : MutableCollection<String> = mutableListOf<String>()
        var imageList : MutableCollection<String> = mutableListOf<String>()
        val searchWord = aramaText.text.toString()
        try{
            val results = get("https://www.googleapis.com/youtube/v3/search?q=$searchWord&part=snippet&key=${GOOGLE_API_KEY}&maxResults=$SEARCH_COUNT")
            val datas = JsonArray(results.jsonObject["items"])
            for (i in 1..10) {
                Thread.sleep(100)
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
            textView.text = ""
            textView2.text = ""
            textView3.text = ""
            textView.text = videoIdList.toString()
            textView2.text = titleList.toString()
            textView3.text = imageList.toString()
        }catch (e: Exception) {
            Log.d("TAG",e.message);
            textView.text = "Sonuc Bulunamadı"
            textView2.text = "Sonuc Yok"
            textView3.text = "Resim Yok"
        }

    }

}


