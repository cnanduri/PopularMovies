package app.udacity.android.cn.popularmovies.util;

import java.io.IOException;
import java.lang.annotation.Annotation;

import app.udacity.android.cn.popularmovies.model.APIError;
import app.udacity.android.cn.popularmovies.util.retrofit.MovieServiceGenerator;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Chaitanya on 3/12/2017.
 */

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                MovieServiceGenerator.retrofit()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
