/**
 * 
 */
package fr.diginamic.demo_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Eloi
 *
 */
public class TestConnexionJdbc {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		Connection maConnection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/base_tp_jdbc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "Eloi@diginamic2019");

		System.out.println(maConnection);

	}

}
