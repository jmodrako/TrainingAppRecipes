package com.recipes.operations;

public class Recipe {

    private String title;
    private String subTitle;
    private String recipeDescription;

    public Recipe(String title, String subTitle, String description) {
        this.title = title;
        this.subTitle = subTitle;
        recipeDescription =description;
    }

    public Recipe() {

    }

    public String getTitle(){
        return title;
    }
    public String getSubTitle(){
        return subTitle;
    }
    public String getRecipeDescription(){
        return recipeDescription;
    }

}
