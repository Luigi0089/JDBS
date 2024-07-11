package com.ibm.databaseTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Test_azioni {
	
	private Connection con; 
	
	protected Connection startConnection() throws SQLException {
		if (con==null) {
			MysqlDataSource dataSource=new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPortNumber(3306);
			dataSource.setUser("root");
			dataSource.setPassword("admin");
			con=dataSource.getConnection();
		}
		
		return con;
	}
	
	protected Connection startConnection(String nome) throws SQLException {
		if (con==null) {
			MysqlDataSource dataSource=new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPortNumber(3306);
			dataSource.setUser("root");
			dataSource.setPassword("admin");
			
			dataSource.setDatabaseName(CreaDatabase(nome));
				
			con=dataSource.getConnection();
		}
		
		return con;
	}
	
	
	
	private String CreaDatabase(String nome) throws SQLException {
		
		String sql="CREATE DATABASE IF NOT EXISTS "+nome;
		PreparedStatement ps=startConnection().prepareStatement(sql);
		ps.executeUpdate();	
		ps.close();
		    
		

		
		return nome;
	}
	
	
	protected void CreaTabella( String nome,ArrayList<String> colonne, ArrayList<String> tipo,String database,String tabella1,String tabella2) throws SQLException {
		String sql1="USE "+database;
		PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
		ps1.executeUpdate();
		ps1.close();
		
		
		 String sql="CREATE TABLE IF NOT EXISTS "+nome+"( \n" ;
		
		if(colonne.size()==tipo.size()) {
			for(int i=0;i<colonne.size();i++) {
				sql+=colonne.get(i)+" "+ tipo.get(i);
				if(i==0)
					sql+=" PRIMARY KEY";
				if(i<=colonne.size()-1)
				sql+=",\n";
				
					
			
				
			}
			sql+="FOREIGN KEY("+colonne.get(colonne.size()-2)+") REFERENCES " +tabella1+"(id),\n";
			sql+="FOREIGN KEY("+colonne.get(colonne.size()-1)+") REFERENCES " +tabella2+"(id)\n";		
			sql+=")";
			
			
			
			 PreparedStatement ps=startConnection(database).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 ps.executeUpdate();	
			 ps.close();
		
		
		}
		
	}
	
	protected void CreaTabella( String nome,ArrayList<String> colonne, ArrayList<String> tipo,String database) throws SQLException {
		String sql1="USE "+database;
		PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
		ps1.executeUpdate();
		ps1.close();
		
		
		 String sql="CREATE TABLE IF NOT EXISTS "+nome+"( \n" ;
		
		if(colonne.size()==tipo.size()) {
			for(int i=0;i<colonne.size();i++) {
				sql+=colonne.get(i)+" "+ tipo.get(i);
				if(i==0)
					sql+=" PRIMARY KEY";
				if(i<colonne.size()-1)
				sql+=",\n";
				else if(i==colonne.size()-1) {
					sql+=" \n";

					
				}
					
			
				
			}
			sql+=")";
			
			
			 PreparedStatement ps=startConnection(database).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 ps.executeUpdate();	
			 ps.close();
		
		
		}
		
	}
	
	protected void InserisicTabella( String nome,ArrayList<String> colonne, ArrayList<String> valori,String database) throws SQLException {
		String sql1="USE "+database;
		PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
		ps1.executeUpdate();
		ps1.close();
		
		
		 String sql="INSERT INTO "+nome+"(" ;
		 for(int i=0;i<colonne.size();i++) {
			 sql+=colonne.get(i);
			 if(i<colonne.size()-1)
				 sql+=", ";
			 else
				 sql+=")\n";
		 }
		 for(int i=0;i<valori.size();i++) {
			 if(i==0) {
				sql+="VALUES"; 
			 }
			 
				 sql+="(";
			 sql+=valori.get(i);
			 if(i<valori.size()-1)
				 sql+="), ";
			 else
				 sql+=")";
		 }
		 sql+=";";
			 System.out.println(sql);
		     PreparedStatement ps=startConnection(database).prepareStatement(sql);
			 ps.executeUpdate();	
			 ps.close();
		
		
		}
	
	
protected void getQuery1(String database) throws SQLException {
	String sql1="USE "+database;
	PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
	ps1.executeUpdate();
	ps1.close();
	String sql="SELECT p.id,p.inizio,p.fine,p.id_U,u.cognome FROM u\r\n"
			+ "LEFT JOIN p\r\n"
			+ "ON p.id_U=u.id\r\n"
			+ "WHERE u.cognome='Vallieri'\r\n"
			+ "ORDER BY  inizio ASC;";
	//String sql="SELECT id,inizio,fine,id_U FROM p WHERE id_U=4 \n"
		//	+ "ORDER BY  inizio ASC;";
		
		
		
		PreparedStatement ps = startConnection(database).prepareStatement(sql);
		
		ResultSet rs= ps.executeQuery();
		
		while(rs.next()) {
			
			System.out.println("id: "+rs.getInt(1));
			System.out.println("inizio: "+rs.getString(2));
			System.out.println("fine: "+rs.getString(3));
			System.out.println("id_U: "+rs.getString(4));
			System.out.println("Cognome: "+rs.getString(5));
			System.out.println("-------------------------------");
			
		}
		ps.close();
}
		
		protected void getQuery2(String database) throws SQLException {
			String sql1="USE "+database;
			PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
			ps1.executeUpdate();
			ps1.close();
			String sql="SELECT u.Nome,u.cognome,COUNT(p.id_U)  FROM p\r\n"
					+ "LEFT JOIN u\r\n"
					+ "ON p.id_U=u.id \r\n"
					+ "GROUP BY p.id_U\r\n"
					+ "ORDER BY  count(p.id_U) DESC LIMIT 3;";
			
				
				
				
				PreparedStatement ps = startConnection(database).prepareStatement(sql);
				
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					
					System.out.println("Nome: "+ rs.getString(1));
					System.out.println("Cognome: "+rs.getString(2));
					System.out.println("Prestiti_effettuati: "+rs.getInt(3));
					System.out.println("-------------------------------");
					
				}
				ps.close();
		//Bisogna usare ps.executeQuery(); quando laa query è un QL mentre ps.executeUpdate() per gli altri
	}
		
	
		protected void getQuery3(String database) throws SQLException {
			String sql1="USE "+database;
			PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
			ps1.executeUpdate();
			ps1.close();
			String sql="SELECT u.Nome,u.cognome,l.Titolo,p.id_L  FROM p\r\n"
					+ "LEFT JOIN u\r\n"
					+ "ON p.id_U=u.id \r\n"
					+ "LEFT JOIN l\r\n"
					+ "ON p.id_L=l.id\r\n"
					+ "WHERE isnull(p.fine) OR p.fine> current_date()\r\n"
					+ "ORDER BY  inizio;";
			
				
				
				
				PreparedStatement ps = startConnection(database).prepareStatement(sql);
				
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					
					System.out.println("Nome: "+ rs.getString(1));
					System.out.println("Cognome: "+rs.getString(2));
					System.out.println("Libro prestato: "+rs.getString(3));
					System.out.println("ID Libro: "+rs.getInt(4));
					System.out.println("-------------------------------");
					
				}
				ps.close();
		//Bisogna usare ps.executeQuery(); quando laa query è un QL mentre ps.executeUpdate() per gli altri
	}
		
		protected void getQuery4(String database, String Cognome) throws SQLException {
			String sql1="USE "+database;
			PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
			ps1.executeUpdate();
			ps1.close();
			String sql="SELECT l.id,l.titolo,p.inizio,p.fine FROM u\r\n"
					+ "LEFT JOIN p\r\n"
					+ "ON u.id=p.id_U\r\n"
					+ "LEFT JOIN l\r\n"
					+ "ON p.id_L=l.id\r\n"
					+ "WHERE u.cognome='"+Cognome+"';";
			
				
				
				
				PreparedStatement ps = startConnection(database).prepareStatement(sql);
				
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					
					System.out.println("ID LIBRO: "+ rs.getInt(1));
					System.out.println("TITOLO LIBRO: "+rs.getString(2));
					System.out.println("INIZIO: "+rs.getString(3));
					System.out.println("FINE: "+rs.getString(4));
					System.out.println("-------------------------------");
					
				}
				ps.close();
		//Bisogna usare ps.executeQuery(); quando laa query è un QL mentre ps.executeUpdate() per gli altri
	}
		
		protected void getQuery5(String database) throws SQLException {
			String sql1="USE "+database;
			PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
			ps1.executeUpdate();
			ps1.close();
			String sql="SELECT DISTINCT l.id,l.titolo,COUNT(p.id_L) AS numero_prestiti FROM p\r\n"
					+ "LEFT JOIN l\r\n"
					+ "ON p.id_L=l.id\r\n"
					+ "GROUP BY p.id_L\r\n"
					+ "ORDER BY COUNT(p.id_L) DESC;";
			
				
				
				
				PreparedStatement ps = startConnection(database).prepareStatement(sql);
				
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					
					System.out.println("ID LIBRO: "+ rs.getInt(1));
					System.out.println("TITOLO: "+rs.getString(2));
					System.out.println("#PRESTITI: "+rs.getInt(3));
					System.out.println("-------------------------------");
					
				}
				ps.close();
		//Bisogna usare ps.executeQuery(); quando laa query è un QL mentre ps.executeUpdate() per gli altri
	}
		
		protected void getQuery6(String database) throws SQLException {
			String sql1="USE "+database;
			PreparedStatement ps1=startConnection(database).prepareStatement(sql1);
			ps1.executeUpdate();
			ps1.close();
			String sql="SELECT p.id,u.Cognome,l.titolo,p.inizio,p.fine FROM p\r\n"
					+ "LEFT JOIN l\r\n"
					+ "ON p.id_L=l.id\r\n"
					+ "LEFT JOIN u\r\n"
					+ "ON p.id_U=u.id\r\n"
					+ "WHERE  (ISNULL(p.fine) AND CURRENT_DATE()-p.inizio>15) OR (p.fine-p.inizio>15);";
			
				
				
				
				PreparedStatement ps = startConnection(database).prepareStatement(sql);
				
				ResultSet rs= ps.executeQuery();
				
				while(rs.next()) {
					
					System.out.println("ID PRESTITO: "+ rs.getInt(1));
					System.out.println("COGNOME: "+rs.getString(2));
					System.out.println("LIBRO: "+rs.getString(3));
					System.out.println("INIZIO: "+rs.getString(4));
					System.out.println("FINE: "+rs.getString(5));
					System.out.println("-------------------------------");
					
				}
				ps.close();
		//Bisogna usare ps.executeQuery(); quando laa query è un QL mentre ps.executeUpdate() per gli altri
	}
}


