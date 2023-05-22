package org.java.nations.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.java.nations.helper.*;

public class Main {
	public static String initialSelectQuery = "select c.country_id, c.name as country_name , r.name as region_name , c2.name as continent_name \r\n";
	public static String initialFromQuery = "from countries c \r\n";
	public static String initialJoinQuery = "join regions r \r\n"
			+ "		on c.region_id = r.region_id \r\n"
			+ "	join continents c2 \r\n"
			+ "		on r.continent_id = c2.continent_id \r\n";
	
	
	public static String queryOrderCondition = "order by c.name \r\n"; 
	
	public static String initialQuery = initialSelectQuery + " \r\n" + initialFromQuery + " \r\n" + initialJoinQuery;
	
	private Main() {
		run();
	}
	
	private void run() {
		try(Scanner sc = new Scanner(System.in);
				Connection con = DriverManager.getConnection(Db.DB_URL, Db.DB_USER, Db.DB_PASSWORD)){
			System.out.println("Search: ");
			String userSearch = sc.nextLine();
			String queryWhereCondition = "where c.name like \"%" 
			+ ((userSearch.isBlank()) ? "" : userSearch) 
			+ "%\" \r\n";
			String query = initialQuery + queryWhereCondition + queryOrderCondition;
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
			
			System.out.println("Id:");
			int userId = sc.nextInt();
			initialSelectQuery = "select c.country_id, c.name as country_name , l.`language` as languages \r\n";
			initialJoinQuery = "join country_languages cl \r\n"
					+ "		on c.country_id = cl.country_id \r\n"
					+ "	join languages l \r\n"
					+ "		on cl.language_id = l.language_id \r\n";
			String queryHavingCondition = "having c.country_id = " + userId + " \r\n";
			String queryLimitCondition = "limit 1 \r\n";
			
			
			query = initialSelectQuery + initialFromQuery + initialJoinQuery + queryWhereCondition + queryHavingCondition;
			
			try(PreparedStatement ps = con.prepareStatement(query)){
				ResultSet rs = ps.executeQuery();
				
				Set<String> countryLanguages = new HashSet<>(); 
				while(rs.next()) {
					final String countryName = rs.getString(2);
					final String countryLanguage = rs.getString(3);
					
					System.out.println("Country Name: " + countryName);
					countryLanguages.add(countryLanguage);
				}
				System.out.println("Languages: " + countryLanguages);
			}catch(SQLException ex) {
				System.err.println("ERROR: Query not well formed");
			}
			
			initialSelectQuery = "select c.country_id, cs.`year` as anno ,  cs.population  as population , cs.gdp as gdp \r\n";
			initialJoinQuery = "join country_stats cs \r\n"
					+ "		on c.country_id = cs.country_id \r\n";
			queryOrderCondition = "order by cs.`year`  desc \r\n";
			
			query = initialSelectQuery + initialFromQuery + initialJoinQuery + queryWhereCondition + queryHavingCondition + queryOrderCondition + queryLimitCondition;
			
			try(PreparedStatement ps = con.prepareStatement(query)){
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					final String countryYear = rs.getString(2);
					final String countryPopulation = rs.getString(3);
					final String countryGdp = rs.getString(4);
					
					System.out.println("Most recent stats Year: " + countryYear + "\n" + "Population: " + countryPopulation + "GDP: " + countryGdp);
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
