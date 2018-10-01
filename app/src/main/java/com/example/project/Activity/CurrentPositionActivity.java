package com.example.project.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.project.Model.CustomLocationWithTime;
import com.example.project.R;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentPositionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ImHereTop = database.getReference("ImHere");
    String UserUID = "";
    int locationRadius = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_current_position);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UserUID = getIntent().getStringExtra("UID");
            locationRadius = getIntent().getIntExtra("Radius", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (UserUID != null) {
            if (!UserUID.isEmpty())
                ImHereTop.child("DataUser").child(UserUID).child("Location").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Log.e("Location ", dataSnapshot.getValue(CustomLocationWithTime.class).toString());

                        CustomLocationWithTime location = dataSnapshot.getValue(CustomLocationWithTime.class);
                        if (location != null) {
                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, (16 - locationRadius - 1)));

                            if (locationRadius != 1) {
                                CircleOptions circleOptions = new CircleOptions();
                                circleOptions.center(sydney);
                                circleOptions.radius(200 * (locationRadius * locationRadius));
                                circleOptions.strokeColor(Color.parseColor("#ff1744"));
                                circleOptions.fillColor(Color.parseColor("#28FF1744"));
                                circleOptions.strokeWidth(10);
                                mMap.clear();

                                mMap.addCircle(circleOptions);
                            } else {
                                mMap.clear();

                                mMap.addMarker(new MarkerOptions().position(sydney).title(getString(R.string.currentPosition)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot
                                                       dataSnapshot, @Nullable String s) {
                        Log.e("Location ", dataSnapshot.getValue(CustomLocationWithTime.class).toString());
                        CustomLocationWithTime location = dataSnapshot.getValue(CustomLocationWithTime.class);
                        if (location != null) {
                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                            if (locationRadius != 1) {
                                CircleOptions circleOptions = new CircleOptions();
                                circleOptions.center(sydney);
                                if (locationRadius == 2) {
                                    circleOptions.radius(200 * (locationRadius));

                                } else {
                                    circleOptions.radius(200 * (locationRadius * locationRadius));

                                }
                                circleOptions.strokeColor(Color.parseColor("#ff1744"));
                                circleOptions.fillColor(Color.parseColor("#28FF1744"));
                                circleOptions.strokeWidth(10);
                                mMap.clear();
                                mMap.addCircle(circleOptions);
                            } else {
                                mMap.clear();

                                mMap.addMarker(new MarkerOptions().position(sydney).title(getString(R.string.currentPosition)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                            }
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot
                                                     dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        } else {
            ImHereTop.child("DataUser").child(currentUser.getUid()).child("Location").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Log.e("Location ", dataSnapshot.getValue(CustomLocationWithTime.class).toString());

                    CustomLocationWithTime location = dataSnapshot.getValue(CustomLocationWithTime.class);
                    if (location != null) {
                        mMap.clear();

                        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Current Position"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.e("Location ", dataSnapshot.getValue(CustomLocationWithTime.class).toString());

                    CustomLocationWithTime location = dataSnapshot.getValue(CustomLocationWithTime.class);
                    if (location != null) {
                        mMap.clear();

                        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Current Position"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
