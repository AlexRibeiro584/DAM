package com.apistarwars.ApiStarWars;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.apistarwars.ApiStarWars.entidades.Films;
import com.apistarwars.ApiStarWars.entidades.People;
import com.apistarwars.ApiStarWars.entidades.Species;
import com.apistarwars.ApiStarWars.utilidades.JsonUtils;
import com.apistarwars.ApiStarWars.utilidades.XmlUtils;

public class Management {
	public static void Menu() {
		System.out.println("1. Conversor");
		System.out.println("2. Añadir personaje");
		System.out.println("3. Guardar personajes");
		System.out.println("4. Mostrar especie");
		System.out.println("5. Mostrar datos XML");
		System.out.println("6. Salir");
	}

	public static void conversor(int codigo) throws Exception {
		String url = "https://swapi.dev/api/films/" + codigo + "/?format=json";
		XmlUtils.filmToXML(JsonUtils.devolverObjetoGsonGenerico(url, Films.class));
	}

	public static ArrayList<People> cargarPersonajes() throws EOFException {
		return desSerializarObjeto("C:\\Users\\alex_\\Documents", "personajes.dat");
	}

	public static People anadirPeople(int codigo) {
		People persona = JsonUtils
				.devolverObjetoGsonGenerico("https://swapi.dev/api/people/" + codigo + "/?format=json", People.class);
		return persona;
	}

	public static ArrayList<String> mostrarEspecies(int codigo) {
		People personaje = JsonUtils
				.devolverObjetoGsonGenerico("https://swapi.dev/api/people/" + codigo + "/?format=json", People.class);
		ArrayList<String> nombres = new ArrayList<String>();
		for (String e: personaje.getSpecies()) {
			Species especie = JsonUtils
					.devolverObjetoGsonGenerico(e+"?format=json",Species.class);
			nombres.add(especie.getName());
		}
		return nombres;
	}
	public static void mostrarXML() {
		XmlUtils.mostrarXmlDom("C:\\Users\\alex_\\Documents\\resultado.xml");
	}
	public static <T> ArrayList<T> desSerializarObjeto(String directorio, String nombreArchivo) {

		File fichero = new File(directorio + "/" + nombreArchivo);
		T aux = null;
		ArrayList<T> listaAux = new ArrayList<T>();
		try {
			FileInputStream ficheroSalida = new FileInputStream(fichero);
			ObjectInputStream ficheroObjetos = new ObjectInputStream(ficheroSalida);
			while (ficheroObjetos.available() > 0) {
				aux = (T) ficheroObjetos.readObject();
				listaAux.add(aux);
			}
			ficheroObjetos.close();
			return listaAux;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			System.err.println("Fichero vacío");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> boolean serializarListaObjetos(String directorio, String nombreArchivo, ArrayList<T> objetos) {

		File fichero = new File(directorio + "/" + nombreArchivo);
		try {
			ObjectOutputStream ficheroObjetos = new ObjectOutputStream(new FileOutputStream(fichero));
			for (T objeto : objetos) {
				ficheroObjetos.writeObject(objeto);
			}
			ficheroObjetos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
