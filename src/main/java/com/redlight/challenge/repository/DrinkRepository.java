package com.redlight.challenge.repository;

import com.redlight.challenge.data.Drink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Integer> {

    @Query("SELECT d.name FROM Drink d WHERE d.name=?1")
    public Optional<String> checkName(String name);
}
