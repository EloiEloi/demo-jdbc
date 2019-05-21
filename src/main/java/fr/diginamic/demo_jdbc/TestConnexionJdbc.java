/**
 * 
 */
package fr.diginamic.demo_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;

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

		Connection maConnexion = null;
		try {
			maConnexion = DriverManager.getConnection(Url, User, Password);
		} catch (SQLException e) {
			throw new TechnicalException("Impossible de se connecter à la BDD", e);

		}

		System.out.println(maConnexion);

		Statement monStatement;
		try {
			monStatement = maConnexion.createStatement();
		} catch (SQLException e) {
			throw new TechnicalException("Impossible de creer un échange à la BDD", e);
		}

		int nb;
		ResultSet curseur;

		try {

			nb = monStatement
					.executeUpdate("INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('lampe', 'ikea', 10)");
			nb = monStatement.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('casque audio', 'darty', 0.5)");
			nb = monStatement.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('cd francky vincent', 'fnac', 25)");
			nb = monStatement.executeUpdate(
					"INSERT INTO ARTICLE (DESIGNATION, FOURNISSEUR, PRIX) VALUES ('exam certif', 'diginamic', 15)");

		} catch (SQLException e) {

			throw new TechnicalException("Impossible d'insérer les données à la BDD", e);

		}

		try {
			// requête qui augmente les tarifs de 25% des articles de plus de 10€
			nb = monStatement.executeUpdate("UPDATE ARTICLE SET PRIX=PRIX+PRIX*0.25 WHERE PRIX > 10");

		} catch (SQLException e) {
			throw new TechnicalException("Impossible de modifier les données de la BDD", e);
		}

		try {
			// requête qui affiche tous les articles
			curseur = monStatement.executeQuery("SELECT * FROM ARTICLE");

			StringBuilder message = new StringBuilder();

			while (curseur.next()) {

				Integer id = curseur.getInt("ID");

				String designation = curseur.getString("DESIGNATION");

				String fournisseur = curseur.getString("FOURNISSEUR");

				Double prix = curseur.getDouble("PRIX");

				message.append("ID : ").append(id).append(" | ");
				message.append("DESIGNATION : ").append(designation).append(" | ");
				message.append("FOURNISSEUR : ").append(fournisseur).append(" | ");
				message.append("PRIX : ").append(prix);
				message.append("\n");

			}
			curseur.close();
			System.out.println(message);

		} catch (SQLException e) {
			throw new TechnicalException("Impossible de lire les données de la BDD", e);
		}

		try {
			// requ^te pour récupérer tous les prix de la bdd
			curseur = monStatement.executeQuery("SELECT PRIX FROM ARTICLE");

			Double moyenne = 0d;
			int compteur = 0;

			while (curseur.next()) {
				moyenne += curseur.getDouble("PRIX");
				compteur++;
			}
			// calcul du prix moyen
			moyenne = moyenne / compteur;

			curseur.close();

			System.out.println("moyenne = " + moyenne);

		} catch (SQLException e) {
			throw new TechnicalException("Impossible de lire les données de la BDD", e);
		}

		Scanner scanner = new Scanner(System.in);

		System.out.println("Supprimer les données de la table ?");
		String valeur = "0";

		while (!valeur.equals("1")) {
			System.out.println("tapez '1' pour accepter");
			valeur = scanner.nextLine();

		}
		scanner.close();

		try {
			// requete qui efface toutes n-uples de la BDD
			nb = monStatement.executeUpdate("DELETE FROM ARTICLE");
			System.out.println("toutes les données ont été effacées");
		} catch (SQLException e) {
			throw new TechnicalException("Impossible d'effacer les données de la BDD", e);
		}

		finally {
			try {
				if (monStatement != null) {
					monStatement.close();
				}

			} catch (SQLException e) {
				throw new TechnicalException("Impossible de fermer le statement à la BDD", e);
			}
			try {

				if (maConnexion != null) {
					maConnexion.close();
				}

			} catch (SQLException e) {
				throw new TechnicalException("Impossible de fermer la connection à la BDD", e);
			}

		}

	}
}
