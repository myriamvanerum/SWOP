package view.eventhandlers;

import java.awt.event.KeyEvent;

import view.InteractionManager;

/**
 * EventHandler class. Translates user input for Controller
 * 
 * @author groep 03
 */
public class KeyEventHandler {
	private InteractionManager interactionManager;
	private KeyModifierHandler keyModifierHandler;

	/**
	 * EventHandler Constructor
	 * 
	 * @param window
	 *            Main Window
	 */
	public KeyEventHandler(InteractionManager interactionManager) {
		this.interactionManager = interactionManager;
		keyModifierHandler = new KeyModifierHandler();
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

		if (keyChar == KeyEvent.CHAR_UNDEFINED) {
			keyModifierHandler.setModifier(keyCode);
		} else {
            if (keyModifierHandler.ctrlModifierActive()) {
            	switch (keyCode) {
    			case KeyEvent.VK_N:
    				interactionManager.createNewInteraction();
    				break;
    			case KeyEvent.VK_D:
    				interactionManager.duplicateActiveWindow();
    				break;
    			case KeyEvent.VK_ENTER:
    				interactionManager.openDialogBox();
    				break;
    			}
            }
            
			switch (keyCode) {
			case KeyEvent.VK_TAB:
				interactionManager.pressTab();
				break;
			case KeyEvent.VK_DELETE:
				interactionManager.deleteComponent();
				break;
			case KeyEvent.VK_ENTER:
				interactionManager.confirmLabel();
				break;
			case KeyEvent.VK_BACK_SPACE:
				interactionManager.removeLabelCharacter();
				break;
			}

			if (((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) // karakters A tot Z
					|| (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) // alle cijfers
					|| (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) // alle cijfers
					|| keyCode == KeyEvent.VK_COLON // een dubbelpunt
					|| keyCode == KeyEvent.VK_SEMICOLON // een puntkomma
					|| keyCode == KeyEvent.VK_UNDERSCORE // een underscore
					|| keyCode == KeyEvent.VK_MINUS // een liggend streepje
					|| keyCode == KeyEvent.VK_LEFT_PARENTHESIS // een linkerhaakje
					|| keyCode == KeyEvent.VK_RIGHT_PARENTHESIS // een rechterhaakje
					|| keyCode == KeyEvent.VK_SPACE // een spatie
					|| keyCode == KeyEvent.VK_COMMA)) // een komma
			{
				interactionManager.addLabelCharacter(keyCode, keyChar);
			}
		}
	}
}