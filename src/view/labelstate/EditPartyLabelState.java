package view.labelstate;

import java.awt.Color;
import java.awt.event.KeyEvent;

import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * EditPartyLabelState class.
 * State that processes all valid key input, when a party label is being edited
 * @author groep 03
 */
public class EditPartyLabelState extends EditLabelState {

	/**
	 * EditPartyLabelState Constructor
	 * @param subwindow
	 * 			The subwindow in which a label is being edited
	 * @param viewLabel
	 * 			The viewlabel that is being edited
	 */
	public EditPartyLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	/**
	 * Confirm the current input
	 */
	@Override
	public void confirmLabel() {
		if (viewLabel.getOutput() == null || !syntaxChecker.correctPartyLabelSyntax(viewLabel.getOutput())) return;
		enterLabel();
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
		if (!(keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z || keyCode == KeyEvent.VK_COLON
				|| keyCode == KeyEvent.VK_SEMICOLON)) return;

		editLabel(keyChar);

		if (!syntaxChecker.correctPartyLabelSyntax(viewLabel.getOutput()))
			viewLabel.setColor(Color.RED);
		else
			viewLabel.setColor(Color.GREEN);
	}
}
