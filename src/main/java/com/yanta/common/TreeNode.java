package com.yanta.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

  private boolean isDir;
  private List<TreeNode> children = new ArrayList<>();
  private String name;

  public TreeNode(boolean isDir, String name) {
    this.isDir = isDir;
    this.name = name;
  }

  public boolean isDir() {
    return isDir;
  }

  public List<TreeNode> getChildren() {
    return children;
  }

  public String getName() {
    return name;
  }
}
