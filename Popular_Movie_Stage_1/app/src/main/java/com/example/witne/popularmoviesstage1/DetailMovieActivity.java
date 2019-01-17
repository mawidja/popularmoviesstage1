package com.example.witne.popularmoviesstage1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witne.data.Movie;
import com.example.witne.data.Trailer;
import com.example.witne.utilities.JsonUtils;
import com.example.witne.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity implements MovieTrailerAdapter.ListItemClickListener {

    private MovieTrailerAdapter movieTrailerAdapter;
    private TextView tv_trailers;
    private ArrayList<Trailer> trailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        TextView tv_movie_title = findViewById(R.id.tv_movie_title);
        ImageView iv_movie_poster = findViewById(R.id.iv_movie_poster);
        TextView tv_movie_release_date = findViewById(R.id.tv_movie_release_date);
        TextView tv_movie_rating = findViewById(R.id.tv_movie_vote_average);
        TextView tv_movie_overview = findViewById(R.id.tv_movie_overview);
        tv_trailers = findViewById(R.id.tv_trailers);

        //set the movie trailer recycler view
        RecyclerView rv_movie_trailer = findViewById(R.id.rv_movie_trailer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_movie_trailer.setLayoutManager(linearLayoutManager);
        rv_movie_trailer.setHasFixedSize(true);
        trailerList = new ArrayList<>();
        movieTrailerAdapter = new MovieTrailerAdapter(trailerList,this);
        rv_movie_trailer.setAdapter(movieTrailerAdapter);

        //get movie details from the intent that started the activity
        //Intent intentStartedThisActivity = getIntent();
        Bundle movieData = getIntent().getExtras();
        Movie movie = Objects.requireNonNull(movieData).getParcelable("Movie_Details");

        if(movie != null){
            tv_movie_title.setText(movie.getTitle());
            //String posterPath = IMAGE_BASE_URL + movie.getPoster_path();
            Picasso.get()
                    .load(movie.getPoster_path())
                    .into(iv_movie_poster);

            tv_movie_release_date.setText((movie.getRelease_date()));
            //tv_movie_rating.setText(movie.getVote_average());
            //tv_movie_rating.setText(String.valueOf(movie.getVote_average())+ " /10");
            String movieRating = getString(R.string.movie_rating_total,String.valueOf(movie.getVote_average()));
            tv_movie_rating.setText(movieRating);
            tv_movie_overview.setText(movie.getMovie_overview());

            if(isNetworkAvailable()) {
                startMovieTrailerSearch(String.valueOf(movie.getMovieId()));
            }else{
                showErrorMessage();
            }

        }

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }

    private void showErrorMessage(){
        //tv_trailer_error_message.setVisibility(View.VISIBLE);
        tv_trailers.setText(getString(R.string.movie_trailer_error_message));
    }

    private void startMovieTrailerSearch(String movieId ){
        URL movieSearchURL = NetworkUtils.buildUrl(movieId);
        //fetch data on separate thread
        // and initialize the recycler viewer with data from movie adapter
        new  FetchMovieTrailerTask().execute(movieSearchURL);
    }

    private void showJSONData(String jsonData){
        //trailerList = new ArrayList<>();
        trailerList = JsonUtils.parseMovieTrailerJson(jsonData);
        movieTrailerAdapter.setMovieTrailerAdapter(trailerList);
    }

    private void playMovieTrailer(Uri movieTrailerUri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(movieTrailerUri);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(Trailer trailer){
        String movieTrailerKey = trailer.getMovieKey();
        URL movieTrailerURL = NetworkUtils.buildUrl(movieTrailerKey);
        Uri movieTrailerUri = Uri.parse(movieTrailerURL.toString());
        playMovieTrailer(movieTrailerUri);
    }
    //Async inner class to fetch network data
    class FetchMovieTrailerTask extends AsyncTask<URL, Void, String> {

        /*@Override
        protected void onPreExecute(){
            super.onPreExecute();
        }*/

        @Override
        protected String doInBackground(URL... params){

            URL searchUrl = params[0];
            String jsonData = null;
            try {
                jsonData = NetworkUtils.fetchData(searchUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(String jsonData ){
            //tv_trailer_error_message.setVisibility(View.INVISIBLE);
            if(jsonData != null && !jsonData.equals("")) {
                super.onPostExecute(jsonData);
                showJSONData(jsonData);
            }else{
                showErrorMessage();
            }
        }
    }
}
