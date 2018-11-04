package com.example.project;

import com.example.project.Model.FirbitResponse.FitBitHeartRate;
import com.firebase.ui.auth.data.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FitbitApiInterface {
    @GET("/1/user/-/activities/heart/date/today/1d.json")
    Call<FitBitHeartRate> getUserHrartRateFromFitbit(@Header("Authorization") String authorization);
}
