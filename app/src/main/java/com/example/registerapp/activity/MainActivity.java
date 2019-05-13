package com.example.registerapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registerapp.R;
import com.example.registerapp.UserLocalStore;
import com.example.registerapp.activity.LoginActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    Button bLogout;
    UserLocalStore userLocalStore;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogout = findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        TextView tvWelcome = findViewById(R.id.tvWelcome);

        userLocalStore = new UserLocalStore(this);
        tvWelcome.setText(tvWelcome.getText().toString() + " " + userLocalStore.getLoggedInUser().getName());


        if (userLocalStore.getLoggedInUser().getUsername().equals("admin")) {
            Button bAdmin = findViewById(R.id.bAdmin);
            bAdmin.setVisibility(View.VISIBLE);
        }

        if (isServicesOK()) {
            Button bMap = findViewById(R.id.bMap);
            bMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(mapIntent);
                }
            });
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {

        } else {
            Log.d(TAG, "onStart: was not authenticated and was logged out");
            logout();
        }
    }

    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();
    }

    private void logout() {
        userLocalStore.clearUserData()
                .setUserLoggedIn(false);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //Everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can resolve it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}
