package com.example.witne.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {


    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = IMAGE_BASE_URL + poster_path;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    private String release_date;
    private int movieId;
    private String original_title;
    private String title;
    private double popularity;
    private double vote_average;
    private String poster_path;
    private String movie_overview;
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185";

    //No args constructor
    public Movie(){

    }

    //Args constructor
    public Movie(String release_date,int movieId,String original_title,String title,
                 String poster_path,double popularity,double vote_average,
                 String movie_overview){

        this.release_date = release_date;
        this.movieId = movieId;
        this.original_title = original_title;
        this.title = title;
        this.poster_path = IMAGE_BASE_URL + poster_path;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.movie_overview = movie_overview;
    }

    //De-parcel object
    public Movie(Parcel in){
        release_date = in.readString();
        movieId = in.readInt();
        original_title = in.readString();
        title = in.readString();
        poster_path = in.readString();
        popularity = in.readDouble();
        vote_average = in.readDouble();
        movie_overview = in.readString();

    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag){
        dest.writeString(release_date);
        dest.writeInt(movieId);
        dest.writeString(original_title);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeDouble(vote_average);
        dest.writeString(movie_overview);

    }

    //Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }
        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };
}
