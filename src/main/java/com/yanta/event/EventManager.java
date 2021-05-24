package com.yanta.event;

import com.google.common.eventbus.EventBus;

public class EventManager {


  private static EventBus eventBus = new EventBus();

  public static void sendApplicationEvent(Event event) {
    eventBus.post(event);
  }

  public static void registerListener(Object listener) {
    eventBus.register(listener);
  }

}
