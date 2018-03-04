package domain;

import java.awt.*;

public class ResultMessage extends Message {

    public ResultMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }
}