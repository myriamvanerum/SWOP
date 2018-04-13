package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Actor;
import model.Interaction;
import model.Object;
import model.Party;

public class SubWindow implements Observer {
	private Interaction interaction;

	private ArrayList<ViewParty> viewParties;
	private ArrayList<ViewMessage> viewMessages;

	private Integer x;
	private Integer y;
	private Integer width = 500;
	private Integer height = 400;
	private Integer heightTitlebar = 25;

	private State windowState;
	private SeqState seqState = new SeqState();
	private ComState comState = new ComState();

	/**
	 * Create a new SubWinow for a new Interaction
	 * 
	 * @param interaction
	 * @param x
	 * @param y
	 */
	public SubWindow(Interaction interaction, Integer x, Integer y) {
		setInteraction(interaction);
		interaction.addObserver(this);

		setViewParties(new ArrayList<>());
		setViewMessages(new ArrayList<>());

		setX(x);
		setY(y);

		setState(seqState);
	}

	/**
	 * Create a new SubWindow by duplicating another SubWindow
	 * 
	 * @param activeWindow
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("unchecked")
	public SubWindow(SubWindow activeWindow, Integer x, Integer y) {
		// leg link met interaction
		setInteraction(activeWindow.getInteraction());
		interaction.addObserver(this);

		// maak kopie van alle onderdelen van subwindow
		/*
		 * ArrayList<ViewParty> parties = new
		 * ArrayList<ViewParty>(activeWindow.getViewParties().size()); for (ViewParty
		 * viewParty : activeWindow.getViewParties()) { parties.add(new
		 * ViewParty(viewParty)); }
		 */
		ArrayList<ViewParty> parties = activeWindow.getViewParties();
		ArrayList<ViewMessage> messages = activeWindow.getViewMessages();

		setViewParties((ArrayList<ViewParty>) parties.clone());
		setViewMessages((ArrayList<ViewMessage>) messages.clone());
		setX(x);
		setY(y);

		setState(activeWindow.getState());
	}

	/**
	 * Method to draw a SubWindow and all its contents
	 * 
	 * @param gOrig
	 */
	public void draw(Graphics2D gOrig) {
		Integer padding = 7;
		Integer paddingBig = padding + 10;

		// Create a new Graphics object so clip can be used to only clip contents for
		// this SubWindow
		Graphics2D g = (Graphics2D) gOrig.create();

		// Draw white field
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY() + getHeightTitlebar(), getWidth(), getHeight() - getHeightTitlebar());

		// Draw title bar
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(), getY(), getWidth(), getHeightTitlebar());

		// Draw title bar text
		g.setColor(Color.BLACK);
		drawTitle(g, getX() + padding, getY() + paddingBig);

		// Draw close button
		g.setColor(Color.RED);
		g.fillRect(getX() + getWidth() - getHeightTitlebar(), getY(), getHeightTitlebar(), getHeightTitlebar());
		g.setColor(Color.BLACK);
		Stroke stroke = new BasicStroke(2);
		g.setStroke(stroke);
		g.drawLine(getX() + getWidth() - paddingBig, getY() + padding, getX() + getWidth() - padding,
				getY() + paddingBig);
		g.drawLine(getX() + getWidth() - padding, getY() + padding, getX() + getWidth() - paddingBig,
				getY() + paddingBig);

		// Draw black border
		g.setColor(Color.BLACK);
		stroke = new BasicStroke(1);
		g.setStroke(stroke);
		Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		g.draw(r);

		// Only draw within SubWindow limits (minus 1 px for border)
		g.setClip(getX() + 1, getY() + getHeightTitlebar(), getWidth() - 1, getHeight() - getHeightTitlebar());
		// Draw contents
		drawContents(g, getViewParties(), getViewMessages());
		g.dispose();
	}

	public void changeState() {
		if (getState() == seqState)
			setState(comState);
		else
			setState(seqState);
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

	public State getState() {
		return windowState;
	}

	public void setState(State windowState) {
		this.windowState = windowState;
	}

	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		getState().drawTitle(g, x, y);
	}

	public void drawContents(Graphics2D g, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		getState().drawContents(g, new Point2D.Double(getX(), getY() + getHeightTitlebar()), viewParties, viewMessages);
	}

	@Override
	public void onDeleteParty(Party party) {
		// TODO Auto-generated method stub
		for (ViewParty viewParty : getViewParties()) {
			if (viewParty.getParty().equals(party))
				getViewParties().remove(viewParty);
		}
	}

	@Override
	public void onChangeParty(Party party, Party partyNew) {
		for (ViewParty viewParty : getViewParties()) {
			if (viewParty.getParty().equals(party)) {
				getViewParties().remove(viewParty);
				
				ViewParty newViewParty;
				if (party instanceof Actor) {
					newViewParty = new ViewObject(viewParty);
				} else {
					newViewParty = new ViewActor(viewParty);
				}
				getViewParties().add(newViewParty);
			}
		}
	}
	
	@Override
	public void onAddParty(Party party, Point2D position) {
		ViewParty viewParty = new ViewObject(party, position, new Point2D.Double(getX(), getY()));
		getViewParties().add(viewParty);
	}
}