package com.example.project.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {

                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                // ...
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, R.string.loginUnsuccessful, Toast.LENGTH_SHORT).show();
            }
        }
    }
}