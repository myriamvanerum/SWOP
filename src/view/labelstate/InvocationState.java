package view.labelstate;

import java.awt.Color;

import view.components.ViewLabel;
import view.windows.SubWindow;

public class InvocationState extends LabelState {
	/*public InvocationState(SubWindow subwindow) {
		super(subwindow);
	}*/
	
	public InvocationState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	@Override
	public void confirmLabel() {
		if (viewLabel.getOutput() != null && syntaxChecker.correctInvocationMessageLabelSyntax(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1))) {
			enterLabel(getCurrentViewComponent().getComponent());
		}
	}
	
	@Override
	public void addCharacter(int keyCode, char keyChar) {
		editLabel(keyChar);
		
		if (!syntaxChecker.correctInvocationMessageLabelSyntax(viewLabel.getOutput().substring(0, viewLabel.getOutput().length()-1)))
			viewLabel.setColor(Color.RED);
		else viewLabel.setColor(Color.GREEN);
	}
}
