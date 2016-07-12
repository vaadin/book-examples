package com.vaadin.book.examples.component.properties;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FocusExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -83453485734975384L;

	public void init (String context) {
	    VerticalLayout layout = new VerticalLayout();
	    
	    if ("focus".equals(context))
	        focusMethod();
	    else if ("tabindex".equals(context))
            focusMethod();
        else if ("focusevent".equals(context))
            focusEvent();
        else if ("alternatingfocus".equals(context))
            focusAlternation(layout);
        else
            layout.addComponent(new Label("Invalid context " + context));
	    
	    if (getCompositionRoot() == null)
	        setCompositionRoot(layout);
	}
	
	public void focusMethod() {
		HorizontalLayout layout = new HorizontalLayout();

		// BEGIN-EXAMPLE: component.features.focusable.focus
		Form loginBox = new Form();
		loginBox.setCaption("Login");
		layout.addComponent(loginBox);

		// Create the first field which will be focused
		TextField username = new TextField("User name");
		loginBox.addField("username", username);
		
		// Set focus to the user name
		username.focus();
		
        TextField password = new TextField("Password");
        loginBox.addField("password", password);
		
		Button login = new Button("Login");
		loginBox.getFooter().addComponent(login);
		// END-EXAMPLE: component.features.focusable.focus

        // BEGIN-EXAMPLE: component.features.focusable.tabindex
		// An additional component which natural focus order would
		// be after the button.
        CheckBox remember = new CheckBox("Remember me");
        loginBox.getFooter().addComponent(remember);

        username.setTabIndex(1);
        password.setTabIndex(2);
        remember.setTabIndex(3); // Different than natural place 
        login.setTabIndex(4);
        // END-EXAMPLE: component.features.focusable.tabindex
        
        ((HorizontalLayout) loginBox.getFooter()).setComponentAlignment(remember, Alignment.MIDDLE_CENTER);
		
		setCompositionRoot(layout);
    }
	
	void focusEvent() {
        
        final HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: component.features.focusable.focusevent
        // Have some data that is shared by the TextField and ComboBox
        final ObjectProperty<String> data =
            new ObjectProperty<String>("A choise");

        // Create a text field and bind it to the data
        final TextField textfield = new TextField("Edit This", data);
        textfield.setImmediate(true);
        layout.addComponent(textfield);

        // Create a replacement combo box; this could be created
        // later in the FocusListener just as well
        final ComboBox combobox = new ComboBox ("No, Edit This");
        combobox.addItem("A choise");
        combobox.addItem("My choise");
        combobox.addItem("Your choise");
        combobox.setPropertyDataSource(data);
        combobox.setImmediate(true);
        combobox.setNewItemsAllowed(true);

        // Change TextField to ComboBox when it gets focus
        textfield.addFocusListener(new FocusListener() {
            private static final long serialVersionUID = 8721337946386845992L;

            public void focus(FocusEvent event) {
                layout.replaceComponent(textfield, combobox);
                combobox.focus();
            }
        });
	    
        // Change ComboBox back to TextField when it loses focus
        combobox.addBlurListener(new BlurListener() {
            private static final long serialVersionUID = 7055180877355044203L;

            public void blur(BlurEvent event) {
                layout.replaceComponent(combobox, textfield);
            }
        });
        // END-EXAMPLE: component.features.focusable.focusevent
        
        setCompositionRoot(layout);
	}
	
	final public static String alternatingfocusDescription = "<h1>Focus Alternation Experiment</h1>"
	        + "<p>Doesn't look feasible. There's a small problem if you press Tab key continuously... "
	        + "Also, when the button is clicked, handling the click event won't prevent the blur event.</p>";

    void focusAlternation(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.features.focusable.alternatingfocus
        Panel loginBox = new Panel("Login");
        FormLayout loginContent = new FormLayout();
        loginBox.setContent(loginContent);
        layout.addComponent(loginBox);

        // Create the first field which will be focused
        final TextField username = new TextField("User name");
        loginContent.addComponent(username);
        
        // Set focus to the user name
        username.focus();
        
        final TextField password = new TextField("Password");
        loginContent.addComponent(password);
        
        username.setTabIndex(1);
        password.setTabIndex(2);

        // Whenever either field loses focus, focus the other field
        username.addBlurListener(new BlurListener() {
            private static final long serialVersionUID = 8721337946386845992L;

            public void blur(BlurEvent event) {
                password.focus();
            }
        });
        
        password.addBlurListener(new BlurListener() {
            private static final long serialVersionUID = 7055180877355044203L;

            public void blur(BlurEvent event) {
                username.focus();
            }
        });

        Button login = new Button("Login");
        login.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -3491738578053392691L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Not really necessary to remove the listeners
                for (Object l: username.getListeners(BlurEvent.class))
                    username.removeBlurListener((BlurListener) l); 
                for (Object l: password.getListeners(BlurEvent.class))
                    password.removeBlurListener((BlurListener) l);
                
                Notification.show("Bling!");
            }
        });
        loginContent.addComponent(login);
        // END-EXAMPLE: component.features.focusable.alternatingfocus
        
        setCompositionRoot(layout);
    }

    public void css(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.features.focusable.css
        FormLayout loginBox = new FormLayout();
        loginBox.addStyleName("customfocus");
        layout.addComponent(loginBox);

        // Some components
        loginBox.addComponent(new TextField("User name") {{focus();}});
        loginBox.addComponent(new TextField("Password"));
        layout.addComponent(new Button("Login"));
        // END-EXAMPLE: component.features.focusable.css
    }
}
