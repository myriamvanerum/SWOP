package view;

import java.awt.Graphics2D;

public class ComState  implements State {

	@Override
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		g.drawString("COMMUNICATION DIAGRAM", x, y);		
	}

	@Override
	public void drawContents() {
		// TODO Auto-generated method stub
		
	}

}
