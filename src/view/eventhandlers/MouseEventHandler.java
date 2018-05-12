package view.eventhandlers;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import domain.Component;
import domain.party.Party;
import view.InteractionManager;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.windows.DiagramWindow;
import view.windows.SubWindow;

/**
 * EventHandler class. Translates user input for Controller
 * 
 * @author groep 03
 */
public class MouseEventHandler {
	InteractionManager interactionManager;

	/**
	 * EventHandler Constructor
	 * 
	 * @param window
	 *            Main Window
	 */
	public MouseEventHandler(InteractionManager interactionManager) {
		this.interactionManager = interactionManager;
	}

	/**
	 * When a mouse event occurs, it is handled in this method. If the mouse is
	 * pressed, the object is focused, if it's dragged, the object should move (if
	 * it's a party), if the mouse has clicked, it gets focused or unfocused at one
	 * click, it draws a new party.
	 * 
	 * @param id
	 *            mouseEvent id
	 * @param x
	 *            coordinate x
	 * @param y
	 *            coordinate y
	 * @param clickCount
	 *            the number of times the mouse has clicked.
	 * @throws IllegalArgumentException
	 *             Illegal id, coordinates or clickCount
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		if (id < 0 || x < 0 || y < 0 || clickCount < 0)
			throw new IllegalArgumentException();

		// TODO opkuisen, zie keyEventHandler
		// TODO labelstate !!! aanmaken

		/*if (active.clickOutsideActiveSubwindow(x, y))
			return;*/

			switch (id) {
			case MouseEvent.MOUSE_PRESSED:
				interactionManager.pressed(x,y);
				break;
			case MouseEvent.MOUSE_DRAGGED:
				interactionManager.dragged(x,y);
				break;
			case MouseEvent.MOUSE_RELEASED:
				interactionManager.released(x,y);
				break;
			case MouseEvent.MOUSE_CLICKED:
				interactionManager.clicked(clickCount, x,y);
				break;
			}
	}
}