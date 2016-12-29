package com.epay.example.tmq.megapayexamplerequest.megapay.entity;

/**
 * ItemKV
 * Object dang Key-Value, chua thong tin query cua Url trong request
 */
public class ItemKV {
    private String key;
    private String value;

    public ItemKV(String key, String value){
        setKV(key, value);
    }

    public void setKV(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
