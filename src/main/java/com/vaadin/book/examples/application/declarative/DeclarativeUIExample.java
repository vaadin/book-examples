package com.vaadin.book.examples.application.declarative;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.book.examples.component.table.TableExample;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;


public class DeclarativeUIExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -5434372168449769138L;

    public static final String basicDescription =
            "<h1>Basic Use of Declarative UIs</h1>" +
            "<p></p>";
    
    // EXAMPLE-REF: application.declarative.basic com.vaadin.book.examples.application.declarative.MyBasicDeclarativeUI application.declarative.basic
    public void basic(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/basicdeclarativeui?restartApplication"));
        frame.setWidth("650px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }

    // EXAMPLE-REF: application.declarative.context com.vaadin.book.examples.application.declarative.MyDeclarativeUI application.declarative.context
    // EXAMPLE-FILE: application.declarative.context /com/vaadin/book/examples/application/declarative/MyDeclarativeUI.html
    public void context(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/declarativeui?restartApplication"));
        frame.setWidth("650px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }

    // EXAMPLE-REF: application.declarative.designroot com.vaadin.book.examples.application.declarative.DesignRootUI application.declarative.designroot
    // EXAMPLE-FILE: application.declarative.designroot /com/vaadin/book/examples/application/declarative/MyDeclarativeUI.html
    public void designroot(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/designrootui?restartApplication"));
        frame.setWidth("650px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }

    // EXAMPLE-REF: application.declarative.declarativeview com.vaadin.book.examples.application.declarative.DeclarativeViewUI application.declarative.declarativeview
    // EXAMPLE-FILE: application.declarative.declarativeview /com/vaadin/book/examples/application/declarative/MyDeclarativeUI.html
    public void declarativeview(VerticalLayout layout) {
        BrowserFrame frame = new BrowserFrame("Browser Frame");
        frame.setSource(new ExternalResource(BookExamplesUI.APPCONTEXT + "/declarativeviewui?restartApplication"));
        frame.setWidth("650px");
        frame.setHeight("300px");
        layout.addComponent(frame);
    }

    // EXAMPLE-FILE: application.declarative.customcomponent /com/vaadin/book/examples/application/declarative/CustomComponentDesign.html
    // EXAMPLE-REF: application.declarative.customcomponent com.vaadin.book.examples.application.declarative.ExampleComponent
    // BEGIN-EXAMPLE: application.declarative.customcomponent
    @DesignRoot
    public class CustomComponentDesign extends VerticalLayout {
        private static final long serialVersionUID = -5936676018959172645L;

        public CustomComponentDesign() {
            Design.read(this);
        }
    }
    public void customcomponent(VerticalLayout layout) {
        layout.addComponent(new CustomComponentDesign());
    }
    // END-EXAMPLE: application.declarative.customcomponent

    // EXAMPLE-FILE: application.declarative.inline /com/vaadin/book/examples/application/declarative/InlineDesign.html
    // BEGIN-EXAMPLE: application.declarative.inline
    @DesignRoot
    public class InlineDesign extends VerticalLayout {
        private static final long serialVersionUID = 6252859291836466454L;

        public InlineDesign() {
            Design.read(this);
        }
    }
    public void inline(VerticalLayout layout) {
        layout.addComponent(new InlineDesign());
    }
    // END-EXAMPLE: application.declarative.inline
    
    public void writing(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.declarative.writing
        // The root of the component hierarchy
        VerticalLayout content = new VerticalLayout();
        content.setWidth("600px");
        content.setHeight("400px");
        layout.addComponent(content);
        
        // Add some component
        content.addComponent(new Label("Hello!"));
        
        // Layout inside layout
        HorizontalLayout hor = new HorizontalLayout();
        hor.setSizeFull(); // Use all available space

        // Couple of horizontally laid out components
        Tree tree = new Tree("My Tree",
                TreeExample.createTreeContent());
        hor.addComponent(tree);

        Table table = new Table("My Table",
                TableExample.generateContent());
        table.setSizeFull();
        hor.addComponent(table);
        hor.setExpandRatio(table, 1); // Expand to fill

        content.addComponent(hor);
        content.setExpandRatio(hor, 1); // Expand to fill
        
        Label display = new Label("", ContentMode.PREFORMATTED);
        
        Button save = new Button("Save", click -> { // Java 8
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                Design.write(content, baos);
                display.setValue(baos.toString("UTF-8"));
            } catch (IOException e) {
                Notification.show("Writing the design failed");
            }
        });
        // END-EXAMPLE: application.declarative.writing
        layout.addComponents(save, display);
    }

    // BEGIN-EXAMPLE: application.declarative.writingdesignroot
    @DesignRoot
    class MyDesignRoot extends VerticalLayout {
        public MyDesignRoot() {
            setWidth("600px");
            setHeight("400px");
            
            // Add some component
            addComponent(new Label("Hello!"));
            
            // Layout inside layout
            HorizontalLayout hor = new HorizontalLayout();
            hor.setSizeFull(); // Use all available space

            // Couple of horizontally laid out components
            Tree tree = new Tree("My Tree",
                    TreeExample.createTreeContent());
            hor.addComponent(tree);

            Table table = new Table("My Table",
                    TableExample.generateContent());
            table.setSizeFull();
            hor.addComponent(table);
            hor.setExpandRatio(table, 1); // Expand to fill

            addComponent(hor);
            setExpandRatio(hor, 1); // Expand to fill
        }        
    }
    
    public void writingdesignroot(VerticalLayout layout) {
        // The root of the component hierarchy
        Component designRoot = new MyDesignRoot();
        layout.addComponent(designRoot);
        
        Label display = new Label("", ContentMode.PREFORMATTED);
        
        Button save = new Button("Save", click -> { // Java 8
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                Design.write(designRoot, baos);
                display.setValue(baos.toString("UTF-8"));
            } catch (IOException e) {
                Notification.show("Writing the design failed");
            }
        });
        // END-EXAMPLE: application.declarative.writingdesignroot
        layout.addComponents(save, display);
    }
}
