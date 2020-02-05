package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ryanthomasburke.www.inventorymanagementsystem2020.R;

public class MenuActivity extends AppCompatActivity {

    Button findItem, addItem, updateItem, deleteItem, listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findItem = findViewById(R.id.buttonFindItem);
        addItem = findViewById(R.id.buttonAddItem);
        updateItem = findViewById(R.id.buttonUpdateItem);
        deleteItem = findViewById(R.id.buttonDeleteItem);
        listItems = findViewById(R.id.buttonFindAll);
    }

    public void findItem(View view){
        Intent intent = new Intent(this, FindItemActivity.class);
        startActivity(intent);
    }

    public void addItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    public void updateItem(View view){
        Intent intent = new Intent(this, UpdateItemActivity.class);
        startActivity(intent);
    }

    public void deleteItem(View view){
        Intent intent = new Intent(this, DeleteItemActivity.class);
        startActivity(intent);
    }

    public void listItems(View view){
        Intent intent = new Intent(this, ListAllItemsActivity.class);
        startActivity(intent);
    }
}
