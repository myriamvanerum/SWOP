package view;

import facade.Interactr;
import view.components.ViewComponent;

public class InteractionManager {
	private Interactr interactr;
	
	public InteractionManager() {
		setInteractr(new Interactr());
	}

	public Interactr getInteractr() {
		return interactr;
	}

	public void setInteractr(Interactr interactr) {
		this.interactr = interactr;
	}

	public void deleteComponent(ViewComponent selectedComponent) {
		interactr.deleteComponent(selectedComponent);
	}
}
