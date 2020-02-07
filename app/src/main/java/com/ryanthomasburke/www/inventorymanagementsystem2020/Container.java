package com.ryanthomasburke.www.inventorymanagementsystem2020;

public class Container {

    private String barcode;
    private String company;

    public Container(){
        this.barcode = "";
        this.company = "";
    }

    public void setBarcodeString(String string){
        this.barcode = string;
    }

    public String getBarcodeString(){
        return barcode;
    }

    public void setCompany(String string) {
        this.company = string;
    }

    public String getCompany() {
        return company;
    }

}
