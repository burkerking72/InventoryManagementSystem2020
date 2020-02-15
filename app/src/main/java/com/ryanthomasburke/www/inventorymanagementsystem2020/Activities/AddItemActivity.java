package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    public static Container container;
    EditText editID, editName, editQuantity, editPrice;
    TextView textViewPost;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        container = LoginActivity.container;
        editID = findViewById(R.id.editID);
        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editPrice = findViewById(R.id.editPrice);
        textViewPost = findViewById(R.id.textPostButtonPress);
        editID.setText("");
    }

    public void onResume(){
        super.onResume();
        editID.setText(container.getBarcodeString());
    }

    public boolean allFieldsEntered(){
        if(editID.getText().equals("")
            || editName.getText().equals("")
            || editQuantity.getText().equals("")
            || editPrice.getText().equals("")){
             return false;
        }
        else {
            return true;
        }
    }

    public void addItem(View view){
        if(allFieldsEntered()){
            String id = editID.getText().toString();
            String name = editName.getText().toString();
            int quantity = Integer.parseInt(editQuantity.getText().toString());
            double price = Double.parseDouble(editPrice.getText().toString());

            final Map<String, Object> item = new HashMap<>();
            item.put("id", id);
            item.put("name", name);
            item.put("price", price);
            item.put("quantity", quantity);

            createInstance().collection(container.getCompany()).document(container.getCompany())
                    .collection("Items").document(id).set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            textViewPost.setText(name + " added successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            textViewPost.setText("Could not add " + name);
                        }
                    })
            ;
        } else{
            textViewPost.setText("All fields must be completed");
        }
    }

    public void barcodeScanButtonAddItem(View view){
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

}
