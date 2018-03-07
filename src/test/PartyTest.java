/**
 * A test to see if the party has the right input.
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.*;
import model.Object;

/**
 * @author groep 03
 *
 */
class PartyTest {
	
	Label label = new Label();
	private Party actor = new Actor(1,2,label);
	private Party object = new Object(3,4,label);
	

	@Test
	void test() {
		testActor();
		testObject();
	}
	
	@Test
	void testActor() {
		Label l = actor.getLabel();
		double x = actor.getXCom();
		double y = actor.getYCom();
		
		assertEquals(1, x);
		assertEquals(2, y);
		assertEquals(l, label);
	}
	
	@Test
	void testObject() {
		Label l = object.getLabel();
		double x = object.getXCom();
		double y = object.getYCom();
		
		assertEquals(3, x);
		assertEquals(4, y);
		assertEquals(l, label);
	}
	
	

}
