package com.yanta;

import com.google.common.eventbus.EventBus;
import com.google.common.io.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {
    initControllers();
    Font.loadFont(Resources.getResource("Segoe UI.ttf").openStream(), 14);
    scene = new Scene(loadFXML("main"), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  private void initControllers() {

  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  public static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Resources.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }

}