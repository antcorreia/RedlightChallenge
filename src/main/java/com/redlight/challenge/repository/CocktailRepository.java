package com.redlight.challenge.repository;

import com.redlight.challenge.data.Cocktail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Integer> {

}
