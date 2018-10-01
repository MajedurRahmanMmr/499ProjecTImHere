package com.example.project.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.project.Adapter.TrusteeAdapter;
import com.example.project.DB.FireBaseDataHelper;
import com.example.project.Model.TrustedStatus;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrustedActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ImHereTop = database.getReference("ImHere");
    FireBaseDataHelper db;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted);

        findViewById(R.id.addTrustedContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrustedActivity.this, AddTrusedActivity.class));
            }
        });

        db = new FireBaseDataHelper(this);

        recyclerView = findViewById(R.id.trusteeRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //setListTrustedContact(recyclerView);

    }

    private void setListTrustedContact(final RecyclerView recyclerView) {
        ImHereTop.child("DataUser").child(db.getCurrentUSer().getUid()).child("Trusted")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<TrustedStatus> trustedStatusList = new ArrayList<>();

                        for (DataSnapshot dataS : dataSnapshot.getChildren()) {
                            TrustedStatus status = dataS.getValue(TrustedStatus.class);
                            trustedStatusList.add(status);
                        }
                        recyclerView.setAdapter(new TrusteeAdapter(TrustedActivity.this, trustedStatusList, 0));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListTrustedContact(recyclerView);
    }
}
