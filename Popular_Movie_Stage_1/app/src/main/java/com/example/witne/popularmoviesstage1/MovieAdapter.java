package com.example.witne.popularmoviesstage1;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.witne.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieList;
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    public MovieAdapter(ArrayList<Movie> movieList){
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,viewGroup,shouldAttachToParentImmediately);
        //MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        //return movieViewHolder;
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder movieViewHolder, int position){
        Movie movie = movieList.get(position);

        //movieViewHolder.bind(movie);
        String posterPath = IMAGE_BASE_URL + movie.getPoster_path();
        Picasso.get()
                .load(posterPath)
                .into(movieViewHolder.imageView);
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public void setMovieAdapter(ArrayList<Movie> movieList){
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    //inner class for movie holder
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

        public final ImageView imageView;

        public MovieViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie_poster);
        }

        // method for convenience to bind things up!
        /*public void bind(Movie movieDetails){
            String posterPath = IMAGE_BASE_URL + movieDetails.getPoster_path();
            Picasso.get()
                    .load(posterPath)
                    .into(imageView);
        }*/

    }
}
