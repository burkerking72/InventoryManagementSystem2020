package com.ryanthomasburke.www.inventorymanagementsystem2020.Activities;

public class Item {

    public String pId, pName;
    public int pQuantity;
    public double pPrice;

    public Item(){

    }

    public Item(String id, String name, int quantity, double price){
        this.pId = id; this.pName = name; this.pQuantity = quantity; this.pPrice = price;
    }

}
