package com.example.flatbooking.models;

import java.util.List;

public class Specification {
    private String _id;
    private List<String> name;
    private int sequence_number;
    private int unique_id;
    private List<Property> list;
    private int max_range;
    private int range;
    private int type;
    private boolean is_required;
    private boolean isParentAssociate;

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

    public int getSequence_number() {
        return sequence_number;
    }

    public void setSequence_number(int sequence_number) {
        this.sequence_number = sequence_number;
    }

    public int getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(int unique_id) {
        this.unique_id = unique_id;
    }

    public List<Property> getList() {
        return list;
    }

    public void setList(List<Property> list) {
        this.list = list;
    }

    public int getMax_range() {
        return max_range;
    }

    public void setMax_range(int max_range) {
        this.max_range = max_range;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIs_required() {
        return is_required;
    }

    public void setIs_required(boolean is_required) {
        this.is_required = is_required;
    }

    public boolean isIsParentAssociate() {
        return isParentAssociate;
    }

    public void setIsParentAssociate(boolean isParentAssociate) {
        this.isParentAssociate = isParentAssociate;
    }
}
