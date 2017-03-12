package app.udacity.android.cn.popularmovies.util.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Chaitanya on 3/12/2017.
 */

public interface MovieDBService {

    @GET("3/movie/{sortBy}")
    Call<MovieResponse> fetchMovies(@Path("sortBy") String sortBy);

}
