package app.udacity.android.cn.popularmovies.util.retrofit;

import app.udacity.android.cn.popularmovies.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chaitanya on 3/12/2017.
 * As you know from our getting started with Retrofit tutorial, the Retrofit object
 * and its builder are the heart of all requests. Here you configure and prepare
 * your requests, responses, authentication, logging and error handling. Unfortunately,
 * we've seen too many developers just copy-and-pasting these parts instead of separating
 * into one clean class. The ServiceGenerator will give you our solution, which is based
 * on Bart Kiers' idea.
 *
 * Refer https://futurestud.io/tutorials/retrofit-2-creating-a-sustainable-android-client
 */

public final class MovieServiceGenerator {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final MovieDBRequestInterceptor requestInterceptor = new MovieDBRequestInterceptor();
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().addInterceptor(requestInterceptor);
    private static final Retrofit.Builder builder =
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
