package view.labelstate;

import java.awt.Color;
import java.awt.event.KeyEvent;

import view.windows.DiagramWindow;
import view.windows.SubWindow;

public class PartyState extends LabelState {
	public PartyState(SubWindow subwindow) {
		super(subwindow);
	}

	@Override
	public void confirmLabel() {
		if (getCurrentViewLabel().getOutput() != null
				&& syntaxChecker.correctPartyLabelSyntax(getCurrentViewLabel().getOutput())) {
			enterLabel(getCurrentViewComponent().getComponent(), getCurrentViewComponent());
		}
	}

	@Override
	public void addCharacter(int keyCode, char keyChar) {
		if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z || keyCode == KeyEvent.VK_COLON
				|| keyCode == KeyEvent.VK_SEMICOLON) {

			editLabel(keyChar);

			if (!syntaxChecker.correctPartyLabelSyntax(getCurrentViewLabel().getOutput()))
				getCurrentViewLabel().setColor(Color.RED);
			else
				setLabelColor();
		}
	}
}
