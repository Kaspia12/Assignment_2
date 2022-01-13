package com.example1.assignment_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {
    Cast movieResponse;
    Result result;
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_details);

        result = (Result) getIntent().getSerializableExtra("result");
        TextView titleView = findViewById(R.id.title);
        TextView DetailsView = findViewById(R.id.detailText);
        TextView ratingTextView = findViewById(R.id.ratingTextView);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ImageView bannerImage = findViewById(R.id.banner);
        ImageView posterImage = findViewById(R.id.poster);

        titleView.setText(result.getTitle());
        DetailsView.setText(result.getOverview());
        ratingTextView.setText(""+result.getVoteAverage());

        ratingBar.setRating((float) result.getVoteAverage());

        String poster = "https://image.tmdb.org/t/p/w500" + result.getPosterPath();
        String banner = "https://image.tmdb.org/t/p/w500" + result.getBackdropPath();
        Glide.with(DetailsActivity.this)
                .load(banner)
                .centerCrop()
                .into(bannerImage);


        Glide.with(DetailsActivity.this)
                .load(poster)
                .centerCrop()
                .into(posterImage);

        RecyclerView rv= findViewById(R.id.cast_list);
        rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        try {
            String data = run("https://api.themoviedb.org/3/movie/19404/credits?api_key=3fa9058382669f72dcb18fb405b7a831&fbclid=IwAR3CVWAaeTKdhJBRNRi70D2QJi1muQl4TfM-vqaXWnA2N6vB4oBpoCqVKIg");
            movieResponse= new Gson().fromJson(data,Cast.class);
            System.out.println("Test  "+movieResponse.toString());
            rv.setAdapter(new DetailsActivity.MyAdapter2());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    class MyAdapter2 extends RecyclerView.Adapter<DetailsActivity.MovieViewHolder>{
        @NonNull
        @Override
        public DetailsActivity.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(DetailsActivity.this).inflate(R.layout.view,parent,false);
            return new MovieViewHolder(v);
        }



        @Override
        public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
            holder.textView.setText(movieResponse.getCast().get(position).getName());

            Glide.with(getApplicationContext())
                    .load("https://api.themoviedb.org/3/movie/19404/credits?api_key=3fa9058382669f72dcb18fb405b7a831&fbclid=IwAR3CVWAaeTKdhJBRNRi70D2QJi1muQl4TfM-vqaXWnA2N6vB4oBpoCqVKIg"+movieResponse.getCast().get(position).getProfilePath())
                    .centerCrop()
                    .into(holder.flagImg);


        }

        @Override
        public int getItemCount() {
            return movieResponse.getCast().size();
        }
    }
    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView flagImg;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            flagImg = itemView.findViewById(R.id.poster);

        }
    }

}