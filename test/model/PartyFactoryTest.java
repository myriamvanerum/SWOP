package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.party.Party;
import model.party.PartyFactory;
/**
 * @author groep 03
 *
 */
class PartyFactoryTest {

	@Test
	void PartyFactoryTestBasic() {
		PartyFactory factory = new PartyFactory();
		
		Party actor = factory.createParty("Actor");
		
		assertEquals("|", actor.getLabel());
		assertNull(actor.getSendingMessage());
		
		Party object = factory.createParty("Object");
		
		assertEquals("|", object.getLabel());
		assertNull(object.getSendingMessage());
	}

	@Test
	void PartyFactoryTestNull() {
		PartyFactory factory = new PartyFactory();
		
		Party actor = factory.createParty("Null");
		
		assertNull(actor);
	}
}
