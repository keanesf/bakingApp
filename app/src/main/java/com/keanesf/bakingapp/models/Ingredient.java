package com.keanesf.bakingapp.models;


import java.io.Serializable;


public class Ingredient implements Serializable {

    private float quantity;
    private String measure;
    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
