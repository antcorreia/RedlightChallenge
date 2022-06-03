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
}
