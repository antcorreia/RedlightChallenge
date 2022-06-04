package com.redlight.challenge.repository;

import com.redlight.challenge.data.DrinkCocktail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DrinkCocktailRepository extends CrudRepository<DrinkCocktail, Integer> {

    @Query("SELECT COUNT(dc) FROM DrinkCocktail dc WHERE dc.cocktail_id=?1")
    int getQuantityById(int cocktail_id);

    @Query("SELECT dc.drink_id, dc.quantity FROM DrinkCocktail dc WHERE dc.cocktail_id=?1")
    List<int[]> getPairsById(int cocktail_id);

    @Query("SELECT dc.cocktail_id FROM DrinkCocktail dc WHERE dc.drink_id=?1")
    int[] getCocktailIdsByDrinkId(int drink_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM DrinkCocktail dc WHERE dc.cocktail_id=?1")
    void deleteAllByCocktailId(int cocktail_id);

    @Modifying
    @Transactional
    @Query("DELETE FROM DrinkCocktail dc WHERE dc.drink_id=?1")
    void deleteAllByDrinkId(int cocktail_id);
}
