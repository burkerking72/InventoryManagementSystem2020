package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void onClick(View v){
        String user = username.getText().toString();
        String pass = password.getText().toString();
        readData(user, pass, "Test Company");

    }

    public FirebaseFirestore createInstance(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        return db;
    }

    public void loginSuccess(){
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

    public void readData(final String username, final String password,
                            String company){
        createInstance().collection(company).document("TestDocument")
                .collection("Users").document("ryburke15@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String user = task.getResult().getString("email");
                        String pass = task.getResult().getString("password");
                        System.out.println(1231231);
                        if(username.matches(user) & password.matches(pass)){
                            System.out.println("SUCCESS!!!!!!!!!!!");
                            loginSuccess();
                        }
                        else {
                            //TODO:DELETE THIS AFTER DEBUG
                            System.out.println(user + ":" + username);
                            System.out.println(pass + ":" + password);
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.toString());
            }
        })
        ;
    }

}
