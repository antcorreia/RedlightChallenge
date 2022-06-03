package com.redlight.challenge.repository;

import com.redlight.challenge.data.DrinkCocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkCocktailRepository extends CrudRepository<DrinkCocktail, Integer> {

}
