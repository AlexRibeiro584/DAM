package com.alejandroribeiro.jokes.utils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

//Clase Management en la que almacenaremos funciones útiles

public class Management {
	public static void MostrarMenu() {
		System.out.println("Bienvenid@ a la práctica de chistes de Alejandro Ribeiro");
		System.out.println("0. Salir");
        System.out.println("1.Resetear los datos de la base de datos");
        System.out.println("2.Insertar chiste por Statement");
        System.out.println("3.Insertar chiste por PreparedStatement");
        System.out.println("4.Buscar chiste");
        System.out.println("5.Mostrar chistes sin flags");
	}
	//Función estática para devolver la conexión
	public static Connection Conectar() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/Jokes";
		 String usuario = "postgres";
		 String password = "1234";
		 Connection con = DriverManager.getConnection(url, usuario, password); 
		 return con;
	}
	public static void ReiniciarTablas(Statement st) throws SQLException, ClassNotFoundException{
		String sentencia1 = "DELETE FROM JOKES_FLAGS";
		String sentencia2 = "DELETE FROM JOKES";
		String sentencia3 = "DELETE FROM CATEGORIES";
		String sentencia4 = "DELETE FROM LANGUAGES";
		String sentencia5 = "DELETE FROM FLAGS";
		String sentencia6 = "DELETE FROM JOKETYPES";
		
		st.executeUpdate(sentencia1);
		st.executeUpdate(sentencia2);
		st.executeUpdate(sentencia3);
		st.executeUpdate(sentencia4);
		st.executeUpdate(sentencia5);
		st.executeUpdate(sentencia6);
	}
	public static void InsertarTablasEstaticas(Statement st) throws SQLException {
		String sentencia1 = "INSERT INTO LANGUAGES(language_code) VALUES(0)";
		String sentencia2 = "INSERT INTO LANGUAGES(language_code) VALUES(1)";
		String sentencia3 = "INSERT INTO LANGUAGES(language_code) VALUES(2)";
		String sentencia4 = "INSERT INTO LANGUAGES(language_code) VALUES(3)";
		String sentencia5 = "INSERT INTO LANGUAGES(language_code) VALUES(4)";
		String sentencia6 = "INSERT INTO LANGUAGES(language_code) VALUES(5)";
		
		st.executeUpdate(sentencia1);
		st.executeUpdate(sentencia2);
		st.executeUpdate(sentencia3);
		st.executeUpdate(sentencia4);
		st.executeUpdate(sentencia5);
		st.executeUpdate(sentencia6);
		
		String sentencia7 = "INSERT INTO FLAGS(id_flag) VALUES('nsfw')";
		String sentencia8 = "INSERT INTO FLAGS(id_flag) VALUES('religious')";
		String sentencia9 = "INSERT INTO FLAGS(id_flag) VALUES('political')";
		String sentencia10 = "INSERT INTO FLAGS(id_flag) VALUES('racist')";
		String sentencia11 = "INSERT INTO FLAGS(id_flag) VALUES('sexist')";
		String sentencia12 = "INSERT INTO FLAGS(id_flag) VALUES('explicit')";
		
		st.executeUpdate(sentencia7);
		st.executeUpdate(sentencia8);
		st.executeUpdate(sentencia9);
		st.executeUpdate(sentencia10);
		st.executeUpdate(sentencia11);
		st.executeUpdate(sentencia12);
		
		String sentencia13 = "INSERT INTO CATEGORIES(category_name) VALUES('Any')";
		String sentencia14 = "INSERT INTO CATEGORIES(category_name) VALUES('Misc')";
		String sentencia15 = "INSERT INTO CATEGORIES(category_name) VALUES('Programming')";
		String sentencia16 = "INSERT INTO CATEGORIES(category_name) VALUES('Dark')";
		String sentencia17 = "INSERT INTO CATEGORIES(category_name) VALUES('Pun')";
		String sentencia18 = "INSERT INTO CATEGORIES(category_name) VALUES('Spooky')";
		String sentencia19 = "INSERT INTO CATEGORIES(category_name) VALUES('Christmas')";
		
		st.executeUpdate(sentencia13);
		st.executeUpdate(sentencia14);
		st.executeUpdate(sentencia15);
		st.executeUpdate(sentencia16);
		st.executeUpdate(sentencia17);
		st.executeUpdate(sentencia18);
		st.executeUpdate(sentencia19);
		
		String sentencia20 = "INSERT INTO JOKETYPES(typename) VALUES('single')";
		String sentencia21 = "INSERT INTO JOKETYPES(typename) VALUES('twopart')";
		
		st.executeUpdate(sentencia20);
		st.executeUpdate(sentencia21);
	}
	public static void InsertJokesJson(String file, Statement st, String language) {
		Object obj;
		try {
		
			obj = new JSONParser().parse(new FileReader(file));
		 
			JSONObject jo = (JSONObject) obj;
			JSONArray ja = (JSONArray)jo.get("jokes");
			for (int i = 0; i < ja.size(); i++) {
				String id = null, category = null, type = null, 
						joke = null, setup = null, delivery = null;
				JSONObject o = (JSONObject)ja.get(i);
				id = o.get("id").toString();
				category = o.get("category").toString();
				type = o.get("type").toString();
				if(type.equals("single")) {
					joke = o.get("joke").toString();
				}
				if(type.equals("twopart")) {
					setup = o.get("setup").toString();
					delivery = o.get("delivery").toString();
				}
				
				String sentencia = "INSERT INTO JOKES(id_joke,"
						+ "language_joke, category, type_joke, joke, setup, delivery)"
						+ "VALUES("+id+","+language+",'"+category+"','"+type+"','"+joke+"','"
						+ setup + "','" + delivery+"')";
				st.executeUpdate(sentencia);
				Map flags = (Map)o.get("flags");
				Iterator itr = flags.entrySet().iterator();
				while(itr.hasNext()) {
					Map.Entry flag = (Map.Entry)itr.next();
					if(flag.getValue().toString().equals("true")) {
						String whatFlag = flag.getKey().toString();
						String sentencia2 = "INSERT INTO JOKES_FLAGS(jf_id, "
								+ "jf_lg, jf_fl) VALUES("+id+","+language+",'"+whatFlag+"')";
						st.executeUpdate(sentencia2);
					}
				}
				
			}
			
		}
		catch (Exception e) {
			 
		}
	}
	public static void InsertMasivo() throws SQLException, ClassNotFoundException {
		Connection con = Conectar();
		Statement st = con.createStatement();
		ReiniciarTablas(st);
		InsertarTablasEstaticas(st);
		InsertJokesJson("jokes-cs.json",st,"0");
		InsertJokesJson("jokes-de.json",st,"1");
		InsertJokesJson("jokes-en.json",st,"2");
		InsertJokesJson("jokes-es.json",st,"3");
		InsertJokesJson("jokes-fr.json",st,"4");
		InsertJokesJson("jokes-pt.json",st,"5");
		con.close();
	}
	public static void ChisteStatement() throws SQLException, ClassNotFoundException{
		 Connection con = Conectar();
		 Statement st = con.createStatement();
		 Scanner sc = new Scanner(System.in);
		 String categoria, tipo, sentencia;
		 int language, id = 0;
		 ResultSet rs = st.executeQuery("Select MAX(id_joke) from JOKES");
		 if(rs.next()) {
			 id = rs.getInt("max")+1;
			 rs.close();
		 }
		 System.out.println("Introduzca el idioma del chiste: \n"
		 		+ "0.CS \n"
		 		+ "1.DE \n"
		 		+ "2.EN \n"
		 		+ "3.ES \n"
		 		+ "4.FR \n"
		 		+ "5.PT");
		 language = sc.nextInt();
		 System.out.println("Introduzca la categoría del chiste: \n"
			 		+ "0.Any \n"
			 		+ "1.Misc \n"
			 		+ "2.Programming \n"
			 		+ "3.Dark \n"
			 		+ "4.Pun \n"
			 		+ "5.Spooky \n"
			 		+ "6.Christmas");
		 categoria = sc.next().trim();
		 System.out.println("Introduzca el tipo del chiste: \n"
		 		+ "0. Single \n"
		 		+ "1. Two-part \n");
		 int opcion = sc.nextInt();
		 sc.nextLine();
		 if(opcion == 0) {
			 tipo = "single";
			 System.out.println("Introduzca el chiste");
			 String joke = sc.nextLine();
			 sentencia = "INSERT INTO JOKES(id_joke,"
						+ "language_joke, category, type_joke, joke)"
						+ "VALUES("+id+","+language+",'"+categoria+"','"+tipo+"','"+joke+"')";
			 st.executeUpdate(sentencia);
		 }
		 else if(opcion == 1) {
			 tipo = "twopart";
			 System.out.println("Introduzca el setup");
			 String setup = sc.nextLine();
			 System.out.println("Introduzca el delivery");
			 String delivery = sc.nextLine();
			 sentencia = "INSERT INTO JOKES(id_joke,"
						+ "language_joke, category, type_joke, setup, delivery)"
						+ "VALUES("+id+","+language+",'"+categoria+"','"+tipo+"','"+setup+"','"+delivery+"')";
			 st.executeUpdate(sentencia);
		 }
		 System.out.println("¿Tiene flags? \n 0. Sí \n 1. No");
		 if (sc.nextInt() == 0) {
			 System.out.println("¿NSFW?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 sentencia = "INSERT INTO JOKES_FLAGS(jf_id, "
							+ "jf_lg, jf_fl) VALUES("+id+","+language+",'nsfw')";
				 st.executeUpdate(sentencia);
			 }
			 System.out.println("¿Religious?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 sentencia = "INSERT INTO JOKES_FLAGS(jf_id, "
							+ "jf_lg, jf_fl) VALUES("+id+","+language+",'religious')";
				 st.executeUpdate(sentencia);
			 }
			 System.out.println("¿Political?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 sentencia = "INSERT INTO JOKES_FLAGS(jf_id, "
							+ "jf_lg, jf_fl) VALUES("+id+","+language+",'political')";
				 st.executeUpdate(sentencia);
			 }
			 System.out.println("¿Racist?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 sentencia = "INSERT INTO JOKES_FLAGS(jf_id, "
							+ "jf_lg, jf_fl) VALUES("+id+","+language+",'racist')";
				 st.executeUpdate(sentencia);
			 }
			 System.out.println("¿Sexist? \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 sentencia = "INSERT INTO JOKES_FLAGS(jf_id, "
							+ "jf_lg, jf_fl) VALUES("+id+","+language+",'sexist')";
				 st.executeUpdate(sentencia);
			 }
			 System.out.println("¿Explicit? \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 sentencia = "INSERT INTO JOKES_FLAGS(jf_id, "
							+ "jf_lg, jf_fl) VALUES("+id+","+language+",'explicit')";
				 st.executeUpdate(sentencia);
			 }
		 }
		 st.close();
		 con.close();
	}
	public static void ChistePreparedStatement()throws SQLException, ClassNotFoundException{
		Connection con = Conectar();
		 PreparedStatement st = con.prepareStatement("INSERT INTO JOKES(id_joke,"
		 		+ "language_joke, category, type_joke,joke,setup,delivery)"
		 		+ "VALUES(?,?,?,?,?,?,?)");
		 Statement select = con.createStatement();
		 Scanner sc = new Scanner(System.in);
		 String categoria, tipo;
		 int language, id = 0;
		 ResultSet rs = select.executeQuery("Select MAX(id_joke) from JOKES");
		 if(rs.next()) {
			 id = rs.getInt("max")+1;
			 rs.close();
		 }
		 System.out.println("Introduzca el idioma del chiste: \n"
		 		+ "0.CS \n"
		 		+ "1.DE \n"
		 		+ "2.EN \n"
		 		+ "3.ES \n"
		 		+ "4.FR \n"
		 		+ "5.PT");
		 language = sc.nextInt();
		 System.out.println("Introduzca la categoría del chiste: \n"
			 		+ "0.Any \n"
			 		+ "1.Misc \n"
			 		+ "2.Programming \n"
			 		+ "3.Dark \n"
			 		+ "4.Pun \n"
			 		+ "5.Spooky \n"
			 		+ "6.Christmas");
		 categoria = sc.next().trim();
		 System.out.println("Introduzca el tipo del chiste: \n"
		 		+ "0. Single \n"
		 		+ "1. Two-part \n");
		 int opcion = sc.nextInt();
		 sc.nextLine();
		 if(opcion == 0) {
			 tipo = "single";
			 System.out.println("Introduzca el chiste");
			 String joke = sc.nextLine();
			 st.setInt(1, id);
			 st.setInt(2, language);
			 st.setString(3,categoria);
			 st.setString(4,tipo);
			 st.setString(5, joke);
			 st.setString(6, null);
			 st.setString(7, null);
			 st.executeUpdate();
		 }
		 else if(opcion == 1) {
			 tipo = "twopart";
			 System.out.println("Introduzca el setup");
			 String setup = sc.nextLine();
			 System.out.println("Introduzca el delivery");
			 String delivery = sc.nextLine();
			 st.setInt(1, id);
			 st.setInt(2, language);
			 st.setString(3,categoria);
			 st.setString(4,tipo);
			 st.setString(5, null);
			 st.setString(6, setup);
			 st.setString(7, delivery);
			 st.executeUpdate();
		 }
		 System.out.println("¿Tiene flags? \n 0. Sí \n 1. No");
		 if (sc.nextInt() == 0) {
			 st = con.prepareStatement("INSERT INTO JOKES_FLAGS(jf_id,"
			 		+ "jf_lg, jf_fl) VALUES(?,?,?)");
			 System.out.println("¿NSFW?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 st.setInt(1, id);
				 st.setInt(2, language);
				 st.setString(3,"nsfw");
				 st.executeUpdate();
			 }
			 System.out.println("¿Religious?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 st.setInt(1, id);
				 st.setInt(2, language);
				 st.setString(3,"religious");
				 st.executeUpdate();
			 }
			 System.out.println("¿Political?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 st.setInt(1, id);
				 st.setInt(2, language);
				 st.setString(3,"political");
				 st.executeUpdate();
			 }
			 System.out.println("¿Racist?  \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 st.setInt(1, id);
				 st.setInt(2, language);
				 st.setString(3,"racist");
				 st.executeUpdate();
			 }
			 System.out.println("¿Sexist? \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 st.setInt(1, id);
				 st.setInt(2, language);
				 st.setString(3,"sexist");
				 st.executeUpdate();
			 }
			 System.out.println("¿Explicit? \n 0. Sí \n 1. No");
			 if (sc.nextInt() == 0) {
				 st.setInt(1, id);
				 st.setInt(2, language);
				 st.setString(3,"explicit");
				 st.executeUpdate();
			 }
		 }
		 st.close();
		 con.close();
	}
	public static void BuscarChiste() throws SQLException, ClassNotFoundException{
		Connection con = Conectar();
		System.out.println("Introduzca el texto a buscar");
		Scanner sc = new Scanner(System.in);
		String texto = sc.nextLine();
		CallableStatement cStmt = con.prepareCall(
				 "{call BuscarChiste(?)}");
				 cStmt.setString(1, texto);
				 cStmt.execute();
				 ResultSet rs = cStmt.getResultSet();
					while (rs.next()) {
						System.out.println
						("ID: " + rs.getInt("id_joke") 
						+ "\n Idioma: " + rs.getInt("language_joke") 
						+"\n Categoría: " + rs.getString("category")
						+ "\n Tipo: " + rs.getString("type_joke") 
						+ "\n Chiste: " + rs.getString("joke") 
						+ "\n Setup: " + rs.getString("setup") 
						+ "\n Delivery: " + rs.getString("delivery"));
					}
					cStmt.close();
	}
	public static void ChistesLimpios() throws SQLException, ClassNotFoundException{
		Connection con = Conectar();
		CallableStatement cStmt = con.prepareCall(
				 "{call ChistesLimpios()}");
				 cStmt.execute();
				 ResultSet rs = cStmt.getResultSet();
					while (rs.next()) {
						System.out.println
						("ID: " + rs.getInt("id_joke") 
						+ "\n Idioma: " + rs.getInt("language_joke") 
						+"\n Categoría: " + rs.getString("category")
						+ "\n Tipo: " + rs.getString("type_joke") 
						+ "\n Chiste: " + rs.getString("joke") 
						+ "\n Setup: " + rs.getString("setup") 
						+ "\n Delivery: " + rs.getString("delivery"));
					}
					cStmt.close();
	}
}
