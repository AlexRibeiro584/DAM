package com.apistarwars.ApiStarWars.entidades;

import java.util.List;

public class Species {
private String name;
private String classification;
private String designation;
private String average_height;
private String skin_colors;
private String hair_colors;
private String eye_colors;
private String average_lifespan;
private String homeworld;
private String language;
private List<String> people;
private List<String> films;
private String created;
private String edited;
private String url;
public String getName() {
	return name;
}
public String getClassification() {
	return classification;
}
public String getDesignation() {
	return designation;
}
public String getAverage_height() {
	return average_height;
}
public String getSkin_colors() {
	return skin_colors;
}
public String getHair_colors() {
	return hair_colors;
}
public String getEye_colors() {
	return eye_colors;
}
public String getAverage_lifespan() {
	return average_lifespan;
}
public String getHomeworld() {
	return homeworld;
}
public String getLanguage() {
	return language;
}
public List<String> getPeople() {
	return people;
}
public List<String> getFilms() {
	return films;
}
public String getCreated() {
	return created;
}
public String getEdited() {
	return edited;
}
public String getUrl() {
	return url;
}

}
