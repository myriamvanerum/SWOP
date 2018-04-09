package view;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class SeqState implements State {

	@Override
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		g.drawString("SEQUENCE DIAGRAM", x, y);		
	}

	@Override
	public void drawContents(Graphics2D g, Integer x, Integer y, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		for (ViewParty viewParty : viewParties) {
	        viewParty.drawSeq(g, x, y);
	    }
	    for (ViewMessage viewMessage : viewMessages) {
	    	viewMessage.drawSeq(g);
	    }		
	}
}
