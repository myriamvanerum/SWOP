package view.inputhandler;

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import view.inputhandler.KeyModifierHandler.KeyModifierType;
import view.UI;

/**
 * InputHandler class. 
 * Translates user input for Controller
 * @author groep 03
 */
public class InputHandler {
	private UI ui;
	private KeyModifierHandler keyModifierHandler;

	/**
	 * InputHandler Constructor
	 * 
	 * @param UI
	 *          UI object
	 */
	public InputHandler(UI ui) {
		this.ui = ui;
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
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {	
		
		if (keyCode == KeyEvent.VK_UP)
    		ui.arrowUp();

		if (keyCode == KeyEvent.VK_DOWN)
			ui.arrowDown(); 
		
		if (keyChar == CHAR_UNDEFINED) {
            keyModifierHandler.addModifier(keyCode);
		} else {
			if (id == KeyEvent.KEY_TYPED) return; // ignore to prevent handling same keyPress twice
            KeyModifierType activeModifier = keyModifierHandler.getActiveKeyModifier();
            if (activeModifier == KeyModifierType.CTRL) {
            	switch (keyCode) {
    			case KeyEvent.VK_N:
    				ui.createNewInteraction();
    				break;
    			case KeyEvent.VK_D:
    				ui.duplicateActiveWindow();
    				break;
    			case KeyEvent.VK_ENTER:
    				ui.openDialogBox();
    				break;
    			}
            } else {
            	switch (keyCode) {
    			case KeyEvent.VK_TAB:
    				ui.pressTab();
    				break;
    			case KeyEvent.VK_DELETE:
    				ui.deleteComponent();
    				break;
    			case KeyEvent.VK_ENTER:
    				ui.confirmLabel();
    				break;
    			case KeyEvent.VK_BACK_SPACE:
    				ui.removeLabelCharacter();
    				break;
    			case KeyEvent.VK_SPACE:
    				ui.pressSpace();
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
    				ui.addLabelCharacter(keyCode, keyChar);
            }
		}
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
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		switch (id) {
		case MouseEvent.MOUSE_PRESSED:
			ui.pressed(x,y);
			break;
		case MouseEvent.MOUSE_DRAGGED:
			ui.dragged(x,y);
			break;
		case MouseEvent.MOUSE_RELEASED:
			ui.released(x,y);
			break;
		case MouseEvent.MOUSE_CLICKED:
			switch (clickCount) {
			case 1:
				ui.clickedOnce(x, y);
				break;
			case 2: 
				ui.clickedTwice(x, y);
				break;
			}
			break;
		}
	}
}