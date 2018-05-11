package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import domain.Component;
import domain.Interaction;
import domain.Interactr;
import domain.message.Message;
import domain.party.Party;
import view.components.ViewComponent;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.windows.DiagramWindow;
import view.windows.DialogBox;
import view.windows.SubWindow;

public class ViewInteraction implements Observer {
	private Interactr interactr;
	private Interaction interaction;
	
	public SubWindow activeWindow = null;
	public ArrayList<SubWindow> subWindows = new ArrayList<>();

	/* CONSTRUCTOR */
	
	public ViewInteraction() {
		setInteractr(new Interactr(this));
		setInteraction(interactr.addInteraction());
		getInteraction().addObserver(this);
	}
	
	/* DRAWING */
	
	public void drawWindows(Graphics2D g) {
		for (SubWindow window : getSubWindows()) {
        	if (window != activeWindow)
        		window.draw(g);
		}
		
		// Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g);
	}
	
	/* WINDOW OPERATIONS */
	
	public void createDiagramWindow(Point2D lowestPosition) {
		System.out.println("Create New DiagramWindow.");
		DiagramWindow subWindow = new DiagramWindow(this, (int)lowestPosition.getX(), (int)lowestPosition.getY());
		
		addWindow(subWindow);
	}
	
	public void duplicateActiveWindow() {
		Integer x = getActiveWindow().getX() + 10;
		Integer y = getActiveWindow().getY() + 10;
		
		DiagramWindow subWindow = new DiagramWindow((DiagramWindow)getActiveWindow(), x, y);
		
		addWindow(subWindow);
	}
	
	public void addWindow(DiagramWindow subWindow) {
		getSubWindows().add(subWindow);
		setActiveWindow(subWindow);
	}
	
	public Boolean activateSubwindow(int x, int y) {
		SubWindow subwindow = findSubWindow(x, y);
		
		if (subwindow != null) {
			System.out.println("Activate Window.");
			setActiveWindow(subwindow); 
			return true;
		}

		return false;
	}
	
	public SubWindow findSubWindow(int x, int y) {
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
		if (getActiveWindow() != null && getActiveWindow().clickCloseButton(x, y)) {
			removeWindow(getActiveWindow());
			
			int index = getSubWindows().size();
			if (index == 0)
				setActiveWindow(null);
			else
				setActiveWindow(getSubWindows().get(index-1));
			
			return true;
		}

		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			SubWindow window = getSubWindows().get(i);
			if (window.clickCloseButton(x, y)) {
				removeWindow(window);
				return true;
			}
		}
		return false;
	}

	private void removeWindow(SubWindow window) {
		System.out.println("Close SubWindow.");
		getSubWindows().remove(window);			
	}

	public boolean hasNoWindows() {
		return (getSubWindows().size() == 0);
	}
	
	public void removeInteractionObserver() {
		getInteraction().removeObserver(this);
	}

	public void changeActiveWindowState() {
		DiagramWindow active = (DiagramWindow)getActiveWindow();
		active.changeState();
	}

	public void openDialogBox() {
		if (getActiveWindow().getSelectedComponent() == null) return;
		DialogBox dialogBox = getActiveWindow().getSelectedComponent().createDialogBox();
		subWindows.add(dialogBox);
		activeWindow = dialogBox;
	}
	
	/* COMPONENT OPERATIONS */

	public void deleteComponent() {
		ViewComponent viewComponent = getActiveWindow().getSelectedComponent();
		if (viewComponent == null) return;
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
	
	/* OBSERVER METHODDS */

	/**
	 * Method to be called when a Party type is changed
	 * 
	 * @param party
	 *            The Party whose type was changed
	 */
	@Override
	public void onChangeParty(Party party, Party partyNew) {
		for (SubWindow window : getSubWindows()) {
			window.changeViewParty(party, partyNew);
		}
	}
	
	/**
	 * Method to be called when a Party is deleted
	 * 
	 * @param party
	 *            The Party that was deleted
	 */
	@Override
	public void onDeleteParty(Party party) {
		for (SubWindow window : getSubWindows()) {
			window.removeViewParty(party);
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
		
		for (SubWindow window : getSubWindows()) {
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
		for (SubWindow window : getSubWindows()) {
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
		
		for (SubWindow window : getSubWindows()) {
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
		for (SubWindow window : getSubWindows()) {
			window.editViewLabel(component);
		}
	}
	
	/* GETTERS AND SETTERS */

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
}
