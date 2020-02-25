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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateItemActivity extends AppCompatActivity {

    public static Container container;
    EditText editID, editName, editQuantity, editPrice;
    TextView textViewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        container = LoginActivity.container;
        editID = findViewById(R.id.editIDUpdate);
        editName = findViewById(R.id.editNameUpdate);
        editQuantity = findViewById(R.id.editQuantityUpdate);
        editPrice = findViewById(R.id.editPriceUpdate);
        textViewPost = findViewById(R.id.textPostButtonPressUpdate);
        editID.setText("");
        editPrice.setText("");
    }

    public void onResume(){
        super.onResume();
        editID.setText(container.getBarcodeString());
    }

    public boolean atLeastOneIsUpdating(){
        if(editID.getText().equals("")
                & editName.getText().equals("")
                & editQuantity.getText().equals("")
                & editPrice.getText().equals("")){
            return false;
        }
        else {
            return true;
        }
    }

    public void updateItemButton(View view){
        if(atLeastOneIsUpdating()){
            final Map<String, Object> item = new HashMap<>();
            String id = editID.getText().toString();
            String name = editName.getText().toString();
            String getPriceText = editPrice.getText().toString();
            String getQuantityText = editQuantity.getText().toString();
            if(id.equals("")){
                System.out.println("ID is not being updated");
            }
            else{
                item.put("id", id);
            }
            if(name.equals("")){
                System.out.println("Name is not being updated");
            }
            else{
                item.put("name", name);
            }
            if(getQuantityText.equals("")){
                System.out.println("Quantity is not being updated");
            }
            else{
                item.put("quantity", Integer.parseInt(getQuantityText));
            }
            if(getPriceText.equals("")){
                System.out.println("Price is not being updated");
            }
            else{
                item.put("price", Double.parseDouble(getPriceText));
            }

            createInstance().collection(container.getCompany()).document(container.getCompany())
                    .collection("Items").document(id).update(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            textViewPost.setText("Item updated successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            textViewPost.setText("Could not update item");
                        }
                    })
            ;

        }
        else {
            System.out.println("one isn't updating");
        }
    }

    public void barcodeScanButtonUpdateItem(View view){
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
