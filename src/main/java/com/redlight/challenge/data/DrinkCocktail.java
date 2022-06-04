package com.redlight.challenge.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@IdClass(DrinkCocktailId.class)
public class DrinkCocktail implements Serializable{
    @Id
    private int drink_id;

    @Id
    private int cocktail_id;

    private int quantity;

    public DrinkCocktail() {
    }

    public DrinkCocktail(int drink_id, int cocktail_id) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
