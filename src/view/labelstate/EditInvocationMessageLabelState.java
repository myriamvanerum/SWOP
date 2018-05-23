package view.labelstate;

import java.awt.Color;

import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * EditInvocationMessageLabelState class.
 * State that processes all valid key input, when a invocation message label is being edited
 * @author groep 03 
 */
public class EditInvocationMessageLabelState extends EditLabelState {
	
	/**
	 * EditInvocationMessageLabelState Constructor
	 * @param subwindow
	 * 			The subwindow in which a label is being edited
	 * @param viewLabel
	 * 			The viewlabel that is being edited
	 */
	public EditInvocationMessageLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	/**
	 * Confirm the current input
	 */
	@Override
	public void confirmLabel() {
		if (viewLabel.getOutput() == null || !syntaxChecker.correctInvocationMessageLabelSyntax(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1))) return;
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
		editLabel(keyChar);
		
		if (!syntaxChecker.correctInvocationMessageLabelSyntax(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))
			viewLabel.setColor(Color.RED);
		else 
			viewLabel.setColor(Color.GREEN);
	}
}
