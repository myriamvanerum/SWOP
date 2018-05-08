package view.formelements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;


public class Button extends WindowControl {
	private String text;
	
	public Button(String text) {
		this.text = text;
	}

	@Override
	public void draw(Graphics2D g) {	
		RoundRectangle2D rec = new RoundRectangle2D.Double(20,20,75,20,10,10);
		g.setColor(new Color(165,170,200));	
		g.fill(rec);
		g.setColor(Color.BLACK);
		g.draw(rec);
		g.drawString(text, 35, 35);
	}
}
