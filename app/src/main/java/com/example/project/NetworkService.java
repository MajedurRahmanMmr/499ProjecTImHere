package com.example.project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static Retrofit retrofit;


    public static Retrofit getNetworkInstance() {


        if (retrofit != null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.fitbit.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }

}
