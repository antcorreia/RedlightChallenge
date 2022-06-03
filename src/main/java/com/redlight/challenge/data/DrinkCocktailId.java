package com.redlight.challenge.data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DrinkCocktailId implements Serializable {
    private int drink_id;
    private int cocktail_id;

    public DrinkCocktailId() {
    }

    public DrinkCocktailId(int drink_id, int cocktail_id) {
        this.drink_id = drink_id;
        this.cocktail_id = cocktail_id;
    }

    public int getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }

    public int getCocktail_id() {
        return cocktail_id;
    }

    public void setCocktail_id(int cocktail_id) {
        this.cocktail_id = cocktail_id;
    }
}
