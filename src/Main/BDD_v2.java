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

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.JSONException;

import Background.Background;
import Event.Evenement;
import Flags.Drapeau;
import Players.Joueur;
import Sauvegarde.ConfigurationSaveLoad;

public class BDD_v2 {
	static String url = "jdbc:hsqldb:file:database" + File.separator + "Tennis_BDD_v2;shutdown=true";
	static String login = "sa";
	static String password = "";
	public static String ATP = "ATP";
	public static String WTA = "WTA";
	public static ArrayList<String> tabBdd = new ArrayList<>();
	static Connection connection;

	// --------------------------------------- INITIALISATION --------------------------------------------------------------------------------------------------------------

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

	public static void executerRequeteSQL(String requete) throws ClassNotFoundException, SQLException {
		Class.forName("org.hsqldb.jdbcDriver");
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(requete);
		}catch (SQLException e) {
	        // G�rer les erreurs d'ex�cution de la requ�te SQL
	        System.err.println("Erreur lors de l'ex�cution de la requ�te SQL : " + e.getMessage());
	    }
	}

	static void deconnexionBDD() throws ClassNotFoundException, SQLException {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Database connexion closed");
			}
		} catch (SQLException e) {
			System.err.println("! Error during closing database ! " + e.getMessage());
		}
	}

	public static void suppressionsDesTables() throws ClassNotFoundException, SQLException {
		// nom des tales � d�finir
		String requete = "";
//		requete += "DROP TABLE CUSTOM IF EXISTS;";
		requete += "DROP TABLE WTA IF EXISTS;";
//		requete += "DROP TABLE ATP IF EXISTS;";
//		requete += "DROP TABLE Background IF EXISTS;";
//		requete += "DROP TABLE Event IF EXISTS;";
//		requete += "DROP TABLE Flag IF EXISTS;";

		executerRequeteSQL(requete);

		System.out.println("  - Tables deleted");
	}

	public static void creationdesTables() throws ClassNotFoundException, SQLException {
		String requete = "";
		requete += "CREATE TABLE Background (Nom VARCHAR(20) PRIMARY KEY, Img_1 varchar(255), Img_2 varchar(255), " + "Img_3 varchar(255), Img_4 varchar(255));";
		requete += "CREATE TABLE Event (Nom VARCHAR(20) PRIMARY KEY, NomBG VARCHAR(20));";// , FOREIGN KEY (NomBG) REFERENCES Background (Nom)

		requete += "CREATE TABLE Flag (NomAcro VARCHAR(10) PRIMARY KEY, ImgFlag varchar(255));";
		requete += "CREATE TABLE CUSTOM (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100)," + "NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10),"
				+ "weight varchar(20), PRIZETOTAL varchar(60),BIRTHPLACE varchar(200),CITYRESIDENCE varchar(200)," + "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";
		requete += "CREATE TABLE ATP (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100)," + "NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10),"
				+ "weight varchar(20), PRIZETOTAL varchar(60),BIRTHPLACE varchar(200),CITYRESIDENCE varchar(200)," + "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";
		requete += "CREATE TABLE WTA (id int PRIMARY KEY, Sexe varchar(15), Nom varchar(100), Prenom varchar(100)," + "NomAfficher varchar(100), Nat varchar(10), Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), Ranking int, height varchar(10), hand varchar(25), age varchar(10),"
				+ "weight varchar(20), PRIZETOTAL varchar(60),BIRTHPLACE varchar(200),CITYRESIDENCE varchar(200)," + "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";

		executerRequeteSQL(requete);

		System.out.println("  + create tables finished");
	}

	public static void creationNewTable(String name) throws ClassNotFoundException, SQLException {
		String requete = "";
		requete += "CREATE TABLE " + name + " (id int PRIMARY KEY, "
				+ "Sexe varchar(15), "
				+ "Nom varchar(100), "
				+ "Prenom varchar(100)," 
				+ "NomAfficher varchar(100), "
				+ "Nat varchar(10), "
				+ "Birthdate varchar(50),"
				+ "ImgJoueur varchar(255), "
				+ "Ranking int, "
				+ "height varchar(10), "
				+ "hand varchar(25), "
				+ "age varchar(10),"
				+ "weight varchar(20), "
				+ "PRIZETOTAL varchar(60),"
				+ "BIRTHPLACE varchar(200),"
				+ "CITYRESIDENCE varchar(200)," 
				+ "FOREIGN KEY (Nat) REFERENCES Flag(NomAcro))";
		executerRequeteSQL(requete);

		System.out.println("  + Player tables " + name + " added");
	}

	// --------------------------------------- INSERTIONS--------------------------------------------------------------------------------------------------------------

	public static void insertionBackgroundDansBDD(Background background) throws SQLException, ClassNotFoundException {
		StringBuilder requete = new StringBuilder("");

		requete.append("INSERT INTO Background (NOM, Img_1, Img_2, Img_3, Img_4, Img_5) VALUES" + "('" + background.getNom() + "', '" + background.getImage_1() + "', '" + background.getImage_2() + "', '"
				+ background.getImage_3() + "', '" + background.getImage_4()+ "', '" + background.getImage_5() + "');");

		executerRequeteSQL(requete.toString());
		System.out.println("  + Background : " + background.getNom() + " added");
	}

	public static void insertionEventDansBDD(Evenement event) throws SQLException, ClassNotFoundException {
		StringBuilder requete = new StringBuilder("");

		requete.append("INSERT INTO Event (NOM, NOMBG) VALUES" + "('" + event.getNom() + "', '" + event.getBackground().getNom() + "');");

		executerRequeteSQL(requete.toString());
		System.out.println("  + Evenement : " + event.getNom() + " added");
	}

	public static void insertionDrapeauDansBDD(Drapeau drapeau) throws SQLException, ClassNotFoundException {
		StringBuilder requete = new StringBuilder("");

		requete.append("INSERT INTO Flag (NomAcro, ImgFlag) VALUES" + "('" + drapeau.getNom() + "', '" + drapeau.getImageDrapeau() + "');");

		executerRequeteSQL(requete.toString());
		System.out.println("  + Drapeau de : " + drapeau.getNom() + " added");
	}

	public static void insertionJoueurDansBDD(Joueur joueur, String bddName) throws SQLException, ClassNotFoundException {
		try {
			String requete = "INSERT INTO " + bddName + " (id, Sexe, Nom, Prenom, NomAfficher, Nat, Birthdate, ImgJoueur, Ranking, height, hand, age, weight, PRIZETOTAL, BIRTHPLACE, CityResidence)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
				preparedStatement.setInt(1, joueur.getID());
				preparedStatement.setString(2, joueur.getSexe());
				preparedStatement.setString(3, joueur.getNom());
				preparedStatement.setString(4, joueur.getPrenom());
				preparedStatement.setString(5, joueur.getDisplay_name());
				preparedStatement.setString(6, joueur.getNatio_acronyme());
				preparedStatement.setString(7, joueur.getBirthDate());
				preparedStatement.setString(8, joueur.getImgJoueur());
				preparedStatement.setInt(9, joueur.getRank());
				preparedStatement.setString(10, joueur.getTaille() + "");
				preparedStatement.setString(11, joueur.getMain());
				preparedStatement.setInt(12, joueur.getAge());
				preparedStatement.setString(13, joueur.getWeight() + "");
				preparedStatement.setString(14, joueur.getPrizetotal());
				preparedStatement.setString(15, joueur.getBirthPlace());
				preparedStatement.setString(16, joueur.getCityResidence());

				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------- RECUPERATION------------------------------------------------------------------------------
	public static int compterNbElementsBDD(String NomBDD) throws SQLException {
		int count = 0;
		String requete = "SELECT COUNT(*) FROM " + NomBDD;

		try (PreparedStatement statement = connection.prepareStatement(requete); ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		}
		System.out.println("  numbers of element in database " + NomBDD + " = " + count);
		return count;
	}

	public static Joueur getJoueurParID(int joueurID, String bddname) throws SQLException, ClassNotFoundException {
		Joueur joueur = null;
		String requete = "SELECT * FROM " + bddname + " WHERE id = " + joueurID;// "CREATE INDEX nom_index ON Joueur (Nom);" +

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String sexe = resultSet.getString("Sexe");
				String nom = resultSet.getString("Nom");
				String prenom = resultSet.getString("Prenom");
				String display_name = resultSet.getString("NomAfficher");
				String natio_acronyme = resultSet.getString("Nat");
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

				joueur = new Joueur(id, sexe, nom, prenom, display_name, natio_acronyme, birthDate, imgJoueur, rank, Integer.parseInt(taille), main, Integer.parseInt(age), Integer.parseInt(weight),
						prizetotal, birthPlace, cityResidence);
			}
		}
		return joueur;
	}

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

				joueur = new Joueur(id, sexe, nom, prenom, display_name, natio_acronyme, birthDate, imgJoueur, rank, Integer.parseInt(taille), main, Integer.parseInt(age), Integer.parseInt(weight),
						prizetotal, birthPlace, cityResidence);
			}
		}
		return joueur;
	}

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

				Joueur joueur = new Joueur(id, sexe, nom, prenom, display_name, natio_acronyme, birthDate, imgJoueur, rank, Integer.parseInt(taille), main, Integer.parseInt(age),
						Integer.parseInt(weight), prizetotal, birthPlace, cityResidence);
				joueurs.add(joueur);
			}
		}
		return joueurs;
	}

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

	public static Evenement getEvenementByName(String eventName) throws SQLException {
		Evenement evenement = null;
		String requete = "SELECT Event.*, Background.* FROM Event " + "INNER JOIN Background ON Event.NomBG = Background.Nom WHERE Event.Nom = ?";

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
				backgroundtemp.setImage_4(img5);

				backgroundBDD.add(backgroundtemp);
			}
		}
		return backgroundBDD;
	}

	public static Background getOneBackground(String nomBackground) throws SQLException {
		System.out.println("  get background named : " + nomBackground);
		String requete = "SELECT Background.Nom AS BackgroundName, Background.Img_1, Background.Img_2, Background.Img_3, " + "Background.Img_4,Background.Img_5 FROM BACKGROUND WHERE Nom = '" + nomBackground + "'";
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

	public static String[][] DataFlag() throws SQLException {
		String requete = "SELECT Flag.NomAcro AS Acronyme, Flag.ImgFlag as Flag FROM Flag";

		int rowCount = 0;
		String[][] data = new String[compterNbElementsBDD("Flag")][2];

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			while (resultSet.next()) {
				rowCount++;
				String acronyme = resultSet.getString("Acronyme");
				String imagePath = resultSet.getString("Flag");

				data[rowCount - 1][0] = acronyme;
				data[rowCount - 1][1] = imagePath;
			}
		}
		return data;
	}

	public static String[][] DataJoueur(String bddName) throws SQLException, ClassNotFoundException {
		String requete = "SELECT id, Sexe, Nom, Prenom, NomAfficher, Nat, Birthdate, ImgJoueur, Ranking, height, hand, age, " + "weight, PRIZETOTAL, BIRTHPLACE, CITYRESIDENCE FROM " + bddName;

		// D�terminer la taille de la matrice
		int rowCount = 0;
		// Cr�er la matrice avec le nombre de lignes (rowCount) et 15 colonnes
		String[][] data = new String[compterNbElementsBDD(bddName)][16];
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
				// Stocker les donn�es dans la matrice
				data[rowCount - 1] = new String[] { String.valueOf(id), sexe, nom, prenom, nomAfficher, nat,
						flagImagePath, imgJoueur, String.valueOf(ranking), prizetotal, height, hand, age, weight,
						birthdate, birthplace, cityresidence };
			}
//			System.out.println(data[1][6]+" | "+data[1][7]);
		}
		return data;
	}

	public static String getFlagImagePathByAcronym(String acroNat) throws SQLException, ClassNotFoundException {
		String imagePath = null;
		if(acroNat != " ") {
			String requete = "SELECT ImgFlag FROM Flag WHERE NomAcro = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
				preparedStatement.setString(1, acroNat);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					imagePath = resultSet.getString("ImgFlag");
				}
			}
		}else {
			imagePath = "clear.png";
		}
		return imagePath;
	}

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

	public static String[][] getAllPlayersInfoFromDatabase(String bddName) throws SQLException {
		String[][] playersInfo = new String[compterNbElementsBDD(bddName)][];

		String query = "SELECT id, NomAfficher FROM " + bddName;

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
			int row = 0; // Pour suivre l'index de la premi�re dimension (les joueurs)
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String displayName = resultSet.getString("NomAfficher");
				String[] playerData = { displayName, String.valueOf(id) };
				playersInfo[row] = playerData;
				row++; // Passez au joueur suivant
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les erreurs de la base de donn�es ici
		}

		return playersInfo;
	}

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

	public static List<String[]> getData(String selectedBDD, int start, int pageSize) {
		List<String[]> data = new ArrayList<>();

		String requete = "SELECT id, Sexe, Nom, Prenom, NomAfficher, Nat, Birthdate, ImgJoueur, Ranking, height, hand, age, " + "weight, PRIZETOTAL, BIRTHPLACE, CITYRESIDENCE FROM " + selectedBDD
				+ " LIMIT ?, ?";
		System.out.println("  get all data PLayer from database : " + selectedBDD);

		try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
			preparedStatement.setInt(1, start);
			preparedStatement.setInt(2, pageSize);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String[] playerData = new String[] { resultSet.getString("id"), resultSet.getString("Sexe"), resultSet.getString("Nom"), resultSet.getString("Prenom"),
							resultSet.getString("NomAfficher"), resultSet.getString("Nat"), resultSet.getString("ImgJoueur"), resultSet.getString("Ranking"), resultSet.getString("PRIZETOTAL"),
							resultSet.getString("height"), resultSet.getString("hand"), resultSet.getString("age"), resultSet.getString("weight"), resultSet.getString("Birthdate"),
							resultSet.getString("BIRTHPLACE"), resultSet.getString("CITYRESIDENCE") };
					data.add(playerData);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les exceptions de base de donn�es
		}
		return data;
	}

	public static String[] getObjectJoueur(String selectedBDD, String nomJoueur) throws SQLException {
		String[] playerData = null;

		String requete = "SELECT id, Sexe, Nom, Prenom, NomAfficher, Nat, Birthdate, ImgJoueur, Ranking, height, hand, age, " + "weight, PRIZETOTAL, BIRTHPLACE, CITYRESIDENCE FROM " + selectedBDD
				+ " WHERE Nom = '" + nomJoueur + "'";

		System.out.println("  get data from player : "+nomJoueur+" from database : " + selectedBDD);
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(requete);

			if (resultSet.next()) {
				playerData = new String[] { resultSet.getString("id"), resultSet.getString("Sexe"), resultSet.getString("Nom"), resultSet.getString("Prenom"), resultSet.getString("NomAfficher"),
						resultSet.getString("Nat"), resultSet.getString("ImgJoueur"), resultSet.getString("Ranking"), resultSet.getString("PRIZETOTAL"), resultSet.getString("height"),
						resultSet.getString("hand"), resultSet.getString("age"), resultSet.getString("weight"), resultSet.getString("Birthdate"), resultSet.getString("BIRTHPLACE"),
						resultSet.getString("CITYRESIDENCE") };
			}
		}
		return playerData;
	}

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

	// ---------------------------------------------------- UPDATE------------------------------------------------------------------------------
	public static void updateBackgroundInDatabase(String oldBackgroundName, String[] newBackground) {

		System.out.println("  old background name = " + oldBackgroundName + ", new background name = " + newBackground[0]);
		updateBackgroundIfNeeded(oldBackgroundName, newBackground[0]);

		String deleteQuery = "DELETE FROM Background WHERE Nom = ?";
		String insertQuery = "INSERT INTO Background (Nom, Img_1, Img_2, Img_3, Img_4, Img_5) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			// Supprimer l'ancien background
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

	public static void updateBackgroundIfNeeded(String oldBackgroundName, String newBackgroundName) {
		// V�rifie si l'ancien fond est utilis� dans la table "Event"
		String checkQuery = "SELECT COUNT(*) FROM Event WHERE NomBG = ?";
		try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
			checkStatement.setString(1, oldBackgroundName);
			ResultSet resultSet = checkStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) > 0) {
				// L'ancien fond est utilis� dans la table "Event"
				int choice = JOptionPane.showConfirmDialog(null, "This background is used by events. Do you want to update the events to use the new background?", "Background modification",
						JOptionPane.YES_NO_OPTION);
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
				System.out.println("  background " + oldBackgroundName+" was not used");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteBackground(String backgroundName) {
		// V�rifiez si le fond est utilis� dans la table "Event"
		String checkQuery = "SELECT COUNT(*) FROM Event WHERE NomBG = ?";
		try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
			checkStatement.setString(1, backgroundName);
			ResultSet resultSet = checkStatement.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) > 0) {
				// Le fond est utilis� dans la table "Event"
				int choice = JOptionPane.showConfirmDialog(null, "This background is used by events. Do you want to update the events to use a different background?", "Background delete",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					// Mettez � jour les enregistrements dans "Event" pour utiliser un autre fond (par exemple, un fond par d�faut)
					String newBackgroundName = ""; // Remplacez par le nom de l'arri�re-plan par d�faut
					String updateQuery = "UPDATE Event SET NomBG = ? WHERE NomBG = ?";
					try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
						updateStatement.setString(1, newBackgroundName);
						updateStatement.setString(2, backgroundName);
						updateStatement.executeUpdate();
						System.out.println("  update evenet with defaut background");
					}
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
				// Le fond n'est pas utilis� par un �v�nement, vous pouvez le supprimer en toute s�curit�
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

	public static void deleteEvent(String eventName) throws SQLException, IOException {
		int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this event?", "Event delete", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			String deleteQuery = "DELETE FROM event WHERE Nom = ?";
			try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
				updateStatement.setString(1, eventName);
				updateStatement.executeUpdate();
			}
			System.out.println("  - event " + eventName + " has been deleted");
			Path targetPath = Paths.get("config" + File.separator + eventName);
			ConfigurationSaveLoad.deleteFolder(targetPath);
		} else {
			System.out.println("  ! event hasn't been deleted");
		}
	}

	public static void deleteJoueur(String playertName, String bddname) throws SQLException {
		int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this player ?", "Player delete", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
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

	public static void deletePlayerTable() throws SQLException {
		for (String tableName : tabBdd) {
			if (!tableName.equals("ATP") && !tableName.equals("WTA")) {
				String deleteQuery = "DROP TABLE IF EXISTS " + tableName;
				try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
					updateStatement.executeUpdate();
				}
			}
		}
		System.out.println("  all player table have been deleted except ATP and WTA");
	}
	public static void deleteOnePlayerTable(String s_tableNameToDelete) throws SQLException {
		String s_tableName = s_tableNameToDelete.toUpperCase();
		if (!s_tableName.equals("ATP") && !s_tableName.equals("WTA") && !s_tableName.equals("EVENT") && !s_tableName.equals("BACKGROUND") && !s_tableName.equals("FLAG")) {
			String deleteQuery = "DROP TABLE IF EXISTS " + s_tableName;
			try (PreparedStatement updateStatement = connection.prepareStatement(deleteQuery)) {
				updateStatement.executeUpdate();
			}
			System.out.println("  - table " + s_tableName + " has been deleted");
		}
	}

	public static void updateFlagInDatabase(String currentName, String[] newFlag) {
		try {

			String updateQuery = "UPDATE Flag SET nomacro = ?, imgflag = ? WHERE nomacro = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, newFlag[0]);
				preparedStatement.setString(2, newFlag[1]);
				preparedStatement.setString(3, currentName);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erreur lors de la mise � jour du drapeau dans la base de donn�es.");
		}
	}

	public static void updatePlayersInDatabase(int id, String sexe, String playerName, String playerSurname, String displayName, String acroNat, String flag, String birthdate, String imgJoueur,
			int ranking, String height, String hand, String age, String weight, String prize, String birthplace, String cityResidence, String bddName) {

		try {
			// Cr�ez une requ�te SQL pour mettre � jour les donn�es du joueur
			String updateQuery = "UPDATE " + bddName + " SET Sexe = ?, Nom = ?, Prenom = ?, NomAfficher = ?, Nat = ?, Birthdate = ?, "
					+ "ImgJoueur = ?, Ranking = ?, height = ?, hand = ?, age = ?, weight = ?, PRIZETOTAL = ?, " + "BIRTHPLACE = ?, CITYRESIDENCE = ? WHERE id = ?";
			System.out.println(updateQuery);
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, sexe);
				preparedStatement.setString(2, playerName);
				preparedStatement.setString(3, playerSurname);
				preparedStatement.setString(4, displayName);
				preparedStatement.setString(5, acroNat);
				preparedStatement.setString(6, birthdate);
				preparedStatement.setString(7, imgJoueur);
				preparedStatement.setInt(8, ranking);
				preparedStatement.setString(9, height);
				preparedStatement.setString(10, hand);
				preparedStatement.setString(11, age);
				preparedStatement.setString(12, weight);
				preparedStatement.setString(13, prize);
				preparedStatement.setString(14, birthplace);
				preparedStatement.setString(15, cityResidence);
				preparedStatement.setInt(16, id);
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate(); // Ex�cutez la requ�te de mise � jour
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les erreurs de la base de donn�es ici
		}
	}

	public static void UpdateInfosSupJoueur(int id_recup, String s_birthdate, int i_ranking, String d_height, String s_plays, int i_age, String i_weight, String s_prizetotal, String s_birthplace,
			String s_residence, String bddName) {
		try {
			// Cr�ez une requ�te SQL pour mettre � jour les donn�es du joueur
			String updateQuery = "UPDATE " + bddName + " SET Birthdate = ?, Ranking = ?, height = ?, hand = ?, age = ?, weight = ?, PRIZETOTAL = ?, "
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
			System.out.println("  update additional information from api in database : "+bddName);
		} catch (SQLException e) {
			e.printStackTrace();
			// G�rez les erreurs de la base de donn�es ici
		}

	}

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

	// v�rifiquation si l'image du joueur existe
	public static boolean verif_image(int id, String bddName) throws SQLException, ClassNotFoundException, IOException {
		System.out.println("  checking if an image exist");
		boolean b_image_existe = false;
		String s_requete = "SELECT * FROM " + bddName + " WHERE id = " + id;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(s_requete)) {
			while (resultSet.next()) {
				if (resultSet.next()) {
					// si la case est case est vide
					if (resultSet.getString("ImgJoueur") == "clear.png" || resultSet.getString("ImgJoueur") == null || resultSet.getString("ImgJoueur") == ""
							|| resultSet.getString("ImgJoueur").isEmpty()) {
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

	// renvoi true si des infos sont manquantes sinon renvoie false
	public static boolean verifInfosManquante(int id, String bddName) throws SQLException, ClassNotFoundException {
		System.out.println("  player informations checking in database : "+bddName);

		boolean b_infosManquante = false;
		String s_requete = "SELECT * FROM " + bddName + " WHERE id = " + id;

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(s_requete)) {
			while (resultSet.next()) {
				// Si l'une des colonnes est vide, renvoyer true
				if (resultSet.getString("BIRTHPLACE").isEmpty() 
						|| resultSet.getString("PRIZETOTAL").isEmpty()
						|| resultSet.getString("weight").isEmpty() 
						|| resultSet.getString("ImgJoueur").isEmpty() 
						|| resultSet.getString("birthdate").isEmpty()
						|| resultSet.getString("height").equals("0") 
						|| resultSet.getString("hand").isEmpty() 
						|| resultSet.getString("age").equals("0")) {
					b_infosManquante = true;
					return b_infosManquante;
				} else {
					b_infosManquante = false;
				}
			}
		}

		return b_infosManquante;
	}
	
	// verifications les tables existe =s (efectuer au demarage)
	public static void verifierEtCreerTables() throws ClassNotFoundException, SQLException {
		// Liste des noms de tables � v�rifier
		String[] tables = { "Background", "Event", "Flag", "ATP", "WTA" };
		Background backgroundExample = new Background("backgroundExample");
		for (String table : tables) {
			if (!tableExiste(table)) {
				// Si la table n'existe pas, la creer
				System.out.println("  table : " + table + " doesn't exists. Creation in progress ... ");
				if (table.equals("Background")) {
					executerRequeteSQL("CREATE TABLE " + table + " (Nom VARCHAR(20) PRIMARY KEY, Img_1 varchar(255), Img_2 varchar(255), Img_3 varchar(255), Img_4 varchar(255), Img_5 varchar(255));");

					backgroundExample.setImage_1("Background" + File.separator + "backgroundexample1.png");
					backgroundExample.setImage_2("Background" + File.separator + "backgroundexample2.png");
					backgroundExample.setImage_3("Background" + File.separator + "backgroundexample3.png");
					backgroundExample.setImage_4("Background" + File.separator + "backgroundexample4.png");
					backgroundExample.setImage_5("Background" + File.separator + "backgroundexample5.png");
					BDD_v2.insertionBackgroundDansBDD(backgroundExample);
				} else if (table.equals("Event")) {
					executerRequeteSQL("CREATE TABLE " + table + " (Nom VARCHAR(20) PRIMARY KEY, NomBG VARCHAR(20));");
					Evenement eventExample = new Evenement("eventExample");
					eventExample.setBackground(backgroundExample);// BDD_v2.getOneBackground("backgroundExample"));
					insertionEventDansBDD(eventExample);
				} else if (table.equals("Flag")) {
					executerRequeteSQL("CREATE TABLE " + table + " (NomAcro VARCHAR(10) PRIMARY KEY, ImgFlag varchar(255));");
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

	private static boolean tableExiste(String tableName) throws SQLException {
		ResultSet tables = connection.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
		return tables.next();
	}
	
	//---------------------------------------------------------export / import------------------------------------------------------------------------------------
	public static void exportTable(String tableName) {
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

				JOptionPane.showMessageDialog(null, "Export CSV complete.");

			} catch (SQLException | IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error during CSV export : " + e.getMessage());
			}
		}
	}
	
	public static void importCSV(String tableName, String filePath) throws SQLException, IOException, ClassNotFoundException {
	    creationNewTable(tableName);
	    
	    // Lecture du fichier CSV et insertion des donn�es dans la table
	    String insertQuery = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
	            // Diviser la ligne en valeurs s�par�es par des points-virgules
	            String[] values = line.split(";");
	            // Assigner les valeurs aux param�tres de la requ�te pr�par�e
	            preparedStatement.setInt(1, Integer.parseInt(values[0])); // ID
	            preparedStatement.setString(2, values[1]); // Sexe
	            preparedStatement.setString(3, values[2]); // Nom
	            preparedStatement.setString(4, values[3]); // Pr�nom
	            preparedStatement.setString(5, values[4]); // Display name
	            preparedStatement.setString(6, values[5]); // Acronyme de nationalit�
	            preparedStatement.setString(7, values[6]); // Date de naissance
	            preparedStatement.setString(8, values[7]); // Image joueur
	            preparedStatement.setInt(9, Integer.parseInt(values[8])); // Classement
	            preparedStatement.setInt(10, Integer.parseInt(values[9])); // Taille
	            preparedStatement.setString(11, values[10]); // Main
	            preparedStatement.setInt(12, Integer.parseInt(values[11])); // �ge
	            preparedStatement.setInt(13, Integer.parseInt(values[12])); // Poids
	            preparedStatement.setString(14, values[13]); // Gain total
	            preparedStatement.setString(15, values[14]); // Lieu de naissance
	            preparedStatement.setString(16, values[15]); // Lieu de r�sidence
	            // Ex�cuter la requ�te d'insertion
	            preparedStatement.executeUpdate();
	        }
	        System.out.println("  Data imported successfully from CSV file.");
	    } catch (SQLException | IOException e) {
	        e.printStackTrace();       
	    }
	}
}
