package API;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Flags.Drapeau;
import Main.BDD_v2;
import Players.Joueur;

// TODO: Auto-generated Javadoc
/**
 * The Class GestionAPI.
 */
public class GestionAPI {

	/** The s reponse. */
	public static String s_reponse = "";

	/** The s cle API. */
	public static String s_cleAPI = "7fb7968a03mshd012795cea9a38bp1a4c84jsn2816ff2c7b61";// c837b75253mshe24f11571463ee5p17f8b3jsn31aa86731177
																							// |
																							// 7fb7968a03mshd012795cea9a38bp1a4c84jsn2816ff2c7b61

	/** The Constant httpClient. */
	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(10)).build();

	/** The correspondance drapeaux. */
	Map<String, String> correspondanceDrapeaux = new HashMap<>();

	/**
	 * Instantiates a new gestion API.
	 */
	public GestionAPI() {
		correspondanceDrapeaux.put("ad", "AND");
		correspondanceDrapeaux.put("ae", "ARE");
		correspondanceDrapeaux.put("af", "AFG");
		correspondanceDrapeaux.put("ag", "ATG");
		correspondanceDrapeaux.put("ai", "AIA");
		correspondanceDrapeaux.put("al", "ALB");
		correspondanceDrapeaux.put("am", "ARM");
		correspondanceDrapeaux.put("ao", "AGO");
		correspondanceDrapeaux.put("aq", "ATA");
		correspondanceDrapeaux.put("ar", "ARG");
		correspondanceDrapeaux.put("as", "ASM");
		correspondanceDrapeaux.put("at", "AUT");
		correspondanceDrapeaux.put("au", "AUS");
		correspondanceDrapeaux.put("aw", "ABW");
		correspondanceDrapeaux.put("ax", "ALA");
		correspondanceDrapeaux.put("az", "AZE");
		correspondanceDrapeaux.put("ba", "BIH");
		correspondanceDrapeaux.put("bb", "BRB");
		correspondanceDrapeaux.put("bd", "BGD");
		correspondanceDrapeaux.put("be", "BEL");
		correspondanceDrapeaux.put("bf", "BFA");
		correspondanceDrapeaux.put("bg", "BGR");
		correspondanceDrapeaux.put("bh", "BHR");
		correspondanceDrapeaux.put("bi", "BDI");
		correspondanceDrapeaux.put("bj", "BEN");
		correspondanceDrapeaux.put("bl", "BLM");
		correspondanceDrapeaux.put("bm", "BMU");
		correspondanceDrapeaux.put("bn", "BRN");
		correspondanceDrapeaux.put("bo", "BOL");
		correspondanceDrapeaux.put("bq", "BES");
		correspondanceDrapeaux.put("br", "BRA");
		correspondanceDrapeaux.put("bs", "BHS");
		correspondanceDrapeaux.put("bt", "BTN");
		correspondanceDrapeaux.put("bv", "BVT");
		correspondanceDrapeaux.put("bw", "BWA");
		correspondanceDrapeaux.put("by", "BLR");
		correspondanceDrapeaux.put("bz", "BLZ");
		correspondanceDrapeaux.put("ca", "CAN");
		correspondanceDrapeaux.put("cc", "CCK");
		correspondanceDrapeaux.put("cd", "COD");
		correspondanceDrapeaux.put("cf", "CAF");
		correspondanceDrapeaux.put("cg", "COG");
		correspondanceDrapeaux.put("ch", "CHE");
		correspondanceDrapeaux.put("ci", "CIV");
		correspondanceDrapeaux.put("ck", "COK");
		correspondanceDrapeaux.put("cl", "CHL");
		correspondanceDrapeaux.put("cm", "CMR");
		correspondanceDrapeaux.put("cn", "CHN");
		correspondanceDrapeaux.put("co", "COL");
		correspondanceDrapeaux.put("cr", "CRI");
		correspondanceDrapeaux.put("cu", "CUB");
		correspondanceDrapeaux.put("cv", "CPV");
		correspondanceDrapeaux.put("cw", "CUW");
		correspondanceDrapeaux.put("cx", "CXR");
		correspondanceDrapeaux.put("cy", "CYP");
		correspondanceDrapeaux.put("cz", "CZE");
		correspondanceDrapeaux.put("de", "DEU");
		correspondanceDrapeaux.put("dj", "DJI");
		correspondanceDrapeaux.put("dk", "DNK");
		correspondanceDrapeaux.put("dm", "DMA");
		correspondanceDrapeaux.put("do", "DOM");
		correspondanceDrapeaux.put("dz", "DZA");
		correspondanceDrapeaux.put("ec", "ECU");
		correspondanceDrapeaux.put("ee", "EST");
		correspondanceDrapeaux.put("eg", "EGY");
		correspondanceDrapeaux.put("eh", "ESH");
		correspondanceDrapeaux.put("er", "ERI");
		correspondanceDrapeaux.put("es", "ESP");
		correspondanceDrapeaux.put("et", "ETH");
		correspondanceDrapeaux.put("et", "ETH");
		correspondanceDrapeaux.put("fi", "FIN");
		correspondanceDrapeaux.put("fj", "FJI");
		correspondanceDrapeaux.put("fk", "FLK");
		correspondanceDrapeaux.put("fm", "FSM");
		correspondanceDrapeaux.put("fo", "FRO");
		correspondanceDrapeaux.put("fr", "FRA");
		correspondanceDrapeaux.put("ga", "GAB");
		correspondanceDrapeaux.put("gb", "GBR");
		correspondanceDrapeaux.put("gd", "GRD");
		correspondanceDrapeaux.put("ge", "GEO");
		correspondanceDrapeaux.put("gf", "GUF");
		correspondanceDrapeaux.put("gg", "GGY");
		correspondanceDrapeaux.put("gh", "GHA");
		correspondanceDrapeaux.put("gi", "GIB");
		correspondanceDrapeaux.put("gl", "GRL");
		correspondanceDrapeaux.put("gm", "GMB");
		correspondanceDrapeaux.put("gn", "GIN");
		correspondanceDrapeaux.put("gp", "GLP");
		correspondanceDrapeaux.put("gq", "GNQ");
		correspondanceDrapeaux.put("gr", "GRC");
		correspondanceDrapeaux.put("gs", "SGS");
		correspondanceDrapeaux.put("gt", "GTM");
		correspondanceDrapeaux.put("gu", "GUM");
		correspondanceDrapeaux.put("gw", "GNB");
		correspondanceDrapeaux.put("gy", "GUY");
		correspondanceDrapeaux.put("hk", "HKG");
		correspondanceDrapeaux.put("hm", "HMD");
		correspondanceDrapeaux.put("hn", "HND");
		correspondanceDrapeaux.put("hr", "HRV");
		correspondanceDrapeaux.put("ht", "HTI");
		correspondanceDrapeaux.put("hu", "HUN");
		correspondanceDrapeaux.put("id", "IDN");
		correspondanceDrapeaux.put("ie", "IRL");
		correspondanceDrapeaux.put("il", "ISR");
		correspondanceDrapeaux.put("im", "IMN");
		correspondanceDrapeaux.put("in", "IND");
		correspondanceDrapeaux.put("io", "IOT");
		correspondanceDrapeaux.put("iq", "IRQ");
		correspondanceDrapeaux.put("ir", "IRN");
		correspondanceDrapeaux.put("is", "ISL");
		correspondanceDrapeaux.put("it", "ITA");
		correspondanceDrapeaux.put("je", "JEY");
		correspondanceDrapeaux.put("jm", "JAM");
		correspondanceDrapeaux.put("jo", "JOR");
		correspondanceDrapeaux.put("jp", "JPN");
		correspondanceDrapeaux.put("ke", "KEN");
		correspondanceDrapeaux.put("kg", "KGZ");
		correspondanceDrapeaux.put("kh", "KHM");
		correspondanceDrapeaux.put("ki", "KIR");
		correspondanceDrapeaux.put("km", "COM");
		correspondanceDrapeaux.put("kn", "KNA");
		correspondanceDrapeaux.put("kp", "PRK");
		correspondanceDrapeaux.put("kr", "KOR");
		correspondanceDrapeaux.put("kw", "KWT");
		correspondanceDrapeaux.put("ky", "CYM");
		correspondanceDrapeaux.put("kz", "KAZ");
		correspondanceDrapeaux.put("la", "LAO");
		correspondanceDrapeaux.put("lb", "LBN");
		correspondanceDrapeaux.put("lc", "LCA");
		correspondanceDrapeaux.put("li", "LIE");
		correspondanceDrapeaux.put("lk", "LKA");
		correspondanceDrapeaux.put("lr", "LBR");
		correspondanceDrapeaux.put("ls", "LSO");
		correspondanceDrapeaux.put("lt", "LTU");
		correspondanceDrapeaux.put("lu", "LUX");
		correspondanceDrapeaux.put("lv", "LVA");
		correspondanceDrapeaux.put("ly", "LBY");
		correspondanceDrapeaux.put("ma", "MAR");
		correspondanceDrapeaux.put("mc", "MCO");
		correspondanceDrapeaux.put("md", "MDA");
		correspondanceDrapeaux.put("me", "MNE");
		correspondanceDrapeaux.put("mf", "MAF");
		correspondanceDrapeaux.put("mg", "MDG");
		correspondanceDrapeaux.put("mh", "MHL");
		correspondanceDrapeaux.put("mk", "MKD");
		correspondanceDrapeaux.put("ml", "MLI");
		correspondanceDrapeaux.put("mm", "MMR");
		correspondanceDrapeaux.put("mn", "MNG");
		correspondanceDrapeaux.put("mo", "MAC");
		correspondanceDrapeaux.put("mp", "MNP");
		correspondanceDrapeaux.put("mq", "MTQ");
		correspondanceDrapeaux.put("mr", "MRT");
		correspondanceDrapeaux.put("ms", "MSR");
		correspondanceDrapeaux.put("mt", "MLT");
		correspondanceDrapeaux.put("mu", "MUS");
		correspondanceDrapeaux.put("mv", "MDV");
		correspondanceDrapeaux.put("mw", "MWI");
		correspondanceDrapeaux.put("mx", "MEX");
		correspondanceDrapeaux.put("my", "MYS");
		correspondanceDrapeaux.put("mz", "MOZ");
		correspondanceDrapeaux.put("na", "NAM");
		correspondanceDrapeaux.put("nc", "NCL");
		correspondanceDrapeaux.put("ne", "NER");
		correspondanceDrapeaux.put("nf", "NFK");
		correspondanceDrapeaux.put("ng", "NGA");
		correspondanceDrapeaux.put("ni", "NIC");
		correspondanceDrapeaux.put("nl", "NLD");
		correspondanceDrapeaux.put("no", "NOR");
		correspondanceDrapeaux.put("np", "NPL");
		correspondanceDrapeaux.put("nr", "NRU");
		correspondanceDrapeaux.put("nu", "NIU");
		correspondanceDrapeaux.put("nz", "NZL");
		correspondanceDrapeaux.put("om", "OMN");
		correspondanceDrapeaux.put("pa", "PAN");
		correspondanceDrapeaux.put("pe", "PER");
		correspondanceDrapeaux.put("pf", "PYF");
		correspondanceDrapeaux.put("pg", "PNG");
		correspondanceDrapeaux.put("ph", "PHL");
		correspondanceDrapeaux.put("pk", "PAK");
		correspondanceDrapeaux.put("pl", "POL");
		correspondanceDrapeaux.put("pm", "SPM");
		correspondanceDrapeaux.put("pn", "PCN");
		correspondanceDrapeaux.put("pr", "PRI");
		correspondanceDrapeaux.put("ps", "PSE");
		correspondanceDrapeaux.put("pt", "PRT");
		correspondanceDrapeaux.put("pw", "PLW");
		correspondanceDrapeaux.put("py", "PRY");
		correspondanceDrapeaux.put("qa", "QAT");
		correspondanceDrapeaux.put("re", "REU");
		correspondanceDrapeaux.put("ro", "ROU");
		correspondanceDrapeaux.put("rs", "SRB");
		correspondanceDrapeaux.put("ru", "RUS");
		correspondanceDrapeaux.put("rw", "RWA");
		correspondanceDrapeaux.put("sa", "SAU");
		correspondanceDrapeaux.put("sb", "SLB");
		correspondanceDrapeaux.put("sc", "SYC");
		correspondanceDrapeaux.put("sd", "SDN");
		correspondanceDrapeaux.put("se", "SWE");
		correspondanceDrapeaux.put("sg", "SGP");
		correspondanceDrapeaux.put("sh", "SHN");
		correspondanceDrapeaux.put("si", "SVN");
		correspondanceDrapeaux.put("sj", "SJM");
		correspondanceDrapeaux.put("sk", "SVK");
		correspondanceDrapeaux.put("sl", "SLE");
		correspondanceDrapeaux.put("sm", "SMR");
		correspondanceDrapeaux.put("sn", "SEN");
		correspondanceDrapeaux.put("so", "SOM");
		correspondanceDrapeaux.put("sr", "SUR");
		correspondanceDrapeaux.put("ss", "SSD");
		correspondanceDrapeaux.put("st", "STP");
		correspondanceDrapeaux.put("sv", "SLV");
		correspondanceDrapeaux.put("sx", "SXM");
		correspondanceDrapeaux.put("sy", "SYR");
		correspondanceDrapeaux.put("sz", "SWZ");
		correspondanceDrapeaux.put("tc", "TCA");
		correspondanceDrapeaux.put("td", "TCD");
		correspondanceDrapeaux.put("tf", "ATF");
		correspondanceDrapeaux.put("tg", "TGO");
		correspondanceDrapeaux.put("th", "THA");
		correspondanceDrapeaux.put("tj", "TJK");
		correspondanceDrapeaux.put("tk", "TKL");
		correspondanceDrapeaux.put("tl", "TLS");
		correspondanceDrapeaux.put("tm", "TKM");
		correspondanceDrapeaux.put("tn", "TUN");
		correspondanceDrapeaux.put("to", "TON");
		correspondanceDrapeaux.put("tr", "TUR");
		correspondanceDrapeaux.put("tt", "TTO");
		correspondanceDrapeaux.put("tv", "TUV");
		correspondanceDrapeaux.put("tw", "TWN");
		correspondanceDrapeaux.put("tz", "TZA");
		correspondanceDrapeaux.put("ua", "UKR");
		correspondanceDrapeaux.put("ug", "UGA");
		correspondanceDrapeaux.put("um", "UMI");
		correspondanceDrapeaux.put("us", "USA");
		correspondanceDrapeaux.put("uy", "URY");
		correspondanceDrapeaux.put("uz", "UZB");
		correspondanceDrapeaux.put("va", "VAT");
		correspondanceDrapeaux.put("vc", "VCT");
		correspondanceDrapeaux.put("ve", "VEN");
		correspondanceDrapeaux.put("vg", "VGB");
		correspondanceDrapeaux.put("vi", "VIR");
		correspondanceDrapeaux.put("vn", "VNM");
		correspondanceDrapeaux.put("vu", "VUT");
		correspondanceDrapeaux.put("wf", "WLF");
		correspondanceDrapeaux.put("ws", "WSM");
		correspondanceDrapeaux.put("xk", "XKX");
		correspondanceDrapeaux.put("ye", "YEM");
		correspondanceDrapeaux.put("yt", "MYT");
		correspondanceDrapeaux.put("za", "ZAF");
		correspondanceDrapeaux.put("zm", "ZMB");
		correspondanceDrapeaux.put("zw", "ZWE");
	}

	/**
	 * Connexion ATP.
	 *
	 * @return the string
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	// permet de recuperer le classement des 500 premiers joueur de l'ATP
	private static String ConnexionATP() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://allsportsapi2.p.rapidapi.com/api/tennis/rankings/atp/live"))
				.setHeader("X-RapidAPI-Key", s_cleAPI).setHeader("X-RapidAPI-Host", "allsportsapi2.p.rapidapi.com")
				.build();

		HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

		System.out.println("> Conexion API ATP " + response.statusCode());
		System.out.println("Headers: " + response.headers());

		// Vérifier si la réponse est compressée
		if ("gzip".equals(response.headers().firstValue("content-encoding").orElse(""))) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(response.body());
					BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {
				StringBuilder responseBody = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					responseBody.append(line);
				}
				System.out.println(responseBody.toString());
				return responseBody.toString();
			}
		} else {
			// Si la réponse n'est pas compressée
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
				StringBuilder responseBody = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					responseBody.append(line);
				}
				return responseBody.toString();
			}
		}
	}
//	private String ConnexionATP() throws IOException, InterruptedException {
//		HttpRequest request = HttpRequest.newBuilder().GET()
//				.uri(URI.create("https://allsportsapi2.p.rapidapi.com/api/tennis/rankings/atp/live")) 
//				.setHeader("X-RapidAPI-Key", s_cleAPI)
//				.setHeader("X-RapidAPI-Host", "allsportsapi2.p.rapidapi.com")
//				.build();
//
//		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//		System.out.println("> Conexion API ATP "+response.statusCode());
//		if(!(response.statusCode() == 401)) {
////			s_reponse = response.body();
//			return (response.body());
//		}else {
//			JOptionPane.showMessageDialog(null, "erreur cle api : "+response.statusCode(), "Erreur", JOptionPane.ERROR_MESSAGE);
//		}
//		return null;
//	}

	/**
	 * Connexion WTA.
	 *
	 * @return the string
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	// permet de recuperer le classement des 500 premiers joueur du WTA
	private static String ConnexionWTA() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://allsportsapi2.p.rapidapi.com/api/tennis/rankings/wta/live"))
				.setHeader("X-RapidAPI-Key", s_cleAPI).setHeader("X-RapidAPI-Host", "allsportsapi2.p.rapidapi.com")
				.build();

		HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

		System.out.println("> Conexion API WTA " + response.statusCode());
		System.out.println("Headers: " + response.headers());

		// Vérifier si la réponse est compressée
		if ("gzip".equals(response.headers().firstValue("content-encoding").orElse(""))) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(response.body());
					BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {
				StringBuilder responseBody = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					responseBody.append(line);
				}
				System.out.println(responseBody.toString());
				return responseBody.toString();
			}
		} else {
			// Si la réponse n'est pas compressée
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
				StringBuilder responseBody = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					responseBody.append(line);
				}
				return responseBody.toString();
			}
		}
	}

//	private String ConnexionWTA() throws IOException, InterruptedException {
//		HttpRequest request = HttpRequest.newBuilder().GET()
//				.uri(URI.create("https://allsportsapi2.p.rapidapi.com/api/tennis/rankings/wta/live")) 
//				.setHeader("X-RapidAPI-Key", s_cleAPI)
//				.setHeader("X-RapidAPI-Host", "allsportsapi2.p.rapidapi.com")
//				.build();
//		
//		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//	
//		System.out.println("> Conexion API WTA "+response.statusCode());
//		System.out.println("Headers: " + response.headers());
//		System.out.println(response.body().toString());
//		
//		if(!(response.statusCode() == 401)) {
////			s_reponse = response.body();
//			return (response.body());
//		}else {
//			JOptionPane.showMessageDialog(null, "erreur cle api : "+response.statusCode(), "Erreur", JOptionPane.ERROR_MESSAGE);
//		}
//		return null;
//	}
	/**
	 * Tab atp.
	 *
	 * @throws JSONException          the JSON exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws InterruptedException   the interrupted exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
//mise en forme des donées recu du tableau de l'atp et insertion dans la bdd
	public void TAB_ATP()
			throws JSONException, IOException, InterruptedException, ClassNotFoundException, SQLException {
		String s_imgTemp = "resources" + File.separator + "imgInterface" + File.separator + "clear.png";
		// Convertir la chaîne en objet JSON
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(ConnexionATP());
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// separer les different objet correspondant à des joueurs
		JSONArray rankings = json.getJSONArray("rankings");

		// création d'un joueur
		for (int i = 0; i < rankings.length(); i++) {
			// choix du joueur
			JSONObject ranking = rankings.getJSONObject(i);
			// recupération des differentes infos
			int i_rankingValue = ranking.getInt("ranking");
			JSONObject teamID = ranking.getJSONObject("team");

			String s_fullName = ranking.getString("rowName");

			String s_name = teamID.getString("name").split("\\.")[teamID.getString("name").split("\\.").length - 1]
					.trim().replace("-", " ");

			int i_size_surname = s_fullName.split(" ").length - s_name.split(" ").length;
			String s_surname = "";

			for (int j = 0; j < i_size_surname; j++) {
				s_surname += s_fullName.split(" ")[j] + " ";
			}

			String s_displayName = teamID.getString("shortName");
			String s_alpha3 = "";
			if (teamID.has("country")) {
				JSONObject countryObject = teamID.getJSONObject("country");
				String s_alpha2 = countryObject.optString("alpha2"); // !\\ a modifier car ici que deux carracteres pour
																		// le pays
				s_alpha3 = correspondanceDrapeaux.get(s_alpha2.toLowerCase()); // !\\ a modifier car ici que deux
																				// carracteres pour le pays
			}
			int i_ID = teamID.getInt("id");
			// les valeurs "" et 0 seront ajouter une fois le joueur choisit sinon on
			// atteint la limite de requete de l'api
			Joueur joueur = new Joueur(i_ID, "homme", s_name, s_surname.trim(), s_displayName, s_alpha3,
					Drapeau.getFullName(s_alpha3), "", s_imgTemp, i_rankingValue, 0, "", 0, 0, "", "", "", "");

			// insertion dans la base de données
			BDD_v2.insertionJoueurDansBDD(joueur, BDD_v2.ATP);

		}
	}

	/**
	 * Tab wta.
	 *
	 * @throws JSONException          the JSON exception
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws InterruptedException   the interrupted exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	// mise en forme des donées recu du tableau de l'atp et insertion dans la bdd
	public void TAB_WTA()
			throws JSONException, IOException, InterruptedException, ClassNotFoundException, SQLException {
		String s_imgTemp = "resources" + File.separator + "imgInterface" + File.separator + "clear.png";
		// Convertir la chaîne en objet JSON
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(ConnexionWTA());
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// separer les different objet correspondant à des joueurs
		JSONArray rankings = json.getJSONArray("rankings");

		// création d'un joueur
		for (int i = 0; i < rankings.length(); i++) {
			// choix du joueur
			JSONObject ranking = rankings.getJSONObject(i);

			// recupération des differentes infos
			int i_rankingValue = ranking.getInt("ranking");
			JSONObject teamID = ranking.getJSONObject("team");

			String s_fullName = ranking.getString("rowName");

			String s_name = teamID.getString("name").split("\\.")[teamID.getString("name").split("\\.").length - 1]
					.trim().replace("-", " ");

			int i_size_surname = s_fullName.split(" ").length - s_name.split(" ").length;
			String s_surname = "";

			for (int j = 0; j < i_size_surname; j++) {
				s_surname += s_fullName.split(" ")[j] + " ";
			}

			String s_displayName = teamID.getString("shortName");
			String s_alpha3 = "";
			if (teamID.has("country")) {
				JSONObject countryObject = teamID.getJSONObject("country");
//			        String s_alpha2 = countryObject.optString("alpha2"); //!\\ a modifier car ici que deux carracteres pour le pays
				s_alpha3 = countryObject.optString("alpha3");
//			        s_alpha3 = correspondanceDrapeaux.get(s_alpha2.toLowerCase()); //!\\ a modifier car ici que deux carracteres pour le pays
			} else
				s_alpha3 = "clear";
			if (s_alpha3 == null || s_alpha3 == "" || s_alpha3 == " ")
				s_alpha3 = "clear";
			int i_ID = teamID.getInt("id");
			// les valeurs "" et 0 seront ajouter une fois le joueur choisit sinon on
			// atteint la limite de requete de l'api
			Joueur joueur = new Joueur(i_ID, "women", s_name, s_surname, s_displayName, s_alpha3,
					Drapeau.getFullName(s_alpha3), "", s_imgTemp, i_rankingValue, 0, "", 0, 0, "", "", "", "");
			// System.out.println(" + player added in WTA, name : "+s_name+", surname :
			// "+s_surname);
			// insertion dans la base de données
			BDD_v2.insertionJoueurDansBDD(joueur, BDD_v2.WTA);

		}
	}

	/**
	 * Insertions infos sup joueur.
	 *
	 * @param id      the id
	 * @param bddname the bddname
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws InterruptedException   the interrupted exception
	 * @throws JSONException          the JSON exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	public void insertionsInfosSupJoueur(int id, String bddname)
			throws IOException, InterruptedException, JSONException, ClassNotFoundException, SQLException {
//		Object o_infosJoueur[] = new Object[8]; 
		// verification si il n'y a pas deja les infos
		if (BDD_v2.verifInfosManquante(id, bddname) == true) {
			System.out.println("  ! infos manquante !");
			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("https://allsportsapi2.p.rapidapi.com/api/tennis/player/" + id))
					.setHeader("X-RapidAPI-Key", s_cleAPI).setHeader("X-RapidAPI-Host", "allsportsapi2.p.rapidapi.com")
					.build();

			HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

			System.out.println("  statut code API : " + response.statusCode());
			if (response.statusCode() == 403 || response.statusCode() == 401) {
				JOptionPane.showMessageDialog(null, "le nom de la clé API n'est pas correct : " + s_cleAPI, "Erreur",
						JOptionPane.ERROR_MESSAGE);
			} else if (response.statusCode() == 429) {
				JOptionPane.showMessageDialog(null, "le nombre de requete max à était atteint : " + s_cleAPI, "Erreur",
						JOptionPane.ERROR_MESSAGE);
			} else {
				recupIMGJ(id, bddname);
				// Vérifier si la réponse est compressée
				if ("gzip".equals(response.headers().firstValue("content-encoding").orElse(""))) {
					try (GZIPInputStream gzipInputStream = new GZIPInputStream(response.body());
							BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {
						StringBuilder responseBody = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) {
							responseBody.append(line);
						}
						s_reponse = responseBody.toString();
					}
				} else {
					// Si la réponse n'est pas compressée
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
						StringBuilder responseBody = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null) {
							responseBody.append(line);
						}
						s_reponse = responseBody.toString();
					}
				}
				// System.out.println(s_reponse);
				JSONObject json = new JSONObject(s_reponse);
//				System.out.println(s_reponse);
				int id_recup = id;
				JSONObject playerTeamInfo = json.getJSONObject("team").getJSONObject("playerTeamInfo");

				// rank
				int i_ranking = json.getJSONObject("team").getInt("ranking");
//				int i_ranking =	(int)verificationElement(json.getJSONObject("team"), "ranking");
				// recup taille
				String d_height = playerTeamInfo.has("height") ? playerTeamInfo.getDouble("height") + "" : "0";
				String s_height = d_height.replace(".", "");
				// recup poid
				String s_weight = playerTeamInfo.has("weight") ? playerTeamInfo.getInt("weight") + "" : "0";
				// recup mains de jeux
				String s_plays = playerTeamInfo.has("plays") ? playerTeamInfo.getString("plays") + "" : " ";

				// time stamp naissance
				long birthDateTimestamp = playerTeamInfo.getLong("birthDateTimestamp");
				// Conversion en LocalDate
				LocalDate dateNaissance = LocalDate.ofEpochDay(birthDateTimestamp / 86400);
				// Formater la date au format "dd/MM/yyyy"
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formattedDate = dateNaissance.format(formatter);

				// Calcul de l'âge
				LocalDate dateAujourdhui = LocalDate.now();
				int i_age = Period.between(dateNaissance, dateAujourdhui).getYears();

				// recup du prizetotal
				String s_prizeTotal = playerTeamInfo.has("prizeTotal") ? playerTeamInfo.getLong("prizeTotal") + ""
						: " ";
//				int i_prizeTotal = playerTeamInfo.getInt("prizeTotal");
				String s_valeur = playerTeamInfo.getJSONObject("prizeTotalRaw").getString("currency");
				if ("EUR".equals(s_valeur)) {
					s_valeur = "€";
				} else if ("USD".equals(s_valeur)) {
					s_valeur = "$";
				} else if ("GBP".equals(s_valeur)) {
					s_valeur = "£";
				}
				// String s_prizetotal = s_prizeTotal+s_valeur;
				if (s_prizeTotal.length() > 1) {
					String currency = s_valeur;

					// Convertir en long
//					numericPart.replace(" ", "");
//					numericPart.replace("?", "");
					s_prizeTotal.replaceAll("[^0-9]", "");
					long number = Long.parseLong(s_prizeTotal);

					// Formater avec des espaces comme séparateurs de milliers
					NumberFormat formatterP = NumberFormat.getInstance(Locale.FRANCE);
					String formattedNumber = formatterP.format(number);

					// Retourner la chaîne formatée avec le symbole '€'
					s_prizeTotal = formattedNumber + currency;
				} else
					s_prizeTotal = "0";

				// lieu de residence
				String s_residence = playerTeamInfo.has("residence") ? playerTeamInfo.getString("residence") + "" : " ";
//				String s_residence = playerTeamInfo.getString("residence");
				// recup du lieu de naissance
				String s_birthplace = playerTeamInfo.has("birthplace") ? playerTeamInfo.getString("birthplace") + ""
						: " ";
//				String s_birthplace = playerTeamInfo.getString("birthplace");

				System.out.println("  additional informations : " + id_recup + ", " + formattedDate + ", " + i_ranking
						+ ", " + s_height + ", " + s_plays + ", " + i_age + ", " + s_weight + ", " + s_prizeTotal + ", "
						+ s_birthplace + ", " + s_residence);
				BDD_v2.UpdateInfosSupJoueur(id_recup, formattedDate, i_ranking, s_height, s_plays, i_age, s_weight,
						s_prizeTotal, s_birthplace, s_residence, bddname);
			}
		} else
			System.out.println("  infos complete !");
	}

	/**
	 * Recup IMGJ.
	 *
	 * @param id      the id
	 * @param bddname the bddname
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws InterruptedException   the interrupted exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException           the SQL exception
	 */
	// recupere l'image du joueur selectionne via son id et met le chemin vers
	// l'image dans la bdd
	private void recupIMGJ(int id, String bddname)
			throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		BufferedImage image = null;
		boolean b_imageExiste = BDD_v2.verif_image(id, bddname);
		if (!b_imageExiste) {
			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("https://allsportsapi2.p.rapidapi.com/api/tennis/player/" + id + "/image"))
					.setHeader("X-RapidAPI-Key", s_cleAPI).setHeader("X-RapidAPI-Host", "allsportsapi2.p.rapidapi.com")
					.build();

			HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
			System.out.println("  get image from API : " + response.statusCode());

			byte[] imageData = response.body();
			InputStream in = new ByteArrayInputStream(imageData);
			image = ImageIO.read(in);

			if (image != null) {
				// Enregistrez l'image dans le dossier PlayerImages
				String dossierImages =  "resources"+File.separator+"PlayersImages";
				String s_joueurDisplayName = BDD_v2.getJoueurParID(id, bddname).getDisplay_name();
//                s_joueurDisplayName.replace(" ", "_");
				String nomFichier = s_joueurDisplayName.replace(" ", "_") + ".png"; // Nom du fichier peut être l'ID du
																					// joueur avec extension png

				// Assurez-vous que le dossier existe
				File dossier = new File("resources"+File.separator+"Config/" + dossierImages);
				if (!dossier.exists()) {
					dossier.mkdir();
				} else
					System.out.println("  folder for image exist");

				// Chemin complet du fichier
				String cheminFichier = dossierImages + File.separator + nomFichier;
				System.out.println("  image path from PlayersImages folder : " + cheminFichier);
				// Enregistrez l'image dans le fichier
				ImageIO.write(image, "png", new File(cheminFichier));

				// Ajoutez votre logique pour insérer le chemin du fichier dans la base de
				// données
				BDD_v2.updateImgJoueur(id, cheminFichier, bddname);
			}
		} else {
			System.out.println("  image already exist !");
		}
	}

	/**
	 * Enlever accents.
	 *
	 * @param texteAvecAccents the texte avec accents
	 * @return the string
	 */
	public String enleverAccents(String texteAvecAccents) {
		String texteSansAccents = Normalizer.normalize(texteAvecAccents, Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return texteSansAccents;
	}

	/**
	 * Verification element.
	 *
	 * @param objAverifier the obj averifier
	 * @param element      the element
	 * @return the object
	 * @throws JSONException the JSON exception
	 */
	public Object verificationElement(JSONObject objAverifier, String element) throws JSONException {
		Object o_returnValeur = new Object();
		if (objAverifier.has(element))
			o_returnValeur = objAverifier.get(element);
		return o_returnValeur;

	}
}
