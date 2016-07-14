package com.vaadin.book.examples.themes;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: themes.using.default.valo
@Theme("myvalotheme")
public class ValoThemeUI extends UI {
	private static final long serialVersionUID = 927255270642583907L;

	@Override
	protected void init(VaadinRequest request) {
	    VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		setContent(layout);
		
		layout.addComponent(new Label("Label"));

		HorizontalSplitPanel splitpanel = new HorizontalSplitPanel();
		splitpanel.setSplitPosition(200, Unit.PIXELS);
		layout.addComponent(splitpanel);
		layout.setExpandRatio(splitpanel, 1.0f);
		
		Tree tree = new Tree("Tree", TreeExample.createTreeContent());
		splitpanel.addComponent(tree);
		
		VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);
        splitpanel.addComponent(view);
		
        view.addComponent(panels());
        view.addComponent(buttons());
        view.addComponent(textfields());
        
        Label sourceCode = new Label("int a = 42;",
                                     ContentMode.PREFORMATTED);
        view.addComponent(sourceCode);
	}

    Component panels() {
        Panel panel = new Panel("Panel Styles");
        panel.addStyleName("borderless");
        panel.setWidth("100%");
        CssLayout layout = new CssLayout();
        panel.setContent(layout);
        
        String styles[] = {"Normal", "Well", "Borderless"};
        for (String style: styles) {
            Panel c = new Panel(style);
            c.addStyleName(style.toLowerCase());
            layout.addComponent(c);
            
            c.setContent(new Label("Panel content"));
        }

        return panel;
    }

	Component buttons() {
	    Panel panel = new Panel("Button Styles");
	    panel.addStyleName("borderless");
        panel.setWidth("100%");
	    CssLayout layout = new CssLayout();
        panel.setContent(layout);
	    
	    String styles[] = {"Small", "Normal", "Large", "Primary", "Friendly", "Danger", "Borderless", "Link"};
	    for (String style: styles) {
	        Button button = new Button(style);
	        button.addStyleName(style.toLowerCase());
	        layout.addComponent(button);
	    }

        return panel;
	}

    Component textfields() {
        Panel panel = new Panel("TextField Styles");
        panel.addStyleName("borderless");
        panel.setWidth("100%");
        CssLayout layout = new CssLayout();
        panel.setContent(layout);
        
        String styles[] = {"Borderless", "Small", "Normal", "Large"};
        for (String style: styles) {
            TextField c = new TextField(style);
            c.addStyleName(style.toLowerCase());
            layout.addComponent(c);
        }

        return panel;
    }
}
// END-EXAMPLE: themes.using.default.valo
