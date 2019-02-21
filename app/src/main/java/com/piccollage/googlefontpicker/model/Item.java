package com.piccollage.googlefontpicker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("family")
    @Expose
    private String family;
    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("files")
    @Expose
    private Files files;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }
}
