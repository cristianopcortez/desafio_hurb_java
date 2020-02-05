package com.ccortez.desafiohurbapplication.service.repository;

import java.util.List;

import com.ccortez.desafiohurbapplication.BuildConfig;
import com.ccortez.desafiohurbapplication.service.model.HotelSearch;
import com.ccortez.desafiohurbapplication.wrapper.SuggestionDataWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackEndService {
    String HTTP_API_URL = BuildConfig.API_BASE_URL;

//    @GET(".")
//    @GET("api?q=buzios&page=1")
    @GET("api?page=1")
    Call<HotelSearch> getHotelList(@Query("q") String query);

    @GET("api/suggestion")
    Call<SuggestionDataWrapper> getCityData(@Query("q") String query);
}
