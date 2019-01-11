package com.example.witne.popularmoviesstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.witne.data.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerHolderAdapter> {

    private ArrayList<Trailer> movieTrailers;
    private Trailer trailer;

    public MovieTrailerAdapter(ArrayList<Trailer> movieTrailers){
        this.movieTrailers = movieTrailers;
    }

    @Override
    public MovieTrailerHolderAdapter onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.movie_trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,viewGroup,shouldAttachToParentImmediately);
        return new MovieTrailerHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapter.MovieTrailerHolderAdapter movieTrailerHolder, int position){
        trailer = movieTrailers.get(position);
        movieTrailerHolder.bind(trailer);
    }

    @Override
    public int getItemCount(){
        return movieTrailers.size();
    }

    public void setMovieTrailerAdapter(ArrayList<Trailer> movieTrailers){
        this.movieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

    public class MovieTrailerHolderAdapter extends RecyclerView.ViewHolder{

        public Button bt_movie_trailer;

        public MovieTrailerHolderAdapter(View itemView){
            super(itemView);
            bt_movie_trailer = itemView.findViewById(R.id.bt_movie_trailer);
            //itemView.setOnClickListener(this);
        }

        // method for convenience to bind things up!
        public void bind(Trailer movieTrailer){
            bt_movie_trailer.setText(movieTrailer.getMovieName());
        }

    }
}
