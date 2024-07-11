package com.ibm.databaseTest;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test_void {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
         Test_azioni comandi=new Test_azioni();
        // comandi.startConnection("DB_TEST");
         comandi.startConnection("DB_TEST");
         
         ArrayList<String> colonne=new ArrayList<String>();
         ArrayList<String> tipo=new ArrayList<String>();
         
         colonne.add("id");
         tipo.add("INT");
         
         colonne.add("nome");
         tipo.add("VARCHAR(200)");
         
         colonne.add("cognome");
         tipo.add("VARCHAR(200)");
        
         
       comandi.CreaTabella("U", colonne, tipo,"DBTEST");
         
         ArrayList<String> colonne1=new ArrayList<String>();
         ArrayList<String> tipo1=new ArrayList<String>();
         
         colonne1.add("id");
         tipo1.add("VARCHAR(100)");
         
         colonne1.add("Titolo");
         tipo1.add("VARCHAR(200)");
         
         colonne1.add("Autote");
         tipo1.add("VARCHAR(200)");
         
         comandi.CreaTabella("L", colonne1, tipo1,"DBTEST");
         
         ArrayList<String> colonne2=new ArrayList<String>();
         ArrayList<String> tipo2=new ArrayList<String>();
         colonne2.add("id");
         tipo2.add("INT AUTO_INCREMENT");
         
         colonne2.add("inizio");
         tipo2.add("DATE");
         
         colonne2.add("fine");
         tipo2.add("DATE");
         
         colonne2.add("id_U");
         tipo2.add("INT");
         
         colonne2.add("id_L");
         tipo2.add("VARCHAR(100)");
         
         comandi.CreaTabella("p", colonne2, tipo2,"DBTEST","U","L");
         
         ArrayList<String> valori=new ArrayList<String>();
         valori.add("1,'Mario','Rossi'");
         valori.add("2,'Andrea','Verdi'");
         valori.add("3,'Massimo','Bianchi'");
         valori.add("4,'Sara','Vallieri'");
         valori.add("5,'Marco','Graviglia'");
         valori.add("6,'Marzia','Esposito'");
        // comandi.InserisicTabella("U", colonne, valori, "DBTEST");
         
      /*   ArrayList<String> valori1=new ArrayList<String>();
         valori1.add("'1','Libro1','Autore1'");
         valori1.add("'2','Libro2','Autore2'");
         valori1.add("'3','Libro3','Autore3'");
         valori1.add("'4','Libro4','Autore4'");
         */
         
        comandi.getQuery1("DBTEST");
     
        comandi.getQuery2("DBTEST");
         
        comandi.getQuery3("DBTEST");
        
        comandi.getQuery4("DBTEST","VALLIERI");
        
        comandi.getQuery5("DBTEST");
        
        comandi.getQuery6("DBTEST");
         //System.out.println(comandi.startConnection("DB_TEST").isValid(100)) ;
	}

}
