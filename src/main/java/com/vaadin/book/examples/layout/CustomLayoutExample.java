package com.vaadin.book.examples.layout;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CustomLayoutExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -8458669675797366833L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.customlayout.basic
        Panel loginPanel = new Panel("Login");
        CustomLayout content = new CustomLayout("layoutname");
        content.setSizeUndefined();
        loginPanel.setContent(content);
        loginPanel.setSizeUndefined();
        
        // No captions for fields is they are provided in the template
        content.addComponent(new TextField(), "username");
        content.addComponent(new TextField(), "password");
        content.addComponent(new Button("Login"), "okbutton");
        // END-EXAMPLE: layout.customlayout.basic
        
        layout.addComponent(loginPanel);
    }
    
    public void stream(VerticalLayout root) {
        // BEGIN-EXAMPLE: layout.customlayout.stream
        // So here's the template
        String template =
            "<p>Here's my template</p>"+
            "<table>"+
            "<tr><td>Username:</td><td><div location='username'></div></td></tr>"+
            "<tr><td>Password:</td><td><div location='password'></div></td></tr>"+
            "</table>";
        
        // Read it through an input stream
        ByteArrayInputStream ins = new ByteArrayInputStream(template.getBytes());
        
        CustomLayout layout;
        try {
            layout = new CustomLayout(ins);
        } catch (IOException e) {
            setCompositionRoot(new Label("Bad CustomLayout input stream"));
            return;
        }

        layout.addComponent(new TextField(), "username");
        layout.addComponent(new TextField(), "password");
        // END-EXAMPLE: layout.customlayout.stream
        
        root.addComponent(layout);
    }

    public void maxwidth(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: layout.customlayout.maxwidth
        // A containing panel - this could just as well be
        // the main window (Window inherits Panel)
        Panel panel = new Panel("A Containing Panel");
        
        // The panel has some defined size just like a browser
        // window would have
        panel.setWidth("600px");
        panel.setHeight("300px");
        
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setSpacing(true);
        panel.setContent(panelContent);

        for (int i=1; i<=10; i++) {
            // Read a HTML panel template through an input stream
            String template =
                    "<div style='max-width: 600px; text-align: center;'>"+
                    "  <div style='max-width: 550px; display: inline-block; border: 1px black solid;'>" +
                    "    <div style='border-bottom: 1px gray solid;'>A Caption</div>" +
                    "    <div style='overflow: auto;'>"+
                    "      <div location='content' style='display: inline-block;'></div>" +
                    "    </div>"+
                    "  </div>"+
                    "</div>";
            ByteArrayInputStream ins = new ByteArrayInputStream(template.getBytes());
                
            CustomLayout customLayout = null;
            try {
                 customLayout = new CustomLayout(ins);
            } catch (IOException e) {
                e.printStackTrace();
            }
            customLayout.setWidth("100%");
            
            HorizontalLayout hlayout = new HorizontalLayout();
            for (int j=1; j<=i; j++) {
                Label label = new Label("...This stuff expands...");
                label.setSizeUndefined();
                hlayout.addComponent(label);
            }
            customLayout.addComponent(hlayout, "content");
    
            panel.setContent(customLayout);
        }

        // END-EXAMPLE: layout.customlayout.maxwidth
        rootlayout.addComponent(panel);
    }    
    
    public void styling(VerticalLayout layout) {
        // BEGIN-EXAMPLE: layout.customlayout.styling
        CustomLayout cl = new CustomLayout("stylingexample");
        cl.addStyleName("customlayoutstyling");

        // The stylingexample.html is simply:
        //   <div location="username"></div>
        //   <div location="password"></div>
        
        cl.addComponent(new TextField("Username"), "username");
        cl.addComponent(new TextField("Password"), "password");
        // END-EXAMPLE: layout.customlayout.styling
        layout.addComponent(cl);
    }
}
