package app.udacity.android.cn.popularmovies.bundler;

import android.os.Bundle;

import org.parceler.Parcels;

import java.util.ArrayList;

import app.udacity.android.cn.popularmovies.Movie;
import icepick.Bundler;

/**
 * Created by Chaitanya on 3/12/2017.
 * Bundler implementation for Icepick library to save instances of ArrayList<Movie>
 * From version Icepick 3.2.0, you can supply a class parameter to the State annotation.
 * This class should implement the Bundler interface and you can use it to provide
 * custom serialisation and deserialisation for your types.
 */

public class MoviesBundler implements Bundler<ArrayList<Movie>> {
    @Override
    public void put(String s, ArrayList<Movie> movies, Bundle bundle) {
        bundle.putParcelable(s, Parcels.wrap(movies));
    }

    @Override
    public ArrayList<Movie> get(String s, Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(s));
    }
}
