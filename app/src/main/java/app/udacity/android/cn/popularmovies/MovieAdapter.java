package app.udacity.android.cn.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chaitanya on 3/9/2017.
 * Custom Adapter to convert a list of Movie objects into a view
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Context context = getContext();

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        //Fetch the movie object from the adapter
        Movie movie = getItem(position);

        if (movie != null) {
            String path = context.getString(R.string.the_movie_db_base_url) +
                    context.getString(R.string.movie_image_size) +
                    movie.getPosterPath();

            ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_image);
            Picasso.with(getContext())
                    .load(path)
                    .into(imageView);

            TextView textView = (TextView) convertView.findViewById(R.id.movie_title);
            textView.setText(movie.getOriginalTitle());
        }

        return convertView;
    }
}
