package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Component;
import model.Interaction;
import model.InvocationMessage;
import model.Message;
import model.Party;
import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewLabel;
import view.components.ViewLifeLine;
import view.components.ViewMessage;
import view.components.ViewObject;
import view.components.ViewParty;
import view.components.ViewResultMessage;
import view.diagramstate.ComState;
import view.diagramstate.SeqState;
import view.diagramstate.State;

/**
 * SubWindow class. A SubWindow contains an Interaction with Parties and
 * Messages. Several SubWindows can display the same Interaction in different
 * forms.
 * 
 * @author groep 03
 *
 */
public class SubWindow implements Observer {
	private Interaction interaction;

	private ArrayList<ViewParty> viewParties;
	private ArrayList<ViewMessage> viewMessages;

	private Integer x;
	private Integer y;
	private Integer width = 500;
	private Integer height = 400;
	
	private Titlebar titlebar;

	private State windowState;
	private SeqState seqState = new SeqState();
	private ComState comState = new ComState();

	private LabelState labelState;
	private ShowState showState = new ShowState(this);
	private InvocationState invocationState = new InvocationState(this);
	private PartyState partyState = new PartyState(this);

	public ViewComponent selectedComponent;

	/**
	 * Create a new SubWinow for a new Interaction
	 * 
	 * @param interaction
	 *            The Interaction for this Subwindow
	 * @param x
	 *            The SubWindow's x coordinate
	 * @param y
	 *            The SubWindow's y coordinate
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public SubWindow(Interaction interaction, Integer x, Integer y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		setInteraction(interaction);
		interaction.addObserver(this);

		setViewParties(new ArrayList<>());
		setViewMessages(new ArrayList<>());

		setX(x);
		setY(y);

		setState(seqState);
		// TODO labelstate
		setLabelState(showState);
		selectedComponent = null;
		titlebar = new Titlebar(getX(), getY(), getWidth());
	}

	/**
	 * Create a new SubWindow by duplicating another SubWindow
	 * 
	 * @param activeWindow
	 *            The SubWindow to duplicate
	 * @param x
	 *            The SubWindow's x coordinate
	 * @param y
	 *            The SubWindow's y coordinate
	 * @throws NullPointerException
	 *             No SubWindow supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public SubWindow(SubWindow activeWindow, Integer x, Integer y) {
		if (activeWindow == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		// leg link met interaction
		setInteraction(activeWindow.getInteraction());
		interaction.addObserver(this);

		setX(x);
		setY(y);

		// maak kopie van alle onderdelen van subwindow
		ArrayList<ViewParty> parties = copyParties(activeWindow.getViewParties());
		setViewParties(parties);
		ArrayList<ViewMessage> messages = copyMessages(activeWindow.getViewMessages());
		setViewMessages(messages);

		setState(activeWindow.getState());
		setLabelState(new ShowState(this));
		selectedComponent = null;
		
		titlebar = new Titlebar(getX(), getY(), getWidth());
	}

	/**
	 * Duplicate the ViewMessages of the original subwindow
	 * 
	 * @param viewMessages
	 *            The Messages in the SubWindow
	 * @return Copy of the viewMessages arraylist
	 */
	private ArrayList<ViewMessage> copyMessages(ArrayList<ViewMessage> viewMessages) {
		ArrayList<ViewMessage> copy = new ArrayList<>();

		for (ViewMessage viewMessage : viewMessages) {
			ViewMessage newViewMessage = viewMessage.copy();
			ViewParty sender = findViewParty(viewMessage.getMessage().getSender());
			ViewParty receiver = findViewParty(viewMessage.getMessage().getReceiver());
			newViewMessage.setSender(sender);
			newViewMessage.setReceiver(receiver);
			copy.add(newViewMessage);
		}

		return copy;
	}

	/**
	 * Duplicate the ViewParties of the original subwindow
	 * 
	 * @param viewParties
	 *            The Parties in the SubWindow
	 * @return Copy of the viewParties arraylist
	 */
	private ArrayList<ViewParty> copyParties(ArrayList<ViewParty> viewParties) {
		ArrayList<ViewParty> copy = new ArrayList<>();

		for (ViewParty viewParty : viewParties) {
			ViewParty newViewParty = viewParty.copy();
			copy.add(newViewParty);
		}

		return copy;
	}

	/**
	 * Method to draw a SubWindow and all its contents
	 * 
	 * @param gOrig
	 *            Graphics class
	 */
	public void draw(Graphics2D gOrig) {
		// Create a new Graphics object so clip can be used to only clip contents for
		// this SubWindow
		Graphics2D g = (Graphics2D) gOrig.create();

		// Draw white field
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY() + getTitlebar().getHeight(), getWidth(), getHeight() - getTitlebar().getHeight());

		// Draw title bar
		titlebar.draw(g, getState().getTitle());

		// Draw black border
		g.setColor(Color.BLACK);
		Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		g.draw(r);

		// Only draw within SubWindow limits (minus 1 px for border)
		g.setClip(getX() + 1, getY() + getTitlebar().getHeight(), getWidth() - 1, getHeight() - getTitlebar().getHeight());
		// Draw contents
		drawContents(g, getViewParties(), getViewMessages());
		g.dispose();
	}

	/**
	 * Change the SubWindows State
	 */
	public void changeState() {
		if (getState() == seqState)
			setState(comState);
		else
			setState(seqState);
	}

	/**
	 * Change the SubWindows LabelState
	 * 
	 * @param state
	 *            New label state
	 */
	public void changeLabelState(String state) {
		switch (state.toUpperCase()) {
		case "SHOW":
			labelState = showState;
			break;
		case "MESSAGE":
			labelState = invocationState;
			break;
		case "PARTY":
			labelState = partyState;
			break;
		default:
			break;
		}
	}

	/* GETTERS AND SETTERS */

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

	public Titlebar getTitlebar() {
		return titlebar;
	}

	public void setTitlebar(Titlebar titlebar) {
		this.titlebar = titlebar;
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

	public State getState() {
		return windowState;
	}

	public void setState(State windowState) {
		this.windowState = windowState;
	}

	public LabelState getLabelState() {
		return labelState;
	}
	
	public LabelState getShowState() {
		return showState;
	}

	public void setLabelState(LabelState labelState) {
		this.labelState = labelState;
	}

	public ViewComponent getSelectedComponent() {
		return selectedComponent;
	}

	public void setSelectedComponent(ViewComponent selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	/**
	 * Draw the Parties and Messages in the SubWindow
	 * 
	 * @param g
	 *            Graphics class
	 * @param viewParties
	 *            The Parties in the SubWindow
	 * @param viewMessages
	 *            The Messages in the SubWindow
	 */
	public void drawContents(Graphics2D g, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		getState().drawContents(g, new Point2D.Double(getX(), getY() + getTitlebar().getHeight()), viewParties, viewMessages);
	}

	public void moveComponent(ViewComponent component, int x, int y) {
		if (component == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		getState().moveComponent(component, new Point2D.Double(x, y), new Point2D.Double(getX(), getY()));
	}

	/**
	 * Method to be called when a Party is deleted
	 * 
	 * @param party
	 *            The Party that was deleted
	 */
	@Override
	public void onDeleteParty(Party party) {
		ViewParty viewParty = findViewParty(party);
		getViewParties().remove(viewParty);
	}

	/**
	 * Method to be called when a Party type is changed
	 * 
	 * @param party
	 *            The Party whose type was changed
	 */
	@Override
	public void onChangeParty(Party party, Party partyNew) {
		ViewParty viewParty = findViewParty(party);
		viewParty.setParty(partyNew);
		getViewParties().remove(viewParty);
		ViewParty newViewParty = viewParty.changeType();
		getViewParties().add(newViewParty);
	}

	/**
	 * Method to be called when a Party is added
	 * 
	 * @param party
	 *            The Party that was added
	 * @param position
	 *            The position the Party must be painted at
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	@Override
	public void onAddParty(Party party, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();

		ViewParty viewParty = new ViewObject(party, position, new Point2D.Double(getX(), getY()));
		getViewParties().add(viewParty);
	}

	/**
	 * Method to be called when a Message is deleted
	 * 
	 * @param message
	 *            The Message that was deleted
	 */
	@Override
	public void onDeleteMessage(Message message) {
		ViewMessage viewMessage = findViewMessage(message);
		getViewMessages().remove(viewMessage);
	}

	/**
	 * Method to be called when a Message is added
	 * 
	 * @param message
	 *            The Message that was added
	 * @param position
	 *            The position the Message must be painted at
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	@Override
	public void onAddMessage(Message message, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();

		ViewParty sender = findViewParty(message.getSender());
		ViewParty receiver = findViewParty(message.getReceiver());
		ViewMessage viewMessage;
		Point2D subwindow = new Point2D.Double((double) getX(), (double) getY());

		// TODO instanceof weg
		if (message instanceof InvocationMessage)
			viewMessage = new ViewInvocationMessage(message, position, subwindow, sender, receiver);
		else
			viewMessage = new ViewResultMessage(message, position, subwindow, sender, receiver);

		getViewMessages().add(viewMessage);
	}

	/**
	 * Find the ViewParty for a Party
	 * 
	 * @param party
	 *            The Party to find
	 * @return The ViewParty to find, or null
	 */
	public ViewParty findViewParty(Party party) {
		for (ViewParty viewParty : getViewParties()) {
			if (viewParty.getParty() == party)
				return viewParty;
		}
		return null;
	}

	/**
	 * Find the ViewMessage for a Message
	 * 
	 * @param message
	 *            The Message to find
	 * @return The ViewMessage to find, or null
	 */
	public ViewMessage findViewMessage(Message message) {
		for (ViewMessage viewMessage : getViewMessages()) {
			if (viewMessage.getMessage() == message)
				return viewMessage;
		}
		return null;
	}

	private void updateLabels(Component component, String label) {
		ArrayList<ViewComponent> components = new ArrayList<>();
		components.addAll(getViewParties());
		components.addAll(getViewMessages());
		for (ViewComponent viewComponent : components) {
			if (viewComponent.getComponent() == component) {
			ViewLabel viewLabel = viewComponent.getViewLabel();
			viewLabel.setColor(Color.BLACK);
			viewLabel.setOutput(label);
			}
		}
	}

	@Override
	public void onEditLabel(Component component) {
		setLabelState(showState);
		updateLabels(component, component.getLabel());
		if (getSelectedComponent() != null)
			getSelectedComponent().unselect();
		setSelectedComponent(null);
	}

	/**
	 * Select a Party or Message
	 * 
	 * @throws NullPointerException
	 *             No ViewComponent supplied
	 */
	public void selectComponent() {
		ViewComponent viewComponent = getSelectedComponent();
		if (viewComponent == null)
			throw new NullPointerException();

		System.out.println("Select component.");

		if (viewComponent.selected())
			viewComponent.unselect();
		else
			viewComponent.select();
	}

	/**
	 * Checks if there is a party at the clicked position
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @return Null if there is no party on the position given by the coordinates x
	 *         and y The ViewParty that is on the position given by the coordinates
	 *         x and y
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public ViewParty clickParty(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		for (ViewParty party : getViewParties()) {
			if (getState().checkCoordinates(party, new Point2D.Double(x, y), new Point2D.Double(getX(), getY())))
				return party;
		}
		return null;
	}

	/**
	 * Checks if a Label was clicked
	 * 
	 * @param x
	 *            Clicked x coordinates
	 * @param y
	 *            Clicked y coordinates
	 * @return the clicked Label, if there is one, otherwise null
	 * @throws NullPointerException
	 *             No subwindow supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public ViewLabel clickLabel(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		for (ViewParty party : getViewParties()) {
			if (getState().checkLabelPosition(party, new Point2D.Double(x, y), new Point2D.Double(getX(), getY()))) {
				setSelectedComponent(party);
				return party.getViewLabel();
			}
		}

		for (ViewMessage message : getViewMessages()) {
			if (getState().checkLabelPosition(message, new Point2D.Double(x, y), new Point2D.Double(getX(), getY()))) {
				setSelectedComponent(message);
				return message.getViewLabel();
			}
		}

		return null;
	}

	/**
	 * Checks if a LifeLine was clicked
	 * 
	 * @param x
	 *            The clicked x coordinates
	 * @param y
	 *            The clicked y coordinates
	 * @return the clicked LifeLine or null
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public Party clickLifeline(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		for (ViewParty party : getViewParties()) {
			ViewLifeLine lifeline = party.getViewLifeLine();
			if (x >= lifeline.getX() - 3 && x <= lifeline.getX() + 3 && y >= lifeline.getStartY()
					&& y <= lifeline.getEndY())
				return party.getParty();
		}

		return null;
	}

	/**
	 * Check the Message Call Stack
	 * 
	 * @param sender
	 *            Message sender
	 * @param receiver
	 *            Message receiver
	 * @return true if the call stack is correct
	 * @throws NullPointerException
	 *             No sender, receiver or subwindow supplied
	 */
	public boolean checkCallStack(Party sender, Party receiver) {
		if (sender == null || receiver == null)
			throw new NullPointerException();

		ViewParty first = findViewParty(sender);
		ViewParty second = findViewParty(receiver);

		return first.getPositionSeq().getX() < second.getPositionSeq().getX();
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
}