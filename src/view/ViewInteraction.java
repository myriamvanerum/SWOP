package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import domain.Component;
import domain.Interaction;
import domain.Interactr;
import domain.message.Message;
import domain.party.Party;
import view.components.ViewComponent;
import view.components.ViewParty;
import view.windows.DiagramWindow;
import view.windows.DialogBox;
import view.windows.SubWindow;

public class ViewInteraction implements Observer {
	private Interactr interactr;
	private Interaction interaction;
	
	public SubWindow activeWindow = null;
	public ArrayList<SubWindow> subWindows = new ArrayList<>();
	
	private Point2D lastClickedPosition;

	/* CONSTRUCTOR */
	
	public ViewInteraction() {
		setInteraction(new Interaction());
		setInteractr(new Interactr(this));
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
	
	public Point2D findLowestWindow() {
		Point2D position = new Point2D.Double(0,0);
		if (getSubWindows().size() > 0) {
			SubWindow lowestWindow = Collections.max(getSubWindows(), Comparator.comparing(s -> s.getY()));
			position = new Point2D.Double(lowestWindow.getX() + 10, lowestWindow.getY() + 10);
		}
		return position;
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

	public void pressTab() {
		getActiveWindow().pressTab();
	}

	public void openDialogBox(Point2D lowestPosition) {
		if (selectedComponent() == null) return;
		DialogBox dialogBox = selectedComponent().createDialogBox(this, (int)lowestPosition.getX(), (int)lowestPosition.getY());
		getSubWindows().add(dialogBox);
		setActiveWindow(dialogBox);
	}
	
	/* DOMAIN OPERATIONS */

	public void deleteComponent() {
		if (getActiveWindow().editingLabel()) return;
		ViewComponent viewComponent = selectedComponent();
		if (viewComponent == null) return;
		getInteractr().deleteComponent(viewComponent.getComponent());
	}

	public void addMessage(Party sender, Party receiver, int x, int y) {
		Message previous = getActiveWindow().getPreviousMessage(y);
		getInteractr().addMessage(sender, receiver, previous);
	}
	
	public void editLabel(Component component, String label) {
		getInteractr().editLabel(component, label);
	}
	
	/* COMPONENT OPERATIONS */
	
	public void selectComponent(int x, int y) {
		getActiveWindow().selectComponent(x, y);
	}
	
	private ViewComponent selectedComponent() {
		return getActiveWindow().getSelectedComponent();
	}
	
	public void moveComponent(int x, int y) {
		setLastClickedPosition(new Point2D.Double(x, y));
		getActiveWindow().moveComponent(x, y);
	}
	
	public Party checkLifeLine(int x, int y) {
		return getActiveWindow().clickLifeline(x, y);
	}
	
	/* USER EVENTS */
	
	public void doubleClick(int x, int y) {
		setLastClickedPosition(new Point2D.Double(x, y));
		Party party;
		if ((party = getActiveWindow().getSelectedParty()) != null)
			getInteractr().changePartyType(party);
		else
			getInteractr().addParty();
	}
	
	public void singleClick(int x, int y) {
		setLastClickedPosition(new Point2D.Double(x, y));
		getActiveWindow().singleClick(x, y);
	}	
	
	Party sender, receiver;
	public void pressed(int x, int y) {
		setLastClickedPosition(new Point2D.Double(x, y));

		if (getActiveWindow().editingLabel()) return;
		sender = checkLifeLine(x, y);
		selectComponent(x, y);
	}

	public void released(int x, int y) {
		setLastClickedPosition(new Point2D.Double(x, y));
		receiver = checkLifeLine(x, y);
		if (sender != null && receiver != null) {
			addMessage(sender, receiver, x, y);
		}
		sender = null;
		receiver = null;
	}
	
	public void arrowUp() {
		getActiveWindow().scrollUp();
	}

	public void arrowDown() {
		getActiveWindow().scrollDown();
	}

	public void pressSpace() {
		getActiveWindow().pressSpace();		
	}
	
	/* LABEL METHODS */
	
	public void confirmLabel() {
		getActiveWindow().confirmLabel();
	}
	
	public void removeLabelCharacter() {
		getActiveWindow().removeLabelCharacter();
	}
	
	public void addLabelCharacter(int keyCode, char keyChar) {
		getActiveWindow().addLabelCharacter(keyCode, keyChar);
	}
	
	/* OBSERVER METHODS */

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
	 */
	@Override
	public void onAddParty(Party party) {
		for (SubWindow window : getSubWindows()) {
			window.addViewParty(party, getLastClickedPosition());
		}
		getActiveWindow().selectParty(party);
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
	 */
	@Override
	public void onAddMessage(Message message) {
		
		for (SubWindow window : getSubWindows()) {
			window.addViewMessage(message, getLastClickedPosition());
		}
		getActiveWindow().selectMessage(message);
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

	public Point2D getLastClickedPosition() {
		return lastClickedPosition;
	}

	public void setLastClickedPosition(Point2D lastClickedPosition) {
		this.lastClickedPosition = lastClickedPosition;
	}
}
