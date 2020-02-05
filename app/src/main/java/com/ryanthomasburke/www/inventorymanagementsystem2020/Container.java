package com.ryanthomasburke.www.inventorymanagementsystem2020;

public class Container {

    private String barcode;

    public Container(){
        this.barcode = "";
    }

    public void setBarcodeString(String string){
        this.barcode = string;
    }

    public String getBarcodeString(){
        return barcode;
    }

}
