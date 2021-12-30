package com.example1.assignment_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {
    Cast__1 movieResponse;
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

        setContentView(R.layout.activity_details);

        result = (Result) getIntent().getSerializableExtra("result");
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.List_view);
        TextView t=(TextView) rl.findViewById(R.id.text_view);
        t.setText(result.getOverview());
        RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.view);

        RecyclerView rv= findViewById(R.id.movie_list);
        rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        rv.setAdapter(new DetailsActivity.MyAdapter2());
        try {
            String data = run("https://api.themoviedb.org/3/movie/19404/credits?api_key=3fa9058382669f72dcb18fb405b7a831&fbclid=IwAR3CVWAaeTKdhJBRNRi70D2QJi1muQl4TfM-vqaXWnA2N6vB4oBpoCqVKIg");
            movieResponse= new Gson().fromJson(data,Cast__1.class);

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
            holder.textView.setText(movieResponse.getName());


            Glide.with(getApplicationContext())
                    .load("https://api.themoviedb.org/3/movie/19404/credits?api_key=3fa9058382669f72dcb18fb405b7a831&fbclid=IwAR3CVWAaeTKdhJBRNRi70D2QJi1muQl4TfM-vqaXWnA2N6vB4oBpoCqVKIg"+movieResponse.getCast().get(position).getProfilePath())
                    .centerCrop()
                    .into(holder.flagImg);


        }

        @Override
        public int getItemCount() {
            return movieResponse.getCastId().size();
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