package view.labelstate;

import java.awt.Color;

import domain.message.InvocationMessage;
import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * EditInvocationMessageMethodState class.
 * State that processes all valid key input, when a invocation message method is being edited
 * @author groep 03 
 */
public class EditInvocationMessageMethodState extends EditLabelState {
	private InvocationMessage message;
	
	/**
	 * EditInvocationMessageMethodState Constructor
	 * @param subwindow
	 * 			The subwindow in which a label is being edited
	 * @param viewLabel
	 * 			The viewlabel that is being edited
	 */
	public EditInvocationMessageMethodState(SubWindow subwindow, ViewLabel viewLabel, InvocationMessage message) {
		super(subwindow, viewLabel);
		this.message = message;
	}
	
	public InvocationMessage getInvocationMessage() {
		return this.message;
	}

	/**
	 * Confirm the current input
	 */
	@Override
	public void confirmLabel() {
		if (viewLabel.getOutput() == null || !syntaxChecker.correctInvocationMessageMethod((viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))) return;
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
		
		if (!syntaxChecker.correctInvocationMessageMethod(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))
			viewLabel.setColor(Color.RED);
		else 
			viewLabel.setColor(Color.GREEN);
	}
	
	/**
	 * Set the label of a component
	 */
	@Override
	public void enterLabel() {
		String label = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);
		String method = label.substring(0, label.length() - 1);
		String newLabel = method + "(" + getInvocationMessage().argumentsToString() + ")";
		getSubWindow().editLabel(getInvocationMessage(), newLabel);
		getViewLabel().setOutput(method);
	}
}
