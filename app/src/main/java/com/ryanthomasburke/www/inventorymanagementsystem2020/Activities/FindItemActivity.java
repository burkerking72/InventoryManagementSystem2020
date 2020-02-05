package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

public class FindItemActivity extends AppCompatActivity {

    public static Container container;
    TextView barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_item);
        barcode = findViewById(R.id.barcodeText);
        container = new Container();
    }

    public void onResume(){
        super.onResume();
        barcode.setText(container.getBarcodeString());
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

}
