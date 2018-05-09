package view.labelstate;

import view.windows.DiagramWindow;

public class ShowState extends LabelState {
	public ShowState(DiagramWindow subwindow) {
		super(subwindow);
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
