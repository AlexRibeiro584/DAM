package com.alejandroribeiro.jokesHibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import com.alejandroribeiro.jokesHibernate.entidades.Categories;
import com.alejandroribeiro.jokesHibernate.entidades.EntidadBD;
import com.alejandroribeiro.jokesHibernate.entidades.Flags;
import com.alejandroribeiro.jokesHibernate.entidades.Jokes;
import com.alejandroribeiro.jokesHibernate.entidades.Language;
import com.alejandroribeiro.jokesHibernate.entidades.Types;
import com.alejandroribeiro.jokesHibernate.utilidades.HibernateUtils;
public class App
{
	static Scanner sc = new Scanner(System.in);
	/**
	 * Ejemplo de como acceder a la lista de elementos de una clase
	 * En el ejemplo categories y recorrerla
	 */
	public static List<Integer> listarObjetos(Class<EntidadBD> clase) {
		HibernateUtils.abrirConexion();
		List<Integer> ids = new ArrayList();
		HibernateUtils.devolverListaObjetos(clase)
			.forEach(c->{System.out.println("\n\n\n----------ID: "+c.getId() + "---------- \n\n\n" + c);
			ids.add(c.getId());});
		HibernateUtils.cerrarConexion();
		return ids;
	}

	//Uso de named query para recibir los lenguajes sin jokes
	public static void lenguajesSinJokes() {
		HibernateUtils.abrirConexion();
		List<?> results;
		results = HibernateUtils.devolverListaLenguajes("get_languages_without_jokes");
		System.out.println("Se han encontrado " + results.size() + " lenguajes sin jokes");
		results.forEach(c->System.out.println(c +"\n --------------------------------"));
		HibernateUtils.cerrarConexion();
	}
	public static void jokesSinFlags() throws NullPointerException {
		HibernateUtils.abrirConexion();
		List<Jokes> results;
		results = (List<Jokes>) HibernateUtils.devolverListaObjetos("Jokes");
		for(Jokes joke : results) {
			if(joke.getFlagses().size() == 0) {
				System.out.println(joke.toString());
				System.out.println("----------------------------------------");
			}
		}
		HibernateUtils.cerrarConexion();
	}
	@SuppressWarnings("unchecked")
	public static void borrar(String table, int index) {
		List<Class<?>> clases = new ArrayList<>(Arrays
				.asList(Jokes.class,Categories.class, Language.class, Flags.class));
		List<Integer> ids = listarObjetos((Class<EntidadBD>)clases.get(index));
		HibernateUtils.abrirConexion();
		System.out.println("Elija una fila para borrar");
		List<EntidadBD> filas = HibernateUtils.devolverListaObjetos(clases.get(index));
		int opcion = sc.nextInt();
		sc.nextLine();
		if(index != 0) {
			switch(index) {
			case 1:
				System.out.println("¿De verdad quiere borrar la fila " + opcion + " con " +
					((Categories)(filas.get(ids.indexOf(opcion)))).getJokeses().size() + " filas asociadas? \n1.Sí \n2.No");
				break;
			case 2:
				System.out.println("¿De verdad quiere borrar la fila " + opcion + " con " +
						((Language)(filas.get(ids.indexOf(opcion)))).getJokeses().size() + " filas asociadas? \n1.Sí \n2.No");
				break;
			case 3:
				System.out.println("¿De verdad quiere borrar la fila " + opcion + " con " +
						((Flags)(filas.get(ids.indexOf(opcion)))).getJokeses().size() + " filas asociadas? \n1.Sí \n2.No");
				break;
			default:
					Salir(false);
					break;
			}
		}
		else {
			System.out.println("De verdad quiere borrar la fila " + opcion + " con " +
		((Jokes)(filas.get(ids.indexOf(opcion)))).getFlagses().size() + " filas asociadas? \n1.Sí \n2.No");
		}
		if(sc.nextLine().equals("1")) {
			switch(HibernateUtils.deleteById(clases.get(index), "id="+opcion)) {
			case 1:
				System.out.println("Fila borrada correctamente");
				break;
			case 0:
				System.out.println("Fila no encontrada");
				break;
			default:
				Salir(false);
					break;
			}
		}
		else {
			System.out.println("Borrado cancelado");
			Salir(true);
		}
		HibernateUtils.cerrarConexion();
	}
	@SuppressWarnings("unchecked")
	public static void insertarJoke() {
		Jokes joke = new Jokes();
		Categories category = new Categories();
		Language language = new Language();
		Flags flag = new Flags();
		Set<Flags> flagses = new HashSet<>(0);
		HibernateUtils.abrirConexion();
				List<Categories> categories = (List<Categories>)
				HibernateUtils.devolverListaObjetos("Categories");
				categories.forEach(c->System.out.println(c));
				System.out.println("Elija una categoría por su ID");
				int idCat = sc.nextInt();
				sc.nextLine();
				category = categories.stream().filter(c->c.getId() == idCat).findFirst().get();
				joke.setCategories(category);
				List<Language> languages = (List<Language>)
				HibernateUtils.devolverListaObjetos("Language");
				languages.forEach(c->System.out.println(c));
				System.out.println("Elija un lenguaje por su ID");
				int idLang = sc.nextInt();
				sc.nextLine();
				language = languages.stream().filter(c->c.getId() == idLang).findFirst().get();
				joke.setLanguage(language);
				List<Flags> flags = (List<Flags>)
				HibernateUtils.devolverListaObjetos("Flags");
				flags.forEach(c->System.out.println(c));
				System.out.println("Elija un flag por su ID");
				int idFlag = sc.nextInt();
				sc.nextLine();
				flag = flags.stream().filter(c->c.getId() == idFlag).findFirst().get();
				flagses.add(flag);
				joke.setFlagses(flagses);
				System.out.println("Introduzca el id del joke");
				int idJoke = sc.nextInt();
				sc.nextLine();
				joke.setId(idJoke);
				System.out.println("Introduzca el texto del joke");
				joke.setText1(sc.nextLine());
				System.out.println("Es twopart? \nPulse 1 para confirmar");
				int type = sc.nextInt();
				sc.nextLine();
				if(type == 1) {
					joke.setTypes((Types)HibernateUtils.devolverListaObjetos("Types").get(type));
					System.out.println("Introduzca el delivery");
					joke.setText2(sc.nextLine());
					HibernateUtils.save(joke);
				}
				if(type == 0) {
					joke.setTypes((Types)HibernateUtils.devolverListaObjetos("Types").get(type));
					HibernateUtils.save(joke);
				}
		HibernateUtils.cerrarConexion();
	}
	public static void insertar(int index) {
		HibernateUtils.abrirConexion();
		switch (index) {
			case 1:
				Categories category = new Categories();
				System.out.println("Introduzca el id");
				int idCat = sc.nextInt();
				sc.nextLine();
				category.setId(idCat);
				System.out.println("Introduzca el nombre");
				category.setCategory(sc.nextLine());
				if(HibernateUtils.save(category))
					System.out.println("Categoría insertada correctamente");
				break;
			case 2:
				Language language = new Language();
				System.out.println("Introduzca el id");
				int idLang = sc.nextInt();
				sc.nextLine();
				language.setId(idLang);
				System.out.println("Introduzca el nombre");
				language.setLanguage(sc.nextLine());
				System.out.println("Introduzca el código");
				language.setCode(sc.nextLine());
				if(HibernateUtils.save(language))
					System.out.println("Lenguaje insertado correctamente");
				break;
			case 3:
				Flags flag = new Flags();
				System.out.println("Introduzca el id");
				int idFlag = sc.nextInt();
				sc.nextLine();
				flag.setId(idFlag);
				System.out.println("Introduzca el nombre");
				flag.setFlag(sc.nextLine());
				if(HibernateUtils.save(flag))
					System.out.println("Flag insertada correctamente");
				break;
			default:
				Salir(false);
				break;
		}
		HibernateUtils.cerrarConexion();
	}
	public static void actualizar(String table, int index) {
		List<Class<?>> clases = new ArrayList<>(Arrays
				.asList(Jokes.class,Categories.class, Language.class, Flags.class));
		List<Integer> ids = listarObjetos((Class<EntidadBD>)clases.get(index));
		EntidadBD item = null;
		Class<?> claseItem = null;
		HibernateUtils.abrirConexion();
		System.out.println("Elija una fila para actualizar");
		List<EntidadBD> filas = HibernateUtils.devolverListaObjetos(clases.get(index));
		int opcion = sc.nextInt();
		sc.nextLine();
		if(index != 0) {
			int campo = -1;
			switch(index) {
			case 1:
				claseItem = Categories.class;
				item = (filas.get(ids.indexOf(opcion)));
				System.out.println("Seleccione campo a actualizar:\n1.Id\n2.Category");
				campo = sc.nextInt();
				sc.nextLine();
				if(campo == 1 || campo == 2) {
					if(campo == 1) {
						System.out.println("Introduzca el nuevo id");
						((Categories)item).setId(sc.nextInt());
						sc.nextLine();
					}
					else if(campo == 2) {
						System.out.println("Introduzca el nuevo nombre de categoría");
						((Categories)item).setCategory(sc.nextLine());
						sc.nextLine();
					}
					System.out.println("¿De verdad quiere actualizar la fila " + opcion + " con " +
								((Categories)(filas.get(ids.indexOf(opcion))))
								.getJokeses().size() + " filas asociadas? \n1.Sí \n2.No");
				}
				else {
					Salir(false);
				}
				break;
			case 2:
				claseItem = Language.class;
				item = (filas.get(ids.indexOf(opcion)));
				System.out.println("Seleccione campo a actualizar:\n1.Id\n2.Language");
				campo = sc.nextInt();
				sc.nextLine();
				if(campo == 1 || campo == 2) {
					if(campo == 1) {
						System.out.println("Introduzca el nuevo id");
						((Language)item).setId(sc.nextInt());
						sc.nextLine();
					}
					else if(campo == 2) {
						System.out.println("Introduzca el nuevo nombre de lenguaje");
						((Language)item).setLanguage(sc.nextLine());
					}
					System.out.println("¿De verdad quiere actualizar la fila " + opcion + " con " +
								((Language)(filas.get(ids.indexOf(opcion))))
								.getJokeses().size() + " filas asociadas? \n1.Sí \n2.No");
				}
				else {
					Salir(false);
				}
				break;
			case 3:
				claseItem = Flags.class;
				item = (filas.get(ids.indexOf(opcion)));
				System.out.println("Seleccione campo a actualizar:\n1.Id\n2.Flag");
				campo = sc.nextInt();
				sc.nextLine();
				if(campo == 1 || campo == 2) {
					if(campo == 1) {
						System.out.println("Introduzca el nuevo id");
						((Flags)item).setId(sc.nextInt());
						sc.nextLine();
					}
					else if(campo == 2) {
						System.out.println("Introduzca el nuevo nombre de flag");
						((Flags)item).setFlag(sc.nextLine());
					}
					System.out.println("¿De verdad quiere actualizar la fila " + opcion + " con " +
								((Flags)(filas.get(ids.indexOf(opcion))))
								.getJokeses().size() + " filas asociadas? \n1.Sí \n2.No");
				}
				else {
					Salir(false);
				}
				break;
			default:
					Salir(false);
				break;
			}
		}
		else {
			claseItem = Jokes.class;
			item = (filas.get(ids.indexOf(opcion)));
			System.out.println("Seleccione campo a actualizar:\n1.Id\n2.Contenido del chiste");
			int campo = sc.nextInt();
			sc.nextLine();
			if(campo == 1 || campo == 2) {
				if(campo == 1) {
					System.out.println("Introduzca el nuevo id");
					((Jokes)item).setId(sc.nextInt());
					sc.nextLine();
				}
				else if(campo == 2) {
					if(((Jokes)item).getTypes().toString().equals("single")) {
						System.out.println("Introduzca el nuevo contenido del chiste");
						((Jokes)item).setText1(sc.nextLine());
					}
					else {
						System.out.println("Introduzca el nuevo contenido del setup");
						((Jokes)item).setText1(sc.nextLine());
						System.out.println("Introduzca el nuevo contenido del delivery");
						((Jokes)item).setText2(sc.nextLine());
					}
				}
				System.out.println("¿De verdad quiere actualizar la fila " + opcion + " con " +
							((Jokes)(filas.get(ids.indexOf(opcion))))
							.getFlagses().size() + " filas asociadas? \n1.Sí \n2.No");
			}
			else {
				Salir(false);
			}
		}
		if(sc.nextLine().equals("1")) {
			try {
			if(HibernateUtils.update(claseItem, "id="+opcion, item))
				System.out.println("Fila actualizada correctamente");
				else
				System.out.println("Fila no encontrada");
			}
			catch(Exception e){
				Salir(false);
			}

		}
		else {
			System.out.println("Actualización cancelada");
			Salir(true);
		}
		HibernateUtils.cerrarConexion();
	}
	@SuppressWarnings("unchecked")
	public static void categoriaMasUsada() throws NullPointerException{
		//Esta query no se puede hacer en nativo en
		//versiones anteriores a la 6.1 así que lo he hecho con HQL
		HibernateUtils.abrirConexion();
		List<Categories> results;
		results = (List<Categories>)HibernateUtils.devolverListaObjetos("Categories");
		Optional<Categories> highest = results.stream()
				.max(Comparator.comparingInt(r->r.getJokeses().size()));
		Categories masUsada = highest.get();
		System.out.println("Categoría más usada: \n"+masUsada.toString());
		HibernateUtils.cerrarConexion();
	}
	public static void flagMasUsada() throws NullPointerException{
		HibernateUtils.abrirConexion();
		List<Flags> results;
		results = (List<Flags>) HibernateUtils.devolverListaObjetos("Flags");
		Optional<Flags> highest = results.stream()
				.max(Comparator.comparingInt(r->r.getJokeses().size()));
		Flags masUsada = highest.get();
		System.out.println("Flag más usada: "+masUsada.getFlag() + "\nOcurrencias: "
				+ masUsada.getJokeses().size());
		HibernateUtils.cerrarConexion();
	}
	public static void mostrarMenu() {
		System.out.println("1. Gestión de jokes \n"
				+ "2. Gestión de categories \n"
				+ "3. Gestión de lenguajes \n"
				+ "4. Gestión de flags \n"
				+ "0. Salir");
	}

	public static void mostrarMenu(int index) {
		//En este menú guardamos los nombres de las tablas en un enum para mostrarlo por consola
		enum tables{jokes, categories, lenguajes, flags}
		String table = tables.values()[index].toString();
		System.out.println("1. Consultas \n"
				+ "2. Insertar " + table +" \n"
				+ "3. Modificar " + table +" \n"
				+ "4. Borrar " + table);
		int opcion = sc.nextInt();
		sc.nextLine();
		switch(opcion) {
		/**Si se elige consultas, se entra al menú consultas de su respectiva tabla
		 * De lo contrario, se llama a la función de insert, update o delete 
		 */
			case 1:
				mostrarMenuConsulta(table,index);
				break;
			case 2:
				if(table.equals("jokes"))
					insertarJoke();
				else
					insertar(index);
				break;
			case 3:
				actualizar(table,index);
				break;
			case 4:
				borrar(table,index);
				break;
			default:
				Salir(false);
				break;
		}
	}
	public static void consultaPorTexto(String texto, Class<EntidadBD> tabla) {
		HibernateUtils.abrirConexion();
		//Se ha implementado la interfaz para crear un
		//metodo común que devuelva los campos sin texto decorativo
		//Esto previene que te devuelva todas las jokes si buscas por ejemplo "id"
		//Además permite usar genéricos mediante casteos
		@SuppressWarnings("unchecked")
		List<EntidadBD> results = HibernateUtils.devolverListaObjetos(tabla).stream()
				.filter(o -> o.valoresCampos()
						.toLowerCase().contains(texto.toLowerCase())).toList();
		results.forEach(c -> System.out.println(c + "\n--------------------------------------"));
		HibernateUtils.cerrarConexion();
	}
	public static void mostrarMenuConsulta(String tabla, int index) {
		/**Menú consulta para cada tabla, si se elige la opción 1 se usa una función estática de búsqueda
		 * que controla las diferentes formas según la tabla. Si se elige la opción 2,
		 * comprobamos qué tabla se ha elegido y llamamos a su función específica
		 */
		List<Class<?>> clases = new ArrayList<>(Arrays.asList(Jokes.class,Categories.class, Language.class, Flags.class));
		List<String> opcionesConsulta =
				new ArrayList<>(Arrays
						.asList("Buscar jokes sin flags","Buscar categories más usadas",
								"Buscar lenguajes sin jokes","Buscar flags en más jokes"));
		System.out.println("1. Buscar " + tabla + " por texto \n"
				+ "2. " + opcionesConsulta.get(index) );
		int opcion = sc.nextInt();
		sc.nextLine();
		if(opcion == 1) {
			System.out.println("Introduzca el texto a buscar");
			String texto = sc.nextLine();
			consultaPorTexto(texto, (Class<EntidadBD>)clases.get(index));
			//consulta por texto
		}
		else if(opcion == 2) {
			switch(index) {
				case 0:
					jokesSinFlags();//Buscar jokes sin flags
					break;
				case 1:
					categoriaMasUsada();//Buscar categories más usadas
					break;
				case 2:
					lenguajesSinJokes(); //Buscar lenguajes sin jokes
					break;
				case 3:
					flagMasUsada();//Buscar flags en más jokes
					break;
				default:
					Salir(false);
					break;
			}
		}
		else {
			Salir(false);
		}

	}

	public static void Salir(boolean voluntario) {
		if (voluntario)
			System.out.println("Ha elegido salir. Saliendo...");
		else
			System.out.println("Opción errónea. Saliendo...");
	}
    public static void main( String[] args )
    {
    	try {
    		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
    		System.out.println("Bienvenid@ a gestión de jokes con Hibernate. "
    				+ "Selecciona una opción por su valor numérico.");
    		mostrarMenu(); //Mostramos el primer menú
    		int opcion;
    		opcion = sc.nextInt();
    		sc.nextLine();
    		switch(opcion) {
    			case 1,2,3,4:
    				mostrarMenu(opcion-1); //Mostramos el menú con opción
    				break;
    			case 0:
    				Salir(true);
    				break;
    			default:
    				Salir(false);
    				break;
    		}
    	}
    	catch (Exception ignored){
    		System.out.println(ignored.getMessage());
    		Salir(false);
    	}
    }
}
