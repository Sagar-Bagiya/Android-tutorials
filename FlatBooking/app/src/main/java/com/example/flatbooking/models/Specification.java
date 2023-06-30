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

    private String modifierId;

    private String modifierGroupId,modifierGroupName,modifierName,isAssociated,user_can_add_specification_quantity;


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

    public boolean isParentAssociate() {
        return isParentAssociate;
    }

    public void setParentAssociate(boolean parentAssociate) {
        isParentAssociate = parentAssociate;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierGroupId() {
        return modifierGroupId;
    }

    public void setModifierGroupId(String modifierGroupId) {
        this.modifierGroupId = modifierGroupId;
    }

    public String getModifierGroupName() {
        return modifierGroupName;
    }

    public void setModifierGroupName(String modifierGroupName) {
        this.modifierGroupName = modifierGroupName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getIsAssociated() {
        return isAssociated;
    }

    public void setIsAssociated(String isAssociated) {
        this.isAssociated = isAssociated;
    }

    public String getUser_can_add_specification_quantity() {
        return user_can_add_specification_quantity;
    }

    public void setUser_can_add_specification_quantity(String user_can_add_specification_quantity) {
        this.user_can_add_specification_quantity = user_can_add_specification_quantity;
    }
}
