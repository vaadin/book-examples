package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.book.examples.lib.Description;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class OrderedLayoutExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 5602882631420102318L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.orderedlayout.basic
        // BOOK: layout.components.orderedlayout
        VerticalLayout vertical = new VerticalLayout();
        vertical.addComponent(new TextField("Name"));
        vertical.addComponent(new TextField("Street address"));
        vertical.addComponent(new TextField("Postal code"));

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.addComponent(new TextField("Name"));
        horizontal.addComponent(new TextField("Street address"));
        horizontal.addComponent(new TextField("Postal code"));

        layout.addComponents(vertical, horizontal);
        // END-EXAMPLE: layout.orderedlayout.basic
    }

    public void adjustments(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.orderedlayout.adjustments
        // BOOK: layout.components.orderedlayout
        // A containing layout with a border
        Panel container = new Panel("Panel");
        container.setWidth("500px");
        
        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.setWidth("100%");
        horizontal.setMargin(true);
        horizontal.setSpacing(true);
        
        // Expanding component 
        Label description = new Label("This is a very long "
            + "description that is shown left of the input");
        horizontal.addComponent(description);
        horizontal.setExpandRatio(description, 1.0f);
        
        // Text field without caption
        TextField tf = new TextField();
        tf.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        tf.setIcon(FontAwesome.SEARCH);
        horizontal.addComponent(tf);
        
        container.setContent(horizontal);
        // END-EXAMPLE: layout.orderedlayout.adjustments
        layout.addComponent(container);
    }
    
    @Description(title="Shrinking to fit", value=
        "<p>Layout with undefined size shrinks to fit the largest contained component.</p>")
    public void shrinktofit(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.orderedlayout.sizing.shrinktofit
        VerticalLayout vertical = new VerticalLayout();
        vertical.setWidthUndefined(); // Shrink to fit
        
        for (String str: new String[]{"Short", "A bit longer",
                "Really really really long",
                "Quite long still"}) {
            Label label = new Label(str);
            label.setWidthUndefined(); // Shrink to fit text
            vertical.addComponent(label);
        }
        // END-EXAMPLE: layout.orderedlayout.sizing.shrinktofit
        
        vertical.addStyleName("shrinktofit");
        vertical.setSpacing(true);
        layout.addComponent(vertical);
    }
    
    @Description(title="Defining the Width of an VerticalLayout by Contained Component</h1>", value =
        "<p>Normally, you wouldn't be able to put components with percentual size "+
        "in a layout with undefined size. In <b>VerticalLayout</b> and <b>HorizontalLayout</b>, "+
        "you can use a contained component to "+
        "define the size in the secondary direction of the layout, and then use "+
        "that size for relative sizing of other contained components.</p>"+
        "<p>Here, we define the size by a contained component with undefined size; "+
        "you could also use a component with fixed size.</p>")
    public void undefineddefiningsize(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.orderedlayout.sizing.undefineddefiningsize
        // BOOK: layout.components.orderedlayout
        // Vertical layout would normally have 100% width
        VerticalLayout vertical = new VerticalLayout();
        
        // Shrink to fit the width of contained components
        vertical.setWidth(null); // Undefined
        
        // Label has normally 100% width, but we set it as
        // undefined so that it will take only the needed space
        Label label =
            new Label("\u2190 The VerticalLayout shrinks to fit "+
                      "the width of this Label \u2192");
        label.setWidth(null); // Undefined
        vertical.addComponent(label);
        
        // Button has undefined width by default
        Button butt = new Button("\u2190 This Button takes 100% "+
                                 "of the width \u2192");
        butt.setWidth("100%");
        vertical.addComponent(butt);
        // END-EXAMPLE: layout.orderedlayout.sizing.undefineddefiningsize
        
        vertical.addStyleName("bordered");
        vertical.setSpacing(true);
        setCaption("This is a VerticalLayout with two components");

        layout.addComponent(vertical);
    }

    void relativesize(VerticalLayout content) {
        // BEGIN-EXAMPLE: layout.orderedlayout.sizing.relativesize
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.addStyleName("redborder");
        
        for (int width=10; width <= 100; width += 10) {
            VerticalLayout layout = new VerticalLayout();
            layout.addStyleName("blueborder");
            layout.setWidth(width, Unit.PERCENTAGE);
            wrapper.addComponent(layout);

            Button button = new Button("This is a button");
            button.setWidth("100%");
            layout.addComponent(button);
        }
        // END-EXAMPLE: layout.orderedlayout.sizing.relativesize
        
        content.addComponent(wrapper);
    }
}
