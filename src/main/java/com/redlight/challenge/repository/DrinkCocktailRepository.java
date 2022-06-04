package com.redlight.challenge.repository;

import com.redlight.challenge.data.DrinkCocktail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkCocktailRepository extends CrudRepository<DrinkCocktail, Integer> {

    @Query("SELECT COUNT(dc) FROM DrinkCocktail dc WHERE dc.cocktail_id=?1")
    int getQuantityById(int cocktail_id);

    @Query("SELECT dc.drink_id, dc.quantity FROM DrinkCocktail dc WHERE dc.cocktail_id=?1")
    List<int[]> getPairsById(int cocktail_id);
}
