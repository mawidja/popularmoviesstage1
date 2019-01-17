package com.example.witne.popularmoviesstage1;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.witne.data.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieList;
    private Movie movie;
   //private MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    private final ListItemClickLister listItemClickLister;

    //interface to handle movie click event
    /*public interface MovieAdapterOnClickHandler{
        void onClick(Movie  movie);
    }*/

    public interface ListItemClickLister{
        void onListItemClick(Movie movie);
    }

    public MovieAdapter(ArrayList<Movie> movieList, ListItemClickLister listItemClickLister){
        this.movieList = movieList;
        this.listItemClickLister = listItemClickLister;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        //boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,viewGroup, false);
        //MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        //return movieViewHolder;
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder movieViewHolder, int position){
        movie = movieList.get(position);
        movieViewHolder.bind(movie);
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
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageView;

        MovieViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        // method for convenience to bind things up!
        public void bind(Movie movieDetails){
            //String posterPath = IMAGE_BASE_URL + movieDetails.getPoster_path();
            Picasso.get()
                    .load(movieDetails.getPoster_path())
                    .into(imageView);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            movie = movieList.get(adapterPosition);
            //this.listItemClickLister..onClick(movie);
            //view.setOnClickListener(listItemClickLister);
            listItemClickLister.onListItemClick(movie);
        }
    }
}
