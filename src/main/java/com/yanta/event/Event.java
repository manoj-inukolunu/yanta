package com.yanta.event;

public class Event {

  private final EventType type;
  private final Object eventObject;

  public EventType getType() {
    return type;
  }

  public Object getEventObject() {
    return eventObject;
  }

  public Event(EventType type, Object eventObject) {
    this.type = type;
    this.eventObject = eventObject;
  }


}
