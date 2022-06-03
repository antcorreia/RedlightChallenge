package com.redlight.challenge.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cocktail_id;
    private String name;

    public Cocktail() {
    }

    public Cocktail(int cocktail_id, String name) {
        this.cocktail_id = cocktail_id;
        this.name = name;
    }

    public int getCocktail_id() {
        return cocktail_id;
    }

    public void setCocktail_id(int cocktail_id) {
        this.cocktail_id = cocktail_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
