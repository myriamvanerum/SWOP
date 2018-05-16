package view.labelstate;

import view.components.ViewLabel;
import view.windows.SubWindow;

public class ShowState extends LabelState {
	/*public ShowState(SubWindow subwindow) {
		super(subwindow);
	}*/

	public ShowState(SubWindow subwindow, ViewLabel viewLabel) {
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
