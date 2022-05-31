package com.redlight.challenge.services;

import com.redlight.challenge.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CocktailService {

    @Autowired
    CocktailRepository cocktailRepository;
}
