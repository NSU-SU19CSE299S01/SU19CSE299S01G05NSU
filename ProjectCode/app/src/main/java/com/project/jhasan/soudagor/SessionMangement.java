package com.project.jhasan.soudagor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionMangement {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    Context _context;


    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Service";


    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_NAME = "name";


    public static final String KEY_EMAIL = "email";

    public SessionMangement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.commit();
    }


    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
