package com.tgirard12.jugkotlinandroid

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
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

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val fab by bindView<FloatingActionButton>(R.id.fab)
    val name: TextView by bindView(R.id.name)
    val fullName: TextView by bindView(R.id.fullName)
    val htmlUrl: TextView by bindView(R.id.htmlUrl)
    val description: TextView by bindView(R.id.description)
    val owner: TextView by bindView(R.id.owner)
    val owerAvatar: ImageView by bindView(R.id.owerAvatar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        fab.setImageDrawable(createVector(R.drawable.ic_github))

        setSupportActionBar(toolbar)

        // https://api.github.com/search/repositories?q=kotlin
        githubService.githubSearch("kotlin").enqueue(searchCallback)
    }

    val searchCallback = object : Callback<Search> {
        override fun onResponse(call: Call<Search>, response: Response<Search>) {
            val search = response.body()
            search.items?.firstOrNull()?.let { searchItem ->

                searchItem.name printIn name
                searchItem.full_name printIn fullName
                searchItem.html_url printIn htmlUrl
                searchItem.description printIn description

                getString(R.string.owner, searchItem.owner.login) printIn owner

                picasso.load(searchItem.owner.avatar_url)?.into(owerAvatar)

                fab.setOnClickListener {
                    startActivity {
                        intent {
                            action { Intent.ACTION_VIEW }
                            url { searchItem.html_url }
                        }
                    }
                }
            }
        }

        override fun onFailure(call: Call<Search>, t: Throwable) {
            Log.e("JUG-KOTLIN", t.message, t)
        }
    }
}
