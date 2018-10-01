package com.example.project.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Activity.CurrentPositionActivity;
import com.example.project.DB.FireBaseDataHelper;
import com.example.project.Model.CurrentUser;
import com.example.project.Model.TrustedStatus;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrusteeAdapter extends RecyclerView.Adapter<TrusteeAdapter.TrusteeViewHolder> {

    private Context context;
    private List<TrustedStatus> contactList;
    private LayoutInflater layoutInflater;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ImHereTop = database.getReference("ImHere");
    FireBaseDataHelper db;
    int curentActivity;

    public TrusteeAdapter(Context context, List<TrustedStatus> contacts, int currentActivity) {
        this.context = context;
        contactList = contacts;
        layoutInflater = LayoutInflater.from(context);
        db = new FireBaseDataHelper(context);
        this.curentActivity = currentActivity;
    }

    @NonNull
    @Override
    public TrusteeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrusteeViewHolder(layoutInflater.inflate(R.layout.search_itemtrusted, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrusteeViewHolder holder, int position) {
/*        holder.nameField.setText(contactList.get(position).getContactName());
        holder.relationField.setText(contactList.get(position).getRelation());*/


        getTrusteeName(contactList.get(position), holder);
        setActionButton(holder, position);
    }

    private void setActionButton(@NonNull TrusteeViewHolder holder, final int position) {
        if (contactList.get(position).getStatus().contains("Pending")) {
            if (curentActivity == 0) {
                holder.confirm.setVisibility(View.GONE);
                holder.relationField.setVisibility(View.VISIBLE);
                holder.decline.setVisibility(View.VISIBLE);
                holder.decline.setText("Remove");
                holder.radius.setVisibility(View.VISIBLE);

                holder.decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteOrDeclinedRequest(contactList.get(position).getTrustedUID(), curentActivity);
                        contactList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                holder.radius.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.activity_radius);
                        dialog.show();

                        dialog.findViewById(R.id.areaRadius).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.setRadius(contactList.get(position).getTrustedUID(), 3, dialog);

                            }
                        });
                        dialog.findViewById(R.id.cityRadius).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.setRadius(contactList.get(position).getTrustedUID(), 4, dialog);


                            }
                        });
                        dialog.findViewById(R.id.exactRadius).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.setRadius(contactList.get(position).getTrustedUID(), 1, dialog);


                            }
                        });
                        dialog.findViewById(R.id.fewRadius).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.setRadius(contactList.get(position).getTrustedUID(), 2, dialog);
                            }
                        });


                    }
                });

            }
            if (curentActivity == 1) {
                holder.confirm.setVisibility(View.VISIBLE);
                holder.decline.setVisibility(View.VISIBLE);
                holder.decline.setText(R.string.remove);
                holder.locationIcon.setVisibility(View.VISIBLE);

                holder.locationIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, CurrentPositionActivity.class)
                                .putExtra("UID", contactList.get(position).getTrusteeUID())
                                .putExtra("Radius", contactList.get(position).getLocationRadious()));
                    }
                });
                holder.decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteOrDeclinedRequest(contactList.get(position).getTrusteeUID(), curentActivity);
                        contactList.remove(position);
                        notifyDataSetChanged();
                    }
                });


            }


            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.acceptRequest(contactList.get(position).getTrustedUID());
                }
            });


        }
    }

    private void getTrusteeName(final TrustedStatus status, @NonNull final TrusteeViewHolder holder) {

        ImHereTop.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        CurrentUser user = data.getValue(CurrentUser.class);
                        if (user.getUID().equalsIgnoreCase((curentActivity == 1) ? status.getTrusteeUID() : status.getTrustedUID())) {
                            try {
                                Log.e("Trustee Contact ", user.toString() + " " + status.getTrustedUID());

                                if (user.getName().isEmpty()) {
                                    holder.nameField.setText(user.getPhoneNumber());
                                } else if (!user.getName().isEmpty()) {
                                    holder.nameField.setText(user.getName());
                                } else {
                                    holder.nameField.setText(R.string.unknown);
                                }
                                Picasso.get().load(user.getImageURL()).transform(new CircleTransform()).into(holder.profileImage);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class TrusteeViewHolder extends RecyclerView.ViewHolder {

        TextView nameField;
        TextView relationField;
        Button confirm;
        Button decline;
        Button radius;
        ImageView profileImage;
        ImageView locationIcon;

        TrusteeViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);

            nameField = itemView.findViewById(R.id.nameRequest);
            relationField = itemView.findViewById(R.id.nameRelation);
            confirm = itemView.findViewById(R.id.requestconfirm);
            decline = itemView.findViewById(R.id.requestDecline);
            radius = itemView.findViewById(R.id.userRadiusSelect);
            locationIcon = itemView.findViewById(R.id.viewCurrentLocation);

        }
    }
}
