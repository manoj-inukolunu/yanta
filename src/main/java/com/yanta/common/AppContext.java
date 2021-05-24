package com.yanta.common;

public class AppContext {

  public static String currentNotePath;

  public static String currentNoteContent;

  public static void clearEditorData() {
    currentNoteContent = null;
    currentNotePath = null;
  }
}
