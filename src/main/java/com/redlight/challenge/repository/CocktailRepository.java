package com.redlight.challenge.repository;

import com.redlight.challenge.data.Cocktail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Integer> {
    @Query("SELECT c.name FROM Cocktail c WHERE c.name=?1")
    Optional<String> checkName(String name);

    @Query("SELECT c.cocktail_id FROM Cocktail c WHERE c.name=?1")
    int getCocktailIdByName(String name);

    @Query("SELECT c FROM Cocktail c WHERE c.cocktail_id=?1")
    Cocktail getCocktailById(int cocktail_id);

    @Query("SELECT c.name FROM Cocktail c WHERE c.cocktail_id=?1")
    String getCocktailNameById(int cocktail_id);
}
