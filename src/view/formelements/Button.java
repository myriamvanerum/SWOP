package view.formelements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Button extends WindowControl {
	private String text;
	private boolean isAvailable;

	public Button(String text, int x, int y, int height, int width) {
		this.text = text;
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public void draw(Graphics2D g) {
		if (getX() < 0 || getY() < 0 || getWidth() <= 0 || getHeight() <= 0)
			throw new IllegalArgumentException();

		RoundRectangle2D rec = new RoundRectangle2D.Double(getX(), getY(), getWidth(), getHeight(), 10, 10);

		if (isAvailable)
			g.setColor(new Color(165, 170, 200));
		else
			g.setColor(new Color(195, 195, 195));

		g.fill(rec);
		g.setColor(Color.BLACK);
		g.draw(rec);
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getX() + getWidth() / 2) - (textWidth/2), getY() + 15);
	}
}
