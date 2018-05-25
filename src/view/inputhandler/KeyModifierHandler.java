package view.inputhandler;

import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_SHIFT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * KeyModifierHandler class. 
 * @author groep 03
 */
public class KeyModifierHandler {
	private static final int TIMEOUT_DURATION = 1000; // in milliseconds
    private long timestamp;
    private List<Integer> keyCodes;

    KeyModifierHandler() {
        keyCodes = new ArrayList<>();
        timestamp = getTimeStamp();
    }

    /**
     * Handle the adding of a modifier if supported and not already active.
     * If the timeOut expired, the active modifiers are reset.
     *
     * @param keyCode the keyCode of the modifier to add.
     */
    public void addModifier(int keyCode) {
        resetIfTimedOut();
        if (!isSupported(keyCode) || keyCodes.contains(keyCode)) return;
        if (timedOut()) reset();
        this.keyCodes.add(keyCode);
    }

    /**
     * @return the active KeyModifier.
     */
    public KeyModifierType getActiveKeyModifier() {
        resetIfTimedOut();
        if (isActive(new int[]{VK_SHIFT, VK_CONTROL}))
            return KeyModifierType.CTRL_SHIFT;
        else if (isActive(new int[]{VK_CONTROL}))
            return KeyModifierType.CTRL;
        else if (isActive(new int[]{VK_SHIFT}))
            return KeyModifierType.SHIFT;
        else return KeyModifierType.NONE;
    }

    /**
     * Checks whether a series of modifiers is currently active by comparing keyCodes.
     *
     * @param keyCodes the modifiers to check.
     * @return true if the specified modifiers are active.
     */
    private boolean isActive(int[] keyCodes) {
        if (this.keyCodes.size() != keyCodes.length) return false;
        for (int keyCode : keyCodes) {
            if (!this.keyCodes.contains(keyCode))
                return false;
        }
        return true;
    }

    /**
     * Resets the active modifiers if the timeOut expired.
     */
    private void resetIfTimedOut() {
        if (timedOut()) reset();
    }

    /**
     * Get the current time in passed milliseconds.
     *
     * @return the time in passed milliseconds since January 1, 1970, 00:00:00 GMT.
     */
    private long getTimeStamp() {
        return new Date().getTime();
    }

    /**
     * Reset the keyCodes by replacing the keyCodes list with an empty list and
     * reinitialising the timestamp.
     */
    private void reset() {
        keyCodes = new ArrayList<>();
        timestamp = getTimeStamp();
    }

    /**
     * @return <code>true</code> if more time has passed than the TIMEOUT_DURATION; <code>false</code> otherwise
     */
    private boolean timedOut() {
        return new Date().getTime() - timestamp > TIMEOUT_DURATION;
    }

    /**
     * @param keyCode the code to test.
     * @return true if supported, otherwise false.
     */
    private boolean isSupported(int keyCode) {
        switch (keyCode) {
            case VK_SHIFT:
                return true;
            case VK_CONTROL:
                return true;
            default:
                return false;
        }
    }

    /**
     * Some keyModifierTypes
     */
    enum KeyModifierType {
        NONE, CTRL_SHIFT, CTRL, SHIFT
    }
}