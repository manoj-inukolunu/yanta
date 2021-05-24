package com.yanta.extensions;

import com.yanta.extensions.processor.TaskListPostProcessor;
import com.yanta.extensions.processor.TaskListRenderer;
import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer;

public class TaskListExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {


  private TaskListExtension() {
  }

  public static Extension create() {
    return new TaskListExtension();
  }

  @Override
  public void extend(Parser.Builder parserBuilder) {
    parserBuilder.postProcessor(new TaskListPostProcessor());
  }

  @Override
  public void extend(HtmlRenderer.Builder rendererBuilder) {
    rendererBuilder.nodeRendererFactory(new HtmlNodeRendererFactory() {
      @Override
      public NodeRenderer create(HtmlNodeRendererContext context) {
        return new TaskListRenderer(context);
      }
    });
  }
}
