package com.example.hellodelivery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.hellodelivery.models.User;
import com.google.gson.Gson;

public class SessionManager {
    private static final String PREF_NAME = "DeliveryAppPrefs";
    private static final String KEY_USER = "user_session";
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_FIRST_TIME = "is_first_time";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        gson = new Gson();
    }

    public void createSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_USER, gson.toJson(user));
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public User getUserDetails() {
        String userJson = pref.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }
        return null;
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(KEY_IS_FIRST_TIME, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTime() {
        return pref.getBoolean(KEY_IS_FIRST_TIME, true);
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.putString(KEY_TOKEN, null);
        editor.putString(KEY_USER, null);
        editor.apply();
    }
}
