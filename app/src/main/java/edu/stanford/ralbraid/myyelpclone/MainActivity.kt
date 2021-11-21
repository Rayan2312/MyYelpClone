package edu.stanford.ralbraid.myyelpclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import retrofit2.Call as Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback as Callback
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val TAG ="MainActivity.kt"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "jW8wUevTrmEfskhAEz4JzQll1htdwLxwTLr286-tTWTENaxwBwSyPl-lvb_IcwahPJmvAUwh3avT9kq0gs0kUmjCQjJ2kwSKArX8QWIuBX4mJvzUMKv-thoZx_eYYXYx"
class MainActivity : AppCompatActivity() {
    private lateinit var rvRestaurants: RecyclerView
    private lateinit var tvSearch: EditText
    private lateinit var searchBt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvRestaurants = findViewById(R.id.rvRestaurants)
        tvSearch = findViewById(R.id.tvSearch)
        searchBt = findViewById(R.id.searchBt)
        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants.adapter = adapter

        rvRestaurants.layoutManager = LinearLayoutManager(this)
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService:: class.java)
        getYelpData("Avocado Toast", yelpService, restaurants, adapter)
        searchBt.setOnClickListener{
            val searchTerm = tvSearch.text.toString()
                    getYelpData(searchTerm, yelpService, restaurants, adapter)

        }

    }
    private fun getYelpData(searchTerm: String, yelpService: YelpService, restaurants: MutableList<YelpRestaurant>, adapter: RestaurantsAdapter){
            yelpService.searchRestaurants("Bearer $API_KEY",searchTerm, "New York").enqueue(object: Callback<YelpSearchResult> {
                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                    Log.i(TAG, "onResponse Code: $response")
                    val body = response.body()
                    if(body == null){
                        Log.w(TAG, "Did not receive valid body from Yelp API...exiting")
                        return
                    }
                    restaurants.removeAll(restaurants)
                    restaurants.addAll(body.restaurants)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onFailure Code: $t")
                }

            })
    }
}