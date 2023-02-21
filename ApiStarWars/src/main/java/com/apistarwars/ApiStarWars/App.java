package com.apistarwars.ApiStarWars;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Scanner;

import com.apistarwars.ApiStarWars.entidades.People;

public class App {
	public static void main(String[] args) throws Exception {
		int opcion;
		Scanner sc = new Scanner(System.in);
		ArrayList<People> personajes = new ArrayList<People>();
		Boolean contiene = false;
		try {
			personajes = Management.cargarPersonajes();
		} catch (EOFException e) {

		}
		do {
			Management.Menu();
			opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("Introduzca el código de la película");
				Management.conversor(sc.nextInt());
				break;
			case 2:
				System.out.println("Introduzca el código del personaje");
				People personaje = Management.anadirPeople(sc.nextInt());
				if (personajes == null) {
					personajes = new ArrayList<People>();
				}
				for(People p: personajes){
					if(personaje.getName().equals(p.getName())) {
						contiene = true;
					}
				}
				if(!contiene) {
					personajes.add(personaje);
				}
				else {
					System.out.println("Este personaje ya está en la lista");
				}
				break;
			case 3:
				Management.serializarListaObjetos("C:\\Users\\alex_\\Documents", "personajes.dat", personajes);
				break;
			case 4:
				System.out.println("Introduzca el código del personaje");
				for(String species: Management.mostrarEspecies(sc.nextInt())) {
					System.out.println(species);
				}
				break;
			case 5:
				Management.mostrarXML();
				break;
			case 6:
				System.out.println("Saliendo...");
				Thread.sleep(2000);
				break;
			}
		} while (opcion != 6);
	}
}
