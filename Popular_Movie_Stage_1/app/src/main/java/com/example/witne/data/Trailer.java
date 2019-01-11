package com.example.witne.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieKey() {
        return movieKey;
    }

    public void setMovieKey(String movieKey) {
        this.movieKey = movieKey;
    }

    public String getMovieSite() {
        return movieSite;
    }

    public void setMovieSite(String movieSite) {
        this.movieSite = movieSite;
    }

    public int getMovieSize() {
        return movieSize;
    }

    public void setMovieSize(int movieSize) {
        this.movieSize = movieSize;
    }

    public String getMovieVideoType() {
        return movieVideoType;
    }

    public void setMovieVideoType(String movieVideoType) {
        this.movieVideoType = movieVideoType;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    private String movieId;
    private String movieKey;
    private String movieSite;
    private int movieSize;
    private String movieVideoType;
    private String movieName;

    //no arg constructor
    public Trailer(String movieId,String movieKey,String movieSite,
                   int movieSize,String movieVideoType, String movieName){
        this.movieId = movieId;
        this.movieKey = movieKey;
        this.movieSite = movieSite;
        this.movieSize = movieSize;
        this.movieVideoType = movieVideoType;
        this.movieName = movieName;

    }

    //Args constructor
    public Trailer(){

    }

    //De-parcel object
    public Trailer(Parcel in){
        movieId = in.readString();
        movieKey = in.readString();
        movieSite = in.readString();
        movieSize = in.readInt();
        movieVideoType = in.readString();
        movieName = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag){
        dest.writeString(movieId);
        dest.writeString(movieKey);
        dest.writeString(movieSite);
        dest.writeInt(movieSize);
        dest.writeString(movieVideoType);
        dest.writeString(movieName);
    }

    //Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Trailer createFromParcel(Parcel in){
            return new Trailer(in);
        }
        public Trailer[] newArray(int size){
                        return new Trailer[size];
        }
    };
}