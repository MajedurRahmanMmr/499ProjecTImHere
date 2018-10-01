package com.example.project.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.project.DB.FireBaseDataHelper;
import com.example.project.Model.TrustedStatus;
import com.example.project.R;
import com.example.project.Adapter.TrusteeAdapter;
import com.example.project.Adapter.TrusteeContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrusteeContactActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ImHereTop = database.getReference("ImHere");
    FireBaseDataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trustee_contact);
        db = new FireBaseDataHelper(this);

        final RecyclerView recyclerView = findViewById(R.id.trusteeRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*
        List<TrusteeContact> contacts = new ArrayList<>();
        contacts.add(new TrusteeContact("Emrul", "", 000.87, 3843.38743, "Friend"));
        contacts.add(new TrusteeContact("Kaosain", "", 000.87, 3843.38743, "Friend"));
        contacts.add(new TrusteeContact("Soufia", "", 000.87, 3843.38743, "Friend"));
        contacts.add(new TrusteeContact("Dibya", "", 000.87, 3843.38743, "Friend"));
        contacts.add(new TrusteeContact("Dr. Nova", "", 000.87, 3843.38743, "Teacher"));
        */


        ImHereTop.child("DataUser").child(db.getCurrentUSer().getUid()).child("Trustee").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<TrustedStatus> trustedStatusList = new ArrayList<>();

                for (DataSnapshot dataS : dataSnapshot.getChildren()) {
                    TrustedStatus status = dataS.getValue(TrustedStatus.class);
                    trustedStatusList.add(status);
                }
                recyclerView.setAdapter(new TrusteeAdapter(TrusteeContactActivity.this, trustedStatusList, 1));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
