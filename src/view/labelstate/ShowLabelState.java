package view.labelstate;

import view.components.ViewLabel;
import view.windows.SubWindow;

public class ShowLabelState extends EditLabelState {

	public ShowLabelState(SubWindow subwindow, ViewLabel viewLabel) {
		super(subwindow, viewLabel);
	}

	@Override
	public void confirmLabel() {
		System.out.println("Show label state");
	}

	@Override
	public void removeCharacter() {
		System.out.println("Show label state");
	}

	@Override
	public void addCharacter(int keyCode, char keyChar) {
		System.out.println("Show label state");
	}
}
