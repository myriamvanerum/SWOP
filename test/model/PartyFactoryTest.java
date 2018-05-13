package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import domain.PartyFactory;
import domain.party.Party;
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
		
		Party object = factory.createParty("Object");
		
		assertEquals("|", object.getLabel());
	}

	@Test
	void PartyFactoryTestNull() {
		PartyFactory factory = new PartyFactory();
		
		Party actor = factory.createParty("Null");
		
		assertNull(actor);
	}
}
