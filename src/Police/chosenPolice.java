package Police;

import java.awt.Color;
import java.awt.Font;

public class chosenPolice {
	private Font newfont;
	private Color newColor;
	
	public chosenPolice() {
		newfont = new Font("Arial", Font.PLAIN, 25);
		newColor = Color.BLACK;	
	}

	public Font getNewfont() {
		return newfont;
	}

	public void setNewfont(Font newfont) {
		this.newfont = newfont;
	}

	public Color getNewColor() {
		return newColor;
	}

	public void setNewColor(Color newColor) {
		this.newColor = newColor;
	}
}
