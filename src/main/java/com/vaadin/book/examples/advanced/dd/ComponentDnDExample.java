package com.vaadin.book.examples.advanced.dd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.ServerSideCriterion;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.DragAndDropWrapper.WrapperTargetDetails;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ComponentDnDExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8301626324227210030L;

    String context;
    
    public void init(String context) {
        this.context = context;

        if ("basic".equals(context))
            basic();
        else if ("absolute".equals(context))
            absolute();
        else if ("resize".equals(context))
            resize();
        else
            setCompositionRoot(new Label("Invalid Context"));
    }
    
    void basic() {
        // BEGIN-EXAMPLE: advanced.dragndrop.component.basic
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("800px");

        // Selection of source components 
        final GridLayout sources = new GridLayout(2, 4);
        sources.setCaption("Drag from Here");
        sources.setWidth("210px");
        layout.addComponent(sources);
        
        // Create some source components to drag
        String planets[] = {"Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet: planets) {
            // Create a component
            Embedded image = new Embedded(null,
                    new ThemeResource("img/planets/" + planet + ".jpg"));
            image.setWidth("100px");
            image.setHeight("100px");
            
            // Wrap it in a Drag and Drop Wrapper
            DragAndDropWrapper wrapper = new DragAndDropWrapper(image);
            wrapper.setSizeUndefined(); // Shrink to fit
            
            // Enable dragging the wrapper
            wrapper.setDragStartMode(DragStartMode.WRAPPER);
            
            sources.addComponent(wrapper);
        }
        
        // Create the drag target
        final Panel target = new Panel("Drag it in here");
        target.setContent(new VerticalLayout());
        ((VerticalLayout)target.getContent()).setMargin(false);
        target.getContent().setSizeFull();
        target.setSizeFull();

        // Wrap it
        DragAndDropWrapper targetWrapper =
            new DragAndDropWrapper(target);
        targetWrapper.setWidth("250px");
        targetWrapper.setHeight("270px");
        
        // Handle drops
        targetWrapper.setDropHandler(new DropHandler() {
            private static final long serialVersionUID = -5709370299130660699L;

            @Override
            public AcceptCriterion getAcceptCriterion() {
                // Accept all drops from anywhere
                return AcceptAll.get();
            }
            
            @Override
            public void drop(DragAndDropEvent event) {
                WrapperTransferable t =
                    (WrapperTransferable) event.getTransferable();
                
                // Get the dragged component (not the wrapper)
                Embedded source = (Embedded) t.getDraggedComponent();

                // Show it somehow in the target
                Embedded copy = new Embedded(null, source.getSource());
                copy.setSizeFull();
                target.setContent(copy);
            }
        });

        // Put the wrapper in the layout
        layout.addComponent(targetWrapper);
        layout.setExpandRatio(targetWrapper, 1.0f);
        layout.setComponentAlignment(targetWrapper, Alignment.MIDDLE_CENTER);

        // Disable drag hints - must be added to containing layout
        layout.addStyleName("no-vertical-drag-hints");
        layout.addStyleName("no-horizontal-drag-hints");
        layout.addStyleName("no-box-drag-hints");
        // END-EXAMPLE: advanced.dragndrop.component.basic
        
        setCompositionRoot(layout);
    }
    
    void component() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);

        // BEGIN-EXAMPLE: advanced.dragndrop.component
        // A place for our draggable component
        VerticalLayout sourceArea = new VerticalLayout();
        layout.addComponent(sourceArea);
        
        // The draggable component
        Panel panelToDrag = new Panel("Panel to Drag");
        panelToDrag.setWidth("200px");
        panelToDrag.setContent(new Label(
                "<h1>Draggable Panel</h1>"+
                "<p>You can drag this panel to any of the "+
                "placeholder locations on right.</p>",
                ContentMode.HTML));
        sourceArea.addComponent(panelToDrag);
        
        
        VerticalLayout targetArea = new VerticalLayout();
        layout.addComponent(targetArea);
        
        //DragAndDropWrapper targetWrapper =
        //    new DragAndDropWrapper(targetArea);
        
        // END-EXAMPLE: advanced.dragndrop.component

        setCompositionRoot(layout);
    }

    void absolute() {
        // BEGIN-EXAMPLE: advanced.dragndrop.component.absolute
        // A layout that allows moving its contained components
        // by dragging and dropping them
        final AbsoluteLayout absLayout = new AbsoluteLayout();
        absLayout.setWidth("600px");
        absLayout.setHeight("400px");
        
        // Have a component to drag
        final Button button = new Button("An Absolute Button");
        button.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = -3420821329748965552L;

            public void buttonClick(ClickEvent event) {
                Notification.show("Click!");
            }
        });
        
        // Put the component in a D&D wrapper and allow dragging it
        final DragAndDropWrapper buttWrap = new DragAndDropWrapper(button);
        buttWrap.setDragStartMode(DragStartMode.WRAPPER);

        // Set the wrapper to wrap tightly around the component
        buttWrap.setSizeUndefined();

        // Add the wrapper, not the component, to the layout
        absLayout.addComponent(buttWrap, "left: 50px; top: 50px;");

        // Have another component to drag
        final TextField text = new TextField("This Can Not Be Seen");
        final DragAndDropWrapper textWrap = new DragAndDropWrapper(text);
        textWrap.setCaption("I Have a Caption!");
        textWrap.setDragStartMode(DragStartMode.WRAPPER);
        textWrap.setSizeUndefined();
        absLayout.addComponent(textWrap, "left: 250px; top: 80px;");

        // A text field put in a layout that manages its caption
        final TextField tf2 = new TextField("Sublayout Managed Caption");
        final VerticalLayout tf2Layout = new VerticalLayout();
        tf2Layout.addComponent(tf2);

        // Then wrap the drag and drop wrapper around the layout
        final DragAndDropWrapper tf2Wrap = new DragAndDropWrapper(tf2Layout);
        tf2Wrap.setDragStartMode(DragStartMode.WRAPPER);
        tf2Wrap.setSizeUndefined();
        absLayout.addComponent(tf2Wrap, "left: 300px; top: 300px;");

        // Another component to drag
        final Panel panel = new Panel("Panel to Drag");
        panel.setWidth("200px");
        panel.setContent(new Label(
                "<h1>Draggable Panel</h1>"+
                "<p>You can drag this panel.</p>",
                ContentMode.HTML));
        final DragAndDropWrapper panelWrap = new DragAndDropWrapper(panel);
        panelWrap.setDragStartMode(DragStartMode.WRAPPER);
        panelWrap.setSizeUndefined();
        absLayout.addComponent(panelWrap, "left: 100px; top: 100px;");

        // Wrap the layout to allow handling drops
        DragAndDropWrapper layoutWrapper =
                new DragAndDropWrapper(absLayout);

        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        addStyleName("no-box-drag-hints");

        // Handles drops both on an AbsoluteLayout and
        // on components contained within it
        class MoveHandler implements DropHandler {
            private static final long serialVersionUID = -5709370299130660699L;

            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }

            public void drop(DragAndDropEvent event) {
                WrapperTransferable t =
                    (WrapperTransferable) event.getTransferable();
                WrapperTargetDetails details =
                    (WrapperTargetDetails) event.getTargetDetails();
                
                // Calculate the drag coordinate difference
                int xChange = details.getMouseEvent().getClientX()
                              - t.getMouseDownEvent().getClientX();
                int yChange = details.getMouseEvent().getClientY()
                              - t.getMouseDownEvent().getClientY();

                // Move the component in the absolute layout
                ComponentPosition pos =
                    absLayout.getPosition(t.getSourceComponent());
                pos.setLeftValue(pos.getLeftValue() + xChange);
                pos.setTopValue(pos.getTopValue() + yChange);
            }
        }        

        // Handle moving components within the AbsoluteLayout
        layoutWrapper.setDropHandler(new MoveHandler());
        
        // Handle cases where a component is dropped on another
        // component
        for (Component c: absLayout)
            ((DragAndDropWrapper)c).setDropHandler(new MoveHandler());

        // Put the drag area in a layout that has some extra controls
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(layoutWrapper);

        // Switch between edit mode and drag mode
        final CheckBox moveMode = new CheckBox("Move Mode", true);
        moveMode.setImmediate(true);
        moveMode.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 6067837228547172518L;

            public void valueChange(ValueChangeEvent event) {
                DragStartMode mode = DragStartMode.NONE;
                if (moveMode.getValue().booleanValue())
                    mode = DragStartMode.WRAPPER;
                
                for (Component c: absLayout)
                    if (c instanceof DragAndDropWrapper) {
                        DragAndDropWrapper wrapper = (DragAndDropWrapper) c;
                        wrapper.setDragStartMode(mode);
                    }
            }
        });
        layout.addComponent(moveMode);
        // END-EXAMPLE: advanced.dragndrop.component.absolute
        
        layoutWrapper.setSizeUndefined();
        layoutWrapper.addStyleName("layoutwrapper");
        setSizeFull();
        addStyleName("componentdndexample");

        setCompositionRoot(layout);
    }

    void resize() {
        // BEGIN-EXAMPLE: advanced.dragndrop.component.resize
        // A layout that allows moving its contained components
        // by dragging and dropping them
        final AbsoluteLayout absLayout = new AbsoluteLayout();
        addStyleName("component-resize-example");
        absLayout.setWidth("600px");
        absLayout.setHeight("400px");
        
        // Components to drag
        final Vector<Panel> connectables = new Vector<Panel>();
        int positions[][] = {{25, 25},
                             {50, 200},
                             {300, 250},
                             {450, 200},
                             {300, 100}};
        String someContent[] = {"Hello, World!", "Oranges are usually orange",
                "Here I wrote a little text for you", "Me knows it", "Oh my"};
        for (int i=0; i<5; i++) {
            final Panel c = new Panel("Note " + (i+1));
            connectables.add(c);
            
            final TextField tf = new TextField();
            tf.setValue(someContent[i]);
            tf.setSizeFull();
            c.setContent(tf);

            // Capturing the click events is needed to be able to focus
            // on the text fields. It's not necessary to disable the
            // drag mode in text fields.
            c.addClickListener(new MouseEvents.ClickListener() {
                private static final long serialVersionUID = -1911744712910503523L;

                public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
                    tf.focus();
                }
            });
            
            // Wrap the panel
            final DragAndDropWrapper panelWrap = new DragAndDropWrapper(c);
            panelWrap.setDragStartMode(DragStartMode.WRAPPER);
            panelWrap.setSizeUndefined();
            
            // Put it to random position
            int x = positions[i][0]; 
            int y = positions[i][1]; 
            absLayout.addComponent(panelWrap, "left: "+x+"px; top: "+y+"px;");

            // Set the initial size
            panelWrap.setWidth("200px");
            panelWrap.setHeight("100px");
            c.setSizeFull();
        }

        // Wrap the layout to allow handling drops
        DragAndDropWrapper layoutWrapper =
                new DragAndDropWrapper(absLayout);

        // Make a list of the components
        LinkedList<Component> comps = new LinkedList<Component>();
        for (Component c: absLayout)
            comps.add(c);
        
        // Now the resizing trick: add a resize handle to every component
        ClassResource handleResource = new ClassResource(
            "/com/vaadin/book/examples/advanced/dd/resize-handle.png");
        final HashMap<Component, Embedded> dragHandles =
                new HashMap<Component, Embedded>();
        final HashMap<DragAndDropWrapper, DragAndDropWrapper> componentOfHandles =
                new HashMap<DragAndDropWrapper, DragAndDropWrapper>();
        for (Iterator<Component> i = comps.iterator(); i.hasNext();) {
            DragAndDropWrapper c = (DragAndDropWrapper) i.next();
            //Label handle = new Label("");
            //handle.addStyleName("draggable-resize-handle");
            Embedded handle = new Embedded(null, handleResource);
            handle.setWidth("32px");
            handle.setHeight("32px");
            
            // Bidirectional map between component and handle
            dragHandles.put(c, handle);

            // Position the handle in the lower-right corner of the resizable
            ComponentPosition pos = absLayout.getPosition(c);
            float x = pos.getLeftValue() + c.getWidth() - handle.getWidth();
            float y = pos.getTopValue() + c.getHeight() - handle.getHeight();

            final DragAndDropWrapper handleWrap = new DragAndDropWrapper(handle);
            handleWrap.setDragStartMode(DragStartMode.WRAPPER);
            absLayout.addComponent(handleWrap, "top:" + y + "px; left:" + x + "px;");
            componentOfHandles.put(handleWrap, c);
        }

        addStyleName("no-vertical-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        addStyleName("no-box-drag-hints");
        
        // Handles drops both on an AbsoluteLayout and
        // on components contained within it
        class MoveHandler implements DropHandler {
            private static final long serialVersionUID = -5709370299130660699L;

            public AcceptCriterion getAcceptCriterion() {
                // This server-side criterion handles animation
                // while dragging
                ServerSideCriterion crit = new ServerSideCriterion() {
                    private static final long serialVersionUID = 7708355169207493798L;

                    public boolean accept(DragAndDropEvent dragEvent) {
                        Component src = dragEvent.getTransferable().getSourceComponent();
                        DragAndDropWrapper wrapper = (DragAndDropWrapper) src;
                        Component c = wrapper.iterator().next();
                        BookExamplesUI.getLogger().info("Dragged = " + c.getClass().getName());
                        if (c instanceof Embedded) {
                            // The user is dragging a handle, excellent.
                            WrapperTargetDetails details = (WrapperTargetDetails) dragEvent.getTargetDetails();
                            
                            BookExamplesUI.getLogger().info("Drop target = " + details.getTarget().getClass().getName());
                            
                            // Get the handle position
                            int handlex = details.getMouseEvent().getRelativeX();
                            int handley = details.getMouseEvent().getRelativeY();
                            
                            // Get the component position
                            Component resized = componentOfHandles.get(wrapper);
                            if (resized == null) {
                                BookExamplesUI.getLogger().info("No matching component for resize handle");
                                return true;
                            }

                            ComponentPosition resizedPos = absLayout.getPosition(resized);
                            if (resizedPos == null || resizedPos.getLeftValue() == null || resizedPos.getTopValue() == null) {
                                BookExamplesUI.getLogger().info("No position for resized component " + resized.getClass().getName());
                                return true;
                            }
                            int resizedx = resizedPos.getLeftValue().intValue();
                            int resizedy = resizedPos.getTopValue().intValue();
                            
                            // Calculate new height and width
                            int newheight = handley + 32 - resizedy;
                            int newwidth  = handlex + 32 - resizedx;
                            
                            // Resize it
                            resized.setWidth(newwidth, Unit.PIXELS);
                            resized.setWidth(newheight, Unit.PIXELS);
                            BookExamplesUI.getLogger().info("Resized to " + newwidth + "x" + newheight);
                        }
                        return true;
                    }
                };
                return crit;
            }

            public void drop(DragAndDropEvent event) {
                WrapperTransferable t =
                    (WrapperTransferable) event.getTransferable();
                WrapperTargetDetails details =
                    (WrapperTargetDetails) event.getTargetDetails();
                
                // Calculate the drag coordinate difference
                int xChange = details.getMouseEvent().getClientX()
                              - t.getMouseDownEvent().getClientX();
                int yChange = details.getMouseEvent().getClientY()
                              - t.getMouseDownEvent().getClientY();

                // Move the component in the absolute layout
                ComponentPosition pos =
                    absLayout.getPosition(t.getSourceComponent());
                pos.setLeftValue(pos.getLeftValue() + xChange);
                pos.setTopValue(pos.getTopValue() + yChange);
            }
        }        

        // Handle moving components within the AbsoluteLayout
        layoutWrapper.setDropHandler(new MoveHandler());
        
        // Handle cases where a component is dropped on another
        // component
        for (Component c: absLayout)
            ((DragAndDropWrapper)c).setDropHandler(new MoveHandler());

        // Put the drag area in a layout that has some extra controls
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(layoutWrapper);

        // Switch between edit mode and drag mode
        final CheckBox moveMode = new CheckBox("Move Mode", true);
        moveMode.setImmediate(true);
        moveMode.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = 6067837228547172518L;

            public void valueChange(ValueChangeEvent event) {
                DragStartMode mode = DragStartMode.NONE;
                if (moveMode.getValue().booleanValue())
                    mode = DragStartMode.WRAPPER;
                
                for (Component c: absLayout)
                    if (c instanceof DragAndDropWrapper)
                        ((DragAndDropWrapper) c).setDragStartMode(mode);
            }
        });
        layout.addComponent(moveMode);
        // END-EXAMPLE: advanced.dragndrop.component.resize
        
        layoutWrapper.setSizeUndefined();
        layoutWrapper.addStyleName("layoutwrapper");
        setSizeFull();
        addStyleName("componentdndexample");

        setCompositionRoot(layout);
    }
}
