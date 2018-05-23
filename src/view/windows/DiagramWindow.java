package view.windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;

import domain.Component;
import domain.message.Message;
import domain.party.Party;
import view.ViewInteraction;
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
 * DiagramWindow class. 
 * A DiagramWindow contains Parties and Messages. 
 * Several DiagramWindows can display the same Interaction in different forms.
 * 
 * @author groep 03
 */
public class DiagramWindow extends SubWindow {
	private ArrayList<ViewParty> viewParties;
	private ArrayList<ViewMessage> viewMessages;

	private State windowState;
	private SeqState seqState = new SeqState();
	private ComState comState = new ComState();

	/**
	 * Create a new DiagramWindow for a new Interaction
	 * 
	 * @param interaction
	 *            The Interaction for this DiagramWindow
	 * @param x
	 *            The DiagramWindow's x coordinate
	 * @param y
	 *            The DiagramWindow's y coordinate
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public DiagramWindow(ViewInteraction viewInteraction, Integer x, Integer y) {
		super(x, y, 500, 400, new Titlebar(x, y, 500));

		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		setViewInteraction(viewInteraction);
		setViewParties(new ArrayList<>());
		setViewMessages(new ArrayList<>());

		setState(seqState);
		setSelectedComponent(null);
		setTitlebar(new Titlebar(getX(), getY(), getWidth()));
	}

	/**
	 * Create a new DiagramWindow by duplicating another DiagramWindow
	 * 
	 * @param activeWindow
	 *            The DiagramWindow to duplicate
	 * @param x
	 *            The DiagramWindow's x coordinate
	 * @param y
	 *            The DiagramWindow's y coordinate
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public DiagramWindow(DiagramWindow activeWindow, Integer x, Integer y) {
		super(x, y, 500, 400, new Titlebar(x, y, 500));
		
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		setViewInteraction(activeWindow.getViewInteraction());

		// maak kopie van alle onderdelen van subwindow
		ArrayList<ViewParty> parties = copyParties(activeWindow.getViewParties());
		setViewParties(parties);
		ArrayList<ViewMessage> messages = copyMessages(activeWindow.getViewMessages());
		setViewMessages(messages);

		setState(activeWindow.getState());
		setSelectedComponent(null);
		setTitlebar(new Titlebar(getX(), getY(), getWidth()));
	}
	
	/**
	 * Duplicate the ViewParties of the original DiagramWindow
	 * 
	 * @param viewParties
	 *            The Parties in the DiagramWindow
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
	 * Duplicate the ViewMessages of the original DiagramWindow
	 * 
	 * @param viewMessages
	 *            The Messages in the DiagramWindow
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
	
	/* DRAWING */

	/**
	 * Method to draw a DiagramWindow and all its contents
	 * 
	 * @param gOrig
	 *            Graphics class
	 */
	@Override
	public void draw(Graphics2D gOrig) {
		// Create a new Graphics object so clip can be used to only clip contents for this SubWindow
		Graphics2D g = (Graphics2D) gOrig.create();
		drawWhiteField(g);
		getTitlebar().draw(g, getState().getTitle());
		drawBlackBorder(g);
		// Only draw within SubWindow limits (minus 1 px for border)
		g.setClip(getX() + 1, getY() + getTitlebar().getHeight(), getWidth() - 1, getHeight() - getTitlebar().getHeight());
		drawContents(g, getViewParties(), getViewMessages());
		g.dispose();
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
		for (ViewParty viewParty : viewParties) {
			viewParty.draw(g, getState(), new Point2D.Double(getX(), getY() + getTitlebar().getHeight()));
	    }

    	for (ViewMessage viewMessage : viewMessages) {
    		viewMessage.draw(g, getState(), new Point2D.Double(getX(), getY() + getTitlebar().getHeight()));
	    }
	}

	/**
	 * Change the DiagramWindow State
	 */
	public void changeState() {
		if (getState() == seqState)
			setState(comState);
		else
			setState(seqState);
	}
	
	/* LABEL METHODS */
	
	@Override
	public ViewLabel getCurrentViewLabel() {
		if (getSelectedComponent() == null) return null;
		return getSelectedComponent().getViewLabel();
	}

	/**
	 * Update all the labels of the ViewComponents that have the same component
	 * 
	 * @param component
	 * 		  The component whose label has been changed
	 * @param label
	 * 		  The value of the new label
	 */
	private void updateLabels(Component component, String label) {
		// TODO mss met observer ofzo doen?
		for (ViewComponent viewComponent : getComponents()) {
			if (viewComponent.getComponent() == component) {
				ViewLabel viewLabel = viewComponent.getViewLabel();
				viewLabel.setColor(Color.BLACK);
				viewLabel.setOutput(label);
				return;
			}
		}
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
	@Override
	public ViewLabel clickLabel(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		for (ViewComponent viewComponent : getComponents()) {
			if (viewComponent.getViewLabel().clicked(x, y)) {
				setSelectedComponent(viewComponent);
				return viewComponent.getViewLabel();
			}
		}
		return null;
	}
	
	@Override
	public void editViewLabel(Component component) {
		setLabelState(getShowState());
		updateLabels(component, component.getLabel());
		if (getSelectedComponent() != null)
			getSelectedComponent().unselect();
		setSelectedComponent(null);
	}
	
	/* COMPONENT METHODS */

	/**
	 * Change the position of a component in the DiagramWindow to the given coordinates
	 * 
	 *  @param	x
	 *            The new x coordinate of the selected component
	 *  @param 	y
	 *            The new y coordinate of the selected component
	 */
	public void moveComponent(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		if (getSelectedComponent() == null || getSelectedComponent().isSelected || editingLabel()) return;
		getState().moveComponent(getSelectedComponent(), new Point2D.Double(x, y), new Point2D.Double(getX(), getY()));
	}
	
	/**
	 * Select a Party or Message
	 * 
	 * @throws NullPointerException
	 *             No ViewComponent supplied
	 */
	@Override
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
	 * Select the component that is positioned at the clicked position
	 * 
	 * @param	x
	 *          The x coordinate of the clicked position
	 * @param 	y
	 *          The y coordinate of the clicked position*/
	@Override 
	public void selectComponent(int x, int y) {
		if (editingLabel()) return;
		setSelectedComponent(clickParty(x, y));
	}
	
	/* PARTY METHODS */
	
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
	 * Checks if there is a party at the clicked position
	 * 
	 * @param	x
	 *          The x coordinate of the clicked position
	 * @param 	y
	 *          The y coordinate of the clicked position
	 * @return 	Null if there is no party on the position given by the coordinates x and y 
	 *         	The ViewParty that is on the position given by the coordinates x and y
	 * @throws 	IllegalArgumentException
	 *          	Illegal coordinates
	 */
	@Override
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
	@Override
	public Party clickLifeline(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		if (editingLabel()) return null;
		
		for (ViewParty party : getViewParties()) {
			ViewLifeLine lifeline = party.getViewLifeLine();
			if (x >= lifeline.getX() - 3 && x <= lifeline.getX() + 3 && y >= lifeline.getStartY() && y <= lifeline.getEndY())
				return party.getParty();
		}
		return null;
	}
	
	@Override
	public void addViewParty(Party party, Point2D position) {
		ViewParty viewParty = new ViewObject(party, position, new Point2D.Double(getX(), getY()));
		getViewParties().add(viewParty);
	}
	
	/**
	 * Change party type
	 * 
	 * @param party
	 * 			The original party
	 * @param partyNew
	 * 			The new party
	 */
	@Override
	public void changeViewParty(Party party, Party partyNew) {
		ViewParty viewParty = findViewParty(party);
		viewParty.setParty(partyNew);
		getViewParties().remove(viewParty);
		ViewParty newViewParty = viewParty.changeType();
		getViewParties().add(newViewParty);
		for (ViewMessage viewMessage : getViewMessages())
			viewMessage.changeViewParty(viewParty, newViewParty);
	}

	/**
	 * Remove the ViewParty that belongs to the given party
	 * 
	 * @param	party
	 * 			The party that belongs to the viewparty that has to be removed	
	 */
	@Override
	public void removeViewParty(Party party) {
		ViewParty viewParty = findViewParty(party);
		getViewParties().remove(viewParty);
	}
	
	@Override
	public void selectParty(Party party) {
		setSelectedComponent(findViewParty(party));
		changeLabelState("PARTY");
	}
	
	/* MESSAGE METHODS */

	/**
	 * Find the ViewMessage for a Message
	 * 
	 * @param message
	 *            The Message to find
	 * @return The ViewMessage to find, or null
	 */
	@Override
	public ViewMessage findViewMessage(Message message) {
		for (ViewMessage viewMessage : getViewMessages()) {
			if (viewMessage.getMessage() == message)
				return viewMessage;
		}
		return null;
	}

	@Override
	public void addViewMessage(Message message, Point2D position) {
		ViewParty sender = findViewParty(message.getSender());
		ViewParty receiver = findViewParty(message.getReceiver());
		ViewMessage viewMessage;
		Point2D subwindow = new Point2D.Double((double) getX(), (double) getY());
		
		for (ViewMessage vMessage : getViewMessages()) {
			vMessage.moveDownIfBelow(position.getY() - getY() - getTitlebar().getHeight());
			vMessage.lengthenActivationBar(position.getY() - getY() - getTitlebar().getHeight());
		}

		viewMessage = new ViewInvocationMessage(message, position, subwindow, sender, receiver);
		position.setLocation(position.getX(), position.getY() + 30);
		ViewMessage resMessage = new ViewResultMessage(message.getCompanion(), position, subwindow, receiver, sender);

		getViewMessages().add(viewMessage);
		getViewMessages().add(resMessage);
	}

	@Override
	public void removeViewMessage(Message message) {
		ViewMessage viewMessage = findViewMessage(message);
		getViewMessages().remove(viewMessage);
	}

	@Override
	public void selectMessage(Message message) {
		setSelectedComponent(findViewMessage(message));
		changeLabelState("MESSAGE");
	}
	
	@Override
	public Message getPreviousMessage(int yClicked) {
		Message previous = null;
		ArrayList<ViewMessage> messages = copyMessages(getViewMessages());
		messages.sort(Comparator.comparing(m -> m.getPositionSeq().getY()));
		yClicked -= getY() + getTitlebar().getHeight();
		
		for (ViewMessage message : messages) {
			if (message.getPositionSeq().getY() <= yClicked) 
				previous = message.getMessage();
			else
				break;
		}

		return previous;
	}

	/* USER OPERATIONS */
	
	ViewComponent labelClickedOnce = null;
	@Override
	public void singleClick(int x2, int y2) {
		// TODO code fixen, kan beter qua design. Lege klik -> unselect
		if (editingLabel()) return;
		ViewLabel viewLabel = null;
		selectComponent(x2, y2);
	
		if ((viewLabel = clickLabel(x2, y2)) != null) {
			System.out.println("Label Clicked.");
			
			if (labelClickedOnce == null) {
				selectComponent();
				labelClickedOnce = getSelectedComponent();
			} else if (getSelectedComponent() == labelClickedOnce) {
				getSelectedComponent().setLabelState(this);
				viewLabel.setOutput(getSelectedComponent().getComponent().getLabel() + "|");
				labelClickedOnce = null;
			}
		}
	}
	
	/**
	 *  The tab key is pressed
	 */
	@Override
	public void pressTab() {
		if (editingLabel()) return;
		changeState();
	}
	
	@Override
	public boolean doubleClick() {
		return true;
	}
	
	/* GETTERS AND SETTERS */
	
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

	public State getState() {
		return windowState;
	}

	public void setState(State windowState) {
		this.windowState = windowState;
	}
	
	public ArrayList<ViewComponent> getComponents() {
		ArrayList<ViewComponent> components = new ArrayList<>();
		components.addAll(getViewParties());
		components.addAll(getViewMessages());
		return components;
	}
}