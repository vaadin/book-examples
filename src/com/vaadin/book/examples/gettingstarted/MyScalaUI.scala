package com.vaadin.book.examples.gettingstarted

// BEGIN-EXAMPLE: gettingstarted.scala
import java.util.Date

import com.vaadin.annotations.Theme
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.Label
import com.vaadin.ui.Notification
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout

@Theme("book-examples")
class MyScalaUI extends UI {
  // Enable implicit conversion from a lambda expression to a listener
  implicit def clickListener(f: ClickEvent => Unit) =
    new ClickListener {
      override def buttonClick(event: ClickEvent) {
        f(event)
      }
    }    

  override def init(request: VaadinRequest) = {
    val content: VerticalLayout = new VerticalLayout
    setContent(content)

    val label: Label = new Label("Hello, world!")
    content addComponent label

    // Handle user interaction
    content addComponent new Button("Click Me!",
      new ClickListener {
        override def buttonClick(event: ClickEvent) =
          Notification.show("The time is " + new Date)
      })
  }
}
// END-EXAMPLE: gettingstarted.scala
