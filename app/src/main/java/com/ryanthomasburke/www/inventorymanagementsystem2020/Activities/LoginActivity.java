package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;


public class LoginActivity extends AppCompatActivity {

    public static Container container;
    private EditText username;
    private EditText password;
    private EditText company;
    private TextView loginError;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        container = new Container();
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        company = findViewById(R.id.editCompany);
        loginError = findViewById(R.id.textLoginError);
        company.setText("Test Company");
        username.setText("ryburke15@gmail.com");
        password.setText("password");
    }

    public void onResume(){
        super.onResume();
        password.setText("");
    }

    public void onClick(View v){
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String comp = company.getText().toString();
        readData(user, pass, comp);
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
        createInstance().collection(company).document(company)
                .collection("Users").document(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        // If it searched for the document, continue.
                        if(task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            // If the document searched for exists, continue.
                            if(documentSnapshot.exists()){
                                String user = task.getResult().getString("email");
                                String pass = task.getResult().getString("password");
                                // If the credentials are correct, continue.
                                if (username.matches(user) & password.matches(pass)) {
                                    System.out.println("SUCCESS!!!!!!!!!!!");
                                    container.setCompany(company);
                                    loginSuccess();
                                }
                                // If the credentials are incorrect, notify the user.
                                else {
                                    loginError.setText("Incorrect password");
                                }
                            }
                            // If the company doesn't exist, notify the user.
                            else {
                                loginError.setText("Company Database Does Not Exist");
                            }
                        }
                        // Could not get a document snapshot.
                        else {
                            System.out.println("Could Not Get Document Snapshot");
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
