package view;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ComState  implements State {

	@Override
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		g.drawString("COMMUNICATION DIAGRAM", x, y);		
	}

	@Override
	public void drawContents(Graphics2D g, Integer x, Integer y, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		for (ViewParty viewParty : viewParties) {
	        viewParty.drawCom(g, x, y);
	    }
	    for (ViewMessage viewMessage : viewMessages) {
	    	viewMessage.drawCom(g);
	    }		
	}

}
