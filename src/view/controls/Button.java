package view.controls;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import view.listboxcommand.ListBoxOperator;

public class Button extends WindowControl {
	private String text;
	private boolean isAvailable;
	private ListBoxOperator operator;
	private int[] limits = new int[2];

	public Button(String text, int x, int y, int height, int width, ListBoxOperator operator, boolean isAvailable, int[] limits) {
		super();
		this.text = text;
		this.operator = operator;
		this.isAvailable = isAvailable;
		this.limits = limits;
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
	
	private int[] getLimits() {
		return limits;
	}
	
	public ListBoxOperator getOperator() {
		return operator;
	}

	@Override
	public void draw(Graphics2D g) {
		if (getX() < 0 || getY() < 0 || getWidth() <= 0 || getHeight() <= 0)
			throw new IllegalArgumentException();

		RoundRectangle2D rec = new RoundRectangle2D.Double(getX(), getY(), getWidth(), getHeight(), 10, 10);

		if (isAvailable)
			g.setColor(new Color(165, 170, 200));
		else
			g.setColor(new Color(60, 60, 60));

		g.fill(rec);
		
		if (isActive())
			g.setColor(new Color(255, 145, 70));
		
		g.draw(rec);
		g.setColor(Color.BLACK);
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (getX() + getWidth() / 2) - (textWidth/2), getY() + 15);
	}
	
	@Override
	public void click() {
		if (isAvailable())
			getOperator().action();
		else System.out.println("Button unavailable.");
	}
	
	@Override
	public void space() {
		if (isAvailable())
			getOperator().action();
		else System.out.println("Button unavailable.");
	}
	
	@Override
	public void checkAvailability(int value, int size) {
		if (getLimits().length != 2) {
			if (value != size-1 && value > -1)
				setAvailable(true);
			else setAvailable(false);
		} else if (value > getLimits()[0] && size > getLimits()[1])
			setAvailable(true);
		else setAvailable(false);
	}
}
