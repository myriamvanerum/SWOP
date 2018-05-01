package view;

public class ShowState extends LabelState {
	public ShowState(SubWindow subwindow) {
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
