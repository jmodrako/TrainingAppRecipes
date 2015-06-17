package com.recipes.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RecipeData extends RealmObject{

    @PrimaryKey
    private int recipeId;
    private String recipeTitle;
    private String recipeSubtitle;
    private String recipeDescription;
    private String recipeImageUrl;
    private String recipeThumbUrl;

    public RecipeData(String recipeTitle, String recipeSubtitle, String recipeDescription, String recipeImageUrl,
                      String recipeThumbUrl) {
        this.recipeTitle = recipeTitle;
        this.recipeSubtitle = recipeSubtitle;
        this.recipeDescription = recipeDescription;
        this.recipeImageUrl = recipeImageUrl;
        this.recipeThumbUrl = recipeThumbUrl;
    }

    public RecipeData() {

    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeSubtitle() {
        return recipeSubtitle;
    }

    public void setRecipeSubtitle(String recipeSubtitle) {
        this.recipeSubtitle = recipeSubtitle;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getRecipeImageUrl() {
        return recipeImageUrl;
    }

    public void setRecipeImageUrl(String recipeImageUrl) {
        this.recipeImageUrl = recipeImageUrl;
    }

    public String getRecipeThumbUrl() {
        return recipeThumbUrl;
    }

    public void setRecipeThumbUrl(String recipeThumbUrl) {
        this.recipeThumbUrl = recipeThumbUrl;
    }
}
