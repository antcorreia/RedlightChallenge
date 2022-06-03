package com.redlight.challenge.forms;

import com.redlight.challenge.data.Drink;

public class FormCocktail {
    private String name;
    private int number_of_drinks;
    private String drinks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_of_drinks() {
        return number_of_drinks;
    }

    public void setNumber_of_drinks(int number_of_drinks) {
        this.number_of_drinks = number_of_drinks;
    }

    public String getDrinks() {
        return drinks;
    }

    public void setDrinks(String drinks) {
        this.drinks = drinks;
    }
}
