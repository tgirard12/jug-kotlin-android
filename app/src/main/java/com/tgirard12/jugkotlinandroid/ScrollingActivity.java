package com.tgirard12.jugkotlinandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScrollingActivity extends AppCompatActivity {

    Gson gson = new Gson();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    GithubService githubService = retrofit.create(GithubService.class);
    Picasso picasso;

    Toolbar toolbar;
    FloatingActionButton fab;
    TextView name;
    TextView fullName;
    TextView htmlUrl;
    TextView description;
    TextView owner;
    ImageView owerAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        //
        picasso = new Picasso.Builder(this).build();

        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        name = (TextView) findViewById(R.id.name);
        fullName = (TextView) findViewById(R.id.fullName);
        htmlUrl = (TextView) findViewById(R.id.htmlUrl);
        description = (TextView) findViewById(R.id.description);
        owner = (TextView) findViewById(R.id.owner);
        owerAvatar = (ImageView) findViewById(R.id.owerAvatar);

        //
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.ic_github, getTheme());
        fab.setImageDrawable(vectorDrawableCompat);

        setSupportActionBar(toolbar);

        // https://api.github.com/search/repositories?q=kotlin
        githubService.githubSearch("kotlin").enqueue(searchCallback);
    }

    private final Callback<Search> searchCallback = new Callback<Search>() {
        @Override
        public void onResponse(Call<Search> call, Response<Search> response) {
            Search search = response.body();
            final SearchItem searchItem = search.getItems().get(0);

            if (searchItem != null) {
                name.setText(searchItem.getName());
                fullName.setText(searchItem.getFull_name());
                htmlUrl.setText(searchItem.getHtml_url());
                description.setText(searchItem.getDescription());

                owner.setText(getString(R.string.owner, searchItem.getOwner().getLogin()));

                //
                if (searchItem.getOwner() != null) {
                    picasso.load(searchItem.getOwner().getAvatar_url())
                            .into(owerAvatar);
                }
                fab.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchItem.getHtml_url()));
                                intent.putExtra("extra", true);
                                startActivity(intent);
                            }
                        }
                );
            }
        }

        @Override
        public void onFailure(Call<Search> call, Throwable t) {
            Log.e("JUG-KOTLIN", t.getMessage(), t);
        }
    };
}
