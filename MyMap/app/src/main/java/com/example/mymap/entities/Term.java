package com.example.mymap.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Term implements Serializable {

    @SerializedName("offset")
    private int offset;
    @SerializedName("value")
    private String value;

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}