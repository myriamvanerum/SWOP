package view.formelements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import view.Selectable;

public class RadioButton implements Selectable {
	private String label;
	private String value;
	private boolean isSelected;
	
	public RadioButton(String label, String value) {
		this.label = label;
		this.value = value;
		this.isSelected = false;
	}
	
	public void draw(Graphics2D g, Point2D position) {	
		int x = (int)position.getX();
		int y = (int)position.getY();
		
		// circle one
		int radius = 10;
		Shape c = new Ellipse2D.Double(x-radius/2, y-radius/2, radius, radius);
		g.draw(c);
		g.drawString(label, x + 20, y+5);

		// circle two 
		if (isSelected) {
			int radiusS = radius - 4;
			g.setColor(new Color(70, 170, 220));
			Shape s = new Ellipse2D.Double(x-radiusS/2, y-radiusS/2, radiusS, radiusS);
			g.draw(s);
			g.fill(s);
			g.setColor(new Color(0, 0, 0));
		}
	}

	@Override
	public boolean selected() {
		return isSelected;
	}

	@Override
	public void select() {
		isSelected = true;
	}

	@Override
	public void unselect() {
		isSelected = false;
	}
}
