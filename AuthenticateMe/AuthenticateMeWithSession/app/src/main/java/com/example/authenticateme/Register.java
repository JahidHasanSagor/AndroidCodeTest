package com.example.authenticateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private TextInputLayout fName, lName, email, password;
    private Button btnGetStarted;
    private FirebaseFirestore fireStore;
    private DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnGetStarted = findViewById(R.id.getStarted);

        fireStore = FirebaseFirestore.getInstance();
        reference = fireStore.collection("userData").document();
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fName.getEditText().getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please type your First Name", Toast.LENGTH_SHORT).show();

                }else if(fName.getEditText().getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please type your Last Name", Toast.LENGTH_SHORT).show();

                }else if(lName.getEditText().getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please type your Email", Toast.LENGTH_SHORT).show();

                }else if(email.getEditText().getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please type your Password", Toast.LENGTH_SHORT).show();

                }else if(password.getEditText().getText().toString().equals("")){
                    Toast.makeText(Register.this, "Please type a password", Toast.LENGTH_SHORT).show();

                }else {

                    reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(Register.this, "Another User exists", Toast.LENGTH_SHORT).show();

                            }else {
                                Map<String, Object> insertData = new HashMap<>();
                                insertData.put("fName", fName.getEditText().getText().toString());
                                insertData.put("lName", lName.getEditText().getText().toString());
                                insertData.put("email", email.getEditText().getText().toString());
                                insertData.put("password", password.getEditText().getText().toString());
                                fireStore.collection("userData")
                                        .add(insertData)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());

                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });
    }//End of onCreate Method

    public void signUp(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }//End of signUp Method

}