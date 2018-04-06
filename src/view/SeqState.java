package view;

import java.awt.Graphics2D;

public class SeqState implements State {

	@Override
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		g.drawString("SEQUENCE DIAGRAM", x, y);		
	}

	@Override
	public void drawContents() {
		// TODO Auto-generated method stub
		
	}

}
