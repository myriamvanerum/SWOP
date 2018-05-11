package view.windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import domain.Component;
import domain.message.Message;
import domain.party.Party;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.components.ViewMessage;
import view.components.ViewParty;

public abstract class SubWindow {
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;	
	private Titlebar titlebar;
	
	public SubWindow(Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super();
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setTitlebar(titlebar);
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
	
	public Titlebar getTitlebar() {
		return titlebar;
	}
	
	public void setTitlebar(Titlebar titlebar) {
		this.titlebar = titlebar;
	}	
	
	public void drawWhiteField(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
	}
	
	public void drawBlackBorder(Graphics2D g) {
		g.setColor(Color.BLACK);
		Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		g.draw(r);
	}
	
	/**
	 * Checks if clicked position is part of the active subwindow
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subwindow
	 *            The current active subwindow
	 * @return true if the clickevent occured outside of the active subwindow
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public boolean clickOutsideActiveSubwindow(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		return x < getX() || y < getY() || x > getX() + getWidth() || y > getY() + getHeight();
	}
	
	/**
	 * Checks if the close button of a subwindow is clicked
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subwindow
	 *            The current active subwindow
	 * @return True if the close button of the subwindow is clicked False if the
	 *         close butten of the subwindow isn't clicked
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public boolean clickCloseButton(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		return x >= (getX() + getWidth() - getTitlebar().getHeight()) && x <= (getX() + getWidth()) && y >= getY()
				&& y <= (getY() + getTitlebar().getHeight());
	}

	public abstract void draw(Graphics2D g);
	
	public void removeViewParty(Party party) {}
	public void changeViewParty(Party party, Party partyNew) {}
	public void addViewParty(Party party, Point2D position) {}
	public ViewParty findViewParty(Party party) {return null;}
	public void removeViewMessage(Message message) {}
	public void addViewMessage(Message message, Point2D position) {}
	public ViewMessage findViewMessage(Message message) {return null;}
	public void editViewLabel(Component component) {}
	public ViewComponent clickParty(int x2, int y2) {return null;}
	public Party clickLifeline(int x2, int y2) {return null;}
	public void moveComponent(ViewComponent selectedComponent, int x2, int y2) {}
	
	public void selectParty(Party party) {}
	public void selectMessage(Message message) {}
		
	public void setSelectedComponent(ViewComponent viewComponent) {}
	public ViewComponent getSelectedComponent() {return null;}

	public ViewLabel clickLabel(int x2, int y2) {return null;}
	public void selectComponent() {}
	public void changeLabelState(String string) {}

	public Message getPreviousMessage(Party sender, int y2) {return null;}
}
