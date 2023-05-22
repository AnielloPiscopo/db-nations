package org.java.nations.helper;

public class Helper {
	public static void isNumPositive(int num) throws Exception {
		if(num<=0) throw new Exception("You have insert a number equal or lower than 0");
	}
}
