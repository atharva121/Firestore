package com.example.android.firestone;

import com.google.firebase.firestore.Exclude;

public class Item {
    private String id;
    private String title;
    private String description;

    public Item() {
        /*No argument constructor is needed for Firestore!*/
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
