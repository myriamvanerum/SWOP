package view.inputhandler;

import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_ALT;

import java.util.ArrayList;

/**
 * KeyModifierHandler class. Handles adding KeyModifier functionalty to InputHandler
 * @author groep 03
 */
public class KeyModifierHandler {
	private static final int ACTIVE_TIME = 1000;
    private long timePressed = getCurrentTime();
    private ArrayList<Integer> keyCodes = new ArrayList<>();
    
    /**
     * KeyModifierType enumeration
     */
    enum KeyModifierType {
        NONE, CTRL_SHIFT, CTRL, SHIFT, ALT, CTRL_ALT
    }

    /**
     * Add a new KeyModifier to the list of active KeyModifiers if the key is valid. 
     * Also reset all KeyModifiers that are no longer active.
     *
     * @param keyCode the keyCode of the KeyModifier to add
     */
    public void addKeyModifier(int keyCode) {
        resetExpired();
        if (!isValid(keyCode)) return;
        if (keyCodes.contains(keyCode)) {
        	resetTimer();
        	return;
        }
        this.keyCodes.add(keyCode);
    }

    /**
     * Get the KeyModifierType based on the active KeyModifier(s).
     * @return the active KeyModifier's KeyModifierType
     */
    public KeyModifierType getActiveKeyModifier() {
    	resetExpired();
    	if (isActive(new int[]{VK_CONTROL}))
            return KeyModifierType.CTRL;
        else if (isActive(new int[]{VK_SHIFT}))
            return KeyModifierType.SHIFT;
        else if (isActive(new int[]{VK_ALT}))
            return KeyModifierType.ALT;
        else if (isActive(new int[]{VK_CONTROL, VK_SHIFT}))
            return KeyModifierType.CTRL_SHIFT;
        else if (isActive(new int[]{VK_CONTROL, VK_ALT}))
            return KeyModifierType.CTRL_ALT;
        else return KeyModifierType.NONE;
    }

    /**
     * Checks whether a list of keyCodes is currently active.
     *
     * @param keyCodes
     * 		The list of keyCodes to check
     * @return True if all keyCodes in the list are active
     */
    private boolean isActive(int[] keyCodes) {
        if (this.keyCodes.size() != keyCodes.length) return false;
        for (int keyCode : keyCodes)
            if (!this.keyCodes.contains(keyCode)) return false;
        return true;
    }

    /**
     * Reset if ACTIVE_TIME exceeded.
     */
    private void resetExpired() {
        if (expired()) reset();
    }
    
    /**
     * Reset the current time.
     */
    private void resetTimer() {
    	timePressed = getCurrentTime();
    }

    /**
     * Reset the keyCodes list and current time
     */
    private void reset() {
        keyCodes = new ArrayList<>();
        resetTimer();
    }
    
    /**
     * Get the current time.
     * @return the current time
     */
    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * @return True if the expired time is more than the ACTIVE_TIME, False if not
     */
    private boolean expired() {
        return getCurrentTime() - timePressed > ACTIVE_TIME;
    }

    /**
     * Check if the entered keyCode belongs to a valid KeyModifier.
     * @param keyCode The code to test
     * @return True if valid, False if not
     */
    private boolean isValid(int keyCode) {
        switch (keyCode) {
        	case VK_CONTROL:
        		return true;
            case VK_SHIFT:
                return true;
            case VK_ALT:
            	return true;
            default:
                return false;
        }
    }
}