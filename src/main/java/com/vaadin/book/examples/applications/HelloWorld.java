package com.vaadin.book.examples.applications;

// BEGIN-EXAMPLE: intro.walkthrough.helloworld
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("My UI")
@Theme("mytheme")
public class HelloWorld extends UI {
    private static final long serialVersionUID = 511085335415683713L;
    
    @Override
    protected void init(VaadinRequest request) {
        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Display the greeting
        content.addComponent(new Label("Hello World!"));
        
        // Have a clickable button        
        content.addComponent(new Button("Push Me!",
            new ClickListener() {
                private static final long serialVersionUID = 5808429544582385114L;

                @Override
                public void buttonClick(ClickEvent event) {
                    Notification.show("Pushed!");
                }
            }));
    }
}
// END-EXAMPLE: intro.walkthrough.helloworld
