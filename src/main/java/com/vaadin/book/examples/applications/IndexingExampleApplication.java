package com.vaadin.book.examples.applications;

import java.io.IOException;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// BEGIN-EXAMPLE: advanced.urifragmentutility.indexing
@Theme("book-examples")
public class IndexingExampleApplication extends UI {
    private static final long serialVersionUID = -128617724108192945L;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Indexing Example");

        // Create the content root layout for the UI
        final VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Application state menu
        final ListSelect menu = new ListSelect("Select a URI Fragment");
        menu.addItem("mercury");
        menu.addItem("venus");
        menu.addItem("earth");
        menu.addItem("mars");
        menu.setRows(4);
        menu.setNullSelectionAllowed(false);
        menu.setImmediate(true);
        content.addComponent(menu);

        // Set the URI Fragment when menu selection changes
        menu.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -2599930524184798192L;

            public void valueChange(ValueChangeEvent event) {
                String itemid = (String) event.getProperty().getValue();
                
                // Set the fragment with the exclamation mark, which is
                // understood by the indexing engine
                getPage().setUriFragment("!" + itemid);
            }
        });

        // When the URI fragment is given, use it to set menu selection
        getPage().addUriFragmentChangedListener(new UriFragmentChangedListener() {
            private static final long serialVersionUID = 5070399971246531478L;

            public void uriFragmentChanged(UriFragmentChangedEvent source) {
                String fragment =
                          source.getUriFragment();
                if (fragment != null) {
                    // Skip the exclamation mark
                    if (fragment.startsWith("!"))
                        fragment = fragment.substring(1);
                    
                    // Set the menu selection
                    menu.select(fragment);
                    
                    // Display some content related to the item
                    content.addComponent(new Label(getContent(fragment)));
                }
            }
        });

        // Handle non-Ajax requests
        VaadinSession.getCurrent().addRequestHandler(new RequestHandler() {
            private static final long serialVersionUID = -4333861987675564785L;

            @Override
            public boolean handleRequest(VaadinSession session,
                                         VaadinRequest request,
                                         VaadinResponse response)
                throws IOException {
                // If the special escape paremeter is included, store it
                if (request.getParameterMap().containsKey("_escaped_fragment_")) {
                    String fragment = request.getParameter("_escaped_fragment_");
                    
                    // Generate some HTML content for the indexing engine
                    String content = getContent(fragment);

                    response.setContentType("text/html");
                    response.getWriter().print(
                        "<html><body><p>" + content +
                        "</p></body></html>");
                    return false;
                } else
                    return true;
            }
        });
    }

    // Provides some textual content for both the Vaadin Ajax application
    // and the HTML page for indexing engines.
    String getContent(String fragment) {
        return "Nice little content for #!" + fragment;
    }
}
// END-EXAMPLE: advanced.urifragmentutility.indexing
