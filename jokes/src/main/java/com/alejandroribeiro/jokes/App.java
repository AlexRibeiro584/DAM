package com.alejandroribeiro.jokes;
import java.sql.SQLException;
import java.util.Scanner;

import com.alejandroribeiro.jokes.utils.*;

public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
    	Scanner sc = new Scanner(System.in);
    	int opcion;
    	do {
    		Management.MostrarMenu();
    		opcion = sc.nextInt();
    		switch(opcion) {
    		case 1:
    			Management.InsertMasivo();
    			break;
    		case 2:
    			Management.ChisteStatement();
    			break;
    		case 3:
    			Management.ChistePreparedStatement();
    			break;
    		case 4:
    			Management.BuscarChiste();
    			break;
    		case 5:
    			Management.ChistesLimpios();
    			break;
    		default:
    			break;
    		}
    	}while(opcion != 0);
    }
}
