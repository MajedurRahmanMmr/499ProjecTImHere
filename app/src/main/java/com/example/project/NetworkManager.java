package com.example.project;

import com.example.project.Model.FirbitResponse.FitBitHeartRate;
import com.firebase.ui.auth.data.model.User;

import retrofit2.Call;

public class NetworkManager {

    FitbitApiInterface fitbitApiInterface;

    public NetworkManager() {
        fitbitApiInterface = NetworkService.getNetworkInstance().create(FitbitApiInterface.class);
    }

    public Call<FitBitHeartRate> getFitbitData(String key) {
        Call<FitBitHeartRate> fromFitbit = fitbitApiInterface.getUserHrartRateFromFitbit("Bearer " + key);
        return fromFitbit;
    }

}
