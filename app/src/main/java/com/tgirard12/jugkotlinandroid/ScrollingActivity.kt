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

    val gson = Gson()
    val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    val githubService: GithubService = retrofit.create(GithubService::class.java)
    val picasso: Picasso by lazy { Picasso.with(this) }

    val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    val fab: FloatingActionButton by lazy { findViewById(R.id.fab) as FloatingActionButton }
    val name: TextView by lazy { findViewById(R.id.name) as TextView }
    val fullName: TextView by lazy { findViewById(R.id.fullName) as TextView }
    val htmlUrl: TextView by lazy { findViewById(R.id.htmlUrl) as TextView }
    val description: TextView by lazy { findViewById(R.id.description) as TextView }
    val owner: TextView by lazy { findViewById(R.id.owner) as TextView }
    val owerAvatar: ImageView by lazy { findViewById(R.id.owerAvatar) as ImageView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        //
        val vectorDrawableCompat = VectorDrawableCompat.create(resources, R.drawable.ic_github, theme)
        fab.setImageDrawable(vectorDrawableCompat)

        setSupportActionBar(toolbar)

        // https://api.github.com/search/repositories?q=kotlin
        githubService.githubSearch("kotlin").enqueue(searchCallback)
    }

    private val searchCallback = object : Callback<Search> {
        override fun onResponse(call: Call<Search>, response: Response<Search>) {
            val search = response.body()
            val searchItem = search.items?.firstOrNull()

            searchItem?.let {
                name.text = it.name
                fullName.text = it.full_name
                htmlUrl.text = it.html_url
                description.text = it.description

                owner.text = getString(R.string.owner, it.owner.login)

                picasso.load(it.owner.avatar_url)?.into(owerAvatar)

                fab.setOnClickListener { fab ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.html_url)).apply {
                        this.putExtra("toto", true)
                    })
                }
            }
        }

        override fun onFailure(call: Call<Search>, t: Throwable) {
            Log.e("JUG-KOTLIN", t.message, t)
        }
    }
}
