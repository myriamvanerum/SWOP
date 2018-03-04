package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics2D;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Actor;
import domain.ComponentType;
import domain.Party;

class PartyTest {
	
	private Party actor = new Actor(1,2,ComponentType.ACTOR,"label");
	
	//private Party partyObject = new Party(1,2,ComponentType.OBJECT,"instance","class");
	
	
	// getActorlabel
	// format label : instance;class
	@Test
	void test() {
		/*String label = "test;Label";
		actor.setLabel(label);
		assertEquals(actor.getLabel(), label);
		assertEquals(actor.getActorLabel(), label);
		assertTrue(actor.getActorLabel().contains(";"));*/
	}
	
	// instance name
	@Test
	void testInstanceNameParty() {
		String instanceName = "test";
		actor.setInstanceName("test");
		assertEquals(actor.getInstanceName(),instanceName);
	}
	
	// actor isComponent
	@Test
	void testParty() {

	}
	
	
}
