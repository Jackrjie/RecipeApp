package com.tim.recipeapp.model;

import java.util.UUID;

public class Recipe {
    private UUID mId;
    private String mType;
    private String mTitle;
//    private byte[] mImage;
    private String mIngredient;
    private String mStep;

    public Recipe(){ this(UUID.randomUUID());}

    public Recipe(UUID id){
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

//    public byte[] getImage() {
//        return mImage;
//    }
//
//    public void setImage(byte[] image) {
//        mImage = image;
//    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String ingredient) {
        mIngredient = ingredient;
    }

    public String getStep() {
        return mStep;
    }

    public void setStep(String step) {
        mStep = step;
    }

    public String getPhotoFileName(){ return "IMG_" + getId().toString() + ".jpg"; }

}
