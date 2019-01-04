package com.example.witne.popularmoviesstage1;

//import android.graphics.Movie;
import com.example.witne.data.Movie;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.example.witne.utilities.JsonUtils;
import com.example.witne.utilities.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainMoviesActivity extends AppCompatActivity {

    private final static String TAG = MainMoviesActivity.class.getSimpleName();

    private String popularOrTopRatedMovies;

    private List<Movie> movieList;

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

        recyclerView = (RecyclerView)findViewById(R.id.rv_popularMovies);
        progressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);

        //define layout manager for the recycler view
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager1);

        //to improve performance of the recycler view
        recyclerView.setHasFixedSize(true);

        //build the url string
        //popularOrTopRatedMovies = "popular";
        popularOrTopRatedMovies = "top_rated";
        URL movieSearch = NetworkUtils.buildUrl(popularOrTopRatedMovies);

        //fetch data on separate thread
        // and initialize the recycler viewer with data from movie adapter
        new  FecthMovieTask().execute(movieSearch);
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
                return jsonData;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonData ){
            super.onPostExecute(jsonData);
            progressBar.setVisibility(View.INVISIBLE);
            MovieAdapter movieAdapter = new MovieAdapter(JsonUtils.parseMovieJson(jsonData));
            recyclerView.setAdapter(movieAdapter);
        }
    }
}
