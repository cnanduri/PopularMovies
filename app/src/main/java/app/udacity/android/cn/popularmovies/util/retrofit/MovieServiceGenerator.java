package app.udacity.android.cn.popularmovies.util.retrofit;

import app.udacity.android.cn.popularmovies.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chaitanya on 3/12/2017.
 */

public final class MovieServiceGenerator {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static MovieDBRequestInterceptor requestInterceptor = new MovieDBRequestInterceptor();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().addInterceptor(requestInterceptor);
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();

    private MovieServiceGenerator() {

    }

    public static Retrofit retrofit() {
        return retrofit;
    }

    public static <S> S createService(
            Class<S> serviceClass) {

        addInterceptors(requestInterceptor);

        //Log for Development purpose only
        if (BuildConfig.DEBUG) {
            addInterceptors(loggingInterceptor); //Add logging at the end
        }

        return retrofit.create(serviceClass);
    }

    private static void addInterceptors(Interceptor interceptor) {
        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
    }

}
