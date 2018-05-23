package view.inputhandler;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * KeyModifierHandler class. 
 * @author groep 03
 */
public class KeyModifierHandler {
	// modifier is valid for .5 seconds
	private int validFor = 500;
    private long keyEnteredTime;
    private ArrayList<Integer> keyCodes;
    
    /**
     * Set the modifier
     * @param keyCode
     * 			Key input code
     */
	public void setModifier(int keyCode) {
		resetExpired();
        if ((keyCode != KeyEvent.VK_CONTROL) || keyCodes.contains(keyCode)) return;
        this.keyCodes.add(keyCode);
    }
	
	/**
	 * Checks if Ctrl key was pressed
	 * @return True if the Ctrl key was pressed
	 * 		   False if the Ctrl key was not pressed
	 */
	public boolean ctrlModifierActive() {
		resetExpired();
		return (keyCodes.contains(KeyEvent.VK_CONTROL));
	}
	
	/**
	 * Reset the keyCodes arraylist if the flag has expired
	 */
	private void resetExpired() {
        if (expired()) reset();
    }
	
	/**
	 * Reset the keyCodes arraylist
	 */
	private void reset() {
        keyCodes = new ArrayList<>();
        keyEnteredTime = System.currentTimeMillis();
    }

	/**
	 * Check if a keyevent has expired
	 * @return True if a keyevent has expired
	 * 			False if a keyevent has not been expired
	 */
    private boolean expired() {
        return System.currentTimeMillis() - keyEnteredTime > validFor;
    }
}