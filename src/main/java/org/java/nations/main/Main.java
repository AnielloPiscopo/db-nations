package org.java.nations.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		final String DB_URL = "jdbc:mysql://localhost:3306/nations";
		final String DB_USER = "root";
		final String DB_PASSWORD = "12345";
		
		try(Scanner sc = new Scanner(System.in);
				Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
			String sql = "select c.country_id, c.name as country_name , r.name as region_name , c2.name as continent_name\r\n"
					+ "from countries c \r\n"
					+ "	join regions r \r\n"
					+ "		on c.region_id = r.region_id \r\n"
					+ "	join continents c2 \r\n"
					+ "		on r.continent_id = c2.continent_id \r\n"
					+ "order by c.name ;";
			try(PreparedStatement ps = con.prepareStatement(sql)){
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
}
