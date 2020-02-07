package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

public class FindItemActivity extends AppCompatActivity {

    public static Container container;
    EditText productID;
    TextView idView, nameView, quantityView, priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_item);
        productID = findViewById(R.id.editTextBarcode);
        container = LoginActivity.container;
        idView = findViewById(R.id.textIDPost);
        nameView = findViewById(R.id.textNamePost);
        quantityView = findViewById(R.id.textQuantityPost);
        priceView = findViewById(R.id.textPricePost);
    }

    public void onResume(){
        super.onResume();
        productID.setText(container.getBarcodeString());
    }

    public void openScanner(View view){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permission Not Granted");
        } else {
            System.out.println("Permission Granted");
            try {
                Intent intent = new Intent(this, LiveBarcodeScanningActivity.class);
                startActivity(intent);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public FirebaseFirestore createInstance(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        return db;
    }

    public void findItemButton(View view){
        String barcodeID = productID.getText().toString();
        createInstance().collection(container.getCompany()).document(container.getCompany())
            .collection("Items").document(barcodeID)
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        // If the document searched for exists, continue.
                        if(documentSnapshot.exists()){
                            String productID = task.getResult().getString("id");
                            String name = task.getResult().getString("name");
                            int quantity = task.getResult().getLong("quantity").intValue();
                            long price = task.getResult().getLong("price");
                            idView.setText(productID);
                            nameView.setText(name);
                            quantityView.setText(String.valueOf(quantity));
                            priceView.setText(String.valueOf(price));
                        }
                        // If the company doesn't exist, notify the user.
                        else {
                            System.out.println("Does not exist");
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
            });
    }

    /*
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
     */

}
