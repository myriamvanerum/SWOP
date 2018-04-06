package view;

import java.awt.Graphics2D;

public interface State {
	public void drawTitle(Graphics2D g, Integer x, Integer y);
	public void drawContents();
}
