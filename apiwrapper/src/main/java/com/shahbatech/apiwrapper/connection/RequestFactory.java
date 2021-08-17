package com.shahbatech.apiwrapper.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestFactory {

    private final static String HOST = "http://lasirenegroup.lor.man.mybluehost.me/";
    public final static String IMAGES =  HOST + "storage/";
    private final static String API = HOST + "index.php/api/";


    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX") //yyyy-MM-dd'T'HH:mm:ssX  yyyy-MM-dd'T'HH:mm:ss
            .create();


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS);

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create(gson));


    private static Retrofit retrofit = builder.build();




    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
