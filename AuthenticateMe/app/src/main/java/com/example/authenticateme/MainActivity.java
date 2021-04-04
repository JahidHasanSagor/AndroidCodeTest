package com.example.authenticateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout email, password;
    private Button btnLogIn, btnSignUp;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = findViewById(R.id.email);
        password = findViewById(R.id.loginPassword);

        btnLogIn = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnNewUser);

        fireStore = FirebaseFirestore.getInstance();


    }
    private Boolean validateUserName(){
        String userVal = email.getEditText().getText().toString();
        if(userVal.isEmpty()){
            email.setError("Field cannot be empty");
            return false;
        }
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String passVal = password.getEditText().getText().toString();
        if(passVal.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void logIn(View view) {
        if(email.getEditText().getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }else if( password.getEditText().getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
        }
        fireStore.collection("userData")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot ds : task.getResult()){
                                String emailFromDB = ds.getString("email");
                                String passwordFromDB = ds.getString("password");
                                String emailFromUser = email.getEditText().getText().toString().trim();
                                String passwordFromUser = password.getEditText().getText().toString().trim();
                                if(emailFromDB.equalsIgnoreCase(emailFromUser) & passwordFromDB.equalsIgnoreCase(passwordFromUser)) {
                                    Intent home = new Intent(MainActivity.this, Home.class);
                                    startActivity(home);
                                    Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                    break;
                                }else
                                    Toast.makeText(MainActivity.this, "Cannot login, Incorrect credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void signUp(View view) {
        Intent register_view = new Intent(MainActivity.this,Register.class);
        startActivity(register_view);
    }
}