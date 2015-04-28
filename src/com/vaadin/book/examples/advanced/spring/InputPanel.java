package com.vaadin.book.examples.advanced.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// @UIScoped 
class InputPanel extends Panel {
    private static final long serialVersionUID = -3238668236117542557L;
    
    @Autowired
    private javax.enterprise.event.Event<MyEvent> event;

    public InputPanel() {
        super("Input");

        TextField editor = new TextField();
        Button save = new Button("Save", e -> // Java 8
            event.fire(new MyEvent(editor.getValue())));

        VerticalLayout content = new VerticalLayout(editor, save);
        content.setMargin(true);
        content.setSpacing(true);
        setContent(content);
        setSizeFull();
    }
}