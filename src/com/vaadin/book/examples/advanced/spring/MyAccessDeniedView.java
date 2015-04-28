package com.vaadin.book.examples.advanced.spring;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: advanced.spring.navigation
@SpringView(name=MyAccessDeniedView.NAME)
public class MyAccessDeniedView extends CustomComponent
                                implements View  {
    private static final long serialVersionUID = -3349770763697923092L;

    public final static String NAME = "accessdenied";
    
    Button back = new Button("Go Back");

    public MyAccessDeniedView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        
        layout.addComponents(new Label("Access Denied!"),
                             back);

        setCompositionRoot(layout);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        // UI.getCurrent().getNavigator().navigateTo(event.getOldView(), null, null);
    }
}
// END-EXAMPLE: advanced.spring.navigation
