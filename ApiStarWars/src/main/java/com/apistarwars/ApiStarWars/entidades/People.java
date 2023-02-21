package com.apistarwars.ApiStarWars.entidades;

import java.util.List;

public class People {
	private String name;
	private int height;
	private int mass;
	private String hair_color;
	private String skin_color;
	private String eye_color;
	private String birth_year;
	private String gender;
	private String homeworld;
	private List<String> films;
	private List<String> species;
	private List<String> vehicles;
	private List<String> starships;
	private String created;
	private String edited;
	private String url;
	public String getName() {
		return name;
	}
	public int getHeight() {
		return height;
	}
	public int getMass() {
		return mass;
	}
	public String getHair_color() {
		return hair_color;
	}
	public String getSkin_color() {
		return skin_color;
	}
	public String getEye_color() {
		return eye_color;
	}
	public String getBirth_year() {
		return birth_year;
	}
	public String getGender() {
		return gender;
	}
	public String getHomeworld() {
		return homeworld;
	}
	public List<String> getFilms() {
		return films;
	}
	public List<String> getSpecies() {
		return species;
	}
	public List<String> getVehicles() {
		return vehicles;
	}
	public List<String> getStarships() {
		return starships;
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
