package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

/**
 * @author groep 03
 *
 */
class InteractionTest {
	Interaction interaction = new Interaction();
	Party actor = new Actor("actor");
	Party object = new Object("object");
	Party object2 = new Object("object");
	ResultMessage result = new ResultMessage("message", actor, object);
	InvocationMessage invocation = new InvocationMessage("message", actor, object, result);
	InvocationMessage invocation2 = new InvocationMessage("message", object2, object);
	
	@Test
	void InteractionTestBasic() {
		interaction.addParty(actor, new Point2D.Double(50,50));
		interaction.addParty(object, new Point2D.Double(150,150));
		//interaction.addMessage(invocation, new Point2D.Double(50,50));
		
		assertTrue(interaction.getParties().contains(actor));
		assertTrue(interaction.getParties().contains(object));
		//assertTrue(interaction.getMessages().contains(invocation));
		
		//interaction.removeComponent(invocation);
		
		//assertFalse(interaction.getMessages().contains(invocation));
		
		interaction.changePartyType(actor);
		
		assertFalse(interaction.getParties().contains(actor));
		
		interaction.changePartyType(object);
		
		assertFalse(interaction.getParties().contains(object));
		
		interaction.removeComponent(object);
		
		assertFalse(interaction.getParties().contains(object));
		
		interaction.setParties(null);
		
		assertNull(interaction.getParties());
	}
	
	@Test
	void testInteractionAddPartyNullError() {
		try {
			interaction.addParty(null, new Point2D.Double(50,50));
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testInteractionAddPartyPositionError() {
		try {
			interaction.addParty(actor, new Point2D.Double(-50,50));
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test
	void testInteractionAddMessageNullError() {
		try {
			//interaction.addMessage(null, new Point2D.Double(50,50));
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testInteractionAddMessagePositionError() {
		try {
			//interaction.addMessage(invocation, new Point2D.Double(-50,50));
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test
	void testInteractionRemoveParty() {
		interaction.addParty(actor, new Point2D.Double(50,50));
		interaction.addParty(object, new Point2D.Double(150,150));
		//interaction.addMessage(invocation, new Point2D.Double(50,50));
		//interaction.addMessage(invocation2, new Point2D.Double(50,50));
		//interaction.addMessage(result, new Point2D.Double(50,50));
		
		interaction.removeComponent(actor);
		
		assertFalse(interaction.getParties().contains(actor));
		assertTrue(interaction.getParties().contains(object));
		//assertFalse(interaction.getMessages().contains(invocation));
		//assertFalse(interaction.getMessages().contains(result));
		//assertTrue(interaction.getMessages().contains(invocation2));
	}
	
	@Test
	void testInteractionremoveComponentNullError() {
		try {
			interaction.removeComponent(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}

	@Test
	void testInteractionChangePartyTypeNullError() {
		try {
			interaction.changePartyType(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
}
