/**
 * A test to see if the party has the right input.
 */
package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domain.message.InvocationMessage;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;

/**
 * @author groep 03
 *
 */
class PartyTest {
	
	@Test
	void testPartyBasic() {
		Party actor = new Actor("test");
		Party object = new Object("test");
		
		new InvocationMessage("message", actor, object);		
		
		assertEquals("test", actor.getLabel());
		assertEquals("test", object.getLabel());	
		
		Party newActor = new Actor(object);
		assertEquals(object.getLabel(), newActor.getLabel());
		
		Party newObject = new Object(actor);
		assertEquals(actor.getLabel(), newObject.getLabel());
	}
	
	@Test
	void testPartyNull() {
		Party party = null;
	
		try {
			new Actor(party);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
}
