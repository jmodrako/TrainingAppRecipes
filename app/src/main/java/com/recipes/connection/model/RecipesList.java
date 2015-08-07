package com.recipes.connection.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"recipes"})
public class RecipesList {

	@JsonProperty("recipes") private List<Recipe> recipes = new ArrayList<Recipe>();

	@JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("recipes") public List<Recipe> getRecipes() {
		return recipes;
	}

	@JsonProperty("recipes") public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	@JsonAnyGetter public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}