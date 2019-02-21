package com.piccollage.googlefontpicker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleWebFont {

    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
