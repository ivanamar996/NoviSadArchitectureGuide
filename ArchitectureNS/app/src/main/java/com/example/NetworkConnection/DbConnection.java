package com.example.NetworkConnection;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DbConnection {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.0.31:50185"; // lokalna ip adresa

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}