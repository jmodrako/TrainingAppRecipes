package com.recipes.connection.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"description",
		"id",
		"imageUrl",
		"subtitle",
		"title"
})
public class Recipe {

	@JsonProperty private String description;
	@JsonProperty private Integer id;
	@JsonProperty private String imageUrl;
	@JsonProperty private String subtitle;
	@JsonProperty private String title;
	@JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("description") public String getDescription() {
		return description;
	}

	@JsonProperty("description") public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("id") public Integer getId() {
		return id;
	}

	@JsonProperty("id") public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("imageUrl") public String getImageUrl() {
		return imageUrl;
	}

	@JsonProperty("imageUrl") public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@JsonProperty("subtitle") public String getSubtitle() {
		return subtitle;
	}

	@JsonProperty("subtitle") public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@JsonProperty("title") public String getTitle() {
		return title;
	}

	@JsonProperty("title") public void setTitle(String title) {
		this.title = title;
	}

	@JsonAnyGetter public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}