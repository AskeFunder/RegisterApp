package com.example.registerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.registerapp.model.User;
import com.google.gson.Gson;

public class UserRepository {
    public final static String SP_NAME = "userRepository";
    SharedPreferences userRepository;
    private static final String TAG = "UserRepository";

    public UserRepository(Context context) {
        this.userRepository = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void registerUser(User user) {
        SharedPreferences.Editor spEditor = userRepository.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);

        spEditor.putString(user.getUsername(), json);
        spEditor.commit();
    }

    public User getUser(String username) {
        Gson gson = new Gson();
        String json = userRepository.getString(username, "N/A");

        User user = null;

        try {
            user = gson.fromJson(json, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public User authenticateCredentials(String username, String password) {
        User user = getUser(username);

        Log.d(TAG, "authenticateCredentials: " + user);

        if (user != null) {
            Log.d(TAG, "authenticateCredentials: user was not null");
            if (user.getPassword().equals(password)) {
                Log.d(TAG, "authenticateCredentials: user password matched");
                return user;
            }
            Log.d(TAG, "authenticateCredentials: user password did not match");
        }
        Log.d(TAG, "authenticateCredentials: user was null");
        return null;
    }

    public boolean userExists(String username) {
        String json = userRepository.getString(username, null);
        if (json != null) {
            return true;
        } else {
            return false;
        }
    }
}
