// default package
// Generated 10 ene 2023 11:45:25 by Hibernate Tools 4.3.6.Final
package com.alejandroribeiro.jokesHibernate.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Jokes generated by hbm2java
 */
@Entity
@Table(name = "jokes")
public class Jokes implements java.io.Serializable, EntidadBD{

	private static final long serialVersionUID = 1L;
	private int id;
	private Categories categories;
	private Language language;
	private Types types;
	private String text1;
	private String text2;
	private Set<Flags> flagses = new HashSet<Flags>(0);

	public Jokes() {
	}

	public Jokes(int id) {
		this.id = id;
	}

	public Jokes(int id, Categories categories, Language language, Types types, String text1, String text2,
			Set<Flags> flagses) {
		this.id = id;
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagses = flagses;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	public Types getTypes() {
		return this.types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	@Column(name = "text1")
	public String getText1() {
		return this.text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	@Column(name = "text2")
	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "jokes_flags", joinColumns = {
			@JoinColumn(name = "joke_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "flag_id", nullable = false, updatable = false) })
	public Set<Flags> getFlagses() {
		return this.flagses;
	}

	public void setFlagses(Set<Flags> flagses) {
		this.flagses = flagses;
	}
	@Override
	public String toString() throws NullPointerException {
		String returnableString = "<------Joke------>\nId: " + this.id;
		if(this.language != null) {
			returnableString += " / Lenguaje: " + this.language.getLanguage();
		}
		if(this.categories != null) {
			returnableString += " / Categoría: " + this.categories.getCategory();
		}
		if(this.types != null) {
			returnableString += " / Tipo: " + this.types.getType();
			if(this.types.getType().equals("twopart") && this.text1 != null) {
				returnableString += " / Setup: " + this.text1;
				if(this.text2 != null) {
					returnableString += " / Delivery: " + this.text2;
				}
			}
			else if (this.text1 != null) {
				returnableString += " /\nTexto: " + this.text1;
			}
		}
		returnableString += "\n--------\nNúmero de flags: " + this.flagses.size();
		List<Flags> flagsList = new ArrayList<>(this.flagses);
		for(int i = 0; i < flagsList.size(); i++) {
			returnableString +="\n-----------\n"+ flagsList.get(i).toString();
		}
		return returnableString;
	}
	@Override
	public String valoresCampos() {
		// TODO Auto-generated method stub
		String returnableString = ""+this.id;
		if(this.language != null) {
			returnableString += this.language.getLanguage();
		}
		if(this.categories != null) {
			returnableString += this.categories.getCategory();
		}
		if(this.types != null) {
			returnableString += this.types.getType();
			if(this.types.getType().equals("twopart") && this.text1 != null) {
				returnableString += this.text1;
				if(this.text2 != null) {
					returnableString += this.text2;
				}
			}
			else if (this.text1 != null) {
				returnableString +=this.text1;
			}
		}
		returnableString +=this.flagses.size();
		//Como solo queremos los campos del set tenemos que instanciar una lista de flags
		//De esta manera podemos acceder al método de la interfaz para obtener únicamente los valores
		//Esto previene que se busque la palabra jokes en una joke y te devuelva el toString de las flags
		List<Flags> flagsList = new ArrayList<>(this.flagses);
		for(int i = 0; i < flagsList.size(); i++) {
			returnableString += flagsList.get(i).valoresCampos();
		}
		return returnableString;
	}
}