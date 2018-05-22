package view.labelstate;

import java.awt.Color;

import view.components.ViewLabel;
import view.windows.SubWindow;

public class EditInvocationMessageLabelState extends EditLabelState {
	
	public EditInvocationMessageLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	@Override
	public void confirmLabel() {
		if (viewLabel.getOutput() == null || !syntaxChecker.correctInvocationMessageLabelSyntax(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1))) return;
		enterLabel();
	}
	
	@Override
	public void addCharacter(int keyCode, char keyChar) {
		editLabel(keyChar);
		
		if (!syntaxChecker.correctInvocationMessageLabelSyntax(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))
			viewLabel.setColor(Color.RED);
		else 
			viewLabel.setColor(Color.GREEN);
	}
}
