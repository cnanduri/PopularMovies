package app.udacity.android.cn.popularmovies;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Chaitanya on 3/9/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMAGE_SIZE = "w185";

        //Fetch the movie object from the adapter
        Movie movie = getItem(position);

        String path = new StringBuffer(BASE_URL).append(IMAGE_SIZE)
                .append(movie.getPosterPath()).toString();
        Log.v(LOG_TAG, "Poster Path : " + path);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.with(getContext())
                .load(path)
                .into(imageView);

        TextView textView = (TextView) convertView.findViewById(R.id.movie_title);
        textView.setText(movie.getOriginalTitle());

        return convertView;
    }
}