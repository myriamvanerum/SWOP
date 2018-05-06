package view;

import java.awt.geom.Point2D;

import facade.Interactr;
import model.Interaction;
import model.Message;
import model.Party;
import view.components.ViewComponent;
import view.components.ViewParty;

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

	public Message addMessage(Party first, Party second, int x, int y) {
		return interactr.addMessage(first, second, x, y);
	}

	public Party addParty(Point2D position) {
		return interactr.addParty(position);
	}

	public void changePartyType(ViewParty selectedComponent) {
		interactr.changePartyType(selectedComponent);
	}
}
