package view.eventhandlers;

import java.awt.event.KeyEvent;

import facade.Interactr;
import model.Party;
import view.InteractionManager;
import view.components.ViewComponent;
import view.labelstate.LabelState;
import view.windows.SubWindow;

/**
 * EventHandler class. Translates user input for Controller
 * 
 * @author groep 03
 */
public class KeyEventHandler {
	Interactr controller;
	InteractionManager interactionManager;
	ViewComponent labelClickedOnce;
	Party first, second;

	/**
	 * EventHandler Constructor
	 * 
	 * @param window
	 *            Main Window
	 */
	public KeyEventHandler(InteractionManager interactionManager) {
		controller = new Interactr(interactionManager);
		this.interactionManager = interactionManager;
	}

	/**
	 * When a keyevent occurs, it is handled in this method. If tab is pressed, the
	 * view of the diagram is switched, from communication to sequence and vice
	 * versa, if delete is pressed the focused party gets deleted. Labels can also
	 * be entered with this method. SubWindows can also be created.
	 * 
	 * @param id
	 *            keyEvent id
	 * @param keyCode
	 *            Keyboard key pressed
	 * @param keyChar
	 *            keyboard key pressed keyChar
	 * @throws IllegalArgumentException
	 *             Illegal id or keyCode
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id < 0 || keyCode < 0)
			throw new IllegalArgumentException();

		SubWindow active = interactionManager.getActiveWindow();
		LabelState labelState = null;

		if (active != null)
			labelState = active.getLabelState();

		switch (keyCode) {
		case KeyEvent.VK_TAB:
			if (active != null)
				active.changeState();
			break;
		case KeyEvent.VK_DELETE:
			if (active.getSelectedComponent() != null)
				controller.deleteComponent(active.getSelectedComponent());
			break;
		case KeyEvent.VK_N:
			if (keyChar == '' /* keyChar != 'n' && keyChar != 'N' && keyChar != 'ñ' */) {
				controller.createNewInteraction();
			}
			break;
		case KeyEvent.VK_D:
			if (keyChar == '' /* keyChar != 'd' && keyChar != 'D' && keyChar != 'ð' */) {
				if (interactionManager.getActiveWindow() != null)
					interactionManager.createNewSubWindow(null);
			}
			break;
		case KeyEvent.VK_ENTER:
			if (labelState != null)
				labelState.confirmLabel();
			break;
		case KeyEvent.VK_BACK_SPACE:
			if (labelState != null)
				labelState.removeCharacter();
			break;
		}

		if (labelState != null && keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z
				|| keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || keyCode == KeyEvent.VK_COLON
				|| keyCode == KeyEvent.VK_SEMICOLON || keyCode == KeyEvent.VK_UNDERSCORE
				|| keyCode == KeyEvent.VK_LEFT_PARENTHESIS || keyCode == KeyEvent.VK_RIGHT_PARENTHESIS
				|| keyCode == KeyEvent.VK_SPACE) {

			// TODO voor partylabel enkel bepaalde karakters toelaten
			// TODO voor messagelabel (bijna) alle karakters toelaten
			labelState.addCharacter(keyCode, keyChar);
		}
	}
}