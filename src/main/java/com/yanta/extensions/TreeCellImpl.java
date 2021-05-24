package com.yanta.extensions;

import com.google.common.base.Joiner;
import com.yanta.eventhandler.FolderRenderer.Pair;
import com.yanta.storage.FileStorage;
import com.yanta.storage.Storage;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

public class TreeCellImpl extends TreeCell<Pair> {

  private final Storage storage = new FileStorage();

  @Override
  public void updateItem(Pair item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      setText(getItem() == null ? "" : getItem().toString());
      setGraphic(getTreeItem().getGraphic());
      ContextMenu menu = new ContextMenu();
      if (menu.getItems().isEmpty()) {
        MenuItem menuItem = new MenuItem("Delete");
        menuItem.setStyle("-fx-font: 18 'Segoe UI';");
        if (getTreeItem() != null && getTreeItem().getValue() != null && getTreeItem().getValue().getPath() != null) {
          String path = Joiner.on("/").skipNulls().join(getTreeItem().getValue().getPath());
          menuItem.setOnAction(event -> deleteItem(path));
          menu.getItems().add(menuItem);
        }
        setContextMenu(menu);
      }
    }
  }

  private void deleteItem(String path) {
    storage.delete(path);
  }
}
