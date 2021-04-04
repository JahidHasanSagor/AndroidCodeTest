package com.example.authenticateme;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedPreferenceManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_lOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public SharedPreferenceManager(Context _context) {
        context = _context;
        userSession = _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String email, String password) {
        editor.putBoolean(IS_lOGIN, true);

        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);

        editor.commit();

    }

    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));

        return userData;
    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_lOGIN, false)) {
            return true;
        } else
            return false;
    }

    public void logOutUserFromSession() {
        editor.clear();
        editor.commit();
    }
}
