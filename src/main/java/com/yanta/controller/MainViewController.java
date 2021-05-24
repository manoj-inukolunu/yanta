package com.yanta.controller;


import com.google.common.eventbus.Subscribe;
import com.yanta.common.AppContext;
import com.yanta.eventhandler.AppEventHandler;
import com.yanta.event.Event;
import com.yanta.event.EventManager;
import com.yanta.event.EventType;
import com.yanta.eventhandler.FolderRenderer.Pair;
import com.yanta.eventhandler.MouseClickedOnTreeNodeEventHandler;
import com.yanta.extensions.TreeCellImpl;
import com.yanta.storage.FileStorage;
import com.yanta.eventhandler.FolderRenderer;
import com.yanta.extensions.TaskListExtension;
import java.io.IOException;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import com.yanta.App;


public class MainViewController {

  @FXML
  public TreeView<Pair> folderView;

  @FXML
  public TextArea editor;

  @FXML
  public WebView webView;

  private final HashMap<EventType, AppEventHandler> handlerMap = new HashMap<>();
  List<Extension> extensions = Arrays.asList(AutolinkExtension.create(), TaskListExtension.create());
  Parser parser = Parser.builder().extensions(extensions).build();
  HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();

  @Subscribe
  public void listen(Event event) {
    if (handlerMap.get(event.getType()) != null) {
      handlerMap.get(event.getType()).handle(event);
    }
  }


  public void initialize() {
    editor.setStyle("-fx-font: 18 'Segoe UI';");
    editor.textProperty().addListener((observable, oldValue, newValue) -> handleTextChanged());
    FolderRenderer renderer = new FolderRenderer(folderView, new FileStorage());
    MouseClickedOnTreeNodeEventHandler handler = new MouseClickedOnTreeNodeEventHandler(editor);
    handlerMap.put(EventType.RENDER_TREE, renderer);
    handlerMap.put(EventType.MOUSE_CLICKED_ON_TREENODE, handler);
    renderer.handle(null);
    EventManager.registerListener(this);
    folderView.setCellFactory(pair -> new TreeCellImpl());
  }


  private void handleTextChanged() {
    Node document = parser.parse((editor.getText()));
    String data = renderer.render(document);
    webView.getEngine().loadContent(data);
    AppContext.currentNoteContent = editor.getText();
  }


  @FXML
  public void saveMenuClick(ActionEvent event) {
    try {
      Scene scene = new Scene(App.loadFXML("savewindow"), 350, 120);
      Stage newWindow = new Stage();
      newWindow.setTitle("Save Note");
      newWindow.setScene(scene);
      newWindow.setX(400);
      newWindow.setY(300);
      newWindow.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void createNewNote(ActionEvent event) {
    EventManager.sendApplicationEvent(new Event(EventType.SAVE_NOTE, editor.getText()));
    AppContext.clearEditorData();
    editor.setText("");
  }

  @FXML
  public void saveCurrentNode(ActionEvent event) {
    EventManager.sendApplicationEvent(new Event(EventType.SAVE_NOTE, editor.getText()));
  }
}
