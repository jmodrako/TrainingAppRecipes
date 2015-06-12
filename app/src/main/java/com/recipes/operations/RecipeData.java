package com.recipes.operations;


import java.util.ArrayList;
import java.util.List;

public class RecipeData {
    private List<Recipe> recipes= new ArrayList<Recipe>();

    public List<Recipe> getRecipes(){
        Recipe x= new Recipe("cos","cos","bardzo bardzo dlugi opis1");
        Recipe y= new Recipe("cos","cos","bardzo bardzo dlugi opis2");
        recipes.add(x);
        recipes.add(y);
        return  recipes;
    }
    public Recipe getRecipeData(int position){
        return recipes.get(position);
    }
}
