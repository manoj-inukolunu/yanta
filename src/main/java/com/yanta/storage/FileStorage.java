package com.yanta.storage;


import com.google.common.eventbus.Subscribe;
import com.yanta.common.AppContext;
import com.yanta.common.TreeNode;
import com.yanta.event.Event;
import com.yanta.event.EventManager;
import com.yanta.event.EventType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileStorage implements Storage {

  private static final String basePath = "/Users/manoji/yanta/data/";

  public FileStorage() {
    EventManager.registerListener(this);
  }

  @Subscribe
  public void handle(Event event) {
    if (event.getType().equals(EventType.SAVE_NOTE)) {
      save(AppContext.currentNotePath, AppContext.currentNoteContent);
    }
  }

  @Override
  public void save(String path, String content) {
    if (path != null && content != null) {
      try {
        if (!path.endsWith(".txt")) {
          path += ".txt";
        }
        path = path.startsWith("/") ? path : "/" + path;
        path = basePath + path;
        String directory = path.substring(0, path.lastIndexOf("/"));
        File file = new File(directory);
        if (!file.exists()) {
          file.mkdirs();
        }
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public TreeNode getRootNode() {
    File file = new File(basePath);
    TreeNode root = new TreeNode(false, "");
    for (File f : file.listFiles()) {
      if (f.isDirectory()) {
        root.getChildren().add(buildTree(f));
      } else if (!f.isHidden()) {
        root.getChildren().add(new TreeNode(false, f.getName()));
      }
    }
    return root;
  }

  @Override
  public String getContent(String path) {
    if (path.endsWith(".txt")) {
      File file = new File(basePath + path);
      try {
        return Files.readString(file.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "";
  }

  @Override
  public void delete(String path) {
    new File(basePath + path).delete();
    EventManager.sendApplicationEvent(new Event(EventType.RENDER_TREE, ""));
  }

  private TreeNode buildTree(File file) {
    TreeNode root = new TreeNode(file.isDirectory(), file.getName());
    for (File f : file.listFiles()) {
      if (f.isDirectory()) {
        root.getChildren().add(buildTree(f));
      } else {
        root.getChildren().add(new TreeNode(false, f.getName()));
      }
    }
    return root;
  }


}
