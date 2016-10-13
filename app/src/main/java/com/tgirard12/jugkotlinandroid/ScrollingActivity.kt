package com.tgirard12.jugkotlinandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScrollingActivity : AppCompatActivity() {

    var gson = Gson()
    var retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    var githubService: GithubService = retrofit.create(GithubService::class.java)
    var picasso: Picasso? = null

    var toolbar: Toolbar? = null
    var fab: FloatingActionButton? = null
    var name: TextView? = null
    var fullName: TextView? = null
    var htmlUrl: TextView? = null
    var description: TextView? = null
    var owner: TextView? = null
    var owerAvatar: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        //
        picasso = Picasso.with(this)

        //
        toolbar = findViewById(R.id.toolbar) as Toolbar
        fab = findViewById(R.id.fab) as FloatingActionButton
        name = findViewById(R.id.name) as TextView
        fullName = findViewById(R.id.fullName) as TextView
        htmlUrl = findViewById(R.id.htmlUrl) as TextView
        description = findViewById(R.id.description) as TextView
        owner = findViewById(R.id.owner) as TextView
        owerAvatar = findViewById(R.id.owerAvatar) as ImageView

        //
        val vectorDrawableCompat = VectorDrawableCompat.create(resources, R.drawable.ic_github, theme)
        fab?.setImageDrawable(vectorDrawableCompat)

        setSupportActionBar(toolbar)

        // https://api.github.com/search/repositories?q=kotlin
        githubService.githubSearch("kotlin").enqueue(searchCallback)
    }

    private val searchCallback = object : Callback<Search> {
        override fun onResponse(call: Call<Search>, response: Response<Search>) {
            val search = response.body()
            val searchItem: SearchItem? = search.items!![0]

            name?.text = searchItem!!.name
            fullName?.text = searchItem!!.full_name
            htmlUrl?.text = searchItem!!.html_url
            description?.text = searchItem!!.description

            owner?.text = getString(R.string.owner, searchItem!!.owner.login)

            //
            picasso?.load(searchItem!!.owner.avatar_url)
                    ?.into(owerAvatar)

            fab?.setOnClickListener {
                //
                if (searchItem != null) {
                    if (searchItem.html_url != null) {
                        val webpage = Uri.parse(searchItem!!.html_url)
                        val intent = Intent(Intent.ACTION_VIEW, webpage)
                        startActivity(intent)
                    }
                }
            }
        }

        override fun onFailure(call: Call<Search>, t: Throwable) {
            Log.e("JUG-KOTLIN", t.message, t)
        }
    }
}
