package Background;

public class Background {
	private String nom;
	private String image_1;
	private String image_2;
	private String image_3;
	private String image_4;
	private String image_5;
	
	public Background(String nom) {//, byte image_1, byte image_2, byte image_3, byte image_4
		super();
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public String getImage_1() {
		return image_1;
	}

	public String getImage_2() {
		return image_2;
	}

	public String getImage_3() {
		return image_3;
	}

	public String getImage_4() {
		return image_4;
	}
	public String getImage_5() {
		return image_5;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setImage_1(String image_1) {
		this.image_1 = image_1;
	}

	public void setImage_2(String image_2) {
		this.image_2 = image_2;
	}

	public void setImage_3(String image_3) {
		this.image_3 = image_3;
	}

	public void setImage_4(String image_4) {
		this.image_4 = image_4;
	}
	
	public void setImage_5(String image_5) {
		this.image_5 = image_5;
	}
}
