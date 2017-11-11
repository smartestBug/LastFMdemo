package dev.msemyak.lastfmdemo.mvp.model.remote;

import android.util.Log;
import java.io.File;
import dev.msemyak.lastfmdemo.AppBoss;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private static LastFMAPI lastfmAPI = null;
    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit()
    {
        if (retrofit == null) {

            //cache request when network is available
            final Interceptor NetworkCacheInterceptor = chain -> {
                Response originalResponse = chain.proceed(chain.request());
                String cacheControl = originalResponse.header("Cache-Control");
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                            .build();
                } else {
                    return originalResponse;
                }
            };

            // use cache when there's no network available
            final Interceptor ApplicationOfflineInterceptor = chain -> {
                Request request = chain.request();
                if (!AppBoss.isNetworkAvailable()) {
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached")
                            .build();
                }
                return chain.proceed(request);
            };

            // http request / response logger
            final Interceptor LoggingInterceptor = chain -> {
                Request request = chain.request();
                Log.d("TAGLAST", String.format("Sending request %s %nHeaders: %n%s", request.url(), request.headers()));

                Response response = chain.proceed(request);
                Log.d("TAGLAST", String.format("Received response for %s %nHeaders: %n%s", response.request().url(), response.headers()));

                return response;
            };

            File cacheFile = new File(AppBoss.getContext().getCacheDir(), "GUMegaCache");
            Log.d("TAGLAST", "Default cache dir: " + AppBoss.getContext().getCacheDir().toString());
            Cache cache = new Cache(cacheFile, 10 * 1024 * 1024); //10Mb
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .cache(cache)
                    .addNetworkInterceptor(NetworkCacheInterceptor)
                    .addInterceptor(ApplicationOfflineInterceptor)
                    .addInterceptor(LoggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static LastFMAPI getLastFMAPI() {

        if (lastfmAPI == null) {
            lastfmAPI = getRetrofit().create(LastFMAPI.class);
        }

        return lastfmAPI;
    }

}
