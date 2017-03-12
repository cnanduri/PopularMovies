package app.udacity.android.cn.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.udacity.android.cn.popularmovies.R;
import app.udacity.android.cn.popularmovies.model.Movie;
import app.udacity.android.cn.popularmovies.util.NetworkCheck;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    private Movie mMovie;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        mMovie = Parcels.unwrap(intent.getParcelableExtra(getString(R.string.movie)));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        String originalTitle = mMovie.originalTitle;
        String overview = mMovie.overview;
        float voteAvg = mMovie.voteAvg;
        String releaseDtStr = mMovie.releaseDt;

        //Deprecated, but needed to support older versions
        @SuppressWarnings("deprecation") Locale locale = getResources().getConfiguration().locale;
        SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.movie_date_format), locale);
        try {
            Date releaseDt = formatter.parse(releaseDtStr);
            formatter = new SimpleDateFormat(getString(R.string.movie_date_format_ui), locale);
            releaseDtStr = formatter.format(releaseDt);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error occurred while parsing date : " + e.getMessage(), e);
            e.printStackTrace();
        }

        TextView originalTitleTextView = (TextView) rootView.findViewById(R.id.movie_original_title);
        originalTitleTextView.setText(originalTitle);

        TextView overviewTextView = (TextView) rootView.findViewById(R.id.movie_overview);
        overviewTextView.setText(overview);

        TextView voteAvgTextView = (TextView) rootView.findViewById(R.id.movie_vote_average);
        voteAvgTextView.setText(String.valueOf(voteAvg));

        TextView releaseDtTextView = (TextView) rootView.findViewById(R.id.movie_release_date);
        releaseDtTextView.setText(releaseDtStr);

        if (NetworkCheck.isOnline(getContext())) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_image);
            String path = getString(R.string.the_movie_db_base_url) +
                    getString(R.string.movie_image_size) +
                    mMovie.posterPath;
            Picasso.with(getActivity())
                    .load(path)
                    .into(imageView);
        } else {
            Log.e(LOG_TAG, "No Network connection.");
        }

        return rootView;
    }
}