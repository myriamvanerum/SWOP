package view.labelstate;

import java.awt.Color;

import view.windows.SubWindow;

public class InvocationState extends LabelState {
	public InvocationState(SubWindow subwindow) {
		super(subwindow);
	}

	@Override
	public void confirmLabel() {
		String label = getCurrentViewLabel().getOutput();
		
		if (getCurrentViewLabel().getOutput() != null && syntaxChecker.correctInvocationMessageLabelSyntax(label.substring(0, label.length()-1))) {
			enterLabel(getCurrentViewComponent().getComponent(), getCurrentViewComponent());
		}
	}
	
	@Override
	public void addCharacter(int keyCode, char keyChar) {
		editLabel(keyChar);
		String label = getCurrentViewLabel().getOutput();
		
		if (!syntaxChecker.correctInvocationMessageLabelSyntax(label.substring(0, label.length()-1)))
			getCurrentViewLabel().setColor(Color.RED);
		else setLabelColor();
	}
}
