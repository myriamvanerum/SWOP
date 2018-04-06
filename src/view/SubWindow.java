package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;

import model.Interaction;

public class SubWindow {
	private Interaction interaction;
	private ArrayList<ViewParty> viewParties;
	private ArrayList<ViewMessage> viewMessages;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;
	private Integer heightTitlebar;
	
	public SubWindow(Interaction interaction, Integer x, Integer y) {
		// ctrl n -> nieuwe, lege interaction
		setInteraction(interaction);
		
		setX(x);
		setY(y);
		setWidth(500);
		setHeight(400);
		setHeightTitlebar(25);
		
		setViewParties(new ArrayList<>());
		setViewMessages(new ArrayList<>());
	}
	
	public SubWindow(SubWindow activeWindow, Integer x, Integer y) {
		// leg link met interaction
		setInteraction(activeWindow.getInteraction());
		
		// maak kopie van alle onderdelen van subwindow
		ArrayList<ViewParty> parties = new ArrayList<ViewParty>(activeWindow.getViewParties().size());
	    for (ViewParty viewParty : activeWindow.getViewParties()) {
	        parties.add(new ViewParty(viewParty));
	    }
	    setViewParties(parties);
	    ArrayList<ViewMessage> messages = new ArrayList<ViewMessage>(activeWindow.getViewMessages().size());
	    for (ViewMessage viewMessage : activeWindow.getViewMessages()) {
	    	messages.add(new ViewMessage(viewMessage));
	    }
		setViewMessages(messages);
		
		setX(x);
		setY(y);
		setWidth(500);
		setHeight(400);
		setHeightTitlebar(25);
	}
	
	public void draw(Graphics2D g) {
		Integer padding = 7;
		
		// Draw white field
		g.setColor(Color.WHITE);
	    g.fillRect(getX(), getY() + heightTitlebar, getWidth(), getHeight() - heightTitlebar);
	    
		// Draw title bar
		g.setColor(Color.LIGHT_GRAY);
	    g.fillRect(getX(), getY(), getWidth(), heightTitlebar);
	    
		// Draw title bar text
	    g.setColor(Color.BLACK);
		g.drawString("SEQUENCE DIAGRAM", getX() + 10, getY() + 10 + padding);
		
		// Draw close button
	    g.setColor(Color.RED);
		g.fillRect(getX() + getWidth() - heightTitlebar, getY(), heightTitlebar, heightTitlebar);
		g.setColor(Color.BLACK);
		Stroke stroke = new BasicStroke(2);
		g.setStroke(stroke);
	    g.drawLine(getX() + getWidth() - 10 - padding, getY() + padding, getX() + getWidth() - padding, getY() + 10 + padding);
	    g.drawLine(getX() + getWidth() - padding, getY() + padding, getX() + getWidth() - 10 - padding, getY() + 10 + padding);
	    
	    // Draw black border
	    g.setColor(Color.BLACK);
	    stroke = new BasicStroke(1);
		g.setStroke(stroke);
	    Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		g.draw(r);
		
		// Draw contents
		// ! only draw contents inside the window !
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

	public ArrayList<ViewParty> getViewParties() {
		return viewParties;
	}

	public void setViewParties(ArrayList<ViewParty> viewParties) {
		this.viewParties = viewParties;
	}

	public ArrayList<ViewMessage> getViewMessages() {
		return viewMessages;
	}

	public void setViewMessages(ArrayList<ViewMessage> viewMessages) {
		this.viewMessages = viewMessages;
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

	public Integer getHeightTitlebar() {
		return heightTitlebar;
	}

	public void setHeightTitlebar(Integer heightTitlebar) {
		this.heightTitlebar = heightTitlebar;
	}
}
