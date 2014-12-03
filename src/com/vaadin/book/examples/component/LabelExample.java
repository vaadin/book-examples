package com.vaadin.book.examples.component;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class LabelExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic();
        else if ("getset".equals(context))
            getset(layout);
        else if ("contentmodes".equals(context))
            contentmodes(layout);
        else if ("html".equals(context))
            html(layout);
        else if ("javascript".equals(context))
            javascript(layout);
        else if ("wrap".equals(context))
            wrap();
        else if ("binding".equals(context))
            binding(layout);
        else if ("delegation".equals(context))
            delegation(layout);
        else if ("non-breaking".equals(context))
            spacingNonBreaking();
        else if ("preformatted".equals(context))
            spacingPreformatted();
        else if ("adjustable".equals(context))
            spacingadjustable();
        else if ("expanding".equals(context))
            spacingExpanding();
        else if ("switchbutton".equals(context))
            switchbutton(layout);
        else if ("css".equals(context))
            css(layout);
        else if ("predefinedstyles".equals(context))
            predefinedstyles(layout);
        else
            setCompositionRoot(new Label("Invalid context: " + context));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }

    void basic() {
        // BEGIN-EXAMPLE: component.label.basic
        // A container that is 100% wide by default
        VerticalLayout layout = new VerticalLayout();

        Label label = new Label("Labeling can be dangerous");
        layout.addComponent(label);
        // END-EXAMPLE: component.label.basic

        setCompositionRoot(layout);
    }
    
    void getset(VerticalLayout vlayout) {
        // BEGIN-EXAMPLE: component.label.getset
        // Have a label with some text
        Label label = new Label("edit this");

        // Get the label's text to initialize a field
        TextField editor = new TextField(null, // No caption
                                         label.getValue());
        
        // Change the label's text
        editor.addValueChangeListener(event -> // Java 8
            label.setValue(editor.getValue()));
        editor.setImmediate(true); // Send on Enter

        // Display nicely -------------------------------------------
        Label arrows = new Label(FontAwesome.EXCHANGE.getHtml(),
                                 ContentMode.HTML);
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponents(label, arrows, editor);
        layout.setSpacing(true);
        for (Component c: layout)
            layout.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
        // END-EXAMPLE: component.label.getset
        
        vlayout.addComponent(layout);
    }
    
    public static String bindingDescription = "<h1>Binding Label to an Editor</h1>"
            + "<p>You can bind a <b>Label</b> to a <b>PropertyEditor</b>.</p>";
    
    void binding(VerticalLayout vlayout) {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.label.binding
        // A Label that has a value
        Label label = new Label("some value");
        
        // Label itself is a Property - 
        // we can bind a property editor field to it
        TextField editor = new TextField();
        editor.setPropertyDataSource(label);
        editor.setImmediate(true);

        // Display nicely -------------------------------------------
        Label arrows = new Label(FontAwesome.EXCHANGE.getHtml(),
            ContentMode.HTML);
        layout.addComponents(label, arrows, editor);
        layout.setSpacing(true);
        for (Component c: layout)
            layout.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
        // END-EXAMPLE: component.label.binding
        vlayout.addComponent(layout);
    }
    
    public static String delegationDescription = "<h1>Label Is Not a Property Editor</h1>"
            + "<p>While Label is a <b>Property</b> and <b>Property.Viewer</b>, it is not a <b>Property.Editor</b>, so "
            + "a property data source bound to it can <u><i>not</i></u> be written to through delegation from a <b>Property.Editor</b> (a field).</p>";
    
    void delegation(VerticalLayout vlayout) {
        HorizontalLayout layout = new HorizontalLayout();

        // BEGIN-EXAMPLE: component.label.delegation
        // Some property
        ObjectProperty<String> property =
            new ObjectProperty<String>("some value");
        
        // Label that is bound to the property
        Label label = new Label(property);
        
        // Label is itself a Property (delegated) - 
        // we can try to bind a property editor field to it
        TextField editor = new TextField();
        editor.setPropertyDataSource(label);
        editor.setImmediate(true);

        // Display nicely -------------------------------------------
        Label arrows = new Label(FontAwesome.EXCHANGE.getHtml(),
            ContentMode.HTML);
        layout.addComponents(label, arrows, editor);
        layout.setSpacing(true);
        for (Component c: layout)
            layout.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
        // END-EXAMPLE: component.label.delegation
        vlayout.addComponent(layout);
    }
    
    void contentmodes(VerticalLayout vlayout) {
        // BEGIN-EXAMPLE: component.label.content-modes.contentmodes
        FormLayout layout = new FormLayout();
        layout.addStyleName("withborders");
        layout.setWidth("600px");
        
        Label textLabel = new Label(
            "Text where formatting characters, such as \\n, " +
            "and HTML, such as <b>here</b>, are quoted.",
            ContentMode.TEXT);

        Label preLabel = new Label(
            "Preformatted text is shown in an HTML <pre> tag.\n" +
            "Formatting such as\n" +
            "  * newlines\n" + 
            "  * whitespace\n" +
            "and such are preserved. HTML tags, \n"+
            "such as <b>bold</b>, are quoted.",
            ContentMode.PREFORMATTED);

        Label htmlLabel = new Label(
            "In HTML mode, all HTML formatting tags, such as \n" +
            "<ul>"+
            "  <li><b>bold</b></li>"+
            "  <li>itemized lists</li>"+
            "  <li>etc.</li>"+
            "</ul> "+
            "are preserved.",
            ContentMode.HTML);

        textLabel.setCaption("TEXT");
        preLabel.setCaption("PREFORMATTED");
        htmlLabel.setCaption("HTML");
        layout.addComponents(textLabel, preLabel, htmlLabel);
        // END-EXAMPLE: component.label.content-modes.contentmodes
        vlayout.addComponent(layout);
    }
    
    void html(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.label.content-modes.html
        // Image in the current theme
        String imageName = "img/smiley2-20px.png";
        
        // Find full path to the theme
        String imagePath = Page.getCurrent().getLocation().resolve("/") + 
            VaadinServlet.getCurrent().getServletContext().getContextPath() +
            "/VAADIN/themes/" + UI.getCurrent().getTheme() +
            "/" + imageName;
        System.out.println(imagePath);
        
        layout.addComponent(new Label("Here we have an image <img src=\"" +
                                    imagePath +"\"/> within text.",
                                    ContentMode.HTML));
        // END-EXAMPLE: component.label.content-modes.html
    }
    
    void javascript(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.label.content-modes.javascript
        Label script = new Label("<script type=\"text/javascript\">\nprint();\n</script>", ContentMode.HTML);
        String foo = "<script src=\"http://widgets.twimg.com/j/2/widget.js\"></script>\n" +
        		"<script>\n" +
        		"print();\n"+
                "new TWTR.Widget({\n"+
                "version: 2,\n"+
                "  type: 'profile',\n"+
                "  rpp: 4,\n"+
                "  interval: 6000,\n"+
                "  width: 250,\n"+
                "  height: 300,\n"+
                "  theme: {\n"+
                "    shell: {\n"+
                "      background: '#333333',\n"+
                "      color: '#ffffff'\n"+
                "    },\n"+
                "    tweets: {\n"+
                "      background: '#000000',\n"+
                "      color: '#ffffff',\n"+
                "      links: '#4aed05'\n"+
                "    }\n"+
                "  },\n"+
                "  features: {\n"+
                "    scrollbar: false,\n"+
                "    loop: false,\n"+
                "    live: false,\n"+
                "    hashtags: true,\n"+
                "    timestamp: true,\n"+
                "    avatars: false,\n"+
                "    behavior: 'all'\n"+
                "  }\n"+
                "}).render().setUser('Magi42').start();\n"+
                "</script>";
        // END-EXAMPLE: component.label.content-modes.javascript
        layout.addComponent(new Label("Here it is:" + foo));
        layout.addComponent(script);
    }
    
    void wrap() {
        // BEGIN-EXAMPLE: component.label.wrap
        // A container with a defined width. The default content layout
        // of Panel is VerticalLayout, which has 100% default width.
        Panel panel = new Panel("Panel Containing a Label");
        panel.setWidth("300px");

        panel.setContent(
            new Label("This is a Label inside a Panel. There is " +
                      "enough text in the label to make the text " +
                      "wrap when it exceeds the width of the panel."));
        // END-EXAMPLE: component.label.wrap

        setCompositionRoot(panel);
    }

    void spacingNonBreaking() {
        // BEGIN-EXAMPLE: component.label.spacing.non-breaking
        VerticalLayout layout = new VerticalLayout();

        Label label = new Label("This is a Label");
        layout.addComponent(label);

        Label emptyLabel = new Label("&nbsp;", ContentMode.HTML);
        layout.addComponent(emptyLabel);

        Label label2 = new Label("This is another Label");
        layout.addComponent(label2);
        // END-EXAMPLE: component.label.spacing.non-breaking
        
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth("300px");
        layout.addStyleName("withborders");

        setCompositionRoot(layout);
    }
    
    void spacingPreformatted() {
        // BEGIN-EXAMPLE: component.label.spacing.preformatted
        VerticalLayout layout = new VerticalLayout();

        Label label1 = new Label("This is a Label");
        layout.addComponent(label1);

        layout.addComponent(new Label(" ", ContentMode.PREFORMATTED));
        
        Label label2 = new Label("This is another Label");
        layout.addComponent(label2);
        // END-EXAMPLE: component.label.spacing.preformatted
        
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth("300px");
        layout.addStyleName("withborders");

        setCompositionRoot(layout);
    }
   
    void spacingadjustable() {
        // BEGIN-EXAMPLE: component.label.spacing.adjustable
        VerticalLayout layout = new VerticalLayout();

        Label label1 = new Label("This is a Label");
        layout.addComponent(label1);

        Label adjustableGap = new Label();
        adjustableGap.setHeight("13pt");
        layout.addComponent(adjustableGap);
        
        Label label2 = new Label("This is another Label");
        layout.addComponent(label2);
        // END-EXAMPLE: component.label.spacing.adjustable
        
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth("300px");
        layout.addStyleName("withborders");

        setCompositionRoot(layout);
    }

    void spacingExpanding() {
        // BEGIN-EXAMPLE: component.label.spacing.expanding
        // Have a wide component bar
        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.setWidth("100%");
        
        // Have a component before the gap (a collapsing cell)
        Button button1 = new Button("I'm on the left");
        horizontal.addComponent(button1);
        
        // An expanding gap spacer
        Label expandingGap = new Label();
        expandingGap.setWidth("100%");
        horizontal.addComponent(expandingGap);
        horizontal.setExpandRatio(expandingGap, 1.0f);
        
        // A component after the gap (a collapsing cell)
        Button button2 = new Button("I'm on the right");
        horizontal.addComponent(button2);
        // END-EXAMPLE: component.label.spacing.expanding
        
        // Fake it a bit - otherwise can't set background
        expandingGap.setValue("&nbsp;");
        expandingGap.setHeight("100%");
        expandingGap.setContentMode(ContentMode.HTML);

        horizontal.setMargin(true);
        horizontal.setSpacing(true);
        horizontal.setWidth("400px");
        horizontal.addStyleName("withborders");

        setCompositionRoot(horizontal);
    }

    void switchbutton(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: component.label.styling.switchbutton
        class SwitchButton extends CustomComponent {
            private static final long serialVersionUID = -1375727765541536855L;

            public SwitchButton(String caption) {
                // Layout to catch clicks
                VerticalLayout layout = new VerticalLayout();
                
                // A label in button's clothes
                final Label button = new Label("<span class='v-button-wrap'>" +
                		"<span class='v-button-caption'>" +
                		caption + "</span>" +
                		"</span>", ContentMode.HTML);
                button.setStyleName("v-button");
                button.setWidth(null);
                button.setData(false);
                layout.addComponent(button);

                // Toggling logic
                layout.addLayoutClickListener(new LayoutClickListener() {
                    private static final long serialVersionUID = 4029720293992759477L;
        
                    @Override
                    public void layoutClick(LayoutClickEvent event) {
                        if (((Boolean) button.getData()).booleanValue()) {
                            button.setStyleName("v-button");
                            button.setData(false);
                        } else {
                            button.setStyleName("v-button v-pressed");
                            button.setData(true);
                        }
                    }
                });
                
                setCompositionRoot(layout);
            }
        }
        
        SwitchButton button = new SwitchButton("Click and unclick me");
        // END-EXAMPLE: component.label.styling.switchbutton
        rootlayout.addComponent(button);
    }

    void databinding() {
        // A container that is 100% wide by default
        VerticalLayout layout = new VerticalLayout();

        // BEGIN-EXAMPLE: component.label.databinding
        // Bind a label to a property data source
        ObjectProperty<String> property =
                new ObjectProperty<String>("Initial value");
        Label label = new Label(property);

        // Set the property value through the label
        Button set = new Button("Set Value", event -> // Java 8
            label.setValue("New value"));
        // END-EXAMPLE: component.label.databinding
        
        layout.addComponent(label);
        setCompositionRoot(layout);
    }
    
    public static final String cssDescription =
        "<h1>Styling Label with CSS</h1>" +
        "<p>You can style a Label just like any other component. Remember that Label normally has 100% width " +
        "to enable wrapping.</p>";
    
    void css(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: component.label.styling.css
        Label label = new Label("This is...Label!");
        label.addStyleName("fancylabel");
        // END-EXAMPLE: component.label.styling.css
        rootlayout.addComponent(label);
    }

    public static final String predefinedstylesDescription =
        "<h1>Predefined Styles for Label</h1>" +
        "<p>Label has a number of predefined styles in the Reindeer theme.</p>";
    
    void predefinedstyles(VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: component.label.styling.predefinedstyles
        Label label1 = new Label("Do It the Reindeer Way!");
        label1.addStyleName(Reindeer.LABEL_H1);

        Label label2 = new Label("Do It the Reindeer Way!");
        label2.addStyleName(Reindeer.LABEL_H2);

        Label label3 = new Label("Do It the Reindeer Way!");
        label3.addStyleName(Reindeer.LABEL_SMALL);
        // END-EXAMPLE: component.label.styling.predefinedstyles

        rootlayout.addComponent(label1);
        rootlayout.addComponent(label2);
        rootlayout.addComponent(label3);
    }
}
