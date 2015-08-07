package com.recipes.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recipe extends RealmObject {

	@PrimaryKey private int recipeId;
	private String recipeTitle;
	private String recipeSubtitle;
	private String recipeDescription;
	private String recipeImageUrl;

	public Recipe(String recipeTitle, String recipeSubtitle, String recipeDescription,
				  String recipeImageUrl) {
		this.recipeTitle = recipeTitle;
		this.recipeSubtitle = recipeSubtitle;
		this.recipeDescription = recipeDescription;
		this.recipeImageUrl = recipeImageUrl;
	}

	public Recipe() {
		// Nothing.
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
}
