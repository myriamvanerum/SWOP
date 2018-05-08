package view.eventhandlers;

import java.awt.event.KeyEvent;

import view.InteractionManager;
import view.labelstate.LabelState;

/**
 * EventHandler class. Translates user input for Controller
 * 
 * @author groep 03
 */
public class KeyEventHandler {
	private InteractionManager windowManager;
	private KeyModifierHandler keyModifierHandler;
	private LabelState labelState;

	/**
	 * EventHandler Constructor
	 * 
	 * @param window
	 *            Main Window
	 */
	public KeyEventHandler(InteractionManager windowManager) {
		this.windowManager = windowManager;
		keyModifierHandler = new KeyModifierHandler();
		labelState = null;
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

		labelState = null;

		if (keyChar == KeyEvent.CHAR_UNDEFINED) {
			keyModifierHandler.setModifier(keyCode);
		} else {
            if (keyModifierHandler.ctrlModifierActive()) {
            	switch (keyCode) {
    			case KeyEvent.VK_N:
    				windowManager.createNewInteraction();
    				break;
    			case KeyEvent.VK_D:
    				windowManager.duplicateActiveWindow();
    				break;
    			case KeyEvent.VK_ENTER:
    				// TODO open dialog
    				break;
    			}
            }
            
            if (windowManager.getActiveInteraction().getActiveWindow() != null)
				labelState = windowManager.getActiveInteraction().getActiveWindow().getLabelState();
            
			switch (keyCode) {
			case KeyEvent.VK_TAB:
				windowManager.changeDiagramState();
				break;
			case KeyEvent.VK_DELETE:
				windowManager.deleteComponent();
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
}