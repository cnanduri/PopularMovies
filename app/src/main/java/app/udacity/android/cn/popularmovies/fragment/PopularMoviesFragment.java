package app.udacity.android.cn.popularmovies.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import app.udacity.android.cn.popularmovies.R;
import app.udacity.android.cn.popularmovies.activity.MovieDetailActivity_;
import app.udacity.android.cn.popularmovies.adapter.MovieAdapter;
import app.udacity.android.cn.popularmovies.model.APIError;
import app.udacity.android.cn.popularmovies.model.Movie;
import app.udacity.android.cn.popularmovies.util.ErrorUtils;
import app.udacity.android.cn.popularmovies.util.NetworkCheck;
import app.udacity.android.cn.popularmovies.util.bundler.MoviesBundler;
import app.udacity.android.cn.popularmovies.util.retrofit.MovieDBService;
import app.udacity.android.cn.popularmovies.util.retrofit.MovieResponse;
import app.udacity.android.cn.popularmovies.util.retrofit.MovieServiceGenerator;
import icepick.Icepick;
import icepick.State;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class PopularMoviesFragment extends Fragment {

    private final String LOG_TAG = PopularMoviesFragment.class.getSimpleName();

    @SuppressWarnings({"CanBeFinal", "WeakerAccess"})
    @State(MoviesBundler.class)
    ArrayList<Movie> mMovies = new ArrayList<>();

    @ViewById(R.id.movies_grid)
    GridView gridView;

    private MovieAdapter mMovieAdapter;

    public PopularMoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkCheck.isOnline(getContext())) {
            updateMovies();
        } else {
            Log.e(LOG_TAG, "No Network connection.");
        }
    }

    @AfterViews
    void configureGridView() {
        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
        // Get a reference to the GridView, and attach this adapter to it.
        gridView.setAdapter(mMovieAdapter);
    }

    @ItemClick(R.id.movies_grid)
    void onMovieClick(Movie movie) {
        if (movie != null) {
            Intent intent = new Intent(getActivity(), MovieDetailActivity_.class)
                    .putExtra(getString(R.string.movie), Parcels.wrap(movie)); //Pass the Movie object at the position clicked
            startActivity(intent);
        }
    }

    private void updateMovies() {
        //Retrieve Shared Preferences to get the user set location from settings
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortOrder = getString(R.string.pref_sort_order_value_popular);
        String path;
        if (settings != null) {
            sortOrder = settings.getString(getString(R.string.pref_sort_order_key), sortOrder);
        }

        if (getString(R.string.pref_sort_order_value_popular).equalsIgnoreCase(sortOrder)) {
            path = "popular";
        } else {
            path = "top_rated";
        }

        fetchMovies(path);
    }

    private void fetchMovies(String sortBy) {
        MovieDBService service = MovieServiceGenerator.createService(MovieDBService.class);
        Call<MovieResponse> call = service.fetchMovies(sortBy);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    List<Movie> movies = movieResponse.getMovies();
                    if (movies != null) {
                        mMovieAdapter.clear();
                        for (Movie movie : movies) {
                            mMovies.add(movie);//To accommodate for older versions of Android, otherwise use addAll
                        }
                    }
                } else {
                    // parse the response body …
                    APIError error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.e("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage(), t);
                t.printStackTrace();
            }
        });
    }

}
