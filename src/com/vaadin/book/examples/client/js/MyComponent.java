package com.vaadin.book.examples.client.js;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

// BEGIN-EXAMPLE: gwt.javascript.basic
/** Server-side API for the JavaScript component */
@JavaScript({"mylibrary.js", "mycomponent-connector.js"})
public class MyComponent extends AbstractJavaScriptComponent {
    private static final long serialVersionUID = -8510122663484494839L;
    
    // Java 8
    ArrayList<Consumer<String>> listeners =
            new ArrayList<Consumer<String>>();
    public void onChange(Consumer<String> listener) {
        listeners.add(listener);
    }
    
    public void setValue(String value) {
        getState().setValue(value);
        markAsDirty();
    }
    
    public String getValue() {
        return getState().getValue();
    }

    @Override
    public MyComponentState getState() {
        return (MyComponentState) super.getState();
    }
    
    public MyComponent() {
        addFunction("onClick", new JavaScriptFunction() {
            private static final long serialVersionUID = 1256984845028849243L;

            @Override
            public void call(JSONArray arguments)
                    throws JSONException {
                getState().setValue(arguments.getString(0));
                for (Consumer<String> listener: listeners)
                    listener.accept(arguments.getString(0));
            }
        });
    }
}
// END-EXAMPLE: gwt.javascript.basic
