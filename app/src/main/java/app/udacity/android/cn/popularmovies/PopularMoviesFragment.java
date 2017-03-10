package app.udacity.android.cn.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment {

    private final String LOG_TAG = PopularMoviesFragment.class.getSimpleName();

    private MovieAdapter mMovieAdapter;

    public PopularMoviesFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        mMovieAdapter = new MovieAdapter(getActivity(), R.layout.movie_item);
        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Retrieve movie object at the position clicked
                Movie movie = mMovieAdapter.getItem(position);
                Log.v(LOG_TAG, "Movie at position : " + position + " is :" + movie.toString());
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(Constants.MOVIE, movie); //Pass the Movie object at the position clicked
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void updateMovies() {
        FetchMoviesTask task = new FetchMoviesTask();
        task.execute();
    }

    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Movie> doInBackground(Void... params) {
            List<Movie> movies = null;
            String moviesJsonStr = fetchMoviesData();
            try {
                movies = parseMovieData(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error " + e.getMessage(), e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return null;
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                mMovieAdapter.clear();

                for (Movie movie : movies) {
                    mMovieAdapter.add(movie); //To accomodate for older versions of Android, otherwise use addAll
                }

            }
        }

        private String fetchMoviesData() {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                final String SORT_BY = "sort_by";
                final String API_KEY_PARAM = "api_key";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.themoviedb.org")
                        .path("3/discover/movie")
                        .appendQueryParameter(SORT_BY, "popularity.desc")
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY);

                URL url = new URL(builder.build().toString());

                Log.v(LOG_TAG, "Built URI : " + url.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies JSON String : " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return moviesJsonStr;
        }

        /**
         * Refer https://developers.themoviedb.org/3/discover to view JSON
         * format of the data
         *
         * @param moviesJsonStr
         * @return
         * @throws JSONException
         */
        private List<Movie> parseMovieData(String moviesJsonStr) throws JSONException {

            List<Movie> movies = new ArrayList<Movie>();

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray results = moviesJson.getJSONArray("results");

            if (results != null) {
                int length = results.length();
                for (int i = 0; i < length; i++) {
                    JSONObject movieJson = results.getJSONObject(i);

                    if (movieJson != null) {

                        String posterPath = movieJson.getString("poster_path");
                        String originalTitle = movieJson.getString("original_title");
                        String overview = movieJson.getString("overview");
                        String releaseDt = movieJson.getString("release_date");
                        int voteAvg = movieJson.getInt("vote_average");

                        Movie movie = new Movie(posterPath, originalTitle, overview, releaseDt, voteAvg);
                        Log.v(LOG_TAG, movie.toString());
                        movies.add(movie);
                    }
                }
            }
            return movies;
        }
    }
}
