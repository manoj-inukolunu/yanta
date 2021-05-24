package com.yanta.eventhandler;


import com.yanta.common.TreeNode;
import com.yanta.event.Event;
import com.yanta.event.EventManager;
import com.yanta.event.EventType;
import com.yanta.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FolderRenderer implements AppEventHandler {

  private final TreeView<Pair> view;
  private final Storage storage;

  public FolderRenderer(TreeView<Pair> folderView, Storage storage) {
    this.view = folderView;
    this.storage = storage;
  }


  public static class Pair {

    String name;
    List<String> path;

    public Pair(String name) {
      this.name = name;
    }

    public List<String> getPath() {
      return path;
    }

    public void setPath(List<String> path) {
      this.path = path;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  @Override
  public void handle(Event event) {
    TreeNode root = storage.getRootNode();
    TreeItem<Pair> items = buildTreeView(root, new ArrayList<>());
    TreeItem<Pair> rootTreeItem = new TreeItem<>(new Pair("All Notes"));
    rootTreeItem.getChildren().addAll(items.getChildren());
    view.setRoot(rootTreeItem);
    view.setOnMouseClicked(mouseEvent -> {
      ObservableList<TreeItem<Pair>> selectedItems = view.getSelectionModel().getSelectedItems();
      for (TreeItem<Pair> item : selectedItems) {
        EventManager.sendApplicationEvent(new Event(EventType.MOUSE_CLICKED_ON_TREENODE, item.getValue().getPath()));
      }
    });
    view.refresh();
  }

  private TreeItem<Pair> buildTreeView(TreeNode root, List<String> path) {
    path.add(root.getName());
    Pair pair = new Pair(root.getName());
    pair.setPath(new ArrayList<>(path));
    TreeItem<Pair> item = new TreeItem<>(pair);
    for (TreeNode child : root.getChildren()) {
      TreeItem<Pair> childItems = buildTreeView(child, path);
      if (childItems.getChildren().isEmpty() && child.isDir()) {
        childItems.getChildren().add(new TreeItem<>());
      }
      item.getChildren().add(childItems);
    }
    path.remove(path.size() - 1);
    return item;
  }
}
