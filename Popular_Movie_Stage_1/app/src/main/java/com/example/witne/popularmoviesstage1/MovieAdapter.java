package com.example.witne.popularmoviesstage1;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.witne.data.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;
//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Movie movie;
    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList){
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem,viewGroup,shouldAttachToParentImmediately);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
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

    //inner class for movie holder
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

        /*@BindView(R.id.tv_movie_title)
        TextView textView;

        @BindView(R.id.iv_movie_poster)
        ImageView imageView;*/

        TextView textView;
        ImageView imageView;

        public MovieViewHolder(View itemView){
            super(itemView);
            //ButterKnife.bind(this,itemView);
            textView = (TextView)itemView.findViewById(R.id.tv_movie_title);
            imageView =(ImageView)itemView.findViewById(R.id.iv_movie_poster);
        }

        // method for convenience to bind things up!
        public void bind(Movie movieDetails){
            textView.setText(movieDetails.getTitle());
            Picasso.get()
                    .load(IMAGE_BASE_URL+movieDetails.getPoster_path())
                    .into(imageView);
        }

    }
}
