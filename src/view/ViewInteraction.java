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
import view.windows.DiagramWindow;

public class ViewInteraction implements Observer {
	private Interactr interactr;
	private Interaction interaction;
	
	public DiagramWindow activeWindow = null;
	public ArrayList<DiagramWindow> subWindows = new ArrayList<>();

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
	
	public DiagramWindow getActiveWindow() {
		return activeWindow;
	}

	public void setActiveWindow(DiagramWindow activeWindow) {
		this.activeWindow = activeWindow;
	}
	
	public ArrayList<DiagramWindow> getSubWindows() {
		return subWindows;
	}

	public void setSubWindows(ArrayList<DiagramWindow> subWindows) {
		this.subWindows = subWindows;
	}	

	public void deleteComponent() {
		ViewComponent viewComponent = getActiveWindow().getSelectedComponent();
		interactr.deleteComponent(viewComponent);
	}

	public Message addMessage(Party first, Party second, int x, int y) {
		return interactr.addMessage(first, second, x, y);
	}

	public Party addParty(Point2D position) {
		return interactr.addParty(position);
	}

	public void changePartyType() {
		ViewParty viewParty = (ViewParty) getActiveWindow().getSelectedComponent();
		interactr.changePartyType(viewParty);
	}

	public void drawWindows(Graphics2D g) {
		for (DiagramWindow window : getSubWindows()) {
        	if (window != activeWindow)
        		window.draw(g);
		}
		
		// Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g);
	}

	// TODO aanpassen duplicate code
	// TODO aanpassen zoek laagst gelegen window voor ALLE interactions
	public void addWindow() {
		Integer x = 10, y = 10;
		if (getActiveWindow() != null) {
			x = getActiveWindow().getX() + 10;
			y = getActiveWindow().getY() + 10;
		}
		
		// create new subwindow for new interaction
		DiagramWindow subWindow = new DiagramWindow(this, x, y);
		
		subWindows.add(subWindow);
		setActiveWindow(subWindow);
	}
	
	public void duplicateActiveWindow() {
		Integer x = getActiveWindow().getX() + 10;
		Integer y = getActiveWindow().getY() + 10;
		
		// create new subwindow for new interaction
		DiagramWindow subWindow = new DiagramWindow(getActiveWindow(), x, y);
		
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
		for (DiagramWindow window : getSubWindows()) {
			window.removeViewParty(party);
		}
	}

	/**
	 * Method to be called when a Party type is changed
	 * 
	 * @param party
	 *            The Party whose type was changed
	 */
	@Override
	public void onChangeParty(Party party, Party partyNew) {
		for (DiagramWindow window : getSubWindows()) {
			window.changeViewParty(party, partyNew);
		}
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
		
		for (DiagramWindow window : getSubWindows()) {
			window.addViewParty(party, position);
			if (window == getActiveWindow()) {
				window.setSelectedComponent(window.findViewParty(party));
				window.changeLabelState("PARTY");
			}
		}
	}

	/**
	 * Method to be called when a Message is deleted
	 * 
	 * @param message
	 *            The Message that was deleted
	 */
	@Override
	public void onDeleteMessage(Message message) {
		for (DiagramWindow window : getSubWindows()) {
			window.removeViewMessage(message);
		}
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
		
		for (DiagramWindow window : getSubWindows()) {
			window.addViewMessage(message, position);
			
			if (window == getActiveWindow()) {
				ViewMessage viewMessage = window.findViewMessage(message);
				window.changeLabelState("MESSAGE");
				window.setSelectedComponent(viewMessage);
			}
		}
	}
	
	@Override
	public void onEditLabel(Component component) {
		for (DiagramWindow window : getSubWindows()) {
			window.editViewLabel(component);
		}
	}

	public Boolean activateSubwindow(int x, int y) {
		DiagramWindow subwindow = findSubWindow(x, y);
		
		if (subwindow != null) {
			setActiveWindow(subwindow); 
			return true;
		}

		return false;
	}
	
	public DiagramWindow findSubWindow(int x, int y) {
		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			if (getSubWindows().get(i) != getActiveWindow()) {
				int xSub = getSubWindows().get(i).getX();
				int ySub = getSubWindows().get(i).getY();
				int width = getSubWindows().get(i).getWidth();
				int height = getSubWindows().get(i).getHeight();

				if (x >= xSub && x <= xSub + width && y >= ySub && y <= ySub + height) {	
					return getSubWindows().get(i);
				}
			}
		}
		return null;
	}

	public boolean closeWindow(int x, int y) {
		if (getActiveWindow().clickCloseButton(x, y)) {
			removeWindow(getActiveWindow());
			
			int index = getSubWindows().size();
			if (index <= 0)
				setActiveWindow(null);
			else
				setActiveWindow(getSubWindows().get(index-1));
			
			return true;
		}

		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			DiagramWindow window = getSubWindows().get(i);
			if (window.clickCloseButton(x, y)) {
				removeWindow(window);
				return true;
			}
		}
		return false;
	}

	private void removeWindow(DiagramWindow window) {
		System.out.println("Close SubWindow.");
		getSubWindows().remove(window);			
	}

	public boolean hasNoWindows() {
		return (getSubWindows().size() > 0);
	}

	public void removeInteractionObserver() {
		getInteraction().removeObserver(this);
	}

	public void changeActiveWindowState() {
		getActiveWindow().changeState();
	}
}