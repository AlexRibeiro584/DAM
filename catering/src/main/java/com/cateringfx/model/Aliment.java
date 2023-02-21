package com.cateringfx.model;

public class Aliment implements Nameable, MenuElement {
    private String name;
    private String description;
    private String frequency;
    private boolean nuts;
    private boolean milk;
    private boolean egg;
    private boolean gluten;
    private double calories;
    private double fat;
    private double carbohydrates;

    public Aliment(String name, String description, String frequency,
                   boolean nuts, boolean milk, boolean egg, boolean gluten,
                   double calories, double fat, double carbohydrates)
                    {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.nuts = nuts;
        this.milk = milk;
        this.egg = egg;
        this.gluten = gluten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public boolean hasNuts() {
        return nuts;
    }

    public void setNuts(boolean nuts) {
        this.nuts = nuts;
    }

    public boolean hasMilk() {
        return milk;
    }

    public void setMilk(boolean milk) {
        this.milk = milk;
    }

    public boolean hasEgg() {
        return egg;
    }

    public void setEgg(boolean egg) {
        this.egg = egg;
    }

    public boolean hasGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }
    @Override
    public String toString() {
        return getName()+";"+getDescription()+";"
                +frequency+";"+hasGluten()+";"+hasMilk()+";"
                +hasNuts()+";"+hasEgg()+";"+calories+";"+carbohydrates+";"+fat+";";
    }
}
