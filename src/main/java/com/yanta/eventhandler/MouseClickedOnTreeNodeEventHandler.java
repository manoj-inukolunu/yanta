package com.yanta.eventhandler;

import com.google.common.base.Joiner;
import com.yanta.common.AppContext;
import com.yanta.event.Event;
import com.yanta.storage.FileStorage;
import com.yanta.storage.Storage;
import java.util.List;
import javafx.scene.control.TextArea;

public class MouseClickedOnTreeNodeEventHandler implements AppEventHandler {

  private final TextArea textArea;
  Storage storage = new FileStorage();

  public MouseClickedOnTreeNodeEventHandler(TextArea editor) {
    this.textArea = editor;
  }

  @Override
  public void handle(Event event) {
    List<String> path = (List<String>) event.getEventObject();
    if (path != null && !path.isEmpty()) {
      String pathOnDisk = Joiner.on("/").skipNulls().join(path);
      AppContext.currentNotePath = pathOnDisk;
      textArea.setText(storage.getContent(pathOnDisk));
    }
  }
}
