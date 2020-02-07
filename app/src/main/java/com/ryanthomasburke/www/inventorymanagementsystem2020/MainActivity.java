package com.ryanthomasburke.www.inventorymanagementsystem2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ryanthomasburke.www.inventorymanagementsystem2020.Activities.FindItemActivity;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);
        //Intent intent = new Intent(this, FindItemActivity.class);
        this.startActivity(intent);
    }
}

/*
This will be the main project space for the Inventory Management System Application
That I will be developing for Individual Study. It will be in a GitHub Repository.
 */