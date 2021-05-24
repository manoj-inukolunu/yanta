package com.yanta.extensions.processor;

import org.commonmark.ext.task.list.items.TaskListItemMarker;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TaskListRenderer implements NodeRenderer {

  private final HtmlNodeRendererContext context;
  private final HtmlWriter html;

  public TaskListRenderer(HtmlNodeRendererContext context) {
    this.context = context;
    this.html = context.getWriter();
  }

  @Override
  public Set<Class<? extends Node>> getNodeTypes() {
    return Collections.<Class<? extends Node>>singleton(TaskListItemMarker.class);
  }

  @Override
  public void render(Node node) {
    if (node instanceof TaskListItemMarker) {
      Map<String, String> attributes = new LinkedHashMap<>();
      attributes.put("type", "checkbox");
      if (((TaskListItemMarker) node).isChecked()) {
        attributes.put("checked", "");
      }
      html.tag("input", context.extendAttributes(node, "input", attributes));
      html.line();
      renderChildren(node);
    }
  }

  private void renderChildren(Node parent) {
    Node node = parent.getFirstChild();
    while (node != null) {
      Node next = node.getNext();
      context.render(node);
      node = next;
    }
  }
}
