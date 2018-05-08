package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import facade.Interactr;
import model.Component;
import model.Interaction;
import model.InvocationMessage;
import model.Message;
import model.Party;
import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewMessage;
import view.components.ViewObject;
import view.components.ViewParty;
import view.components.ViewResultMessage;
import view.windows.SubWindow;

public class ViewInteraction implements Observer {
	private Interactr interactr;
	private Interaction interaction;
	
	public SubWindow activeWindow = null;
	public ArrayList<SubWindow> subWindows = new ArrayList<>();
	
//	public ViewInteraction(Interaction interaction) {
//		setInteractr(new Interactr(this));
//		setInteraction(interaction);
//		interaction.addObserver(this);
//	}

	public ViewInteraction() {
		setInteractr(new Interactr(this));
		setInteraction(interactr.addInteraction());
		getInteraction().addObserver(this);
	}

	public Interactr getInteractr() {
		return interactr;
	}

	public void setInteractr(Interactr interactr) {
		this.interactr = interactr;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}
	
	public SubWindow getActiveWindow() {
		return activeWindow;
	}

	public void setActiveWindow(SubWindow activeWindow) {
		this.activeWindow = activeWindow;
	}
	
	public ArrayList<SubWindow> getSubWindows() {
		return subWindows;
	}

	public void setSubWindows(ArrayList<SubWindow> subWindows) {
		this.subWindows = subWindows;
	}	

	public void deleteComponent(ViewComponent selectedComponent) {
		interactr.deleteComponent(selectedComponent);
	}

	public Message addMessage(Party first, Party second, int x, int y) {
		return interactr.addMessage(first, second, x, y);
	}

	public Party addParty(Point2D position) {
		return interactr.addParty(position);
	}

	public void changePartyType(ViewParty selectedComponent) {
		interactr.changePartyType(selectedComponent);
	}

	public void drawWindows(Graphics2D g) {
		for (SubWindow window : getSubWindows()) {
        	if (window != activeWindow)
        		window.draw(g);
		}
		
		// Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g);
	}

	public void addWindow() {
		Integer x = getActiveWindow().getX();
		Integer y = getActiveWindow().getY();
		
		// create new subwindow for new interaction
		SubWindow subWindow = new SubWindow(this, x, y);
		
		subWindows.add(subWindow);
		setActiveWindow(subWindow);
	}
	
	public void duplicateActiveWindow() {
		Integer x = getActiveWindow().getX();
		Integer y = getActiveWindow().getY();
		
		// create new subwindow for new interaction
		SubWindow subWindow = new SubWindow(getActiveWindow(), x, y);
		
		subWindows.add(subWindow);
		setActiveWindow(subWindow);
	}
	
	/**
	 * Method to be called when a Party is deleted
	 * 
	 * @param party
	 *            The Party that was deleted
	 */
	@Override
	public void onDeleteParty(Party party) {
//		ViewParty viewParty = findViewParty(party);
//		getViewParties().remove(viewParty);
	}

	/**
	 * Method to be called when a Party type is changed
	 * 
	 * @param party
	 *            The Party whose type was changed
	 */
	@Override
	public void onChangeParty(Party party, Party partyNew) {
//		ViewParty viewParty = findViewParty(party);
//		viewParty.setParty(partyNew);
//		getViewParties().remove(viewParty);
//		ViewParty newViewParty = viewParty.changeType();
//		getViewParties().add(newViewParty);
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
//		if (position.getX() < 0 || position.getY() < 0)
//			throw new IllegalArgumentException();
//
//		ViewParty viewParty = new ViewObject(party, position, new Point2D.Double(getX(), getY()));
//		getViewParties().add(viewParty);
	}

	/**
	 * Method to be called when a Message is deleted
	 * 
	 * @param message
	 *            The Message that was deleted
	 */
	@Override
	public void onDeleteMessage(Message message) {
//		ViewMessage viewMessage = findViewMessage(message);
//		getViewMessages().remove(viewMessage);
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
//		if (position.getX() < 0 || position.getY() < 0)
//			throw new IllegalArgumentException();
//
//		ViewParty sender = findViewParty(message.getSender());
//		ViewParty receiver = findViewParty(message.getReceiver());
//		ViewMessage viewMessage;
//		Point2D subwindow = new Point2D.Double((double) getX(), (double) getY());
//
//		// TODO instanceof weg
//		if (message instanceof InvocationMessage)
//			viewMessage = new ViewInvocationMessage(message, position, subwindow, sender, receiver);
//		else
//			viewMessage = new ViewResultMessage(message, position, subwindow, sender, receiver);
//
//		getViewMessages().add(viewMessage);
	}
	
	@Override
	public void onEditLabel(Component component) {
//		setLabelState(showState);
//		updateLabels(component, component.getLabel());
//		if (getSelectedComponent() != null)
//			getSelectedComponent().unselect();
//		setSelectedComponent(null);
	}
}
