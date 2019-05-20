/**
 * 
 */
package fr.diginamic.demo_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Eloi
 *
 */
public class TestConnexionJdbc {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub

		ResourceBundle monFichierConf = ResourceBundle.getBundle("database");

		String driverName = monFichierConf.getString("database.driver");

		String Url = monFichierConf.getString("database.url");
		String User = monFichierConf.getString("database.user");
		String Password = monFichierConf.getString("database.password");

		Class.forName(driverName);

		Connection maConnection = DriverManager.getConnection(Url, User, Password);

		System.out.println(maConnection);

	}

}
