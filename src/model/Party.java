package model;

import java.awt.geom.Point2D;

import model.Component;

/**
 * A Party class
 * 
 * @author  groep 03
 */
public abstract class Party extends Component {

    private Message sendingMessage;

    /**
     * Party constructor
     * @param x
	 * 		The x coordinate of the party
	 * @param y
	 * 		The y coordinate of the party
	 * @param label
	 * 		The party's label
     */
    public Party(int x, int y, String label) {
        this.label = label;
    }

    /* GETTERS AND SETTERS */

    public Message getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(Message sendingMessage) {
        this.sendingMessage = sendingMessage;
    }
}