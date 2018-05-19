package view.labelstate;

import java.awt.Color;
import java.awt.event.KeyEvent;

import view.components.ViewLabel;
import view.windows.SubWindow;

public class EditPartyLabelState extends EditLabelState {

	public EditPartyLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	@Override
	public void confirmLabel() {
		if (viewLabel.getOutput() != null
				&& syntaxChecker.correctPartyLabelSyntax(viewLabel.getOutput())) {
			enterLabel();
		}
	}

	@Override
	public void addCharacter(int keyCode, char keyChar) {
		if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z || keyCode == KeyEvent.VK_COLON
				|| keyCode == KeyEvent.VK_SEMICOLON) {

			editLabel(keyChar);

			if (!syntaxChecker.correctPartyLabelSyntax(viewLabel.getOutput()))
				viewLabel.setColor(Color.RED);
			else
				viewLabel.setColor(Color.GREEN);
		}
	}
}