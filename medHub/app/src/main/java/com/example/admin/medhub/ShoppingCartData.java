package com.example.admin.medhub;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Admin on 04-04-2017.
 */

@IgnoreExtraProperties
public class ShoppingCartData {

    private String name;
    private String url;
    private String manufacturer;
    private String price;
    private String quantity;
    private String unit;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerID() {
        return dealerID;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    private String dealerName;
    private String dealerID;

    public ShoppingCartData() {
    }

    public ShoppingCartData(String name, String url, String manufacturer, String price, String quantity, String unit, String type, String dealerName, String dealerID) {
        this.name = name;
        this.url = url;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.type= type;
        this.dealerName= dealerName;
        this.dealerID= dealerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
