package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import model.Interaction;

public class SubWindow {
	private Interaction interaction;
	private Integer x;
	private Integer y;
	
	public SubWindow(Interaction interaction, Integer x, Integer y) {
		this.interaction = interaction;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		Integer width = 500;
		Integer height = 400;
		Integer heightTitlebar = 25;
		Integer padding = 7;
		
		// Draw white field
		g.setColor(Color.WHITE);
	    g.fillRect(x, y + heightTitlebar, width, height - heightTitlebar);
	    
		// Draw title bar
		g.setColor(Color.LIGHT_GRAY);
	    g.fillRect(x, y, width, heightTitlebar);
	    
		// Draw title bar text
	    g.setColor(Color.BLACK);
		g.drawString("SEQUENCE DIAGRAM", x + 10, y + 18);
		
		// Draw close button
	    g.setColor(Color.RED);
		g.fillRect(x + width - heightTitlebar, y, heightTitlebar, heightTitlebar);
		g.setColor(Color.BLACK);
		Stroke stroke = new BasicStroke(2);
		g.setStroke(stroke);
	    g.drawLine(x + width - 10 - padding, y + padding, x + width - padding, y + 10 + padding);
	    g.drawLine(x + width - padding, y + padding, x + width - 10 - padding, y + 10 + padding);
	    
	    // Draw black border
	    g.setColor(Color.BLACK);
	    stroke = new BasicStroke(1);
		g.setStroke(stroke);
	    Rectangle r = new Rectangle(x, y, width, height);
		g.draw(r);
		
		// Draw contents
		
	}
	
	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
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
}
