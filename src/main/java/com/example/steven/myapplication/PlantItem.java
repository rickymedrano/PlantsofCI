package com.example.steven.myapplication;

public class PlantItem {
    private int plantItemId;
    private String plantItemName;
    private String photoID;

    public PlantItem(int itemId, String itemName, String photoID) {
        this.plantItemId = itemId;
        this.plantItemName = itemName;
        this.photoID = photoID;
    }
    public int getItemId() {
        return plantItemId;
    }

    public String getItemName() {
        return plantItemName;
    }

    public String getPhotoID() {return photoID;}

}
