package view.eventhandlers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

public class KeyModifierHandler {
	// modifier is valid for 1.5 seconds
	private int validFor = 1500;
    private long timestamp;
    private ArrayList<Integer> keyCodes;
    
	public void setModifier(int keyCode) {
        resetPrevious();
        if ((keyCode != KeyEvent.VK_CONTROL) || keyCodes.contains(keyCode)) return;
        this.keyCodes.add(keyCode);
    }
	
	public boolean ctrlModifierActive() {
		return false;
	}
	
	private void resetPrevious() {
        if (expired()) reset();
    }
	
	private void reset() {
        keyCodes = new ArrayList<>();
        timestamp = new Date().getTime();
    }

    /**
     * @return <code>true</code> if more time has passed than the TIMEOUT_DURATION; <code>false</code> otherwise
     */
    private boolean expired() {
        return new Date().getTime() - timestamp > validFor;
    }

}
