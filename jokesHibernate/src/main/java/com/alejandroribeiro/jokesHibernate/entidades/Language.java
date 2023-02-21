// default package
// Generated 10 ene 2023 11:45:25 by Hibernate Tools 4.3.6.Final
package com.alejandroribeiro.jokesHibernate.entidades;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@NamedQuery(
		name="get_languages_without_jokes",
		query= "select language from Language where id not in "
				+ "(select language from Jokes where language is not null)")
@NamedQuery(name="get_languages",
			query="select language from Language")
@Entity
@Table(name = "language")
public class Language implements java.io.Serializable, EntidadBD {

	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String language;
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Language() {
	}

	public Language(int id) {
		this.id = id;
	}

	public Language(int id, String code, String language, Set<Jokes> jokeses) {
		this.id = id;
		this.code = code;
		this.language = language;
		this.jokeses = jokeses;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "code", length = 2)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}

	@Override
	public String toString() {
		return "Id: " + id + "\nCode:" + code + "\nLanguage: " + language + "\nJokes: " + jokeses.size();
	}

	@Override
	public String valoresCampos() {
		// TODO Auto-generated method stub
		return id+code+language+jokeses.size();
	}	
}
