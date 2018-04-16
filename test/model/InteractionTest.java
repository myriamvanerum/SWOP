package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

/**
 * @author groep 03
 *
 */
class InteractionTest {

	@Test
	void InteractionTestBasic() {
		Interaction interaction = new Interaction();
		
		Party actor = new Actor("actor");
		Party object = new Object("object");
		Message invocation = new InvocationMessage("message", actor, object);
		
		interaction.addParty(actor, new Point2D.Double(50,50));
		interaction.addParty(object, new Point2D.Double(150,150));
		interaction.addMessage(invocation, new Point2D.Double(50,50));
		
		assertTrue(interaction.getParties().contains(actor));
		assertTrue(interaction.getParties().contains(object));
		assertTrue(interaction.getMessages().contains(invocation));
		
		interaction.removeMessage(invocation);
		
		assertFalse(interaction.getMessages().contains(invocation));
		
		interaction.changePartyType(actor);
		
		assertFalse(interaction.getParties().contains(actor));
		assertEquals(actor.getLabel(), interaction.getParties().get(0).getLabel());
		
		interaction.changePartyType(object);
		
		assertFalse(interaction.getParties().contains(object));
		assertEquals(object.getLabel(), interaction.getParties().get(1).getLabel());
		
		interaction.removeParty(object);
		
		assertFalse(interaction.getParties().contains(object));
	}

}
