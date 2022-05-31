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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cocktail_id;
    private String name;
}
