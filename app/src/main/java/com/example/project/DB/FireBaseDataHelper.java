package com.example.project.DB;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.project.Model.CurrentUser;
import com.example.project.Model.CustomLocationWithTime;
import com.example.project.Model.TrustedStatus;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDataHelper {
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ImHereTop = database.getReference("ImHere");

    private DatabaseReference userHomeRef = database.getReference("ImHere").child("User").child(currentUser.getUid());
    private DatabaseReference userTrustedRef = database.getReference("ImHere").child("Trusted").child(currentUser.getUid());
    private DatabaseReference userTruseteeRef = database.getReference("ImHere").child("Trustee").child(currentUser.getUid());
    private DatabaseReference userDataRef = database.getReference("ImHere").child("DataUser").child(currentUser.getUid());
    private DatabaseReference userDataHomeRef = database.getReference("ImHere").child("DataUser");
    private DatabaseReference userDataLocationRef = userDataRef.child("Location");
    private DatabaseReference userDataTrustedRef = userDataRef.child("Trusted");
    private DatabaseReference userDataTrusteeRef = userDataRef.child("Trustee");

    private Context context;

    public FireBaseDataHelper(Context c) {
        context = c;
    }

    public void setUserDataToDB() {
        CurrentUser cUser = new CurrentUser(currentUser.getDisplayName(), currentUser.getUid());
        try {
            if (currentUser.getPhoneNumber() != null) {
                cUser.setPhoneNumber(currentUser.getPhoneNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!(currentUser.getEmail() + "").isEmpty()) {
                cUser.setEmail(currentUser.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (currentUser.getPhotoUrl() != null) {
                cUser.setImageURL(currentUser.getPhotoUrl().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        userHomeRef.setValue(cUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    public void updateUserCurentLocation(CustomLocationWithTime location) {
        userDataLocationRef.child("0").setValue(location).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void adUSerAsTrustedContact(final TrustedStatus status) {

        userDataTrustedRef.child(status.getTrustedUID()).setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e("Updated Trusted List ", "");

                // TrustedStatus newtstatus = new TrustedStatus(status.getTrustedUID(), status.getTrusteeUID(), "Pending");

                userDataHomeRef.child(status.getTrustedUID()).child("Trustee").child(status.getTrusteeUID()).setValue(status);
            }
        });
    }


    public FirebaseUser getCurrentUSer() {
        return currentUser;
    }


    public void acceptRequest(String key) {
        userDataTrustedRef.child(key).child("status").setValue("Accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Request Pending Complete ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Request  Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteOrDeclinedRequest(String key, int curentActivity) {


        if (curentActivity == 0) {

            userDataTrustedRef.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, R.string.updatedSuccessfully, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, R.string.requestError, Toast.LENGTH_SHORT).show();
                }
            });
            userDataHomeRef.child(key).child("Trustee").child(getCurrentUSer().getUid()).removeValue();

        } else {
            userDataTrusteeRef.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context,  R.string.updatedSuccessfully, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, R.string.requestError, Toast.LENGTH_SHORT).show();
                }
            });
            userDataHomeRef.child(key).child("Trusted").child(getCurrentUSer().getUid()).removeValue();
        }


    }

    public void setRadius(String key, int radius, final Dialog dialog) {
        userDataTrustedRef.child(key).child("locationRadious").setValue(radius).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, R.string.locationRadiusUpdated, Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.requestError, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        userDataHomeRef.child(key).child("Trustee").child(getCurrentUSer().getUid()).child("locationRadious").setValue(radius);
    }


}
