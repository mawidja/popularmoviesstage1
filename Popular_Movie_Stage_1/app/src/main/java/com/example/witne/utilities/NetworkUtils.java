 package com.example.witne.utilities;

//import android.graphics.Movie;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "api_key";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String myPersonalApiKey = "";

    private static final String PARAM_LANGUAGE = "language";
    private static final String language = "en-US";

    private static final String PARAM_PAGE = "page";
    private static final String page = "1";

    //private static final String PARAM_VIDEO = "video";

    private static final String TRAILER_BASE_URL = "https://www.youtube.com/watch";
    private static final String PARAM_VIDEO_SEARCH = "v";

    public static URL buildUrl(String movieOrVideoSearch) {


        String movieTrailerToSearchFor = null;
        URL url = null;
        Uri builtUri;
        try{

            if(isStringAnInteger(movieOrVideoSearch)){
                movieTrailerToSearchFor = movieOrVideoSearch;
                movieOrVideoSearch = "videos";
            }

            switch (movieOrVideoSearch) {
                case "popular":
                case "top_rated":
                    builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                            .appendPath(movieOrVideoSearch)
                            .appendQueryParameter(API_KEY, myPersonalApiKey)
                            .appendQueryParameter(PARAM_LANGUAGE, language)
                            .appendQueryParameter(PARAM_PAGE, page)
                            .build();
                    break;
                case "videos":
                    builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                            .appendPath(movieTrailerToSearchFor)
                            .appendPath(movieOrVideoSearch)
                            .appendQueryParameter(API_KEY, myPersonalApiKey)
                            .appendQueryParameter(PARAM_LANGUAGE, language)
                            .build();
                    break;
                default:
                    builtUri = Uri.parse(TRAILER_BASE_URL).buildUpon()
                            .appendQueryParameter(PARAM_VIDEO_SEARCH, movieOrVideoSearch)
                            .build();
                    break;

            }
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String fetchData(URL url)throws  IOException{

        //Open HTTP connection
        //URL newUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{
            //Reading from open connection object
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()){
                return  scanner.next();
            }else{
                return null;
            }
        }finally {
            connection.disconnect();
        }
    }

    private static boolean isStringAnInteger(String stringMovieId){
        try{
            Integer.parseInt(stringMovieId);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
