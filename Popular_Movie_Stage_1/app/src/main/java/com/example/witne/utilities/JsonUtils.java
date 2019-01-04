package com.example.witne.utilities;

import android.util.Log;

import com.example.witne.data.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String TAG = JsonUtils.class.getSimpleName();

    public static List<Movie> parseMovieJson(String json){

        Movie movie;
        //Movie movie = new Movie();
        List<Movie> movieArrayList = new ArrayList<>();

        try {
            JSONObject baseName = new JSONObject(json);
            //JSONObject name = baseName.getJSONObject("page");
            Log.v(TAG,baseName.toString());

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
            Log.d(TAG,"Error in the JSON object.......Oooooouch!");
            e.printStackTrace();
        }
        return null;
    }
}