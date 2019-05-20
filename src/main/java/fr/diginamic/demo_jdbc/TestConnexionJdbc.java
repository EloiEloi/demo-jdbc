/**
 * 
 */
package fr.diginamic.demo_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fr.diginamic.exceptions.TechnicalException;

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ResourceBundle monFichierConf = ResourceBundle.getBundle("database");
		String driverName = monFichierConf.getString("database.driver");
		String Url = monFichierConf.getString("database.url");
		String User = monFichierConf.getString("database.user");
		String Password = monFichierConf.getString("database.password");

		try {

			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			throw new TechnicalException("Le driver JBDC " + driverName + " n'a pas été trouvé.", e);
		}

		Connection maConnection = null;
		try {
			maConnection = DriverManager.getConnection(Url, User, Password);
		} catch (SQLException e) {
			throw new TechnicalException("Impossible de se connecter à la BDD", e);

		} finally {
			try {
				if (maConnection != null) {
					maConnection.close();
				}

			} catch (SQLException e) {
				throw new TechnicalException("iImpossible de fermer la connection à la BDD", e);
			}

		}

		System.out.println(maConnection);

	}

}
