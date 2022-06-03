package com.gameofjess.javachess.helper.messages;

import java.util.Date;

public interface Message {

    String getMessage();

    Date getTime();

    String toJSON();

    MessageType getType();

}
