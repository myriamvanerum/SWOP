package view.windows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Titlebar {
	private Integer x, y, width, height = 25;
	
	public Titlebar(Integer x, Integer y, Integer width) {
		setX(x);
		setY(y);
		setWidth(width);
	}
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void draw(Graphics2D g, String title) {
		Integer padding = 7;
		Integer paddingBig = padding + 10;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		// Draw title bar text
		g.setColor(Color.BLACK);
		g.drawString(title, getX() + padding, getY() + paddingBig);		

		// Draw close button
		g.setColor(Color.RED);
		g.fillRect(getX() + getWidth() - getHeight(), getY(), getHeight(), getHeight());
		g.setColor(Color.BLACK);
		Stroke stroke = new BasicStroke(2);
		g.setStroke(stroke);
		g.drawLine(getX() + getWidth() - paddingBig, getY() + padding, getX() + getWidth() - padding,
				getY() + paddingBig);
		g.drawLine(getX() + getWidth() - padding, getY() + padding, getX() + getWidth() - paddingBig,
				getY() + paddingBig);
		stroke = new BasicStroke(1);
		g.setStroke(stroke);
	}
}
