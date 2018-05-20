package view.labelstate;

import java.awt.Color;

import domain.message.InvocationMessage;
import view.components.ViewLabel;
import view.windows.SubWindow;

public class EditInvocationMessageArgumentState extends EditLabelState {
	private InvocationMessage message;
	
	public EditInvocationMessageArgumentState(SubWindow subwindow, ViewLabel viewLabel, InvocationMessage message) {
		super(subwindow, viewLabel);
		this.message = message;
	}
	
	public InvocationMessage getInvocationMessage() {
		return this.message;
	}

	@Override
	public void confirmLabel() {
		System.out.println("Use the \"add\" button");
	}
	
	public void addArgument() {
		if (viewLabel.getOutput() != null && syntaxChecker.correctInvocationMessageArgument((viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))) {
			enterLabel();
		}
	}
	
	@Override
	public void addCharacter(int keyCode, char keyChar) {
		editLabel(keyChar);
		
		if (!syntaxChecker.correctInvocationMessageArgument(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))
			viewLabel.setColor(Color.RED);
		else 
			viewLabel.setColor(Color.GREEN);
	}
	
	@Override
	public void enterLabel() {
		String argument = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);		
		String newLabel = argument;
		
		if (getInvocationMessage().argumentsToString().trim().length() > 0)
			newLabel = getInvocationMessage().getMethod() + "(" + getInvocationMessage().argumentsToString() + ", " + argument +  ")";
		else newLabel = getInvocationMessage().getMethod() + "(" + argument +  ")";
		
		// TODO FIXME niet echt goed design
		subwindow.getViewInteraction().editLabel(getInvocationMessage(), newLabel);
		getViewLabel().setOutput("");
	}
}
