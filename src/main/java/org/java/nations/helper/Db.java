package org.java.nations.helper;

public abstract class Db {
	public final static String DB_URL = "jdbc:mysql://localhost:3306/nations";
	public final static String DB_USER = "root";
	public final static String DB_PASSWORD = "12345";
	
	public static String initialQuery = "select c.country_id, c.name as country_name , r.name as region_name , c2.name as continent_name\r\n"
			+ "from countries c \r\n"
			+ "	join regions r \r\n"
			+ "		on c.region_id = r.region_id \r\n"
			+ "	join continents c2 \r\n"
			+ "		on r.continent_id = c2.continent_id \r\n";
	public static String orderCondition = "order by c.name"; 
}
