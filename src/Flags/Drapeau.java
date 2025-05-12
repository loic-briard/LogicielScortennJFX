/*
 * 
 */
package Flags;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Main.BDD_v2;

// TODO: Auto-generated Javadoc
/**
 * The Class Drapeau.
 */
public class Drapeau {
	
	/** The Nom. */
	private String Nom;
	
	private String Pays;

	/** The Image drapeau. */
	private String ImageDrapeau;
	
	/**
	 * Instantiates a new drapeau.
	 *
	 * @param nom the nom
	 * @param path the path
	 */
	public Drapeau(String nom,String pays, String path) {
		super();
		this.Nom = nom;
		this.Pays = pays;
		this.ImageDrapeau = path;
	}

	/**
	 * Gets the image drapeau.
	 *
	 * @return the image drapeau
	 */
	public String getImageDrapeau() {
		return ImageDrapeau;
	}

	/**
	 * Sets the image drapeau.
	 *
	 * @param imageDrapeau the new image drapeau
	 */
	public void setImageDrapeau(String imageDrapeau) {
		ImageDrapeau = imageDrapeau;
	}

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return Nom;
	}

	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setNom(String nom) {
		Nom = nom;
	}
	
	public String getPays() {
		return Pays;
	}
	
	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setPays(String pays) {
		Pays = pays;
	}
	
	private static final Map<String, String> countryMap = new HashMap<>();

	static {
		countryMap.put("ABW", "Aruba");
		countryMap.put("AFG", "Afghanistan");
		countryMap.put("AGO", "Angola");
		countryMap.put("AIA", "Anguilla");
		countryMap.put("ALA", "Åland Islands");
		countryMap.put("ALB", "Albania");
		countryMap.put("AND", "Andorra");
		countryMap.put("ARE", "United Arab Emirates");
		countryMap.put("ARG", "Argentina");
		countryMap.put("ARM", "Armenia");
		countryMap.put("ASM", "American Samoa");
		countryMap.put("ATA", "Antarctica");
		countryMap.put("ATF", "French Southern Territories");
		countryMap.put("ATG", "Antigua and Barbuda");
		countryMap.put("AUS", "Australia");
		countryMap.put("AUT", "Austria");
		countryMap.put("AZE", "Azerbaijan");
		countryMap.put("BDI", "Burundi");
		countryMap.put("BEL", "Belgium");
		countryMap.put("BEN", "Benin");
		countryMap.put("BES", "Bonaire, Sint Eustatius and Saba");
		countryMap.put("BFA", "Burkina Faso");
		countryMap.put("BGD", "Bangladesh");
		countryMap.put("BGR", "Bulgaria");
		countryMap.put("BHR", "Bahrain");
		countryMap.put("BHS", "Bahamas");
		countryMap.put("BIH", "Bosnia and Herzegovina");
		countryMap.put("BLM", "Saint Barthélemy");
		countryMap.put("BLR", "Belarus");
		countryMap.put("BLZ", "Belize");
		countryMap.put("BMU", "Bermuda");
		countryMap.put("BOL", "Bolivia");
		countryMap.put("BRA", "Brazil");
		countryMap.put("BRB", "Barbados");
		countryMap.put("BRN", "Brunei");
		countryMap.put("BTN", "Bhutan");
		countryMap.put("BVT", "Bouvet Island");
		countryMap.put("BWA", "Botswana");
		countryMap.put("CAF", "Central African Republic");
		countryMap.put("CAN", "Canada");
		countryMap.put("CCK", "Cocos (Keeling) Islands");
		countryMap.put("CHE", "Switzerland");
		countryMap.put("CHL", "Chile");
		countryMap.put("CHN", "China");
		countryMap.put("CIV", "Ivory Coast");
		countryMap.put("CMR", "Cameroon");
		countryMap.put("COD", "Democratic Republic of the Congo");
		countryMap.put("COG", "Republic of the Congo");
		countryMap.put("COK", "Cook Islands");
		countryMap.put("COL", "Colombia");
		countryMap.put("COM", "Comoros");
		countryMap.put("CPV", "Cape Verde");
		countryMap.put("CRI", "Costa Rica");
		countryMap.put("CUB", "Cuba");
		countryMap.put("CUW", "Curaçao");
		countryMap.put("CXR", "Christmas Island");
		countryMap.put("CYM", "Cayman Islands");
		countryMap.put("CYP", "Cyprus");
		countryMap.put("CZE", "Czech Republic");
		countryMap.put("DEU", "Germany");
		countryMap.put("DJI", "Djibouti");
		countryMap.put("DMA", "Dominica");
		countryMap.put("DNK", "Denmark");
		countryMap.put("DOM", "Dominican Republic");
		countryMap.put("DZA", "Algeria");
		countryMap.put("ECU", "Ecuador");
		countryMap.put("EGY", "Egypt");
		countryMap.put("ERI", "Eritrea");
		countryMap.put("ESH", "Western Sahara");
		countryMap.put("ESP", "Spain");
		countryMap.put("EST", "Estonia");
		countryMap.put("ETH", "Ethiopia");
		countryMap.put("FIN", "Finland");
		countryMap.put("FJI", "Fiji");
		countryMap.put("FLK", "Falkland Islands");
		countryMap.put("FRA", "France");
		countryMap.put("FRO", "Faroe Islands");
		countryMap.put("FSM", "Micronesia");
		countryMap.put("GAB", "Gabon");
		countryMap.put("GBR", "United Kingdom");
		countryMap.put("GEO", "Georgia");
		countryMap.put("GGY", "Guernsey");
		countryMap.put("GHA", "Ghana");
		countryMap.put("GIB", "Gibraltar");
		countryMap.put("GIN", "Guinea");
		countryMap.put("GLP", "Guadeloupe");
		countryMap.put("GMB", "Gambia");
		countryMap.put("GNB", "Guinea-Bissau");
		countryMap.put("GNQ", "Equatorial Guinea");
		countryMap.put("GRC", "Greece");
		countryMap.put("GRD", "Grenada");
		countryMap.put("GRL", "Greenland");
		countryMap.put("GTM", "Guatemala");
		countryMap.put("GUF", "French Guiana");
		countryMap.put("GUM", "Guam");
		countryMap.put("GUY", "Guyana");
		countryMap.put("HKG", "Hong Kong");
		countryMap.put("HMD", "Heard Island and McDonald Islands");
		countryMap.put("HND", "Honduras");
		countryMap.put("HRV", "Croatia");
		countryMap.put("HTI", "Haiti");
		countryMap.put("HUN", "Hungary");
		countryMap.put("IDN", "Indonesia");
		countryMap.put("IMN", "Isle of Man");
		countryMap.put("IND", "India");
		countryMap.put("IOT", "British Indian Ocean Territory");
		countryMap.put("IRL", "Ireland");
		countryMap.put("IRN", "Iran");
		countryMap.put("IRQ", "Iraq");
		countryMap.put("ISL", "Iceland");
		countryMap.put("ISR", "Israel");
		countryMap.put("ITA", "Italy");
		countryMap.put("JAM", "Jamaica");
		countryMap.put("JEY", "Jersey");
		countryMap.put("JOR", "Jordan");
		countryMap.put("JPN", "Japan");
		countryMap.put("KAZ", "Kazakhstan");
		countryMap.put("KEN", "Kenya");
		countryMap.put("KGZ", "Kyrgyzstan");
		countryMap.put("KHM", "Cambodia");
		countryMap.put("KIR", "Kiribati");
		countryMap.put("KNA", "Saint Kitts and Nevis");
		countryMap.put("KOR", "South Korea");
		countryMap.put("KWT", "Kuwait");
		countryMap.put("LAO", "Laos");
		countryMap.put("LBN", "Lebanon");
		countryMap.put("LBR", "Liberia");
		countryMap.put("LBY", "Libya");
		countryMap.put("LCA", "Saint Lucia");
		countryMap.put("LIE", "Liechtenstein");
		countryMap.put("LKA", "Sri Lanka");
		countryMap.put("LSO", "Lesotho");
		countryMap.put("LTU", "Lithuania");
		countryMap.put("LUX", "Luxembourg");
		countryMap.put("LVA", "Latvia");
		countryMap.put("MAC", "Macau");
		countryMap.put("MAF", "Saint Martin");
		countryMap.put("MAR", "Morocco");
		countryMap.put("MCO", "Monaco");
		countryMap.put("MDA", "Moldova");
		countryMap.put("MDG", "Madagascar");
		countryMap.put("MDV", "Maldives");
		countryMap.put("MEX", "Mexico");
		countryMap.put("MHL", "Marshall Islands");
        countryMap.put("MKD", "North Macedonia");
        countryMap.put("MLI", "Mali");
        countryMap.put("MLT", "Malta");
        countryMap.put("MMR", "Myanmar");
        countryMap.put("MNE", "Montenegro");
        countryMap.put("MNG", "Mongolia");
        countryMap.put("MNP", "Northern Mariana Islands");
        countryMap.put("MOZ", "Mozambique");
        countryMap.put("MRT", "Mauritania");
        countryMap.put("MSR", "Montserrat");
        countryMap.put("MTQ", "Martinique");
        countryMap.put("MUS", "Mauritius");
        countryMap.put("MWI", "Malawi");
        countryMap.put("MYS", "Malaysia");
        countryMap.put("MYT", "Mayotte");
        countryMap.put("NAM", "Namibia");
        countryMap.put("NCL", "New Caledonia");
        countryMap.put("NER", "Niger");
        countryMap.put("NFK", "Norfolk Island");
        countryMap.put("NGA", "Nigeria");
        countryMap.put("NIC", "Nicaragua");
        countryMap.put("NIU", "Niue");
        countryMap.put("NLD", "Netherlands");
        countryMap.put("NOR", "Norway");
        countryMap.put("NPL", "Nepal");
        countryMap.put("NRU", "Nauru");
        countryMap.put("NZL", "New Zealand");
        countryMap.put("OMN", "Oman");
        countryMap.put("PAK", "Pakistan");
        countryMap.put("PAN", "Panama");
        countryMap.put("PCN", "Pitcairn Islands");
        countryMap.put("PER", "Peru");
        countryMap.put("PHL", "Philippines");
        countryMap.put("PLW", "Palau");
        countryMap.put("PNG", "Papua New Guinea");
        countryMap.put("POL", "Poland");
        countryMap.put("PRI", "Puerto Rico");
        countryMap.put("PRK", "North Korea");
        countryMap.put("PRT", "Portugal");
        countryMap.put("PRY", "Paraguay");
        countryMap.put("PSE", "Palestine");
        countryMap.put("PYF", "French Polynesia");
        countryMap.put("QAT", "Qatar");
        countryMap.put("REU", "Réunion");
        countryMap.put("ROU", "Romania");
        countryMap.put("RUS", "Russia");
        countryMap.put("RWA", "Rwanda");
        countryMap.put("SAU", "Saudi Arabia");
        countryMap.put("SDN", "Sudan");
        countryMap.put("SEN", "Senegal");
        countryMap.put("SGP", "Singapore");
        countryMap.put("SGS", "South Georgia and the South Sandwich Islands");
        countryMap.put("SHN", "Saint Helena, Ascension and Tristan da Cunha");
        countryMap.put("SJM", "Svalbard and Jan Mayen");
        countryMap.put("SLB", "Solomon Islands");
        countryMap.put("SLE", "Sierra Leone");
        countryMap.put("SLV", "El Salvador");
        countryMap.put("SMR", "San Marino");
        countryMap.put("SOM", "Somalia");
        countryMap.put("SPM", "Saint Pierre and Miquelon");
        countryMap.put("SRB", "Serbia");
        countryMap.put("SSD", "South Sudan");
        countryMap.put("STP", "São Tomé and Príncipe");
        countryMap.put("SUR", "Suriname");
        countryMap.put("SVK", "Slovakia");
        countryMap.put("SVN", "Slovenia");
        countryMap.put("SWE", "Sweden");
        countryMap.put("SWZ", "Eswatini (Swaziland)");
        countryMap.put("SXM", "Sint Maarten");
        countryMap.put("SYC", "Seychelles");
        countryMap.put("SYR", "Syria");
        countryMap.put("TCA", "Turks and Caicos Islands");
        countryMap.put("TCD", "Chad");
        countryMap.put("TGO", "Togo");
        countryMap.put("THA", "Thailand");
        countryMap.put("TJK", "Tajikistan");
        countryMap.put("TKL", "Tokelau");
        countryMap.put("TKM", "Turkmenistan");
        countryMap.put("TLS", "Timor-Leste");
        countryMap.put("TON", "Tonga");
        countryMap.put("TTO", "Trinidad and Tobago");
        countryMap.put("TUN", "Tunisia");
        countryMap.put("TUR", "Turkey");
        countryMap.put("TUV", "Tuvalu");
        countryMap.put("TWN", "Taiwan");
        countryMap.put("TZA", "Tanzania");
        countryMap.put("UGA", "Uganda");
        countryMap.put("UKR", "Ukraine");
        countryMap.put("UMI", "United States Minor Outlying Islands");
        countryMap.put("URY", "Uruguay");
        countryMap.put("USA", "United States");
        countryMap.put("UZB", "Uzbekistan");
        countryMap.put("VAT", "Vatican City");
        countryMap.put("VCT", "Saint Vincent and the Grenadines");
        countryMap.put("VEN", "Venezuela");
        countryMap.put("VGB", "British Virgin Islands");
        countryMap.put("VIR", "U.S. Virgin Islands");
        countryMap.put("VNM", "Vietnam");
        countryMap.put("VUT", "Vanuatu");
        countryMap.put("WLF", "Wallis and Futuna");
        countryMap.put("WSM", "Samoa");
        countryMap.put("XKX", "Kosovo");
        countryMap.put("YEM", "Yemen");
        countryMap.put("ZAF", "South Africa");
        countryMap.put("ZMB", "Zambia");
        countryMap.put("ZWE", "Zimbabwe");
        countryMap.put("clear", "clear");
        countryMap.put("blank", "blank");
	}
    public static String getFullName(String abbreviation) {
        return countryMap.getOrDefault(abbreviation, " ");
    }

	/**
	 * Charger drapeau.
	 */
	public static void chargerDrapeau() {
		try {
//		    if (MainJFX.openOnEclipse) {
		        // Mode Eclipse: Acc�s direct aux fichiers
		        File folder = new File("resources"+File.separator+"flag"+File.separator);
		        if (folder.exists() && folder.isDirectory() && folder.canRead()) {
		            File[] files = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg")); // Filtre pour les images
		            for (File file : files) {
		                String fileName = file.getName();
		                String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
		                String filePath = file.getPath();
		                System.out.println("++++ fichier vers drapeau " + filePath);
		                BDD_v2.insertionDrapeauDansBDD(new Drapeau(nameWithoutExtension,getFullName(nameWithoutExtension), filePath));
		            }
		            System.out.println("++++ Drapeaux inserer dans bdd avec succes.");
		        } else {
		            System.err.println("---- Le dossier de drapeaux est introuvable ou inaccessible.");
		        }
//		    } else {
//		    	// Mode JAR: Utilisation de getResourceAsStream pour acc�der aux ressources
//                // Supposons que nous avons un fichier 'flag_manifest.txt' qui liste tous les fichiers drapeaux
//                InputStream manifestStream = MainJFX.class.getClassLoader().getResourceAsStream("manifest.txt");
//                if (manifestStream != null) {
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(manifestStream));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        // Pour chaque ligne (nom de fichier) dans le manifeste, chargez le drapeau correspondant
//                        InputStream flagStream = MainJFX.class.getClassLoader().getResourceAsStream("flag/" + line);
//                        if (flagStream != null) {
//                        	String nameWithoutExtension = line.substring(0, line.lastIndexOf('.'));
//                            // Ici, vous pouvez utiliser l'image charg�e, par exemple l'ins�rer dans la base de donn�es
//                            BDD_v2.insertionDrapeauDansBDD(new Drapeau(nameWithoutExtension, getFullName(nameWithoutExtension),"flag/" + line));
//                            System.out.println("++++ Drapeau charge: " + line);
//                        } else {
//                            System.err.println("---- Impossible de charger le drapeau: " + line);
//                        }
//                    }
//                } else {
//                    System.err.println("---- Impossible de trouver le manifeste des drapeaux.");
//                }
//		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
