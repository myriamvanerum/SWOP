package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Actor;
import model.Component;
import model.Interaction;
import model.InvocationMessage;
import model.Message;
import model.Party;
/**
 * SubWindow class. A SubWindow contains an Interaction with Parties and Messages. 
 * Several SubWindows can display the same Interaction in different forms.
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
	private Integer heightTitlebar = 25;

	private State windowState;
	private SeqState seqState = new SeqState();
	private ComState comState = new ComState();
	
	private LabelMode labelMode;
	public ViewComponent selectedComponent;

	/**
	 * Create a new SubWinow for a new Interaction
	 * 
	 * @param interaction
	 * 		The Interaction for this Subwindow
	 * @param x
	 * 		The SubWindow's x coordinate
	 * @param y
	 * 		The SubWindow's y coordinate
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
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
		setLabelMode(LabelMode.SHOW);
		selectedComponent = null;
	}

	/**
	 * Create a new SubWindow by duplicating another SubWindow
	 * 
	 * @param activeWindow
	 * 		The SubWindow to duplicate
	 * @param x
	 * 		The SubWindow's x coordinate
	 * @param y
	 * 		The SubWindow's y coordinate
	 * @throws NullPointerException
	 * 		No SubWindow supplied
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	@SuppressWarnings("unchecked")
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
		setLabelMode(LabelMode.SHOW);
		selectedComponent = null;
	}

	// TODO
	private ArrayList<ViewMessage> copyMessages(ArrayList<ViewMessage> viewMessages) {
		ArrayList<ViewMessage> copy = new ArrayList<>();
		
		for (ViewMessage viewMessage : viewMessages) {
			ViewMessage newViewMessage; //new ViewMessage(message, position, windowPosition, sender, receiver)
			ViewParty receiver = null, sender = null;
			
			for (ViewParty viewParty: getViewParties()) {				
				if ( viewMessage.getSender().getParty() == viewParty.getParty()) {
					sender = viewMessage.getSender();
				}
				if ( viewMessage.getReceiver().getParty() == viewParty.getParty()) {
					receiver = viewMessage.getReceiver();
				}
			}
			
			Point2D position;
			if (windowState == seqState) {
				position = new Point2D.Double(viewMessage.getPositionSeq().getX(), viewMessage.getPositionSeq().getY());
			} else {
				position = new Point2D.Double(viewMessage.getPositionCom().getX(), viewMessage.getPositionCom().getY());
			}
			
			
			Point2D subwindow = new Point2D.Double((double)getX(), (double)getY());
			if (viewMessage instanceof ViewInvocationMessage)
				newViewMessage = new ViewInvocationMessage(viewMessage.getMessage(),position,subwindow, sender, receiver);
			else newViewMessage = new ViewResultMessage(viewMessage.getMessage(),position,subwindow, sender, receiver);
			
			/*double xSeq = newViewMessage.getPositionSeq().getX() + subwindow.getX();
			double ySeq = newViewMessage.getPositionSeq().getY() + subwindow.getY() + 25;
			newViewMessage.setPositionSeq(new Point2D.Double(xSeq, ySeq));
			
			double xCom = newViewMessage.getPositionCom().getX() + subwindow.getX();
			double yCom = newViewMessage.getPositionCom().getY() + subwindow.getY() + 25;
			newViewMessage.setPositionCom(new Point2D.Double(xCom, yCom));*/
			
			copy.add(newViewMessage);
		}
		
		return copy;
	}

	// TODO
	private ArrayList<ViewParty> copyParties(ArrayList<ViewParty> viewParties) {
		ArrayList<ViewParty> copy = new ArrayList<>();
		
		for (ViewParty viewParty : viewParties) {
			Component party = viewParty.getComponent();				
			Point2D position;
			if (windowState == seqState) {
				position = new Point2D.Double(viewParty.getPositionSeq().getX(), viewParty.getPositionSeq().getY());
			} else {
				position = new Point2D.Double(viewParty.getPositionCom().getX(), viewParty.getPositionCom().getY());
			}
			
			ViewParty newViewParty;
			if (viewParty instanceof ViewObject)
				newViewParty = new ViewObject((Party)party, position);
			else newViewParty = new ViewActor((Party)party, position);
			newViewParty.setViewLabel(new ViewLabel(party.getLabel()));
			copy.add(newViewParty);
		}
		
		return copy;
	}

	/**
	 * Method to draw a SubWindow and all its contents
	 * 
	 * @param gOrig
	 * 		Graphics class
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

	/** 
	 * Change the SubWindows State
	 */
	public void changeState() {
		if (getState() == seqState)
			setState(comState);
		else
			setState(seqState);
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

	/**
	 * Draw the SubWindow title
	 * @param g
	 * 		Graphics class
	 * @param x
	 * 		X coordinates
	 * @param y
	 * 		Y coordinates
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	public void drawTitle(Graphics2D g, Integer x, Integer y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		getState().drawTitle(g, x, y);
	}

	/**
	 * Draw the Parties and Messages in the SubWindow
	 * @param g
	 * 		Graphics class
	 * @param viewParties
	 * 		The Parties in the SubWindow
	 * @param viewMessages
	 * 		The Messages in the SubWindow
	 */
	public void drawContents(Graphics2D g, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		getState().drawContents(g, new Point2D.Double(getX(), getY() + getHeightTitlebar()), viewParties, viewMessages);
	}

	/**
	 * Method to be called when a Party is deleted
	 * @param party
	 * 		The Party that was deleted
	 */
	@Override
	public void onDeleteParty(Party party) {
		ViewParty viewParty = findViewParty(party);
		getViewParties().remove(viewParty);
	}

	/**
	 * Method to be called when a Party type is changed
	 * @param party
	 * 		The Party whose type was changed
	 */
	@Override
	public void onChangeParty(Party party, Party partyNew) {
		ViewParty viewParty = findViewParty(party);
		viewParty.setParty(partyNew);
		getViewParties().remove(viewParty);
		
		ViewParty newViewParty;
		if (party instanceof Actor) {
			newViewParty = new ViewObject(viewParty);
		} else {
			newViewParty = new ViewActor(viewParty);
		}
		getViewParties().add(newViewParty); 
	}
	
	/**
	 * Method to be called when a Party is added
	 * @param party
	 * 		The Party that was added
	 * @param position
	 * 		The position the Party must be painted at
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
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
	 * @param message
	 * 		The Message that was deleted
	 */
	@Override
	public void onDeleteMessage(Message message) {
		ViewMessage viewMessage = findViewMessage(message);
		getViewMessages().remove(viewMessage);
	}

	/**
	 * Method to be called when a Message is added
	 * @param message
	 * 		The Message that was added
	 * @param position
	 * 		The position the Message must be painted at
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	@Override
	public void onAddMessage(Message message, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		ViewParty sender = findViewParty(message.getSender());
		ViewParty receiver = findViewParty(message.getReceiver());
		ViewMessage viewMessage;
		
		if (message instanceof InvocationMessage)		
			viewMessage = new ViewInvocationMessage(message, position, new Point2D.Double(getX(), getY()), sender, receiver);
		else 
			viewMessage = new ViewResultMessage(message, position, new Point2D.Double(getX(), getY()), sender, receiver);
		
		getViewMessages().add(viewMessage);
	}
		
	/**
	 * Find the ViewParty for a Party
	 * @param party
	 * 		The Party to find
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
	 * @param message
	 * 		The Message to find
	 * @return The ViewMessage to find, or null
	 */
	public ViewMessage findViewMessage(Message message) {
		for (ViewMessage viewMessage : getViewMessages()) {
			if (viewMessage.getMessage() == message)
				return viewMessage;
		}
		return null;
	}

	public LabelMode getLabelMode() {
		return labelMode;
	}

	public void setLabelMode(LabelMode labelMode) {
		this.labelMode = labelMode;
	}

	public ViewComponent getSelectedComponent() {
		return selectedComponent;
	}

	public void setSelectedComponent(ViewComponent selectedComponent) {
		this.selectedComponent = selectedComponent;
	}
	
	public void updateLabels() {
		ArrayList<ViewComponent> components = new ArrayList<>();
		components.addAll(getViewParties());
		components.addAll(getViewMessages());
		for (ViewComponent component: getViewMessages()) {
			component.getViewLabel().setLabelMode(LabelMode.SHOW);
		}
	}
}