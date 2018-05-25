package view.labelstate;

import java.awt.Color;

import domain.message.InvocationMessage;
import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * EditInvocationMessageArgumentState class.
 * State that processes all valid key input, when a invocation message argument is being edited
 * @author groep 03 
 */
public class EditInvocationMessageArgumentState extends EditLabelState {
	private InvocationMessage message;
	
	/**
	 * EditInvocationMessageArgumentState Constructor
	 * @param subwindow
	 * 			The subwindow in which a label is being edited
	 * @param viewLabel
	 * 			The viewlabel that is being edited
	 * @param message
	 * 			The InvocationMessage whose label is being edited
	 */
	public EditInvocationMessageArgumentState(SubWindow subwindow, ViewLabel viewLabel, InvocationMessage message) {
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
		System.out.println("Use the \"add\" button");
	}
	
	/**
	 * Add an argument
	 */
	public void addArgument() {
		if (viewLabel.getOutput() == null || !syntaxChecker.correctInvocationMessageArgument((viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))) return;
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
		
		if (!syntaxChecker.correctInvocationMessageArgument(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))
			viewLabel.setColor(Color.RED);
		else 
			viewLabel.setColor(Color.GREEN);
	}
	
	/**
	 * Set the label of a component
	 */
	@Override
	public void enterLabel() {
		String argument = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);		
		String newLabel = argument;
		
		if (getInvocationMessage().argumentsToString().trim().length() > 0)
			newLabel = getInvocationMessage().getMethod() + "(" + getInvocationMessage().argumentsToString() + ", " + argument +  ")";
		else 
			newLabel = getInvocationMessage().getMethod() + "(" + argument +  ")";
		
		getSubWindow().editLabel(getInvocationMessage(), newLabel);
		getViewLabel().setOutput("");
	}
}
