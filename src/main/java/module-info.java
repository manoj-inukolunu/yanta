module com.yanta {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires org.commonmark;
  requires org.jsoup;
  requires org.commonmark.ext.autolink;
  requires org.commonmark.ext.task.list.items;
  requires commonmark.ext.notifications;
  requires com.google.common;

  opens com.yanta to javafx.fxml;
  exports com.yanta.controller;
  opens com.yanta.controller to javafx.fxml;
  exports com.yanta;
  exports com.yanta.storage;
  opens com.yanta.storage to javafx.fxml;
  exports com.yanta.eventhandler;
  opens com.yanta.eventhandler to javafx.fxml;
  exports com.yanta.common;
  opens com.yanta.common to javafx.fxml;
}