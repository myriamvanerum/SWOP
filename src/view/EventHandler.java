package view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class EventHandler {

	/**
	 * When a keyevent occurs, it is handled in this method. If tab is pressed, the
	 * view of the diagram is switched, from communication to sequence and vice
	 * versa, if delete is pressed (or backspace on a mac, which doesn't have a
	 * deletebutton) the focused party gets deleted.
	 * 
	 * @param id 
	 * 		keyEvent id
	 * @param keyCode:
	 * 		Keyboard key pressed
	 * @param keyChar:
	 * 		keyboard key pressed keyChar
	 * @throws IllegalArgumentException
	 * 			  Illegal id or keyCode
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id < 0 || keyCode < 0)
			throw new IllegalArgumentException();

//		if (inputMode == true)
//		{
//			int index = parties.indexOf(currentComponent);
//			String inputLabel = currentComponent.getLabel().getText();
//			
//			// Enkel letters (hoofdletters & kleineletters)
//	        // 513 is de keyCode voor ":"
//	        // 65-90 zijn de keyCodes voor alle letters
//	        // 8 is de keyCode voor backspace
//	        // 10 is de keyCode voor enter		
//	        if (keyCode >= 65 && keyCode <= 90 || keyCode == KeyEvent.VK_COLON || keyCode == 8) { 	        	
//	        		    			    		    		
//	        	if (keyCode == 8 && inputLabel.length() > 1)
//	        		currentComponent.getLabel().setText(inputLabel.substring(0, inputLabel.length() - 2) + "|");
//	        	else if (inputLabel != null && inputLabel.length() > 0) {
//	        		currentComponent.getLabel().setText(inputLabel.substring(0, inputLabel.length() - 1)  +  keyChar + "|");
//	    	    }    	
//	        }   
//	        
//	        if (currentComponent.getLabel().correctSyntax() && keyCode == 10) {
//	        	currentComponent.getLabel().setText(inputLabel.substring(0, inputLabel.length() - 1));
//		        setInputMode(false);
//	        	currentComponent = null;  
//	        }  	     
//
//        	if (currentComponent instanceof Party)       		        	
//        		parties.set(index, (Party)currentComponent);
//        	if (currentComponent instanceof Message)
//        		messages.set(index, (Message)currentComponent);
//        	
//		} else {
			switch (keyCode) {

			case KeyEvent.VK_TAB:
//				switchDiagram();
				break;

			case KeyEvent.VK_DELETE:
//				if (labelClickedOnce) {
//					deleteFocused();
//					labelClickedOnce = false;
//				}
				break;
			}	
//		}
	}
	
	/**
	 * When a mouse event occurs, it is handled in this method. If the mouse is
	 * pressed, the object is focused, if it's dragged, the object should move (if
	 * it's a party), if the mouse has clicked, it gets focused or unfocused at one
	 * click, it draws a new party.
	 * 
	 * @param id
	 * 			  mouseEvent id
	 * @param x:
	 *            coordinate x
	 * @param y:
	 *            coordinate y
	 * @param clickCount:
	 *            the number of times the mouse has clicked.
	 * @throws IllegalArgumentException
	 * 			  Illegal id, coordinates or clickCount
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		if (id < 0 || x < 0 || y < 0 || clickCount < 0)
			throw new IllegalArgumentException();

//		if ( inputMode == false) {
			switch (id) {
			case MouseEvent.MOUSE_PRESSED:
//				checkAndFocus(x, y);
				break;
			case MouseEvent.MOUSE_DRAGGED:
//			if (getFocusedObject() != null && getFocusedObject() instanceof Party) {
//				moveComponent((Party) getFocusedObject(), x, y);
//			}
//			getFocusedObject().unfocus();
			break;
			case MouseEvent.MOUSE_CLICKED:
//				Party party = checkCoordinate(x, y);
				
				switch (clickCount) {
				case 1:
//					if (party == null && getFocusedObject() != null) {
//						unFocus();
//					} else if (party != null && labelClickedParty(party, x, y) && labelClickedOnce == false) {
//						checkAndFocus(x, y);
//						labelClickedOnce = true;
//					} else if (party != null && labelClickedParty(party, x, y) && labelClickedOnce == true) {
//						unFocus();
//						setInputMode(true);
//						labelClickedOnce = false;
//						currentComponent = party;
//						party.getLabel().setText(party.getLabelText()+ "|");						
//					}
					break;

				case 2:
//					if (party == null) {
//						addComponent(x, y);
//					} else {
//						if (getDiagramType() == DiagramType.COMMUNICATION && !labelClickedParty(party, x, y)) {
//							changeParty(party, (int) party.getXCom(), (int) party.getYCom());
//
//						} else if (getDiagramType() == DiagramType.SEQUENCE && !labelClickedParty(party, x, y)) {
//							changeParty(party, (int) party.getXSeq(), (int) party.getYSeq());
//						}
//					}
					break;
				default:
					break;
				}
			}
		}		
//	}
}
