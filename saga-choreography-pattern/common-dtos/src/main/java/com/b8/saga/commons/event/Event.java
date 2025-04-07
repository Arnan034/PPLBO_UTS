package com.b8.saga.commons.event;

import java.util.UUID;
import java.util.Date;
public interface Event {
    UUID getEventId();
    Date getDate();
}
