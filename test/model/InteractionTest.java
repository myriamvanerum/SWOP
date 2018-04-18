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
		InvocationMessage invocation = new InvocationMessage("message", actor, object);
		ResultMessage result = new ResultMessage("message", actor, object);
		invocation.setResultMessage(result);
		
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
		
		interaction.changePartyType(object);
		
		assertFalse(interaction.getParties().contains(object));
		
		interaction.removeParty(object);
		
		assertFalse(interaction.getParties().contains(object));
	}

}
