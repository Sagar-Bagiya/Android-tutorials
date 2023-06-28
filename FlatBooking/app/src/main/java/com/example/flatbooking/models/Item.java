package com.example.flatbooking.models;

import java.util.List;

public class Item {
    private String _id;
    private List<String> name;
    private int price;
    private List<Integer> item_taxes;
    private List<Specification> specifications;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getItem_taxes() {
        return item_taxes;
    }

    public void setItem_taxes(List<Integer> item_taxes) {
        this.item_taxes = item_taxes;
    }

    public List<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<Specification> specifications) {
        this.specifications = specifications;
    }
}

