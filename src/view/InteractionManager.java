package view;

import facade.Interactr;
import model.Interaction;
import view.components.ViewComponent;

public class InteractionManager {
	private Interactr interactr;
	private Interaction interaction;
	
	public InteractionManager(Interaction interaction) {
		setInteractr(new Interactr(this));
		setInteraction(interaction);
	}

	public Interactr getInteractr() {
		return interactr;
	}

	public void setInteractr(Interactr interactr) {
		this.interactr = interactr;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

	public void deleteComponent(ViewComponent selectedComponent) {
		interactr.deleteComponent(selectedComponent);
	}
}
