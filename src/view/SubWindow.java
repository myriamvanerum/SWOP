package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SubWindow {
	public void draw(Graphics2D g, int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		Integer width = 400;
		Integer height = 300;
		Integer heightTitlebar = 25;
		
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
	    g.drawLine(x + width - 17, y + 7, x + width - 7, y + 17);
	    g.drawLine(x + width - 7, y + 7, x + width - 17, y + 17);
	    
	    // Draw black border
	    g.setColor(Color.BLACK);
	    Rectangle r = new Rectangle(x, y, width, height);
		g.draw(r);
	}
}
