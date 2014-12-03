package com.vaadin.book.examples.advanced;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class JSAPIExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();
        
        if ("basic".equals(context))
            basic(layout);
        else if ("status".equals(context))
            status(layout);
        else if ("callbackbasic".equals(context))
            callbackbasic(layout);
        else if ("callbackparameters".equals(context))
            callbackparameters(layout);
        else if ("screendump".equals(context))
            screendump(layout);
        
        setCompositionRoot(layout);
    }

    void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.basic
        Button execute = new Button("Execute", new ClickListener() {
            private static final long serialVersionUID = 4941615365448832738L;

            public void buttonClick(ClickEvent event) {
                // Execute JavaScript in the currently processed page
                Page.getCurrent().getJavaScript().execute("alert('Hello')");
            }
        });

        Button shorthand = new Button("Shorthand", new ClickListener() {
            private static final long serialVersionUID = 4941615365448832738L;

            public void buttonClick(ClickEvent event) {
                // Shorthand
                JavaScript.getCurrent().execute("alert('Hello')");
            }
        });
        // END-EXAMPLE: advanced.jsapi.basic
        layout.addComponent(execute);
        layout.addComponent(shorthand);
    }
    
    public static final String statusDescription =
        "<h1>Setting Status Message</h1>"+
        "<p>Enter a message and observe the status bar of the browser.</p>"+
        "<p><b>Note</b>: Modification of status messages must be enabled in the browser. "+
        "For example in Firefox they are not, but must be set in "+
        "<b>Preferences \u2192 Content \u2192 Enable JavaScript \u2192 Advanced \u2192 Change status bar text</b>.</p>";
    
    void status (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.status
        // FORUM: http://vaadin.com/forum/-/message_boards/message/250304
        final TextField msgField = new TextField("Status Message");
        layout.addComponent(msgField);
        
        Button set = new Button("Set Status");
        set.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 4941611508948832738L;

            public void buttonClick(ClickEvent event) {
                String message = (String) msgField.getValue();
                
                // Quote single quotes
                message = message.replace("'", "\\'");

                JavaScript.getCurrent().execute("window.status = '" +
                                                message + "';");
            }
        });
        layout.addComponent(set);
        // END-EXAMPLE: advanced.jsapi.status

        set.setClickShortcut(KeyCode.ENTER);
        set.addStyleName("primary");
    }

    void callbackbasic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.callbackbasic
        JavaScript.getCurrent().addFunction("com.example.foo.myfunc",
                                            new JavaScriptFunction() {
            private static final long serialVersionUID = -2399933021928502854L;

            @Override
            public void call(JSONArray arguments) throws JSONException {
                Notification.show("Received call");
            }
        });
        
        Link link = new Link("Send Message", new ExternalResource(
                "javascript:com.example.foo.myfunc()"));
        // END-EXAMPLE: advanced.jsapi.callbackbasic
        layout.addComponent(link);
    }
    
    void callbackparameters (VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.callbackparameters
        JavaScript.getCurrent().addFunction("com.example.foo.myfunc",
                                            new JavaScriptFunction() {
            private static final long serialVersionUID = -2399933021928502854L;

            @Override
            public void call(JSONArray arguments) throws JSONException {
                try {
                    String message = arguments.getString(0);
                    int    value   = arguments.getInt(1);
                    Notification.show("Message: " + message +
                                      ", value: " + value);
                } catch (JSONException e) {
                    Notification.show("Error: " + e.getMessage());
                }
            }
        });
        
        Link link = new Link("Send Message", new ExternalResource(
                "javascript:com.example.foo.myfunc(prompt('Message'), 42)"));
        // END-EXAMPLE: advanced.jsapi.callbackparameters
        layout.addComponent(link);
    }

    void screendump(VerticalLayout layout) {
        // BEGIN-EXAMPLE: advanced.jsapi.screendump
        // Panel to display HTML of dumped content
        final Panel panel = new Panel("Dumped Content");
        panel.setWidth("640px");
        panel.setHeight("400px");
        final Label dumpContent = new Label();
        panel.setContent(dumpContent);
        layout.addComponent(panel);

        // Handle dump content from server-side
        Page.getCurrent().getJavaScript().addFunction("dumpcontent",
            new JavaScriptFunction() {
            private static final long serialVersionUID = -2431634757915680123L;

            @Override
            public void call(JSONArray arguments)
                    throws JSONException {
                dumpContent.setValue(arguments.getString(0));
            }
        });
        
        // Button for dumping the content
        Button takeadump = new Button("Take a Dump");
        takeadump.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -7000595589752593427L;

            @Override
            public void buttonClick(ClickEvent event) {
                Page.getCurrent().getJavaScript().execute(
                    "dumpcontent(document.documentElement.innerHTML)");
            }
        });
        layout.addComponent(takeadump);
        // END-EXAMPLE: advanced.jsapi.screendump
    }
}
