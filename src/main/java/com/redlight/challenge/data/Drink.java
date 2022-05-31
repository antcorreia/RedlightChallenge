package com.redlight.challenge.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int drink_id;
    private String name;
    private int quantity;
    private String image_url;

    public Drink() {
    }

    public Drink(int drink_id, String name, int quantity, String image_url) {
        this.drink_id = drink_id;
        this.name = name;
        this.quantity = quantity;
        this.image_url = image_url;
    }

    public int getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
