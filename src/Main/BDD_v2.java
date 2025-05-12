/*
 * 
 */
package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;

import Background.Background;
import Event.Evenement;
import Event.ListOfEventsFrame;
import Flags.Drapeau;
import Players.Joueur;
import Sauvegarde.ConfigurationSaveLoad;

// TODO: Auto-generated Javadoc
/**
 * The Class BDD_v2.
 */
public class BDD_v2 {

	/** The url. */
	static String url = "jdbc:hsqldb:file:database" + File.separator + "Tennis_BDD_v2;shutdown=true";

	/** The login. */
	static String login = "sa";

	/** The password. */
	static String password = "";

	/** The atp. */
	public static String ATP = "ATP";

	/** The wta. */
	public static String WTA = "WTA";

	/** The tab bdd. */
	public static ArrayList<String> tabBdd = new ArrayList<>();

	/** The connection. */
	static Connection connection;

	// --------------------------------------- INITIALISATION
	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Connexion BDD.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public static void connexionBDD() throws ClassNotFoundException, SQLException {
		System.out.println("Database connexion");
		Class.forName("org.hsqldb.jdbcDriver");
		try {
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("  Database connected !!");
		} catch (SQLException e) {
			System.err.println("  ! Erreur connexion database ! " + e.getMessage());
		}
	}

	/**
	 * Executer requete SQL.
	 *
	 * @param requete the requete
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public static void executerRequeteSQL(String requete) throws ClassNotFoundException, SQLException {
		Class.forName("org.hsqldb.jdbcDriver");
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(requete);
		} catch (SQLException e) {
			// G�rer les erreurs d'ex�cution de la requ�te SQL
			System.err.println("Erreur lors de l'ex�cution de la requ�te SQL : " + e.getMessage());
		}
	}

	/**
	 * Deconnexion BDD.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public static void deconnexionBDD() throws ClassNotFoundException, SQLException {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Database connexion closed");
			}
		} catch (SQLException e) {
			System.err.println("! Error during closing database ! " + e.getMessage());
		}
	}

	/**
	 * Suppressions des tables.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public static void suppressionsDesTables() throws ClassNotFoundException, SQLException {
		// nom des tales � d�finir
		String requete = "";
		requete += "DROP TABLE CUSTOM IF EXISTS;";
		requete += "DROP TABLE WTA IF EXISTS;";
		requete += "DROP TABLE ATP IF EXISTS;";
//		requete += "DROP TABLE LIST_8_JOUEUR IF EXISTS;";
		requete += "DROP TABLE Background IF EXISTS;";
		requete += "DROP TABLE Event IF EXISTS;";
//		requete += "DROP TABLE FLAG IF EXISTS;";

		executerRequeteSQL(requete);

		System.out.println("  - Tables deleted");
	}

	/**
	 * Creationdes tables.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public static void creationdesTables() throws ClassNotFoundException, SQLException {
		String requete = "";
		requete += "CREATE TABLE Background (Nom VARCHAR(120) PRIMARY KEY, Img_1 varchar(255), Img_2 varchar(255), "
				+ "Img_3 varchar(255), Img_4 varchar(255));";
		requete += "CREATE TABLE Event (Nom VARCHAR(20) PRIMARY KEY, NomBG VARCHAR(20));";// , FOREIGN KEY (NomBG)
																							// REFERENCES Background
																							// (Nom)

		requete += "CREATE TABLE Flag (NomAcro VARCHAR(10) PRIMARY KEY,NomPays VARCHAR(125), ImgFlag varchar(255));";
//		requete += "CREATE TABLE CUSTOM (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100)," + "NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50),"
//				+ "ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10),"
//				+ "weight varchar(20), PRIZETOTAL varchar(60),BIRTHPLACE varchar(200),CITYRESIDENCE varchar(200),TETEDESERIE varchar(20)," + "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";
		requete += "CREATE TABLE ATP (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100),"
				+ "NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10),"
				+ "weight varchar(20), PRIZETOTAL varchar(60),BIRTHPLACE varchar(200),CITYRESIDENCE varchar(200),TETEDESERIE varchar(20),"
				+ "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";
		requete += "CREATE TABLE WTA (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100),"
				+ "NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10),"
				+ "weight varchar(20), PRIZETOTAL varchar(60),BIRTHPLACE varchar(200),CITYRESIDENCE varchar(200),TETEDESERIE varchar(20),"
				+ "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";

		executerRequeteSQL(requete);

		System.out.println("  + create tables finished");
	}

	/**
	 * Creation new table.
	 *
	 * @param name the name
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public static void creationNewTable(String name) throws ClassNotFoundException, SQLException {
		String requete = "";
		requete += "CREATE TABLE " + name + " (id int PRIMARY KEY, " + "Sexe varchar(15), " + "Nom varchar(100), "
				+ "Prenom varchar(100)," + "NomAfficher varchar(100), " + "Nat varchar(10), " + "Country varchar(100), " + "Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), " + "Ranking int, " + "height varchar(10), " + "hand varchar(25), "
				+ "age varchar(10)," + "weight varchar(20), " + "PRIZETOTAL varchar(60)," + "BIRTHPLACE varchar(200),"
				+ "CITYRESIDENCE varchar(200)," + "TETEDESERIE varchar(20),"
				+ "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";
		executerRequeteSQL(requete);

		System.out.println("  + Player tables " + name + " added");
	}

	// ---------------------------------------
	// INSERTIONS--------------------------------------------------------------------------------------------------------------

	/**
	 * Insertion background dans BDD.
	 *
	 * @param background the background
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void insertionBackgroundDansBDD(Background background) throws SQLException, ClassNotFoundException {
		StringBuilder requete = new StringBuilder("");

		requete.append("INSERT INTO Background (NOM, Img_1, Img_2, Img_3, Img_4, Img_5) VALUES" + "('"
				+ background.getNom() + "', '" + background.getImage_1() + "', '" + background.getImage_2() + "', '"
				+ background.getImage_3() + "', '" + background.getImage_4() + "', '" + background.getImage_5()
				+ "');");
		executerRequeteSQL(requete.toString());
		System.out.println("  + Background : " + background.getNom() + " added");
	}

	/**
	 * Insertion event dans BDD.
	 *
	 * @param event the event
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void insertionEventDansBDD(Evenement event) throws SQLException, ClassNotFoundException {
		StringBuilder requete = new StringBuilder("");

		requete.append("INSERT INTO Event (NOM, NOMBG) VALUES" + "('" + event.getNom() + "', '"
				+ event.getBackground().getNom() + "');");

		executerRequeteSQL(requete.toString());
		System.out.println("  + Evenement : " + event.getNom() + " added");
	}

	/**
	 * Insertion drapeau dans BDD.
	 *
	 * @param drapeau the drapeau
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void insertionDrapeauDansBDD(Drapeau drapeau) throws SQLException, ClassNotFoundException {
		StringBuilder requete = new StringBuilder("");

		requete.append("INSERT INTO Flag (NomAcro, NomPays, ImgFlag) VALUES" 
		+ "('" + drapeau.getNom() + "', '"
			   + drapeau.getPays() + "', '"
			   + drapeau.getImageDrapeau() + "');");

		executerRequeteSQL(requete.toString());
		System.out.println("  + Drapeau de : " + drapeau.getNom()+", "+ drapeau.getPays() + " added");
	}

	/**
	 * Insertion joueur dans BDD.
	 *
	 * @param joueur  the joueur
	 * @param bddName the bdd name
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void insertionJoueurDansBDD(Joueur joueur, String bddName)
			throws SQLException, ClassNotFoundException {
		String requete = "INSERT INTO " + bddName
				+ " (id, Sexe, Nom, Prenom, NomAfficher, Nat, Country, Birthdate, ImgJoueur, Ranking, height, hand, age, weight, PRIZETOTAL, BIRTHPLACE, CityResidence,TETEDESERIE)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
			preparedStatement.setInt(1, joueur.getID());
			preparedStatement.setString(2, joueur.getSexe());
			preparedStatement.setString(3, joueur.getNom());
			preparedStatement.setString(4, joueur.getPrenom());
			preparedStatement.setString(5, joueur.getDisplay_name());
			preparedStatement.setString(6, joueur.getNatio_acronyme());
			preparedStatement.setString(7, joueur.getCountry());
			preparedStatement.setString(8, joueur.getBirthDate());
			preparedStatement.setString(9, joueur.getImgJoueur());
			preparedStatement.setInt(10, joueur.getRank());
			preparedStatement.setString(11, joueur.getTaille() + "");
			preparedStatement.setString(12, joueur.getMain());
			preparedStatement.setInt(13, joueur.getAge());
			preparedStatement.setString(14, joueur.getWeight() + "");
			preparedStatement.setString(15, joueur.getPrizetotal());
			preparedStatement.setString(16, joueur.getBirthPlace());
			preparedStatement.setString(17, joueur.getCityResidence());
			preparedStatement.setString(18, joueur.getTeteDeSerie());

			preparedStatement.executeUpdate();
		}catch (SQLException e1) {
			System.out.println(joueur.toString());
			e1.printStackTrace();
		}
	}

	/**
	 * Compter nb elements BDD.
	 *
	 * @param NomBDD the nom BDD
	 * @return the int
	 * @throws SQLException the SQL exception
	 */
	// ----------------------------------------------------
	// RECUPERATION------------------------------------------------------------------------------
	public static int compterNbElementsBDD(String NomBDD) throws SQLException {
		int count = 0;
		String requete = "SELECT COUNT(*) FROM " + NomBDD;

		try (PreparedStatement statement = connection.prepareStatement(requete);
				ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		}
		System.out.println("  numbers of element in database " + NomBDD + " = " + count);
		return count;
	}

	/**
	 * Gets the joueur par ID.
	 *
	 * @param joueurID the joueur ID
	 * @param bddname  the bddname
	 * @return the joueur par ID
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Joueur getJoueurParID(int joueurID, String bddname) throws SQLException, ClassNotFoundException {
		Joueur joueur = null;
		String requete = "SELECT * FROM " + bddname + " WHERE id = " + joueurID;// "CREATE INDEX nom_index ON Joueur
																				// (Nom);" +

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String sexe = resultSet.getString("Sexe");
				String nom = resultSet.getString("Nom");
				String prenom = resultSet.getString("Prenom");
				String display_name = resultSet.getString("NomAfficher");
				String natio_acronyme = resultSet.getString("Nat");
				String country = resultSet.getString("Country");
				String birthDate = resultSet.getString("Birthdate");
				String imgJoueur = resultSet.getString("ImgJoueur");
				int rank = resultSet.getInt("Ranking");
				String taille = resultSet.getString("height");
				String main = resultSet.getString("hand");
				String age = resultSet.getString("age");
				String weight = resultSet.getString("weight");
				String prizetotal = resultSet.getString("PRIZETOTAL");
				String birthPlace = resultSet.getString("BIRTHPLACE");
				String cityResidence = resultSet.getString("CityResidence");
				String teteDeSerie = resultSet.getString("TETEDESERIE");

				joueur = new Joueur(id, sexe, nom, prenom, display_name, natio_acronyme, country, birthDate, imgJoueur, rank,
						Integer.parseInt(taille), main, Integer.parseInt(age), Integer.parseInt(weight), prizetotal,
						birthPlace, cityResidence, teteDeSerie);
			}
		}
		return joueur;
	}

	/**
	 * Gets the joueur par nom.
	 *
	 * @param nomJoueur the nom joueur
	 * @param bddName   the bdd name
	 * @return the joueur par nom
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Joueur getJoueurParNom(String nomJoueur, String bddName) throws SQLException, ClassNotFoundException {
		Joueur joueur = null;
		String requete = "SELECT * FROM " + bddName + " WHERE Nom = '" + nomJoueur + "'";

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String sexe = resultSet.getString("Sexe");
				String nom = resultSet.getString("Nom");
				String prenom = resultSet.getString("Prenom");
				String display_name = resultSet.getString("NomAfficher");
				String natio_acronyme = resultSet.getString("Nat");
				String country = resultSet.getString("Country");
				String birthDate = resultSet.getString("Birthdate");
				String imgJoueur = resultSet.getString("ImgJoueur");
				int rank = resultSet.getInt("Ranking");
				String taille = resultSet.getString("height");
				String main = resultSet.getString("hand");
				String age = resultSet.getString("age");
				String weight = resultSet.getString("weight");
				String prizetotal = resultSet.getString("PRIZETOTAL");
				String birthPlace = resultSet.getString("BIRTHPLACE");
				String cityResidence = resultSet.getString("CityResidence");
				String teteDeSerie = resultSet.getString("TETEDESERIE");

				joueur = new Joueur(id, sexe, nom, prenom, display_name, natio_acronyme, country, birthDate, imgJoueur, rank,
						Integer.parseInt(taille), main, Integer.parseInt(age), Integer.parseInt(weight), prizetotal,
						birthPlace, cityResidence, teteDeSerie);
			}
		}
		return joueur;
	}

	/**
	 * Gets the list of all joueurs from bdd name.
	 *
	 * @param bddName the bdd name
	 * @return the all joueurs
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ArrayList<Joueur> getAllJoueurs(String bddName) throws SQLException, ClassNotFoundException {
		ArrayList<Joueur> joueurs = new ArrayList<>();
		String requete = "SELECT * FROM " + bddName;

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String sexe = resultSet.getString("Sexe");
				String nom = resultSet.getString("Nom");
				String prenom = resultSet.getString("Prenom");
				String display_name = resultSet.getString("NomAfficher");
				String natio_acronyme = resultSet.getString("Nat");
				String country = resultSet.getString("Country");
				String birthDate = resultSet.getString("Birthdate");
				String imgJoueur = resultSet.getString("ImgJoueur");
				int rank = resultSet.getInt("Ranking");
				String taille = resultSet.getString("height");
				String main = resultSet.getString("hand");
				String age = resultSet.getString("age");
				String weight = resultSet.getString("weight");
				String prizetotal = resultSet.getString("PRIZETOTAL");
				String birthPlace = resultSet.getString("BIRTHPLACE");
				String cityResidence = resultSet.getString("CityResidence");
				String teteDeSerie = resultSet.getString("TETEDESERIE");

				Joueur joueur = new Joueur(id, sexe, nom, prenom, display_name, natio_acronyme, country,birthDate, imgJoueur,
						rank, Integer.parseInt(taille), main, Integer.parseInt(age), Integer.parseInt(weight),
						prizetotal, birthPlace, cityResidence, teteDeSerie);
				joueurs.add(joueur);
			}
		}
		return joueurs;
	}

	/**
	 * Data event.
	 *
	 * @return the string[][]
	 * @throws SQLException the SQL exception
	 */
	public static String[][] DataEvent() throws SQLException {
		String requete = "SELECT Event.Nom AS EventName, Event.NomBG AS BackgroundName, Background.Img_1, Background.Img_2, Background.Img_3, Background.Img_4, Background.Img_5 "
				+ "FROM Event LEFT JOIN Background ON Event.NomBG = Background.Nom";

		// D�terminer la taille de la matrice
		int rowCount = 0;
		// Cr�er la matrice avec le nombre de lignes (rowCount) et 6 colonnes
		String[][] data = new String[compterNbElementsBDD("event")][7];
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);
//			int row = 0;
			while (resultSet.next()) {
				rowCount++;
				String eventName = resultSet.getString("EventName");
				String backgroundName = resultSet.getString("BackgroundName");
				String img1 = resultSet.getString("Img_1");
				String img2 = resultSet.getString("Img_2");
				String img3 = resultSet.getString("Img_3");
				String img4 = resultSet.getString("Img_4");
				String img5 = resultSet.getString("Img_5");

				// Stocker les donn�es dans la matrice
				data[rowCount - 1][0] = eventName;
				data[rowCount - 1][1] = backgroundName;
				data[rowCount - 1][2] = img1;
				data[rowCount - 1][3] = img2;
				data[rowCount - 1][4] = img3;
				data[rowCount - 1][5] = img4;
				data[rowCount - 1][6] = img5;
			}
		}
		return data;
	}

	/**
	 * Gets the evenement by name.
	 *
	 * @param eventName the event name
	 * @return the evenement by name
	 * @throws SQLException the SQL exception
	 */
	public static Evenement getEvenementByName(String eventName) throws SQLException {
		Evenement evenement = null;
		String requete = "SELECT Event.*, Background.* FROM Event "
				+ "INNER JOIN Background ON Event.NomBG = Background.Nom WHERE Event.Nom = ?";

		try (PreparedStatement statement = connection.prepareStatement(requete)) {
			statement.setString(1, eventName);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String nomEvent = resultSet.getString("nom");
				String nomBG = resultSet.getString("NomBG");
				// Ajoutez d'autres attributs de l'�v�nement

				// R�cup�rez les informations sur le fond d'�cran
				String img1 = resultSet.getString("Img_1");
				String img2 = resultSet.getString("Img_2");
				String img3 = resultSet.getString("Img_3");
				String img4 = resultSet.getString("Img_4");
				String img5 = resultSet.getString("Img_5");

				// Cr�ez un objet Evenement et Background
				evenement = new Evenement(nomEvent);
				Background background = new Background(nomBG);
				background.setImage_1(img1);
				background.setImage_2(img2);
				background.setImage_3(img3);
				background.setImage_4(img4);
				background.setImage_5(img5);
				evenement.setBackground(background);
			}
		}

		return evenement;
	}

	/**
	 * Data background.
	 *
	 * @return the string[][]
	 * @throws SQLException the SQL exception
	 */
	public static String[][] DataBackground() throws SQLException {
		String requete = "SELECT Background.Nom AS BackgroundName, Background.Img_1, Background.Img_2, Background.Img_3, Background.Img_4, Background.Img_5 FROM Background";

		int rowCount = 0;
		String[][] data = new String[compterNbElementsBDD("Background")][6];

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			while (resultSet.next()) {
				rowCount++;
				String backgroundName = resultSet.getString("BackgroundName");
				String img1 = resultSet.getString("Img_1");
//				System.out.println("+++ background image 1 : "+img1);
				String img2 = resultSet.getString("Img_2");
//				System.out.println("+++ background image 2 : "+img2);
				String img3 = resultSet.getString("Img_3");
//				System.out.println("+++ background image 3 : "+img3);
				String img4 = resultSet.getString("Img_4");
//				System.out.println("+++ background image 4 : "+img4);
				String img5 = resultSet.getString("Img_5");
//				System.out.println("+++ background image 5 : "+img5);

				data[rowCount - 1][0] = backgroundName;
				data[rowCount - 1][1] = img1;
				data[rowCount - 1][2] = img2;
				data[rowCount - 1][3] = img3;
				data[rowCount - 1][4] = img4;
				data[rowCount - 1][5] = img5;
			}
		}
		return data;
	}

	/**
	 * Data background list.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Background> DataBackgroundList() throws SQLException {
		String requete = "SELECT Background.Nom AS BackgroundName, Background.Img_1, Background.Img_2, Background.Img_3, Background.Img_4, Background.Img_5 FROM Background";

		ArrayList<Background> backgroundBDD = new ArrayList<>();

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			while (resultSet.next()) {
				String backgroundName = resultSet.getString("BackgroundName");
				String img1 = resultSet.getString("Img_1");
				String img2 = resultSet.getString("Img_2");
				String img3 = resultSet.getString("Img_3");
				String img4 = resultSet.getString("Img_4");
				String img5 = resultSet.getString("Img_5");

				Background backgroundtemp = new Background(backgroundName);
				backgroundtemp.setImage_1(img1);
				backgroundtemp.setImage_2(img2);
				backgroundtemp.setImage_3(img3);
				backgroundtemp.setImage_4(img4);
				backgroundtemp.setImage_5(img5);

				backgroundBDD.add(backgroundtemp);
			}
		}
		return backgroundBDD;
	}

	/**
	 * Gets the one background.
	 *
	 * @param nomBackground the nom background
	 * @return the one background
	 * @throws SQLException the SQL exception
	 */
	public static Background getOneBackground(String nomBackground) throws SQLException {
		System.out.println("  get background named : " + nomBackground);
		String requete = "SELECT Background.Nom AS BackgroundName, Background.Img_1, Background.Img_2, Background.Img_3, "
				+ "Background.Img_4,Background.Img_5 FROM BACKGROUND WHERE Nom = '" + nomBackground + "'";
		Background backgroundtemp = new Background(nomBackground);
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			if (resultSet.next()) {
				String backgroundName = resultSet.getString("BackgroundName");
				String img1 = resultSet.getString("Img_1");
				String img2 = resultSet.getString("Img_2");
				String img3 = resultSet.getString("Img_3");
				String img4 = resultSet.getString("Img_4");
				String img5 = resultSet.getString("Img_5");

				backgroundtemp.setNom(backgroundName);
				backgroundtemp.setImage_1(img1);
				backgroundtemp.setImage_2(img2);
				backgroundtemp.setImage_3(img3);
				backgroundtemp.setImage_4(img4);
				backgroundtemp.setImage_5(img5);

			}
		}

		return backgroundtemp;
	}

	/**
	 * Data flag.
	 *
	 * @return the string[][]
	 * @throws SQLException the SQL exception
	 */
	public static String[][] DataFlag() throws SQLException {
		String requete = "SELECT NomAcro, NomPays, ImgFlag FROM Flag";

		int rowCount = compterNbElementsBDD("Flag");
		String[][] data = new String[rowCount][3];

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);
			
			int i = 0;
	        while (resultSet.next()) {
	            data[i][0] = resultSet.getString("NomAcro");
	            data[i][1] = resultSet.getString("NomPays");
	            data[i][2] = resultSet.getString("ImgFlag");
	            i++;
	        }
		}
		return data;
	}

	/**
	 * Data joueur.
	 *
	 * @param bddName the bdd name
	 * @return the string[][]
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static String[][] DataJoueur(String bddName) throws SQLException, ClassNotFoundException {
		String requete = "SELECT id, Sexe, Nom, Prenom, NomAfficher, Nat, Country, Birthdate, ImgJoueur, Ranking, height, hand, age, "
				+ "weight, PRIZETOTAL, BIRTHPLACE, CITYRESIDENCE,TETEDESERIE FROM " + bddName;

		// D�terminer la taille de la matrice
		int rowCount = 0;
		// Cr�er la matrice avec le nombre de lignes (rowCount) et 15 colonnes
		String[][] data = new String[compterNbElementsBDD(bddName)][17];
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);
			while (resultSet.next()) {
				rowCount++;
				int id = resultSet.getInt("id");
				String sexe = resultSet.getString("Sexe");
				String nom = resultSet.getString("Nom");
				String prenom = resultSet.getString("Prenom");
				String nomAfficher = resultSet.getString("NomAfficher");
				String nat = resultSet.getString("Nat");
				String country = resultSet.getString("Country");
				String flagImagePath = getFlagImagePathByAcronym(nat);
				String birthdate = resultSet.getString("Birthdate");
				String imgJoueur = resultSet.getString("ImgJoueur");
				int ranking = resultSet.getInt("Ranking");
				String height = resultSet.getString("height");
				String hand = resultSet.getString("hand");
				String age = resultSet.getString("age");
				String weight = resultSet.getString("weight");
				String prizetotal = resultSet.getString("PRIZETOTAL");
				String birthplace = resultSet.getString("BIRTHPLACE");
				String cityresidence = resultSet.getString("CITYRESIDENCE");
				String teteDeSerie = resultSet.getString("TETEDESERIE");
				// Stocker les donn�es dans la matrice
				data[rowCount - 1] = new String[] { String.valueOf(id), sexe, nom, prenom, nomAfficher, nat, country,
						flagImagePath, imgJoueur, String.valueOf(ranking), prizetotal, height, hand, age, weight,
						birthdate, birthplace, cityresidence, teteDeSerie };
			}
//			System.out.println(data[1][6]+" | "+data[1][7]);
		}
		return data;
	}

	/**
	 * Gets the flag image path by acronym.
	 *
	 * @param acroNat the acro nat
	 * @return the flag image path by acronym
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static String getFlagImagePathByAcronym(String acroNat) throws SQLException, ClassNotFoundException {
		String imagePath = null;
		if (acroNat != " ") {
			String requete = "SELECT ImgFlag FROM Flag WHERE NomAcro = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
				preparedStatement.setString(1, acroNat);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					imagePath = resultSet.getString("ImgFlag");
				}
			}
		} else {
			imagePath = "resources"+File.separator+"imgInterface"+File.separator+"clear.png";
		}
		
		return imagePath;
	}

	/**
	 * Gets the last player ID.
	 *
	 * @param bddname the bddname
	 * @return the last player ID
	 * @throws SQLException the SQL exception
	 */
	public static int getLastPlayerID(String bddname) throws SQLException {
		int lastPlayerID = -1;

		String query = "SELECT MAX(id) AS last_id FROM " + bddname;

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

			if (resultSet.next()) {
				lastPlayerID = resultSet.getInt("last_id");
			}
		}

		return lastPlayerID;
	}

	/**
	 * Gets the all players info from database.
	 *
	 * @param bddName the bdd name
	 * @return the all players info from database
	 * @throws SQLException the SQL exception
	 */
	public static String[][] getAllPlayersInfoFromDatabase(String bddName) throws SQLException {
		String[][] playersInfo = new String[compterNbElementsBDD(bddName)][];

		String query = "SELECT NOM, PRENOM FROM " + bddName;

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
			int row = 0; // Pour suivre l'index de la premi�re dimension (les joueurs)
			while (resultSet.next()) {
//				int id = resultSet.getInt("id");
				String name = resultSet.getString("nom");
				String surname = resultSet.getString("prenom");
				String[] playerData = { name, surname};
				playersInfo[row] = playerData;
				row++; // Passez au joueur suivant
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les erreurs de la base de donn�es ici
		}

		return playersInfo;
	}

	/**
	 * Gets the names from database.
	 *
	 * @param nomBDD the nom BDD
	 * @return the names from database
	 */
	public static String[] getNamesFromDatabase(String nomBDD) {
		String requete = "SELECT Nom FROM " + nomBDD;// +" Event";
		if (nomBDD == "flag") {
			requete = "SELECT nomacro FROM " + nomBDD;// +" Event";
		}

		ArrayList<String> names = new ArrayList<>();

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			while (resultSet.next()) {
				String name = new String();
				if (nomBDD == "flag") {
					name = resultSet.getString("nomacro");
				} else
					name = resultSet.getString("Nom");
				names.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Convertir la liste en tableau de cha�nes
		String[] nameArray = new String[names.size()];
		names.toArray(nameArray);

		return nameArray;
	}

	/**
	 * Gets the data.
	 *
	 * @param selectedBDD the selected BDD
	 * @param start       the start
	 * @param pageSize    the page size
	 * @return the data
	 */
	public static List<String[]> getData(String selectedBDD, int start, int pageSize) {
		List<String[]> data = new ArrayList<>();

		String requete = "SELECT id, Sexe, Nom, Prenom, NomAfficher, Nat, Country, Birthdate, ImgJoueur, Ranking, height, hand, age, "
				+ "weight, PRIZETOTAL, BIRTHPLACE, CITYRESIDENCE,TETEDESERIE FROM " + selectedBDD + " LIMIT ?, ?";
		System.out.println("  get all data PLayer from database : " + selectedBDD);

		try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
			preparedStatement.setInt(1, start);
			preparedStatement.setInt(2, pageSize);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String[] playerData = new String[] { resultSet.getString("id"), resultSet.getString("Sexe"),
							resultSet.getString("Nom"), resultSet.getString("Prenom"),
							resultSet.getString("NomAfficher"), resultSet.getString("Nat"),resultSet.getString("Country"),
							resultSet.getString("ImgJoueur"), resultSet.getString("Ranking"),
							resultSet.getString("PRIZETOTAL"), resultSet.getString("height"),
							resultSet.getString("hand"), resultSet.getString("age"), resultSet.getString("weight"),
							resultSet.getString("Birthdate"), resultSet.getString("BIRTHPLACE"),
							resultSet.getString("CITYRESIDENCE"), resultSet.getString("TETEDESERIE") };
					data.add(playerData);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les exceptions de base de donn�es
		}
		return data;
	}

	/**
	 * Gets the object joueur.
	 *
	 * @param selectedBDD the selected BDD
	 * @param nomJoueur   the nom joueur
	 * @return the object joueur
	 * @throws SQLException the SQL exception
	 */
	public static String[] getObjectJoueur(String selectedBDD, String nomJoueur) throws SQLException {
		String[] playerData = null;

		String requete = "SELECT id, Sexe, Nom, Prenom, NomAfficher, Nat, Country, Birthdate, ImgJoueur, Ranking, height, hand, age, "
				+ "weight, PRIZETOTAL, BIRTHPLACE, CITYRESIDENCE,TETEDESERIE FROM " + selectedBDD + " WHERE Nom = '"
				+ nomJoueur + "'";

		System.out.println("  get data from player : " + nomJoueur + " from database : " + selectedBDD);
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			if (resultSet.next()) {
				playerData = new String[] { resultSet.getString("id"), resultSet.getString("Sexe"),
						resultSet.getString("Nom"), resultSet.getString("Prenom"), resultSet.getString("NomAfficher"),
						resultSet.getString("Nat"), resultSet.getString("Country"), resultSet.getString("ImgJoueur"), resultSet.getString("Ranking"),
						resultSet.getString("PRIZETOTAL"), resultSet.getString("height"), resultSet.getString("hand"),
						resultSet.getString("age"), resultSet.getString("weight"), resultSet.getString("Birthdate"),
						resultSet.getString("BIRTHPLACE"), resultSet.getString("CITYRESIDENCE"),
						resultSet.getString("TETEDESERIE") };
			}
		}
		return playerData;
	}

	/**
	 * Gets the all list player table name.
	 *
	 * @return the all list player table name
	 * @throws SQLException the SQL exception
	 */
	public static void getAllListPlayerTableName() throws SQLException {
		String requete = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.SYSTEM_TABLES WHERE TABLE_TYPE='TABLE'";
		tabBdd.clear();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				// Ajouter le nom de la table � la liste
				try {
					if (!tableName.equals("EVENT") && !tableName.equals("FLAG") && !tableName.equals("BACKGROUND")) {
						tabBdd.add(tableName);
						System.out.println("  get player tables; name of table retrieved : " + tableName);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Update background in database.
	 *
	 * @param oldBackgroundName the old background name
	 * @param newBackground     the new background
	 */
	// ----------------------------------------------------
	// UPDATE------------------------------------------------------------------------------
	public static void updateBackgroundInDatabase(String oldBackgroundName, String[] newBackground) {

		System.out.println(
				"  old background name = " + oldBackgroundName + ", new background name = " + newBackground[0]);
		updateBackgroundIfNeeded(oldBackgroundName, newBackground[0]);

		String deleteQuery = "DELETE FROM Background WHERE Nom = ?";
		String insertQuery = "INSERT INTO Background (Nom, Img_1, Img_2, Img_3, Img_4, Img_5) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			// Supprimer l'ancien background
			if (oldBackgroundName != "empty") {
				String[] allImgOld = getOneBackground(oldBackgroundName).getAllImage();
				ArrayList<String> imgToNotDelete = new ArrayList<String>();
				ArrayList<Background> listBackground = DataBackgroundList();
				for (Background background : listBackground) {
					if (!background.getNom().equals(oldBackgroundName)) {
						String[] allImg = background.getAllImage();
						for (int i = 0; i < allImg.length; i++) {
							if (allImg[i].equals(allImgOld[i]) || background.getNom().equals("backgroundExample32")) {
								System.out.println("image already used : "+allImg[i]);
								imgToNotDelete.add(allImgOld[i]);
							}
						}
					}
				}
//					String[] allImgOld = getOneBackground(oldBackgroundName).getAllImage();
				for (int i = 0; i < allImgOld.length; i++) {
					System.out.println("old bg img : " + allImgOld[i] + " | new img : " + newBackground[i + 1]);
					if (!imgToNotDelete.contains(allImgOld[i])) {
						if (!allImgOld[i].equals(newBackground[i + 1])) {
							deleteFile(allImgOld[i]);
						}
					}
				}		
			}			
			
			PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
			deleteStatement.setString(1, oldBackgroundName);
			deleteStatement.executeUpdate();
			System.out.println("  - remove background : " + oldBackgroundName);
			// Ins�rer le nouveau background
			PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
			insertStatement.setString(1, newBackground[0]);
			insertStatement.setString(2, newBackground[1]);
			insertStatement.setString(3, newBackground[2]);
			insertStatement.setString(4, newBackground[3]);
			insertStatement.setString(5, newBackground[4]);
			insertStatement.setString(6, newBackground[5]);
			insertStatement.executeUpdate();
			System.out.println("  + add background : " + newBackground[0]);
			System.out.println("  Background update successful");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("  ! Error during background update !");
		}
	}

	/**
	 * Update background if needed.
	 *
	 * @param oldBackgroundName the old background name
	 * @param newBackgroundName the new background name
	 */
	public static void updateBackgroundIfNeeded(String oldBackgroundName, String newBackgroundName) {
		// V�rifie si l'ancien fond est utilis� dans la table "Event"
		String checkQuery = "SELECT COUNT(*) FROM Event WHERE NomBG = ?";
		try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
			checkStatement.setString(1, oldBackgroundName);
			ResultSet resultSet = checkStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) > 0) {
				// L'ancien fond est utilis� dans la table "Event"
				int choice = JOptionPane.showConfirmDialog(null,
						"This background is used by events. Do you want to update the events to use the new background?",
						"Background modification", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					// Mettez � jour les enregistrements dans "Event" pour utiliser le nouveau fond
					String updateQuery = "UPDATE Event SET NomBG = ? WHERE NomBG = ?";
					try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
						updateStatement.setString(1, newBackgroundName);
						updateStatement.setString(2, oldBackgroundName);
						updateStatement.executeUpdate();
					}
					System.out.println("  update event for new background");
				} else {
					System.out.println("  ! background not update");
				}
			} else {
				// L'ancien fond n'est pas utilis� par un �v�nement
				System.out.println("  background " + oldBackgroundName + " was not used");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete background.
	 *
	 * @param backgroundName the background name
	 */
	public static void deleteBackground(String backgroundName) {
		// V�rifiez si le fond est utilis� dans la table "Event"
		String checkQuery = "SELECT COUNT(*) FROM Event WHERE NomBG = ?";
		try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
			checkStatement.setString(1, backgroundName);
			ResultSet resultSet = checkStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) > 0) {
				// Le fond est utilis� dans la table "Event"
				int choice = JOptionPane.showConfirmDialog(null,
						"This background is used by events. Do you want to update the events to use a different background?",
						"Background delete", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					// Mettez � jour les enregistrements dans "Event" pour utiliser un autre fond
					// (par exemple, un fond par d�faut)
					String newBackgroundName = ""; // Remplacez par le nom de l'arri�re-plan par d�faut
					String updateQuery = "UPDATE Event SET NomBG = ? WHERE NomBG = ?";
					try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
						updateStatement.setString(1, newBackgroundName);
						updateStatement.setString(2, backgroundName);
						updateStatement.executeUpdate();
						System.out.println("  update evenet with defaut background");
					}
					
					String[] allImgOld = getOneBackground(backgroundName).getAllImage();
					ArrayList<String> imgToNotDelete = new ArrayList<String>();
					ArrayList<Background> listBackground = DataBackgroundList();
					for (Background background : listBackground) {
						if (!background.getNom().equals(backgroundName)) {
							String[] allImg = background.getAllImage();
							for (int i = 0; i < allImg.length; i++) {
								System.out.println("background : "+background.getNom()+", img : "+allImg[i]);
								if (allImg[i].equals(allImgOld[i]) || background.getNom().equals("backgroundExample32")) {
									imgToNotDelete.add(allImgOld[i]);
								}
							}
						}
					}
					for (int i = 0; i < allImgOld.length; i++) {
						if (!imgToNotDelete.contains(allImgOld[i])) {
							deleteFile(allImgOld[i]);
						}
					}		
							
//					String[] allImg = getOneBackground(backgroundName).getAllImage();
//					for (String pathImg : allImg) {
//						deleteFile(pathImg);
//					}
					String deleteQuery = "DELETE FROM Background WHERE Nom = ?";
					try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
						deleteStatement.setString(1, backgroundName);
						deleteStatement.executeUpdate();
						System.out.println("  - Background " + backgroundName + " has been deleted");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("  ! events were not upadted");
				}
			} else {
				// Le fond n'est pas utilis� par un �v�nement, vous pouvez le supprimer en toute
				// s�curit�
				String[] allImgOld = getOneBackground(backgroundName).getAllImage();
				ArrayList<String> imgToNotDelete = new ArrayList<String>();
				ArrayList<Background> listBackground = DataBackgroundList();
				for (Background background : listBackground) {
					if (!background.getNom().equals(backgroundName)) {
						String[] allImg = background.getAllImage();
						for (int i = 0; i < allImg.length; i++) {
							System.out.println("background : "+background.getNom()+", img : "+allImg[i]);
							if (allImg[i].equals(allImgOld[i]) || background.getNom().equals("backgroundExample32")) {
								imgToNotDelete.add(allImgOld[i]);
							}
						}
					}
				}
				for (int i = 0; i < allImgOld.length; i++) {
					if (!imgToNotDelete.contains(allImgOld[i])) {
						deleteFile(allImgOld[i]);
					}
				}
				String deleteQuery = "DELETE FROM Background WHERE Nom = ?";
				try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
					deleteStatement.setString(1, backgroundName);
					deleteStatement.executeUpdate();
					System.out.println("  - Background " + backgroundName + " has been deleted");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update event in database.
	 *
	 * @param currentName       the current name
	 * @param newName           the new name
	 * @param backgroundNewName the background new name
	 */
	public static void updateEventInDatabase(String currentName, String newName, String backgroundNewName) {
		String updateQuery = "UPDATE Event SET Nom = ?, NomBG = ? WHERE Nom = ?";
		try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
			updateStatement.setString(1, newName);
			updateStatement.setString(2, backgroundNewName);
			updateStatement.setString(3, currentName);
			updateStatement.executeUpdate();
			System.out.println("  event " + currentName + " has been updated with new name and new background");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("  ! Error during update event in database !");
		}
	}

	/**
	 * Delete event.
	 *
	 * @param eventName the event name
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	public static void deleteEvent(String eventName) throws SQLException, IOException {
		int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this event?", "Event delete",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			String deleteQuery = "DELETE FROM event WHERE Nom = ?";
			try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
				updateStatement.setString(1, eventName);
				updateStatement.executeUpdate();
			}
			System.out.println("  - event " + eventName + " has been deleted");
			Path targetPath = Paths.get("resources"+File.separator+"config" + File.separator + eventName);
			ConfigurationSaveLoad.deleteFolder(targetPath);
		} else {
			System.out.println("  ! event hasn't been deleted");
		}
	}
	public static void deleteFlag(String acroFlag) throws SQLException, IOException, ClassNotFoundException {
		int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this flag?", "flag delete",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			String deleteQuery = "DELETE FROM flag WHERE NomAcro = ?";
			deleteFile(getFlagImagePathByAcronym(acroFlag));
			try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
				updateStatement.setString(1, acroFlag);
				updateStatement.executeUpdate();
			}
			System.out.println("  - flag " + acroFlag + " has been deleted");
			File fileFlag = new File("flag" + File.separator + acroFlag+".png");
			if(fileFlag.exists()) {
				fileFlag.delete();
			}
		} else {
			System.out.println("  ! event hasn't been deleted");
		}
	}

	/**
	 * Delete joueur.
	 *
	 * @param playertName the playert name
	 * @param bddname     the bddname
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException 
	 */
	public static void deleteJoueur(String playertName, String bddname) throws SQLException, ClassNotFoundException {
		int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this player ?", "Player delete",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			deleteFile(getJoueurParNom(playertName, bddname).getImgJoueur());
			String deleteQuery = "DELETE FROM " + bddname + " WHERE Nom = ?";
			try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
				updateStatement.setString(1, playertName);
				updateStatement.executeUpdate();
			}
			System.out.println("  - PLayer " + playertName + " has been deleted");
		} else {
			System.out.println("  ! player hasn't been deleted");
		}
	}

	/**
	 * Delete player table.
	 *
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException 
	 */
	public static void deletePlayerTable() throws SQLException, ClassNotFoundException {
		for (String tableName : tabBdd) {
			if (!tableName.equals("ATP") && !tableName.equals("WTA")) {
				for (Joueur joueur : getAllJoueurs(tableName)) {
					System.out.println("path to img joueur : "+joueur.getImgJoueur());
					deleteFile(joueur.getImgJoueur());
				}
				String deleteQuery = "DROP TABLE IF EXISTS " + tableName;
				try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
					updateStatement.executeUpdate();
				}
			}
		}
		System.out.println("  all player table have been deleted except ATP and WTA");
	}

	/**
	 * Delete one player table.
	 *
	 * @param s_tableNameToDelete the s table name to delete
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException 
	 */
	public static void deleteOnePlayerTable(String s_tableNameToDelete) throws SQLException, ClassNotFoundException {
		String s_tableName = s_tableNameToDelete.toUpperCase();
		if (!s_tableName.equals("ATP") && !s_tableName.equals("WTA") && !s_tableName.equals("EVENT")
				&& !s_tableName.equals("BACKGROUND") && !s_tableName.equals("FLAG")) {
			
			for (Joueur joueur : getAllJoueurs(s_tableNameToDelete)) {
				System.out.println("path to img joueur : "+joueur.getImgJoueur());
				deleteFile(joueur.getImgJoueur());
			}
			String deleteQuery = "DROP TABLE IF EXISTS " + s_tableName;
			try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
				updateStatement.executeUpdate();
			}
			System.out.println("  - table " + s_tableName + " has been deleted");
		}
	}

	/**
	 * Update flag in database.
	 *
	 * @param currentName the current name
	 * @param newDrapeau     the new flag
	 */
	public static void updateFlagInDatabase(String currentName, Drapeau newDrapeau) {
		try {
			
			System.out.println(currentName+", drapeau : "+newDrapeau.getNom()+" | "+newDrapeau.getPays()+" | "+newDrapeau.getImageDrapeau());
			String updateQuery = "UPDATE Flag SET nomacro = ?, nompays = ?, imgflag = ? WHERE nomacro = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, newDrapeau.getNom());
				preparedStatement.setString(2, newDrapeau.getPays());
				preparedStatement.setString(3, newDrapeau.getImageDrapeau());
				preparedStatement.setString(4, currentName);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erreur lors de la mise � jour du drapeau dans la base de donn�es.");
		}
	}


	/**
	 * Update players in database.
	 *
	 * @param id            the id
	 * @param sexe          the sexe
	 * @param playerName    the player name
	 * @param playerSurname the player surname
	 * @param displayName   the display name
	 * @param acroNat       the acro nat
	 * @param flag          the flag
	 * @param birthdate     the birthdate
	 * @param imgJoueur     the img joueur
	 * @param ranking       the ranking
	 * @param height        the height
	 * @param hand          the hand
	 * @param age           the age
	 * @param weight        the weight
	 * @param prize         the prize
	 * @param birthplace    the birthplace
	 * @param cityResidence the city residence
	 * @param bddName       the bdd name
	 * @param bddchoosen
	 */
	public static void updatePlayersInDatabase(int id, String sexe, String playerName, String playerSurname,
			String displayName, String acroNat, String country, String flag, String birthdate, String imgJoueur, int ranking,
			String height, String hand, String age, String weight, String prize, String birthplace,
			String cityResidence, String teteDeSerie, String bddchoosen) {

		try {
			// Cr�ez une requ�te SQL pour mettre � jour les donn�es du joueur
			String updateQuery = "UPDATE " + bddchoosen
					+ " SET Sexe = ?, Nom = ?, Prenom = ?, NomAfficher = ?, Nat = ?,Country = ?, Birthdate = ?, "
					+ "ImgJoueur = ?, Ranking = ?, height = ?, hand = ?, age = ?, weight = ?, PRIZETOTAL = ?, "
					+ "BIRTHPLACE = ?, CITYRESIDENCE = ?,TETEDESERIE = ? WHERE id = ?";
			System.out.println(updateQuery);
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, sexe);
				preparedStatement.setString(2, playerName);
				preparedStatement.setString(3, playerSurname);
				preparedStatement.setString(4, displayName);
				preparedStatement.setString(5, acroNat);
				preparedStatement.setString(6, country);
				preparedStatement.setString(7, birthdate);
				preparedStatement.setString(8, imgJoueur);
				preparedStatement.setInt(9, ranking);
				preparedStatement.setString(10, height);
				preparedStatement.setString(11, hand);
				preparedStatement.setString(12, age);
				preparedStatement.setString(13, weight);
				preparedStatement.setString(14, prize);
				preparedStatement.setString(15, birthplace);
				preparedStatement.setString(16, cityResidence);
				preparedStatement.setString(17, teteDeSerie);
				preparedStatement.setInt(18, id);
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate(); // Ex�cutez la requ�te de mise � jour
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les erreurs de la base de donn�es ici
		}
	}

	/**
	 * Update infos sup joueur.
	 *
	 * @param id_recup     the id recup
	 * @param s_birthdate  the s birthdate
	 * @param i_ranking    the i ranking
	 * @param d_height     the d height
	 * @param s_plays      the s plays
	 * @param i_age        the i age
	 * @param i_weight     the i weight
	 * @param s_prizetotal the s prizetotal
	 * @param s_birthplace the s birthplace
	 * @param s_residence  the s residence
	 * @param bddName      the bdd name
	 */
	public static void UpdateInfosSupJoueur(int id_recup, String s_birthdate, int i_ranking, String d_height,
			String s_plays, int i_age, String i_weight, String s_prizetotal, String s_birthplace, String s_residence,
			String bddName) {
		try {
			// Cr�ez une requ�te SQL pour mettre � jour les donn�es du joueur
			String updateQuery = "UPDATE " + bddName
					+ " SET Birthdate = ?, Ranking = ?, height = ?, hand = ?, age = ?, weight = ?, PRIZETOTAL = ?, "
					+ "BIRTHPLACE = ?, CITYRESIDENCE = ? WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, s_birthdate);
				preparedStatement.setInt(2, i_ranking);
				preparedStatement.setString(3, d_height);
				preparedStatement.setString(4, s_plays);
				preparedStatement.setString(5, i_age + "");
				preparedStatement.setString(6, i_weight + "");
				preparedStatement.setString(7, s_prizetotal);
				preparedStatement.setString(8, s_birthplace);
				preparedStatement.setString(9, s_residence);
				preparedStatement.setInt(10, id_recup);

				preparedStatement.executeUpdate(); // Ex�cutez la requ�te de mise � jour
			}
			System.out.println("  update additional information from api in database : " + bddName);
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les erreurs de la base de donn�es ici
		}

	}

	/**
	 * Update img joueur.
	 *
	 * @param id      the id
	 * @param path    the path
	 * @param bddName the bdd name
	 */
	// mise a jour du chemin vers l'image du joueur
	public static void updateImgJoueur(int id, String path, String bddName) {
		System.out.println("  image pathh from the player who is updated : " + path);
		try {
			
			String updateQuery = "UPDATE " + bddName + " SET ImgJoueur = ? WHERE id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, path);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erreur lors de la mise a jour de l'image du joueur dans la base de donnees.");
		}
	}

	/**
	 * Update age joueur.
	 *
	 * @param id      the id
	 * @param age     the age
	 * @param bddName the bdd name
	 */
	public static void updateAgeJoueur(int id, int age, String bddName) {
		try {

			String updateQuery = "UPDATE " + bddName + " SET ImgJoueur = ? WHERE id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setInt(1, age);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erreur lors de la mise � jour du drapeau dans la base de donn�es.");
		}
	}
	// -------------------------------------------------------------------VERIFICATION-------------------------------------------------------------------------------------//

	/**
	 * Verif image.
	 *
	 * @param id      the id
	 * @param bddName the bdd name
	 * @return true, if successful
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 */
	// v�rifiquation si l'image du joueur existe
	public static boolean verif_image(int id, String bddName) throws SQLException, ClassNotFoundException, IOException {
		System.out.println("  checking if an image exist");
		boolean b_image_existe = false;
		String s_requete = "SELECT * FROM " + bddName + " WHERE id = " + id;
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(s_requete)) {
			while (resultSet.next()) {
				if (resultSet.next()) {
					// si la case est case est vide
					if (resultSet.getString("ImgJoueur") == "resources"+File.separator+"imgInterface"+File.separator+"clear.png" || resultSet.getString("ImgJoueur") == null
							|| resultSet.getString("ImgJoueur") == "" || resultSet.getString("ImgJoueur").isEmpty()) {
						b_image_existe = false;
						System.out.println("  ! image missing");
						return b_image_existe;
					} else {
						System.out.println("  image exists");
						b_image_existe = true;
					}
				}
			}
		}
		return b_image_existe;
	}

	/**
	 * Verif infos manquante.
	 *
	 * @param id      the id
	 * @param bddName the bdd name
	 * @return true, if successful
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	// renvoi true si des infos sont manquantes sinon renvoie false
	public static boolean verifInfosManquante(int id, String bddName) throws SQLException, ClassNotFoundException {
		System.out.println("  player informations checking in database : " + bddName);

		boolean b_infosManquante = false;
		String s_requete = "SELECT * FROM " + bddName + " WHERE id = " + id;

		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(s_requete)) {
			while (resultSet.next()) {
				// Si l'une des colonnes est vide, renvoyer true
				if (resultSet.getString("BIRTHPLACE").isEmpty() || resultSet.getString("PRIZETOTAL").isEmpty()
						|| resultSet.getString("weight").isEmpty() || resultSet.getString("ImgJoueur").isEmpty()
						|| resultSet.getString("birthdate").isEmpty() || resultSet.getString("height").equals("0")
						|| resultSet.getString("hand").isEmpty() || resultSet.getString("age").equals("0")) {
					b_infosManquante = true;
					return b_infosManquante;
				} else {
					b_infosManquante = false;
				}
			}
		}

		return b_infosManquante;
	}

	/**
	 * Verifier et creer tables.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	// verifications les tables existe =s (efectuer au demarage)
	public static void verifierEtCreerTables() throws ClassNotFoundException, SQLException {
		// Liste des noms de tables � v�rifier
		String[] tables = { "Background", "Event", "Flag", "ATP", "WTA" };
		Background backgroundExample = new Background("backgroundExample32");
		for (String table : tables) {
			if (!tableExiste(table)) {
				// Si la table n'existe pas, la creer
				System.out.println("  table : " + table + " doesn't exists. Creation in progress ... ");
				if (table.equals("Background")) {
					executerRequeteSQL("CREATE TABLE " + table
							+ " (Nom VARCHAR(120) PRIMARY KEY, Img_1 varchar(255), Img_2 varchar(255), Img_3 varchar(255), Img_4 varchar(255), Img_5 varchar(255));");

					backgroundExample.setImage_1("resources"+File.separator+"Background" + File.separator + "1bgFull32.png");
					backgroundExample.setImage_2("resources"+File.separator+"Background" + File.separator + "2bgPlayer.png");
					backgroundExample.setImage_3("resources"+File.separator+"Background" + File.separator + "3bgGame.png");
					backgroundExample.setImage_4("resources"+File.separator+"Background" + File.separator + "4bgTab32.png");
					backgroundExample.setImage_5("resources"+File.separator+"Background" + File.separator + "5bgWaiting.png");
					BDD_v2.insertionBackgroundDansBDD(backgroundExample);
				} else if (table.equals("Event")) {
					executerRequeteSQL("CREATE TABLE " + table + " (Nom VARCHAR(120) PRIMARY KEY, NomBG VARCHAR(120));");
					Evenement eventExample = new Evenement("eventExample32");
					eventExample.setBackground(backgroundExample);// BDD_v2.getOneBackground("backgroundExample"));
					insertionEventDansBDD(eventExample);
				} else if (table.equals("Flag")) {
					executerRequeteSQL(
							"CREATE TABLE " + table + " (NomAcro VARCHAR(10) PRIMARY KEY, NomPays VARCHAR(125), ImgFlag varchar(255));");
					Drapeau.chargerDrapeau();
				} else {
					creationNewTable(table);
//					executerRequeteSQL("CREATE TABLE " + table
//							+ " (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100), NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50), ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10), weight varchar(20), PRIZETOTAL varchar(60), BIRTHPLACE varchar(200), CITYRESIDENCE varchar(200), FOREIGN KEY (Nat) REFERENCES Flag(NomAcro));");
				}
				System.out.println("  + Table " + table + " creating with success");
			} else
				System.out.println("  Table : " + table + " already exists !");
		}
		// verifications donn�es atp et wta
		if (compterNbElementsBDD("ATP") < 1) {
			try {
				MainJFX.API.TAB_ATP();
			} catch (ClassNotFoundException | JSONException | IOException | InterruptedException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (compterNbElementsBDD("WTA") < 1) {
			try {
				MainJFX.API.TAB_WTA();
			} catch (ClassNotFoundException | JSONException | IOException | InterruptedException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Table existe.
	 *
	 * @param tableName the table name
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private static boolean tableExiste(String tableName) throws SQLException {
		ResultSet tables = connection.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
		return tables.next();
	}

	/**
	 * Export table.
	 *
	 * @param tableName the table name
	 */
	// ---------------------------------------------------------export /
	// import------------------------------------------------------------------------------------
	public static void exportTable(JFrame frameParent, String tableName) {
		// Requ�te SQL pour r�cup�rer toutes les donn�es de la table
		String query = "SELECT * FROM " + tableName;

		// Cr�ation d'un s�lecteur de fichier
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export to CSV file");
		// Pr�-remplir le nom de fichier avec le nom de la table
		fileChooser.setSelectedFile(new java.io.File(tableName + ".csv"));
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try (Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(query);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileChooser.getSelectedFile()), StandardCharsets.UTF_8))) {

				// �crire l'en-t�te CSV
				StringBuilder header = new StringBuilder();
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					header.append(resultSet.getMetaData().getColumnName(i)).append(";");
				}
				header.deleteCharAt(header.length() - 1); // Supprimer la virgule finale
				writer.write(header.toString() + "\n");

				// �crire les donn�es CSV
				while (resultSet.next()) {
					StringBuilder row = new StringBuilder();
					for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
						row.append(resultSet.getString(i)).append(";");
					}
					row.deleteCharAt(row.length() - 1); // Supprimer la virgule finale
					writer.write(row.toString() + "\n");
				}

				JOptionPane.showMessageDialog(frameParent, "Export CSV complete.");

			} catch (SQLException | IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frameParent, "Error during CSV export : " + e.getMessage());
			}
		}
	}

	public static void exportTableToZip(JFrame frameParent, String tableName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export to ZIP file");
        fileChooser.setSelectedFile(new File(tableName + ".zip"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Export annulé.");
            return;
        }

        File zipFile = fileChooser.getSelectedFile();
        if (!zipFile.getName().toLowerCase().endsWith(".zip")) {
            zipFile = new File(zipFile.getAbsolutePath() + ".zip");
        }

        // Créer un fichier CSV temporaire
        File csvFile = new File(System.getProperty("java.io.tmpdir"), tableName + ".csv");

        try {
            exportTableToCSV(tableName, csvFile); //  Générer le fichier CSV

            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
                //  Ajouter le fichier CSV au ZIP
                ListOfEventsFrame.addFileToZip(zipOut, csvFile, tableName + ".csv");

                // Ajouter les images référencées
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT ImgJoueur FROM " + tableName)) {
                    
                    while (resultSet.next()) {
                        String imagePath = resultSet.getString("ImgJoueur");
                        if (imagePath != null && !imagePath.isEmpty()) {
                            File imageFile = new File(imagePath);
                            if (imageFile.exists()) {
                                ListOfEventsFrame.addFileToZip(zipOut, imageFile, "player_image/" + imageFile.getName());
                            } else {
                                System.err.println("Image introuvable : " + imagePath);
                            }
                        }
                    }
                }

                JOptionPane.showMessageDialog(frameParent, "Export ZIP terminé : " + zipFile.getAbsolutePath());
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frameParent, "Erreur lors de l'export ZIP : " + e.getMessage());
        } finally {
            // 📌 Supprimer le fichier CSV temporaire après exportation
            if (csvFile.exists()) {
                csvFile.delete();
            }
        }
    }
	
	/**
     * Génère un fichier CSV à partir de la table SQL et l'enregistre en local.
     */
    private static void exportTableToCSV(String tableName, File csvFile) throws SQLException, IOException {
        String query = "SELECT * FROM " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8))) {

            // Écrire l'en-tête CSV
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                header.append(resultSet.getMetaData().getColumnName(i)).append(";");
            }
            header.deleteCharAt(header.length() - 1);
            writer.write(header.toString() + "\n");

            // Écrire les données CSV
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.append(resultSet.getString(i)).append(";");
                }
                row.deleteCharAt(row.length() - 1);
                writer.write(row.toString() + "\n");
            }
        }
    }

	/**
	 * Import CSV.
	 *
	 * @param tableName the table name
	 * @param filePath  the file path
	 * @throws SQLException           the SQL exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void importCSV(String tableName, String filePath) throws SQLException, IOException, ClassNotFoundException {
		creationNewTable(tableName);

		// Lecture du fichier CSV et insertion des donn�es dans la table
		String insertQuery = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			// Sp�cifier l'encodage UTF-8 lors de la lecture du fichier
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String line;
			// Ignorer la premi�re ligne si elle contient des en-t�tes de colonne
			boolean isFirstLine = true;
			while ((line = reader.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}
				System.out.println(line);
				// Diviser la ligne en valeurs s�par�es par des points-virgules
				String[] values = line.split(";");
				// Assigner les valeurs aux param�tres de la requ�te pr�par�e
				preparedStatement.setInt(1, Integer.parseInt(values[0])); // ID
				preparedStatement.setString(2, values[1]); // Sexe
				preparedStatement.setString(3, values[2]); // Nom
				preparedStatement.setString(4, values[3]); // Pr�nom
				preparedStatement.setString(5, values[4]); // Display name
				preparedStatement.setString(6, values[5]); // Acronyme de nationalit�
				preparedStatement.setString(7, values[6]); // Country
				preparedStatement.setString(8, values[7]); // Date de naissance
				preparedStatement.setString(9, values[8]); // Image joueur
				preparedStatement.setInt(10, Integer.parseInt(values[9])); // Classement
				preparedStatement.setInt(11, Integer.parseInt(values[10])); // Taille
				preparedStatement.setString(12, values[11]); // Main
				preparedStatement.setInt(13, Integer.parseInt(values[12])); // �ge
				preparedStatement.setInt(14, Integer.parseInt(values[13])); // Poids
				preparedStatement.setString(15, values[14]); // Gain total
				preparedStatement.setString(16, values[15]); // Lieu de naissance
				preparedStatement.setString(17, values[16]); // Lieu de r�sidence
				String seeding = " ";
				System.out.println(values.length);
				if (values.length > 17) {
					seeding = values[17];
				}
				preparedStatement.setString(18, seeding); // tete de serie
				// Ex�cuter la requ�te d'insertion
				preparedStatement.executeUpdate();
			}
			System.out.println("  Data imported successfully from CSV file.");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String filePath) {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(filePath));
            if (deleted) {
                System.out.println("Fichier supprimé : " + filePath);
            } else {
                System.out.println("Le fichier n'existe pas : " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
