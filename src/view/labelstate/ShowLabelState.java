package view.labelstate;

import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * ShowLabelState class.
 * No key input allowed 
 * @author groep 03 
 */
public class ShowLabelState extends EditLabelState {

	/**
	 * ShowLabelState Constructor
	 * @param subwindow
	 * 			The subwindow in which a label is being edited
	 * @param viewLabel
	 * 			The viewlabel that is being edited
	 */
	public ShowLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	/**
	 * Confirm the current input
	 */
	@Override
	public void confirmLabel() {
		System.out.println("Show label state");
	}

	/**
	 * Remove the last character of the label
	 */
	@Override
	public void removeCharacter() {
		System.out.println("Show label state");
	}

	/**
	 * Add a character to the label
	 * @param keyCode
	 * 			KeyCode of the key input
	 * @param keyChar
	 * 			Character input
	 */
	@Override
	public void addCharacter(int keyCode, char keyChar) {
		System.out.println("Show label state");
	}
}
