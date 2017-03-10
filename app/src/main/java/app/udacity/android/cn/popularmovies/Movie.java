package app.udacity.android.cn.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Chaitanya on 3/9/2017.
 */

public class Movie implements Parcelable {

    private String posterPath;
    private String originalTitle;
    private String overview;
    private String releaseDt;
    private int voteAvg;


    public Movie(String posterPath, String originalTitle,
                 String overview, String releaseDt, int voteAvg) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDt = releaseDt;
        this.voteAvg = voteAvg;
    }


    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDt = in.readString();
        this.voteAvg = in.readInt();
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDt);
        dest.writeInt(voteAvg);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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

    public int getVoteAvg() {
        return voteAvg;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDt(String releaseDt) {
        this.releaseDt = releaseDt;
    }

    public void setVoteAvg(int voteAvg) {
        this.voteAvg = voteAvg;
    }
}
