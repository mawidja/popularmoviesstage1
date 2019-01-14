package com.example.witne.popularmoviesstage1;

//import android.graphics.Movie;
import com.example.witne.data.Movie;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witne.utilities.JsonUtils;
import com.example.witne.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//import com.example.witne.popularmoviesstage1.MovieAdapter.MovieAdapterOnClickHandler;
import java.util.List;
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainMoviesActivity extends AppCompatActivity implements MovieAdapter.ListItemClickLister {

    private final static String TAG = MainMoviesActivity.class.getSimpleName();

    private String popularOrTopRatedMovies;
    private URL movieSearchURL;
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    /*@BindView(R.id.rv_popularMovies)
    RecyclerView recyclerView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;*/

    private TextView tv_error_message;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movies);
        //ButterKnife.bind(this);

        tv_error_message = findViewById(R.id.tv_error_message);
        recyclerView = findViewById(R.id.rv_popularMovies);
        progressBar = findViewById(R.id.pb_loading_indicator);

        //define layout manager for the recycler view
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager1);

        //to improve performance of the recycler view
        recyclerView.setHasFixedSize(true);

        //initialize adapter and set to the recycler view object
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList,this);
        recyclerView.setAdapter(movieAdapter);

        //build the url string - default to 'popular movies'
        popularOrTopRatedMovies = "popular";
        startMovieSearch(popularOrTopRatedMovies);
    }

    private void showErrorMessage(){
        progressBar.setVisibility(View.INVISIBLE);
        tv_error_message.setVisibility(View.VISIBLE);
    }

    private void showJSONData(String jsonData){
        progressBar.setVisibility(View.INVISIBLE);
        tv_error_message.setVisibility(View.INVISIBLE);
        //movieList = new ArrayList<>();
        movieList = JsonUtils.parseMovieJson(jsonData);
        movieAdapter.setMovieAdapter(movieList);
    }

    private void startMovieSearch(String popularOrTopRatedMovies){
        movieSearchURL = NetworkUtils.buildUrl(popularOrTopRatedMovies);
        //fetch data on separate thread
        // and initialize the recycler viewer with data from movie adapter
        new  FecthMovieTask().execute(movieSearchURL);
    }
    @Override
    public void onListItemClick(Movie movie) {
        //Toast.makeText(this.getBaseContext(),"List item clicked!",Toast.LENGTH_LONG).show();
        Intent startDetailMovieIntent = new Intent(this, DetailMovieActivity.class);
        startDetailMovieIntent.putExtra("Movie_Details",movie);
        startActivity(startDetailMovieIntent);
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
            startMovieSearch(popularOrTopRatedMovies);
            return true;
        }
        if(itemThatWasSelected == R.id.action_top_rated_movies){
            popularOrTopRatedMovies = "top_rated";
            startMovieSearch(popularOrTopRatedMovies);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    //Async inner class to fetch network data
    public class FecthMovieTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

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
            progressBar.setVisibility(View.INVISIBLE);
            if(jsonData != null && !jsonData.equals("")) {
                super.onPostExecute(jsonData);
                showJSONData(jsonData);
            }else{
                showErrorMessage();
            }
        }
    }
}
