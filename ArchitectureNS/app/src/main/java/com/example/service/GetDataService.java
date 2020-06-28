package com.example.service;

import com.example.architecturens.DatabaseVersion;
import com.example.architecturens.PlaceInfo;
import com.example.architecturens.RouteInfo;

import java.util.List;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/getAllRoutes")
    Call<String> getAllRoutes();

    @GET("/getRecommendedPlaces")
    Call<String> getRecommendedPlaces();

    @GET("/postGrade/{id}/{grade}")
    Call<String> postGrade(@Path("id")int id, @Path("grade")double grade);


}
