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
		String label = getViewLabel().getOutput();		
		getViewLabel().setColor(Color.BLACK);		
		String argument = label.substring(0, label.length() - 1);
		String newLabel = getInvocationMessage().getMethod() + "(" + getInvocationMessage().argumentsToString() + ", " + argument +  ")";
		// TODO FIXME niet echt goed design
		subwindow.getViewInteraction().getInteractr().editLabel(getInvocationMessage(), newLabel);
		getViewLabel().setOutput("");
	}
}
