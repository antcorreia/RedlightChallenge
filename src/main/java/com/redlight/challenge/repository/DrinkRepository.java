package com.redlight.challenge.repository;

import com.redlight.challenge.data.Drink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Integer> {

    @Query("SELECT d.name FROM Drink d WHERE d.name=?1")
    Optional<String> checkName(String name);

    @Query("SELECT d.name FROM Drink d WHERE d.drink_id=?1")
    String getDrinkNameById(int drink_id);

    @Query("SELECT COUNT(d) FROM Drink d")
    int getDrinkCount();

    @Query("SELECT d.name FROM Drink d")
    String[] getAllDrinkNames();

    @Query("SELECT d.drink_id FROM Drink d WHERE d.name=?1")
    int getDrinkIdByName(String name);
}
