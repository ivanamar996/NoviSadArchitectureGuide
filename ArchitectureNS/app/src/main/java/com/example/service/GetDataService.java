package com.example.service;

import com.example.architecturens.DatabaseVersion;
import com.example.architecturens.RouteInfo;

import java.util.List;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/getAllRoutes")
    Call<String> getAllRoutes();

}
