package com.example.admin.medhub;

import org.parceler.Parcel;

/**
 * Created by Admin on 26-03-2017.
 */

@Parcel
public class Data {

    public String str_tradeName, str_price, str_manufacturerName, str_unit, str_quantity, str_type;

    public String getStr_tradeName() {
        return str_tradeName;
    }

    public void setStr_tradeName(String str_tradeName) {
        this.str_tradeName = str_tradeName;
    }

    public String getStr_price() {
        return str_price;
    }

    public void setStr_price(String str_price) {
        this.str_price = str_price;
    }

    public String getStr_manufacturerName() {
        return str_manufacturerName;
    }

    public void setStr_manufacturerName(String str_manufacturerName) {
        this.str_manufacturerName = str_manufacturerName;
    }

    public String getStr_unit() {
        return str_unit;
    }

    public void setStr_unit(String str_unit) {
        this.str_unit = str_unit;
    }

    public String getStr_quantity() {
        return str_quantity;
    }

    public void setStr_quantity(String str_quantity) {
        this.str_quantity = str_quantity;
    }

    public String getStr_type() {
        return str_type;
    }

    public void setStr_type(String str_type) {
        this.str_type = str_type;
    }

    public Data() {
    }

    public Data(String tradeName, String price, String manufacturerName, String unit, String quantity, String type) {
        this.str_manufacturerName = manufacturerName;
        this.str_price = price;
        this.str_quantity = quantity;
        this.str_tradeName = tradeName;
        this.str_unit = unit;
        this.str_type= type;
    }
}
