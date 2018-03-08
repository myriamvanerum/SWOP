/**
 * Tests to test the controller.
 */
package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.Controller;
import model.Actor;
import model.DiagramType;
import model.InvocationMessage;
import model.Label;
import model.Message;
import model.Party;
import view.DiagramWindow;
import model.Object;

/**
 * @author groep 03
 * 
 */
class ControllerTest {

	DiagramWindow dg = new DiagramWindow("test");
	Controller c = new Controller(dg);
	DiagramType dt = DiagramType.SEQUENCE;
	DiagramType dtc = DiagramType.COMMUNICATION;
	Label label = new Label();
	Party party1 = new Actor(1, 2, label);
	Party party2 = new Actor(3, 4, label);
	Party party3 = new Object(1, 2, label);

	@Test
	void SwitchDiagramSeqTest() {
		c.setDiagramType(dt);
		c.switchDiagram();

		assertEquals(DiagramType.COMMUNICATION, c.getDiagramType());
	}

	@Test
	void SwitchDiagramComTest() {

		c.setDiagramType(dtc);
		c.switchDiagram();

		assertEquals(DiagramType.SEQUENCE, c.getDiagramType());
	}

	@Test
	public void controllerTest() {
		ArrayList<Party> parties = new ArrayList<>();
		ArrayList<Message> messages = new ArrayList<>();
		/*
		 * parties.add(new Object(1,1,new Label(1,1,""))); parties.add(new
		 * Object(2,2,new Label(2,2,""))); parties.add(new Actor(3,3,new
		 * Label(3,3,"")));
		 */

		assertEquals(parties, c.getParties());
		assertEquals(messages, c.getMessages());
		assertEquals(false, c.isInputMode());

	}

	@Test
	void checkCoordinateTestYSubZero() {
		try {
			c.checkCoordinate(0, -2);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	void checkCoordinateTestNoPartyCom() {
		c.setDiagramType(dtc);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Object(500, 500, label);
		parties.add(newParty);
		c.parties = parties;
		Party party = c.checkCoordinate(100, 100);
		assertEquals(party, null);
	}

	@Test
	void checkCoordinateTestPartyCommObject1() {
		c.setDiagramType(dtc);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Object(500, 500, label);
		parties.add(newParty);
		c.parties = parties;

		Party party = c.checkCoordinate(500, 500);
		assertEquals(newParty, party);
	}

	@Test
	void checkCoordinateTestPartyCommActor() {
		c.setDiagramType(dtc);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Actor(500, 500, label);
		parties.add(newParty);
		c.parties = parties;

		Party party = c.checkCoordinate(500, 500);
		assertEquals(newParty, party);
	}

	@Test
	void checkCoordinateTestPartySeqObject() {
		c.setDiagramType(dt);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Object(500, 50, label);
		parties.add(newParty);
		c.parties = parties;

		Party party = c.checkCoordinate(500, 50);
		assertEquals(newParty, party);
	}

	@Test
	void checkCoordinateTestPartySeqActor() {
		c.setDiagramType(dt);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Actor(500, 50, label);
		parties.add(newParty);
		c.parties = parties;

		Party party = c.checkCoordinate(500, 50);
		assertEquals(newParty, party);
	}
	//
	// @Test
	// void moveComponentTestNullParty() {
	// try {
	// c.moveComponent(null,0,0);
	// fail( "No exception" );
	// } catch (Exception expectedException) {
	// assertEquals(IllegalArgumentException.class, expectedException.getClass());
	// }
	// }
	//
	// @Test
	// void moveComponentTestXSubZero() {
	// try {
	// c.moveComponent(party1,-2,0);
	// fail( "No exception" );
	// } catch (Exception expectedException) {
	// assertEquals(IllegalArgumentException.class, expectedException.getClass());
	// }
	// }
	//
	// @Test
	// void moveComponentTestYSubZero() {
	// try {
	// c.moveComponent(party1,0,-2);
	// fail( "No exception" );
	// } catch (Exception expectedException) {
	// assertEquals(IllegalArgumentException.class, expectedException.getClass());
	// }
	// }
	//
	// @Test
	// void moveComponentTestPartyComm() {
	// c.setDiagramType(dtc);
	// Party newParty = new Actor(500,50,label);
	// Integer x = 0, y = 0;
	// c.moveComponent(newParty, x, y);
	//
	// assertTrue(newParty.getXCom() == x);
	// assertTrue(newParty.getYCom() == y);
	// }
	//
	// @Test
	// void moveComponentTestPartySeq() {
	// c.setDiagramType(dt);
	// Party newParty = new Object(500,50,label);
	// Integer x = 0, y = 0;
	// c.moveComponent(newParty, x, y);
	//
	// assertTrue(newParty.getXSeq() == x);
	// }

	// Illegal argument

	@Test
	void checkCoordinateTestXSubZero() {
		try {
			c.checkCoordinate(-2, 0);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}


	@Test
	void checkCoordinateTestNoPartySeq() {
		c.setDiagramType(dt);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Object(500, 500, label);
		parties.add(newParty);
		c.parties = parties;
		Party party = c.checkCoordinate(100, 100);
		assertEquals(party, null);
	}

	@Test
	void checkCoordinateTestPartyCommObject() {
		c.setDiagramType(dtc);
		ArrayList<Party> parties = new ArrayList<>();
		Party newParty = new Object(500, 500, label);
		parties.add(newParty);
		c.parties = parties;

		Party party = c.checkCoordinate(500, 500);
		assertEquals(newParty, party);
	}

	@Test
	public void TestAddParty() {
		int x1 = 1;
		int y1 = 2;
		int lengte = c.parties.size();

		Party party = new Actor(x1, y1, label);
		c.addParty(party);
		assertEquals(lengte + 1, c.parties.size());
	}

	@Test
	public void TestRemoveParty() {
		int x1 = 1;
		int y1 = 2;

		Party party = new Actor(x1, y1, label);
		c.addParty(party);
		int lengte = c.parties.size();

		c.removeParty(party);
		assertEquals(lengte - 1, c.parties.size());
	}

	@Test
	public void TestAddMessage() {
		int lengte = c.messages.size();

		Message message = new InvocationMessage(label, party1, party2);
		c.addMessage(message);
		assertEquals(lengte + 1, c.messages.size());
	}

	@Test
	public void TestRemoveMessage() {

		Message message = new InvocationMessage(label, party1, party2);
		c.addMessage(message);
		int lengte = c.messages.size();

		c.removeMessage(message);
		assertEquals(lengte - 1, c.messages.size());
	}

	@Test
	public void labelClickedPartyTest() {
		assertTrue(c.labelClickedParty(new Object(1, 1, new Label(5, 5, "")), 5, 5));
	}

	// Illegal argument

	@Test
	public void removePartyNullTest() {
		try {
			c.removeParty(null);
			fail("My method didn't throw when I expected it to");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	public void changePartyNullTest() {
		try {
			c.changeParty(null, 0, 0);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	public void changePartySubZeroXTest() {
		try {
			c.changeParty(party1, -2, 0);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	public void changePartySubZeroYTest() {
		try {
			c.changeParty(party1, 0, -2);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	public void changePartyObjectTest() {
		ArrayList<Party> parties = new ArrayList<>();
		Party object = new Object(1, 2, label);
		parties.add(object);
		c.parties = parties;
		Integer x = 0, y = 0;
		c.changeParty(object, x, y);
		assertFalse(c.parties.contains(object));
		assertTrue(c.parties.get(0) instanceof Actor);
		assertTrue(c.parties.get(0).getLabel() == label);
	}

	@Test
	public void changePartyActorTest() {
		ArrayList<Party> parties = new ArrayList<>();
		Party actor = new Actor(1, 2, label);
		parties.add(actor);
		c.parties = parties;
		Integer x = 0, y = 0;
		c.changeParty(actor, x, y);
		assertFalse(c.parties.contains(actor));
		assertTrue(c.parties.get(0) instanceof Object);
		assertTrue(c.parties.get(0).getLabel() == label);
	}

	@Test
	void addMessageNullTest() {
		try {
			c.addMessage(null);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	public void addPartyNullTest() {
		try {
			c.addParty(null);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	

	@Test
	public void removeMessageNullTest() {
		try {
			c.removeMessage(null);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	public void testIsComponent() {
		int x1 = 1;
		int y1 = 2;
		int x2 = -1;
		int y2 = -2;
				try {
					c.isComponent(party1, x2, y1, x1, y1);
					fail("No exception");
							c.handleKeyEvent(-2, -2, 'T');
					fail( "No exception" );
				} catch (Exception expectedException) {
					assertEquals(IllegalArgumentException.class, expectedException.getClass());
				}

	try {
		c.isComponent(party1, x1, y1, x1, y2);
		fail("No exception");
	} catch (Exception expectedException) {
		assertEquals(IllegalArgumentException.class, expectedException.getClass());
	}

	boolean bool = c.isComponent(party1, x1, y1, 3, 4);
	assertEquals(true, bool);

	boolean bool2 = c.isComponent(party3, x1, y1, 3, 4);
	assertEquals(false, bool2);
		}

	@Test
	public void handleKeyEventTestWrongInput() {
			try {
				c.handleKeyEvent(-2, -2, 'T');
				fail( "No exception" );
			} catch (Exception expectedException) {
				assertEquals(IllegalArgumentException.class, expectedException.getClass());
			}
		}

	@Test
	public void handleKeyEventTestTab() {
		c.setDiagramType(dt);
		c.setInputMode(false);
		c.handleKeyEvent(1, KeyEvent.VK_TAB, 'a');
		assertEquals(DiagramType.COMMUNICATION, c.getDiagramType());
	}

	@Test
	public void testAddComponont() {
		int x1 = 1;
		int y1 = 2;
		int x2 = -1;
		int y2 = -2;
		try {
			c.addComponent(x2, y1);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}

		try {
			c.addComponent(x1, y2);
			fail("No exception");
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	public void testFocusComponent() {

	}

	//
	// @Test
	// void moveComponentTestNullParty() {
	// try {
	// c.moveComponent(null,0,0);
	// fail( "No exception" );
	// } catch (Exception expectedException) {
	// assertEquals(IllegalArgumentException.class, expectedException.getClass());
	// }
	// }
	//
	// @Test
	// void moveComponentTestXSubZero() {
	// try {
	// c.moveComponent(party1,-2,0);
	// fail( "No exception" );
	// } catch (Exception expectedException) {
	// assertEquals(IllegalArgumentException.class, expectedException.getClass());
	// }
	// }
	//
	// @Test
	// void moveComponentTestYSubZero() {
	// try {
	// c.moveComponent(party1,0,-2);
	// fail( "No exception" );
	// } catch (Exception expectedException) {
	// assertEquals(IllegalArgumentException.class, expectedException.getClass());
	// }
	// }
	//
	// @Test
	// void moveComponentTestPartyComm() {
	// c.setDiagramType(dtc);
	// Party newParty = new Actor(500,50,label);
	// Integer x = 0, y = 0;
	// c.moveComponent(newParty, x, y);
	//
	// assertTrue(newParty.getXCom() == x);
	// assertTrue(newParty.getYCom() == y);
	// }
	//
	// @Test
	// void moveComponentTestPartySeq() {
	// c.setDiagramType(dt);
	// Party newParty = new Object(500,50,label);
	// Integer x = 0, y = 0;
	// c.moveComponent(newParty, x, y);
	//
	// assertTrue(newParty.getXSeq() == x);
	// public void handleKeyEvent(int id, int keyCode, char keyChar) {
	// if (id < 0 || keyCode < 0)
	// throw new IllegalArgumentException();
	//
	// if (inputMode == true)
	// {
	// int index = parties.indexOf(currentComponent);
	// String inputLabel = currentComponent.getLabel().getText();
	//
	// // Enkel letters (hoofdletters & kleineletters)
	// // 513 is de keyCode voor ":"
	// // 65-90 zijn de keyCodes voor alle letters
	// // 8 is de keyCode voor backspace
	// // 10 is de keyCode voor enter
	// if (keyCode >= 65 && keyCode <= 90 || keyCode == KeyEvent.VK_COLON || keyCode
	// == 8) {
	//
	// if (keyCode == 8 && inputLabel.length() > 1)
	// currentComponent.getLabel().setText(inputLabel.substring(0,
	// inputLabel.length() - 2) + "|");
	// else if (inputLabel != null && inputLabel.length() > 0) {
	// currentComponent.getLabel().setText(inputLabel.substring(0,
	// inputLabel.length() - 1) + keyChar + "|");
	// }
	// }
	//
	// if (currentComponent.getLabel().correctSyntax() && keyCode == 10) {
	// currentComponent.getLabel().setText(inputLabel.substring(0,
	// inputLabel.length() - 1));
	// inputMode = false;
	// currentComponent = null;
	// }
	//
	// if (currentComponent instanceof Party)
	// parties.set(index, (Party)currentComponent);
	// if (currentComponent instanceof Message)
	// messages.set(index, (Message)currentComponent);
	//
	// } else {
	// switch (keyCode) {
	// case KeyEvent.VK_I:
	// addMessage(new InvocationMessage(new Label(1,1, ""), parties.get(0),
	// parties.get(1)));
	// break;
	//
	// case KeyEvent.VK_TAB:
	// switchDiagram();
	// break;
	//
	// case KeyEvent.VK_DELETE:
	// if (labelClickedOnce) {
	// deleteFocused();
	// labelClickedOnce = false;
	// }
	// break;
	// }
	// }
	// }
}
