package com.example.witne.popularmoviesstage1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.witne.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends AppCompatActivity {

    private TextView tv_movie_title;
    private ImageView iv_movie_poster;
    private TextView tv_movie_release_date;
    private TextView tv_movie_rating;
    private TextView tv_movie_overview;
    private Movie movie;
    //private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tv_movie_title = findViewById(R.id.tv_movie_title);
        iv_movie_poster = findViewById(R.id.iv_movie_poster);
        tv_movie_release_date = findViewById(R.id.tv_movie_release_date);
        tv_movie_rating = findViewById(R.id.tv_movie_vote_average);
        tv_movie_overview = findViewById(R.id.tv_movie_overview);

        //get movie details from the intent that started the activity
        //Intent intentStartedThisActivity = getIntent();
        Bundle movieData = getIntent().getExtras();
        movie = movieData.getParcelable("Movie_Details");

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
        }

    }
}
