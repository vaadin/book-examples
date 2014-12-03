package com.vaadin.book.examples.layout;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CustomLayoutExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8458669675797366833L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic();
        else if ("styling".equals(context))
            styling();
        else if ("stream".equals(context))
            stream();
        else if ("maxwidth".equals(context))
            maxwidth(layout);
        else
            setCompositionRoot(new Label("Error"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void basic() {
        // BEGIN-EXAMPLE: layout.customlayout.basic
        CustomLayout layout = new CustomLayout("layoutname");
        
        // No captions for fields is they are provided in the template
        layout.addComponent(new TextField(), "username");
        layout.addComponent(new TextField(), "password");
        // END-EXAMPLE: layout.customlayout.basic
        
        setCompositionRoot(layout);
    }
    
    void stream() {
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
        
        setCompositionRoot(layout);
    }

    void maxwidth(VerticalLayout rootlayout) {
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
    
    void styling() {
        // BEGIN-EXAMPLE: layout.customlayout.styling
        CustomLayout layout = new CustomLayout("stylingexample");
        layout.addStyleName("customlayoutstyling");

        // The stylingexample.html is simply:
        //   <div location="username"></div>
        //   <div location="password"></div>
        
        layout.addComponent(new TextField("Username"), "username");
        layout.addComponent(new TextField("Password"), "password");
        // END-EXAMPLE: layout.customlayout.styling
        
        setCompositionRoot(layout);
    }
}
