package com.example.karadvenderapp.NetworkController;

/*import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;*/

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyConfig
{
    public static final String JSON_BASE_URL = "https://onlineq.in/demo/";
    public static final String JSON_PAYMENT_URL = "https://sumagoinfotech.in/rpa_original_webb/api/";
    public static final String Demo = "";
    public static final String APIKEY = "s9&@KPf1E%0GyU89CvDeW$VKQhg3VAREv^9t";


    public static Retrofit retrofit=null;
        public static Retrofit getRetrofit() {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(JSON_BASE_URL)
                    .client(new OkHttpClient.Builder().
                            connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit;
    }





}
