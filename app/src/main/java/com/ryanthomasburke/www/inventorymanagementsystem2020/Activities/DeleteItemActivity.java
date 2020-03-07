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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

public class DeleteItemActivity extends AppCompatActivity {

    public static Container container;
    EditText deleteItem;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_item);
        container = LoginActivity.container;
        deleteItem = findViewById(R.id.editTextDeleteItemID);
        message = findViewById(R.id.textViewDeleteItemMessage);
        deleteItem.setText("");
        message.setText("FUCK YOU ANDROID STUDIO 3.6!!!!!!!!!!!!!!!!");
    }

    public void onResume(){
        super.onResume();
        deleteItem.setText(container.getBarcodeString());
    }

    public FirebaseFirestore createInstance(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        return db;
    }

    public void onClickDeleteItem(View view){
        String barcodeID = deleteItem.getText().toString();
        if (barcodeID.matches("")){
            message.setText("Enter a valid item ID");
        }
        else {
            createInstance().collection(container.getCompany()).document(container.getCompany())
                    .collection("Items").document(barcodeID).delete();
        }
    }

    public void onClickOpenBarcode(View view){
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

}
