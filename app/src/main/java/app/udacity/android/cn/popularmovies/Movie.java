package app.udacity.android.cn.popularmovies;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.parceler.ParcelConstructor;

/**
 * Created by Chaitanya on 3/9/2017.
 * Holds the following information:
 *  1. Poster path --> This is required if we need the poster of the
 *      movie we are displaying
 *  2. Original Title
 *  3. Overview
 *  4. Release Date
 *  5. Vote Average
 *
 *  For more information, refer https://developers.themoviedb.org/3/getting-started/introduction
 */

@org.parceler.Parcel
@SuppressWarnings("CanBeFinal")
public class Movie {

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("overview")
    String overview;

    @SerializedName("release_date")
    String releaseDt;

    @SerializedName("vote_average")
    float voteAvg;

    @ParcelConstructor
    public Movie(String posterPath, String originalTitle,
                 String overview, String releaseDt, float voteAvg) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDt = releaseDt;
        this.voteAvg = voteAvg;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDt() {
        return releaseDt;
    }

    public float getVoteAvg() {
        return voteAvg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
