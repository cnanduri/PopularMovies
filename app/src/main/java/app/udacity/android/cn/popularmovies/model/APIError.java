package app.udacity.android.cn.popularmovies.model;

/**
 * Created by Chaitanya on 3/12/2017.
 */

public class APIError {

    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }

}
