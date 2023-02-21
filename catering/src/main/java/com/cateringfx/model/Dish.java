package com.cateringfx.model;

import java.util.ArrayList;
import java.util.List;
//Class Dish in which we will store all the relevant data to dishes
public class Dish implements Nameable, MenuElement{
    String name;
    String description;
    List<Ingredient> ingredientList;

    //Constructor in which we initialize the dish with an empty Ingredient list
    public Dish(String name, String description) {
        this.name = name;
        this.description = description;
        ingredientList = new ArrayList<>();
    }
    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public double getCalories() {
        double calories = 0;
        for (Ingredient ingredient : ingredientList){
            calories += ingredient.getCalories();
        }
        return calories;
    }

    @Override
    public double getCarbohydrates() {
        double carbohydrates = 0;
             for (Ingredient ingredient : ingredientList) {
                 carbohydrates += ingredient.getCarbohydrates();
             }
        return carbohydrates;
    }

    @Override
    public double getFat() {
        double fat = 0;
        for (Ingredient ingredient : ingredientList) {
            fat += ingredient.getFat();
        }
        return fat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
    //we add ingredients to the list here...
    public void addIngredient(Ingredient ingredient){
        this.ingredientList.add(ingredient);
    }
    //toString method that returns the format required for the txt file
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getName() + ";" + getDescription()+";");
        for (Ingredient ingredient: ingredientList) {
            result.append(ingredient.toString());
        }
        return result.toString();
    }
}
