package view.labelstate;

import java.awt.Color;

import view.windows.SubWindow;

public class InvocationState extends LabelState {
	public InvocationState(SubWindow subwindow) {
		super(subwindow);
	}

	@Override
	public void confirmLabel() {
		String label = viewLabel.getOutput();
		
		if (viewLabel.getOutput() != null && syntaxChecker.correctInvocationMessageLabelSyntax(label.substring(0, label.length()-1))) {
			enterLabel(getCurrentViewComponent().getComponent());
		}
	}
	
	@Override
	public void addCharacter(int keyCode, char keyChar) {
		editLabel(keyChar);
		String label = viewLabel.getOutput();
		
		if (!syntaxChecker.correctInvocationMessageLabelSyntax(label.substring(0, label.length()-1)))
			viewLabel.setColor(Color.RED);
		else viewLabel.setColor(Color.GREEN);
	}
}
