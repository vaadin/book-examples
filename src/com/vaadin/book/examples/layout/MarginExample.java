package com.vaadin.book.examples.layout;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MarginExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 4598073828719119575L;

    public void init (String context) {
        // BEGIN-EXAMPLE: layout.formatting.margin
        // A layout with a margin
        VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("mymargins");
        layout.setMargin(true);
        layout.setMargin(new MarginInfo(false, true, false, true));

        // A component inside the layout
        Label label = new Label("My Label with Margins");
        layout.addComponent(label);

        layout.setSizeUndefined();
        setSizeUndefined();
        setCompositionRoot(layout);
        // END-EXAMPLE: layout.formatting.margin
    }
}
