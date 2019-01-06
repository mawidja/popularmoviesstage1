package com.example.witne.popularmoviesstage1;

//import android.graphics.Movie;
import com.example.witne.data.Movie;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.example.witne.utilities.JsonUtils;
import com.example.witne.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainMoviesActivity extends AppCompatActivity {

    private final static String TAG = MainMoviesActivity.class.getSimpleName();

    private String popularOrTopRatedMovies;
    private URL movieSearch;
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    /*@BindView(R.id.rv_popularMovies)
    RecyclerView recyclerView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;*/

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movies);
        //ButterKnife.bind(this);

        recyclerView = findViewById(R.id.rv_popularMovies);
        progressBar = findViewById(R.id.pb_loading_indicator);

        //define layout manager for the recycler view
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager1);

        //to improve performance of the recycler view
        recyclerView.setHasFixedSize(true);

        //initialize adapter and set to the recycler view object
        movieAdapter = new MovieAdapter(new ArrayList<Movie>());
        recyclerView.setAdapter(movieAdapter);

        //build the url string - default to 'popular movies'
        popularOrTopRatedMovies = "popular";
        //popularOrTopRatedMovies = "top_rated";
        movieSearch = NetworkUtils.buildUrl(popularOrTopRatedMovies);

        //fetch data on separate thread
        // and initialize the recycler viewer with data from movie adapter
        new  FecthMovieTask().execute(movieSearch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int itemThatWasSelected = menuItem.getItemId();
        if(itemThatWasSelected == R.id.action_popular_movies){
            popularOrTopRatedMovies = "popular";
            movieSearch = NetworkUtils.buildUrl(popularOrTopRatedMovies);
            new  FecthMovieTask().execute(movieSearch);
            return true;
        }
        if(itemThatWasSelected == R.id.action_top_rated_movies){
            popularOrTopRatedMovies = "top_rated";
            movieSearch = NetworkUtils.buildUrl(popularOrTopRatedMovies);
            new  FecthMovieTask().execute(movieSearch);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    //Async inner class to fetch network data
    public class FecthMovieTask extends AsyncTask<URL, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(URL... params){

            URL searchUrl = params[0];
            String jsonData;
            movieList = new ArrayList<>();
            try {
                jsonData = NetworkUtils.fetchData(searchUrl);
                movieList = JsonUtils.parseMovieJson(jsonData);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v ){
            super.onPostExecute(v);
            progressBar.setVisibility(View.INVISIBLE);
            movieAdapter.setMovieAdapter(movieList);
        }
    }
}
