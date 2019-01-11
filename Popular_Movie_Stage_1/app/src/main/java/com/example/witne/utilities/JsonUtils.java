package com.example.witne.utilities;

import android.util.Log;

import com.example.witne.data.Movie;
import com.example.witne.data.Trailer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class JsonUtils {

    //public static final String TAG = JsonUtils.class.getSimpleName();

    //for the movie adapter
    public static ArrayList<Movie> parseMovieJson(String json){

        Movie movie;
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        try {
            JSONObject baseName = new JSONObject(json);
            JSONArray results = baseName.getJSONArray("results");

            for(int i=0; i<results.length();i++){
                JSONObject jsonObject = results.getJSONObject(i);
               movie = new Movie(jsonObject.getString("release_date"),jsonObject.getInt("id"),
                                    jsonObject.getString("original_title"),jsonObject.getString("title"),
                                    jsonObject.getString("poster_path"),jsonObject.getDouble("popularity"),
                                    jsonObject.getDouble("vote_average"),jsonObject.getString("overview"));
                movieArrayList.add(movie);
            }
            return movieArrayList;

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //for the movie trailer adapter
    public static ArrayList<Trailer> parseMovieTrailerJson(String json){

        Trailer trailer;
        ArrayList<Trailer> movieTrailers = new ArrayList<>();

        try {
            JSONObject baseName = new JSONObject(json);
            //JSONArray results = baseName.getJSONArray("results");
            JSONArray results = baseName.optJSONArray("results");

            for(int i=0; i<results.length();i++){
                JSONObject jsonObject = results.getJSONObject(i);
                trailer = new Trailer(jsonObject.getString("id"),jsonObject.getString("key"),jsonObject.getString("site"),
                          jsonObject.getInt("size"),jsonObject.getString("type"),jsonObject.getString("name"));
                movieTrailers.add(trailer);
            }
            return movieTrailers;

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}