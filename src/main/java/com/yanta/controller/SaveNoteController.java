package com.yanta.controller;

import com.yanta.common.AppContext;
import com.yanta.event.Event;
import com.yanta.event.EventManager;
import com.yanta.event.EventType;
import com.yanta.storage.FileStorage;
import com.yanta.storage.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SaveNoteController {


  @FXML
  public TextField savePathTextField;

  private final Storage storage = new FileStorage();


  @FXML
  public void saveNote(ActionEvent actionEvent) {
    storage.save(savePathTextField.getText(), AppContext.currentNoteContent);
    Window window = ((Node) (actionEvent.getSource())).getScene().getWindow();
    if (window instanceof Stage) {
      Alert alert = new Alert(AlertType.NONE, "Save Successful", ButtonType.OK);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.OK) {
        ((Stage) window).close();
        alert.close();
      }
    }
    EventManager.sendApplicationEvent(new Event(EventType.RENDER_TREE, ""));
  }
}
