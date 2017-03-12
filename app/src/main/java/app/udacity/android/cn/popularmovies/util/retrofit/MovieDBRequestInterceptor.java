package app.udacity.android.cn.popularmovies.util.retrofit;

import java.io.IOException;

import app.udacity.android.cn.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Chaitanya on 3/12/2017.
 * Interceptor that will add Authentication parameter to all the
 * calls to Movie DB.
 */

public class MovieDBRequestInterceptor implements Interceptor {

    final String API_KEY_PARAM = "api_key";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
