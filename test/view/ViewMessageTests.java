package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import domain.message.InvocationMessage;
import domain.message.ResultMessage;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;
import view.components.ViewActor;
import view.components.ViewInvocationMessage;
import view.components.ViewMessage;
import view.components.ViewObject;
import view.components.ViewParty;
import view.components.ViewResultMessage;

class ViewMessageTests {
	Party actor = new Actor("actor");
	Party object = new Object("object");
	InvocationMessage invocation = new InvocationMessage("message", actor, object);	
	ResultMessage result = new ResultMessage("result", object, actor);
	InvocationMessage invocation2 = new InvocationMessage("message2", actor, object);	
	ResultMessage result2 = new ResultMessage("result2", object, actor);
	Point2D clickPos = new Point2D.Double(100,100);
	Point2D windowPos = new Point2D.Double(50,50);
	ViewParty viewActor = new ViewActor(actor, clickPos, windowPos);
	ViewParty viewObject = new ViewObject(object, clickPos, windowPos);
	ViewMessage viewMessage = new ViewInvocationMessage(invocation, clickPos, windowPos, viewActor, viewObject);
	ViewMessage resMessage = new ViewResultMessage(result, clickPos, windowPos, viewObject, viewActor);
	ViewMessage viewMessage2 = new ViewInvocationMessage(invocation2, clickPos, windowPos, viewActor, viewObject);
	ViewMessage resMessage2 = new ViewResultMessage(result2, clickPos, windowPos, viewObject, viewActor);
	
	public void setup() {
		invocation.setCompanion(result);
		result.setCompanion(invocation);
		
		invocation2.setCompanion(result2);
		result2.setCompanion(invocation2);
		
		viewMessage.setCompanion(resMessage);
		resMessage.setCompanion(viewMessage);
		
		viewMessage2.setCompanion(resMessage2);
		resMessage2.setCompanion(viewMessage2);
	}

	@Test
	void testCopy() {
		setup();
		
		ViewMessage mes = viewMessage.copy();
		assertEquals(invocation, mes.getMessage());
		assertEquals(viewMessage.getSender(), mes.getSender());
		assertEquals(viewMessage.getReceiver(), mes.getReceiver());
		
		mes = resMessage.copy();
		assertEquals(result, mes.getMessage());
		assertEquals(resMessage.getSender(), mes.getSender());
		assertEquals(resMessage.getReceiver(), mes.getReceiver());
	}

}
