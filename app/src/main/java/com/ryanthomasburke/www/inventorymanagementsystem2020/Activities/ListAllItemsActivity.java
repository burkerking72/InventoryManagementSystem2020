package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ryanthomasburke.www.inventorymanagementsystem2020.Container;
import com.ryanthomasburke.www.inventorymanagementsystem2020.R;
public class ListAllItemsActivity extends AppCompatActivity {

    ViewGroup parentLayout;
    public static Container container;
    TextView id, name, quantity, price;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_items);
        container = LoginActivity.container;
        parentLayout = findViewById(R.id.linearLayoutForItems);

        listAllItems();
        //cardGenerator();
    }

    public void onRestart(){
        super.onRestart();
        parentLayout.removeAllViews();
        listAllItems();
    }

    public void listAllItems(){
        createInstance().collection(container.getCompany()).document(container.getCompany())
                .collection("Items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    //System.out.println("Task is successful.");
                    for(QueryDocumentSnapshot document : task.getResult()){
                        //System.out.println("Inside of QueryDocumentSnapshot");
                        String pID = document.getString("id");
                        String pName = document.getString("name");
                        int pQuant = document.getLong("quantity").intValue();
                        double pPrice = document.getDouble("price");
                        cardGenerator(pID, pName, pQuant, pPrice);
                    }
                }
            }
        });
    }

    private void cardGenerator(String pID, String pname, int pquant, double pprice){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.item_card, null);
        id = (TextView)view.findViewById(R.id.listAllID2);
        name = (TextView)view.findViewById(R.id.listAllName2);
        quantity = (TextView)view.findViewById(R.id.listAllQuantity2);
        price = (TextView)view.findViewById(R.id.listAllPrice2);
        id.setText(pID);
        name.setText(pname);
        quantity.setText(Integer.toString(pquant));
        price.setText(Double.toString(pprice));
        //System.out.println("Card");
        parentLayout.addView(view);
    }

    public FirebaseFirestore createInstance(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        return db;
    }

    public void onCardClick(View view){
        id = view.findViewById(R.id.listAllID2);
        String idText = id.getText().toString();
        //System.out.println(idText + " clicked");
        container.setBarcodeString(idText);
        Intent intent = new Intent(this, UpdateItemActivity.class);
        startActivity(intent);
    }

}
