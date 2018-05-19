package view.eventtranslator;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyModifierHandler {
	// modifier is valid for .5 seconds
	private int validFor = 500;
    private long keyEnteredTime;
    private ArrayList<Integer> keyCodes;
    
	public void setModifier(int keyCode) {
		resetExpired();
        if ((keyCode != KeyEvent.VK_CONTROL) || keyCodes.contains(keyCode)) return;
        this.keyCodes.add(keyCode);
    }
	
	public boolean ctrlModifierActive() {
		resetExpired();
		return (keyCodes.contains(KeyEvent.VK_CONTROL)) ? true : false;
	}
	
	private void resetExpired() {
        if (expired()) reset();
    }
	
	private void reset() {
        keyCodes = new ArrayList<>();
        keyEnteredTime = System.currentTimeMillis();
    }

    private boolean expired() {
        return System.currentTimeMillis() - keyEnteredTime > validFor;
    }

}
