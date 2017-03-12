package app.udacity.android.cn.popularmovies.util.retrofit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import app.udacity.android.cn.popularmovies.model.Movie;

/**
 * Created by Chaitanya on 3/12/2017.
 * JSON parsed object
 */

@SuppressWarnings("WeakerAccess")
public class MovieResponse {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @SuppressWarnings("CanBeFinal")
    @SerializedName("results")
    List<Movie> movies;

    public MovieResponse() {
        movies = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public static MovieResponse parseJSON(String jsonResponse) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(DATE_FORMAT);
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonResponse, MovieResponse.class);
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
