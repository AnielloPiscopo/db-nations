package org.java.nations.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.java.nations.helper.*;

public class Main {
	private Main() {
		run();
	}
	
	private void run() {
		try(Scanner sc = new Scanner(System.in);
				Connection con = DriverManager.getConnection(Db.DB_URL, Db.DB_USER, Db.DB_PASSWORD)){
			System.out.print("Search: ");
			String userSearch = sc.next();
			String queryWhereCondition = "where c.name like \"%" + userSearch + "%\"";
			String query = Db.initialQuery + queryWhereCondition + Db.orderCondition;
			try(PreparedStatement ps = con.prepareStatement(query)){
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					final int countryId = rs.getInt(1);
					final String countryName = rs.getString(2);
					final String regionName = rs.getString(3);
					final String continentName = rs.getString(4);
					
					System.out.println(countryId + " - " + countryName + " - " + regionName + " - " + continentName);
				}
			}catch(SQLException ex) {
				System.err.println("ERROR: Query not well formed");
			}
		}catch(SQLException ex) {
			System.err.println("Error during connection to db");
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
