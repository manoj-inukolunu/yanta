package com.yanta.storage;

import com.yanta.common.TreeNode;

public interface Storage {

  void save(String path, String content);

  TreeNode getRootNode();

  String getContent(String path);

  void delete(String path);
}
