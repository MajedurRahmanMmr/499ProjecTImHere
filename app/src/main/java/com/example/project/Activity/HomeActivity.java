package com.example.project.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.project.DB.FireBaseDataHelper;
import com.example.project.GPSTracker;
import com.example.project.Model.CustomLocationWithTime;
import com.example.project.Model.FirbitResponse.FitBitHeartRate;
import com.example.project.NetworkManager;
import com.example.project.R;
import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    try {
                        CustomLocationWithTime customLocation = new CustomLocationWithTime(location.getLatitude(), location.getLongitude(), location.getTime());
                        new FireBaseDataHelper(HomeActivity.this).updateUserCurentLocation(customLocation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            ;
        };

      /*  GPSTracker gpsTracker = new GPSTracker(this);
        Toast.makeText(this, gpsTracker.getLocation().getLatitude() +"", Toast.LENGTH_SHORT).show();*/
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            CustomLocationWithTime customLocation = new CustomLocationWithTime(location.getLatitude(), location.getLongitude(), location.getTime());
                            new FireBaseDataHelper(HomeActivity.this).updateUserCurentLocation(customLocation);

                        }
                    }
                });


        InitLocationListener();
        new FireBaseDataHelper(this).setUserDataToDB();

        setActions();


        new NetworkManager().getFitbitData("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMkQ1NzUiLCJzdWIiOiI2WTdKUTUiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyc29jIHJhY3QgcnNldCBybG9jIHJ3ZWkgcmhyIHJudXQgcnBybyByc2xlIiwiZXhwIjoxNTQxOTUwOTEwLCJpYXQiOjE1NDEzNDYyNjF9.5PTQwRgra4U8saT4xsK63FgkzWDqCS04t6u0fw8s_04").enqueue(new Callback<FitBitHeartRate>() {
            @Override
            public void onResponse(Call<FitBitHeartRate> call, Response<FitBitHeartRate> response) {
                Log.e("Heart Beat ", response.body().toString());
            }

            @Override
            public void onFailure(Call<FitBitHeartRate> call, Throwable t) {

            }
        });

    }

    private void InitLocationListener() {
        Context context = this;

    }

    private void setActions() {
        findViewById(R.id.callEmergencyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    checkPermission();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.radiusSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RadiusActivity.class));
            }
        });


        findViewById(R.id.trusteeContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TrusteeContactActivity.class));
            }
        });

        findViewById(R.id.health).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, HealthActivity.class));
            }
        });
        findViewById(R.id.comminity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CominityActivity.class));
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthUI.getInstance()
                        .signOut(HomeActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            }
                        });
            }
        });

        findViewById(R.id.currentPosition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CurrentPositionActivity.class));
            }
        });

        findViewById(R.id.trustedContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TrustedActivity.class));
            }
        });
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);


        // LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();


        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                // Toast.makeText(HomeActivity.this, " Location REsponse", Toast.LENGTH_SHORT).show();

                if (locationSettingsResponse.getLocationSettingsStates().isLocationPresent()) {
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(HomeActivity.this);
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        Toast.makeText(HomeActivity.this, " " + location.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }

    private void checkPermission() {

        PermissionListener permissionlistener = new PermissionListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 100));
                startActivity(intent);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(HomeActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedTitle("")
                .setDeniedMessage("")
                .setGotoSettingButtonText("go to Settings")
                .setPermissions(android.Manifest.permission.CALL_PHONE)
                .check();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000 * 5);
        mLocationRequest.setFastestInterval(1000 * 5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /* Looper */);
    }
}
