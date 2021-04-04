package com.example.authenticateme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity {

    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView1 = findViewById(R.id.tvForSession);

        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(this);
        HashMap<String, String> usersData = sharedPreferenceManager.getUserDetailFromSession();

        String email = usersData.get(SharedPreferenceManager.KEY_EMAIL);
        String password = usersData.get(SharedPreferenceManager.KEY_PASSWORD);

        textView1.setText(email + "\n" + password);
    }
}