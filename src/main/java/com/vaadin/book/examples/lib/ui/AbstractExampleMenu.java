package com.vaadin.book.examples.lib.ui;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.vaadin.book.examples.lib.AbstractExampleItem;
import com.vaadin.book.examples.lib.BookExample;
import com.vaadin.book.examples.lib.BookExampleLibrary;
import com.vaadin.book.examples.lib.CaptionedExampleItem;
import com.vaadin.book.examples.lib.ExampleCtgr;
import com.vaadin.book.examples.lib.RedirctItem;
import com.vaadin.book.examples.lib.SourceFragment;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class AbstractExampleMenu extends CustomComponent {
    private static final long serialVersionUID = 3014632759946586790L;

    class MenuSelectionListener implements Property.ValueChangeListener {
        private static final long serialVersionUID = 8236533959795019956L;
        
        Layout viewLayout;
        Label exampleTitle;
        
        public MenuSelectionListener(Layout viewLayout, Label exampleTitle) {
            this.viewLayout = viewLayout;
            this.exampleTitle = exampleTitle;
        }

        public void valueChange(ValueChangeEvent event) {
            viewLayout.removeAllComponents();
            
            String selection = (String) event.getProperty().getValue();
            
            // Find the example
            CaptionedExampleItem exampleItem = exampleitems.get(selection);
            if (selection != null && exampleItem == null)
                Notification.show("Invalid item " + selection);
            else if (exampleItem != null) {
                if (exampleItem.getClass().isAssignableFrom(ExampleCtgr.class)) {
                    if (menu.hasChildren(exampleItem.getExampleId())) {
                        menu.select((String) menu.getChildren(exampleItem.getExampleId()).toArray()[0]);
                    }
                } else { // A leaf
                    BookExample example = (BookExample) exampleItem;

                    exampleTitle.setValue(example.getShortName());
                            
                    // Load unless already loaded
                    File baseDir = VaadinService.getCurrent().getBaseDirectory();
                    example.loadExample(baseDir);

                    if (example.getDescription() != null) {
                        Label descLabel = new Label(example.getDescription(), ContentMode.HTML);
                        descLabel.addStyleName("example-description");
                        viewLayout.addComponent(descLabel);
                    }

                    // The actual example component
                    Component examplecomponent = example.invokeExample();
                    examplecomponent.addStyleName("bookexample");
                    viewLayout.addComponent(examplecomponent);
                                            
                    // Java sources on the left, CSS on the right
                    HorizontalLayout horizontalOrder = new HorizontalLayout();
                    horizontalOrder.addStyleName("sourcecontainer");
                    horizontalOrder.setSpacing(true);
                    horizontalOrder.setMargin(true);

                    Panel bookRefs   = null;
                    Panel forumLinks = null;
                    Panel kbRefs    = null;

                    List<SourceFragment> fragments = example.getSourceFragments(); 
                    if (fragments != null) {
                        // Java Sources are laid out vertically
                        VerticalLayout verticalListings = new VerticalLayout();
                        verticalListings.setSizeUndefined();
                        verticalListings.setSpacing(true);
                        horizontalOrder.addComponent(verticalListings);
                        
                        // Find the widest source fragment
                        int widestIndex = 0;
                        int widestWidth = 0;
                        for (int fragmentNum = 0; fragmentNum < fragments.size(); fragmentNum++)
                            if (fragments.get(fragmentNum).getSrcWidth() > widestWidth) {
                                widestIndex = fragmentNum;
                                widestWidth = fragments.get(fragmentNum).getSrcWidth(); 
                            }
                        // BookExamplesUI.getLogger().info("Widest listing: " + widestIndex + " which is " + widestWidth);
                        
                        for (int fragmentNum = 0; fragmentNum < fragments.size(); fragmentNum++) {
                            SourceFragment fragment = fragments.get(fragmentNum);

                            // Have caption only in the beginning of the listings
                            String listingCaption = fragmentNum == 0? "Source Code" : "";
                            
                            String srcurl = "https://github.com/vaadin/book-examples/tree/master/src" + fragment.getSrcName();
                            SourceListing listing = new SourceListing(listingCaption, srcurl, fragment);
                            verticalListings.addComponent(listing);

                            // Use the width of the widest listing for all listings
                            if (fragmentNum == widestIndex)
                                listing.setWidth(null);
                            else
                                listing.setWidth("100%");
                            
                            if (!fragment.getBookRefs().isEmpty()) {
                                bookRefs = new Panel("Book References");
                                Layout bookRefsContent = new VerticalLayout();
                                bookRefs.setContent(bookRefsContent);
                                bookRefs.setSizeUndefined();
                                for (Iterator<String> iter = fragment.getBookRefs().iterator(); iter.hasNext();) {
                                    String ref = iter.next();
                                    int hashPos = ref.indexOf('#');
                                    String refFragment = "";
                                    if (hashPos != -1) {
                                        refFragment = "#" + ref.replace('#', '.');
                                        ref = ref.substring(0, hashPos);
                                    }
                                    String bookUrl = "http://vaadin.com/book/-/page/" + ref + ".html" + refFragment; 
                                    Link link = new Link(bookUrl, new ExternalResource(bookUrl));
                                    link.setTargetName("_new");
                                    bookRefsContent.addComponent(link);
                                }
                            }

                            if (!fragment.getForumLinks().isEmpty()) {
                                forumLinks = new Panel("Forum Messages");
                                Layout forumLinksContent = new VerticalLayout();
                                forumLinks.setContent(forumLinksContent);
                                forumLinks.setSizeUndefined();
                                for (Iterator<String> iter = fragment.getForumLinks().iterator(); iter.hasNext();) {
                                    String url = iter.next();
                                    Link link = new Link(url, new ExternalResource(url));
                                    link.setTargetName("_new");
                                    forumLinksContent.addComponent(link);
                                }
                            }

                            if (!fragment.getKbRefs().isEmpty()) {
                                kbRefs = new Panel("Pro Account Knowledge Base Articles");
                                Layout kbRefsContent = new VerticalLayout();
                                kbRefs.setContent(kbRefsContent);
                                kbRefs.setSizeUndefined();
                                for (Iterator<SourceFragment.Ref> iter = fragment.getKbRefs().iterator(); iter.hasNext();) {
                                    SourceFragment.Ref ref = iter.next();
                                    String url = "http://vaadin.com/knowledge-base#" + ref.ref;
                                    Link link = new Link(ref.caption, new ExternalResource(url));
                                    link.setTargetName("_new");
                                    kbRefsContent.addComponent(link);
                                }
                            }
                        }
                    }

                    // Show associated CSS
                    if (example.getCssFragments() != null && example.getCssFragments().size() > 0) {
                        SourceFragment csscode = example.getCssFragments().get(0);
                        String srcurl = "https://github.com/vaadin/book-examples/blob/master/WebContent/VAADIN/themes/book-examples/book-examples.scss";
                        horizontalOrder.addComponent(new SourceListing("CSS Code", srcurl, csscode));
                    }
                    
                    if (horizontalOrder.iterator().hasNext())
                        viewLayout.addComponent(horizontalOrder);
                    if (bookRefs != null)
                        viewLayout.addComponent(bookRefs);
                    if (forumLinks != null)
                        viewLayout.addComponent(forumLinks);
                    if (kbRefs != null)
                        viewLayout.addComponent(kbRefs);
                    
                    Page.getCurrent().setUriFragment(example.getExampleId());
                }
            }
        }
    }

    public Tree menu;
    
    // Collect redirects here
    final HashMap<String,String> redirects = new HashMap<String,String>();
    
    HashMap<String,CaptionedExampleItem> exampleitems;
    
    public AbstractExampleMenu(Layout viewLayout, Label exampleTitle) {
        menu = createMenuTree();

        // Collect examples here
        exampleitems = new HashMap<String,CaptionedExampleItem>();

        // Handle menu selection
        menu.addValueChangeListener(new MenuSelectionListener(viewLayout, exampleTitle));
    }
    
    protected Tree createMenuTree() {
        Tree menu = new Tree();
        menu.addContainerProperty("caption", String.class, "");
        menu.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        menu.setItemCaptionPropertyId("caption");

        Tree.ItemStyleGenerator itemStyleGenerator = new Tree.ItemStyleGenerator() {
            private static final long serialVersionUID = -3231268865512947125L;

            @Override
            public String getStyle(Tree source, Object itemId) {
                // Chapter title items do not contain a period
                if (!((String)itemId).contains("."))
                    return "chaptertitle";
                return null;
            }
        }; 
        menu.setItemStyleGenerator(itemStyleGenerator);

        return menu;
    }

    public Tree getMenu() {
        return menu;
    }
    
    public void buildMenu(BookExampleLibrary library) {
        List<AbstractExampleItem> examples = Arrays.asList(library.getAllExamples());
        buildMenu(Arrays.asList(library.getAllExamples()));
    }
    
    public void buildMenu(List<AbstractExampleItem> examples) {
     // Build the menu and collect redirections
        
        for(AbstractExampleItem example : examples) {
            String exampleId = example.getExampleId();
            if (example instanceof BookExample || example instanceof ExampleCtgr) {
                exampleitems.put(exampleId, (CaptionedExampleItem) example);
                menu.addItem(example.getExampleId());
                menu.getContainerProperty(exampleId, "caption")
                    .setValue(((CaptionedExampleItem) example).getShortName());
                if (example.getParentId() != null) {
                    menu.setParent(exampleId, example.getParentId());
                }
            }
            else if (example instanceof RedirctItem) {
                redirects.put(exampleId, ((RedirctItem) example).redirectid);
            }
        }

        // Expand the menu
        for (AbstractExampleItem example : examples) {
            String exampleId = example.getExampleId();
            if (example.getParentId() == null)
                menu.expandItemsRecursively(exampleId);
            
            if (example.isCollapsed())
                menu.collapseItem(exampleId);
                
            if (menu.getChildren(exampleId) == null)
                menu.setChildrenAllowed(exampleId, false);
        }
        
        // React to fragment changes
        Page.getCurrent().addUriFragmentChangedListener(new UriFragmentChangedListener() {
            private static final long serialVersionUID = -1480152629301855309L;
            
            public void uriFragmentChanged(UriFragmentChangedEvent source) {
                // BookExamplesUI.getLogger().info("URI fragment changed");
                menuSelectByFragment(source.getUriFragment(), redirects);
            }
        });
        
        // Handle the fragment received in the initial request
        menuSelectByFragment(Page.getCurrent().getUriFragment(), redirects);
    }

    private void menuSelectByFragment(String fragment, HashMap<String,String> redirects) {
        if (fragment == null)
            return;
        
        // Handle redirection
        while (redirects.containsKey(fragment))
            fragment = redirects.get(fragment);
        
        menu.setValue(fragment);
        
        // Open the tree nodes leading to the example
        for (Object parent = menu.getParent(fragment);
             parent != null;
             parent = menu.getParent(parent))
            menu.expandItem(parent);
    }
}
