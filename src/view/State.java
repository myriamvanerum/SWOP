package view;

import java.awt.Graphics2D;
import java.util.ArrayList;

public interface State {
	public void drawTitle(Graphics2D g, Integer x, Integer y);
	public void drawContents(Graphics2D g, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages);
}
