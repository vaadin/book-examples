package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class CustomComponentExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2893838661604268626L;
    
    public void init (String context) {
        if ("basic".equals(context))
            basic();
        else if ("joining".equals(context))
            joining();
        else
            setCompositionRoot(new Label("Invalid context"));
    }
    
    void basic() {
        VerticalLayout layout = new VerticalLayout();
        
        MyComposite mycomposite = new MyComposite("Hello");
        layout.addComponent(mycomposite);
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: component.customcomponent.basic
    class MyComposite extends CustomComponent {
        private static final long serialVersionUID = 6404759983019488775L;
        
        public MyComposite(String message) {
            // A layout structure used for composition
            Panel panel = new Panel("My Custom Component");
            VerticalLayout panelContent = new VerticalLayout();
            panelContent.setMargin(true); // Very useful
            panel.setContent(panelContent);
            
            // Compose from multiple components
            Label label = new Label(message);
            label.setSizeUndefined(); // Shrink
            panelContent.addComponent(label);
            panelContent.addComponent(new Button("Ok"));

            // Set the size as undefined at all levels
            panelContent.setSizeUndefined();
            panel.setSizeUndefined();
            setSizeUndefined();

            // The composition root MUST be set
            setCompositionRoot(panel);
        }
    }
    // END-EXAMPLE: component.customcomponent.basic

    // BEGIN-EXAMPLE: component.customcomponent.joining
    /** A Button + ComboBox compoment. */
    class SplitButton extends CustomComponent {
        private static final long serialVersionUID = -543689535974760204L;
        
        Button   button;
        ComboBox combobox;

        public SplitButton(String caption, Container dataSource) {
            addStyleName("splitbutton");
            
            // HorizontalLayout layout = new HorizontalLayout();
            CssLayout layout = new CssLayout();

            // Create the Button part on the left
            button = new Button(caption);
            layout.addComponent(button);
            
            // Create the ComboBox part on the right
            combobox = new ComboBox();
            combobox.setNullSelectionAllowed(false);
            combobox.setWidth("26px"); // Truncate to get only the button
            combobox.setContainerDataSource(dataSource);
            combobox.setImmediate(true); // Immediate by default
            layout.addComponent(combobox);
            
            setCompositionRoot(layout);
        }
        
        /* Forward various methods to the proper subcomponent. */
        public void addListener(Button.ClickListener listener) {
            button.addClickListener(listener);
        }
        
        public void addListener(Property.ValueChangeListener listener) {
            combobox.addValueChangeListener(listener);
        }
        
        public void setIcon(Resource icon) {
            button.setIcon(icon);
        }

        public void setCaption(String caption) {
            button.setCaption(caption);
        }
        
        public void setContainerDataSource(Container newDataSource) {
            combobox.setContainerDataSource(newDataSource);
        }
    }

    void joining() {
        VerticalLayout layout = new VerticalLayout();
        
        // Items for the drop-down menu
        IndexedContainer container = new IndexedContainer();
        String items[] = new String[] {"Hard", "Harder", "Even harder"};
        for (String item: items)
            container.addItem(item);
        
        SplitButton splitbutton = new SplitButton("Kick me", container);
        
        // Handle clicks in the Button part
        splitbutton.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 7360678548310920066L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Aaaaagh!");
            }
        });

        // Handle selections in the drop-down list
        splitbutton.addListener(new ValueChangeListener() {
            private static final long serialVersionUID = 458979472699458342L;

            public void valueChange(ValueChangeEvent event) {
                Notification.show((String) event.getProperty().getValue());
            }
        });
        
        layout.addComponent(splitbutton);
        // END-EXAMPLE: component.customcomponent.joining
        
        setCompositionRoot(layout);
    }
}
