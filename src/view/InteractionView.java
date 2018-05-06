package view;

import java.util.ArrayList;

import facade.Interactr;
import view.windows.SubWindow;

public class InteractionView {
	private SubWindow activeWindow;	
	private ArrayList<SubWindow> subWindows;
	private Interactr interactr;
	
	public InteractionView() {
		setActiveWindow(null);
		setSubWindows(new ArrayList<>());
		setInteractr(new Interactr());
	}

	public SubWindow getActiveWindow() {
		return activeWindow;
	}

	public void setActiveWindow(SubWindow activeWindow) {
		this.activeWindow = activeWindow;
	}

	public ArrayList<SubWindow> getSubWindows() {
		return subWindows;
	}

	public void setSubWindows(ArrayList<SubWindow> subWindows) {
		this.subWindows = subWindows;
	}

	public Interactr getInteractr() {
		return interactr;
	}

	public void setInteractr(Interactr interactr) {
		this.interactr = interactr;
	}
}
