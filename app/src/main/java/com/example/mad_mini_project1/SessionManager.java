package com.example.mad_mini_project1;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_LOGGED_IN = "key_logged_in";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUser(String name, String email, String password) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_LOGGED_IN, false);

        // init module scores
        editor.putInt("module1_score", 0);
        editor.putInt("module2_score", 0);
        editor.putInt("module3_score", 0);

        editor.apply();
    }

    public boolean checkLogin(String email, String password) {
        String savedEmail = prefs.getString(KEY_EMAIL, null);
        String savedPass = prefs.getString(KEY_PASSWORD, null);

        if (savedEmail == null || savedPass == null) return false;

        boolean ok = savedEmail.equals(email) && savedPass.equals(password);
        if (ok) {
            editor.putBoolean(KEY_LOGGED_IN, true);
            editor.apply();
        }
        return ok;
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "");
    }

    public void logout() {
        editor.putBoolean(KEY_LOGGED_IN, false);
        editor.apply();
    }

    public void saveScoreForModule(int module, int score) {
        editor.putInt("module" + module + "_score", score);
        editor.apply();
    }

    public int getScoreForModule(int module) {
        return prefs.getInt("module" + module + "_score", 0);
    }

    public int getAverageScoreRoundedUp() {
        int m1 = getScoreForModule(1);
        int m2 = getScoreForModule(2);
        int m3 = getScoreForModule(3);
        double avg = (m1 + m2 + m3) / 3.0;
        return (int) Math.ceil(avg);
    }
}
