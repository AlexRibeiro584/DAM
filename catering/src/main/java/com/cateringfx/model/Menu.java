package com.cateringfx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    LocalDate date;
    List<MenuElement> elements;

    public Menu(LocalDate date) {
        this.date = date;
        this.elements = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<MenuElement> getElements() {
        return elements;
    }
    public void addNewElement(MenuElement newMenuElement){
        elements.add(newMenuElement);
    }


    public void setElements(List<MenuElement> elements) {
        this.elements = elements;
    }
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder result= new StringBuilder(date.format(formatter)+";");
        for (MenuElement m: elements) {
            result.append(m.toString());
        }
        return result.toString();
    }
}
