package com.example.project.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.example.project.Adapter.RequestAdapter;
import com.example.project.DB.FireBaseDataHelper;
import com.example.project.Model.CurrentUser;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrusedActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ImHereTop = database.getReference("ImHere");
    RecyclerView recyclerView;
    FireBaseDataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trused);

        db = new FireBaseDataHelper(this);
        recyclerView = findViewById(R.id.searchRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SearchView searchView = (SearchView) findViewById(R.id.searchText);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("Text Submit ", query);
                getUserByEmailOrPhone(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("Text Change ", newText);

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.e("Search Onclose ", " Close ");
                return false;
            }
        });

    }

    public void getUserByEmailOrPhone(final String email) {
        ImHereTop.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CurrentUser> list = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    try {
                        if (data.getValue(CurrentUser.class).getEmail().contains(email) && !data.getValue(CurrentUser.class).getUID().equalsIgnoreCase(db.getCurrentUSer().getUid())) {
                            list.add(data.getValue(CurrentUser.class));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (data.getValue(CurrentUser.class).getPhoneNumber().contains(email) && !data.getValue(CurrentUser.class).getUID().equalsIgnoreCase(db.getCurrentUSer().getUid())) {
                            list.add(data.getValue(CurrentUser.class));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setAdapter(new RequestAdapter(AddTrusedActivity.this, list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
