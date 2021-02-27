package com.jun.seeyouagain.repository;

import com.jun.seeyouagain.common.model.Ingredient;

import java.util.List;

public interface IngredientRepositoryService {
    boolean save(Ingredient ingredient);
    Ingredient findById(String id);
    List<Ingredient> findAll();
}
