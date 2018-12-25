package com.example.android.firestone;

public class Item {
    private String title;
    private String description;

    public Item() {
        /*No argument constructor is needed for Firestore!*/
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
