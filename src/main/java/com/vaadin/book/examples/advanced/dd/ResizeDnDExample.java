package com.vaadin.book.examples.advanced.dd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.DragAndDropWrapper.WrapperTargetDetails;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ResizeDnDExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -8301626324227210030L;

    String context;
    
    public void init(String context) {
        this.context = context;
    }
    
    @Override
    public void attach() {
        if ("diagram".equals(context))
            diagram();
        else
            setCompositionRoot(new Label("Invalid Context"));
    }

    // BEGIN-EXAMPLE: advanced.dragndrop.component.diagram
    /** A StreamResource that draws connector images. */
    class ConnectorSource implements StreamResource.StreamSource {
        private static final long serialVersionUID = -5110230700707238367L;

        ByteArrayOutputStream imagebuffer = null;
        
        public class Connector {
            Component source;
            Component target;
            int x1, y1, x2, y2;
            
            public Connector(Component source, Component target,
                             int x1, int y1, int x2, int y2) {
                this.source = source;
                this.target = target;
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            }
            
            public void setSourcePos(int x1, int y1) {
                this.x1 = x1;
                this.y1 = y1;
            }

            public void setTargetPos(int x2, int y2) {
                this.x2 = x2;
                this.y2 = y2;
            }
        }
        
        HashSet<Connector> connectors = new HashSet<Connector>();
        
        public void setConnector(Component source, Component target, int x1, int y1, int x2, int y2) {
            // See if it's already there
            Connector c = findConnector(source, target);
            if (c != null) {
                c.x1 = x1;
                c.y1 = y1;
                c.x2 = x2;
                c.y2 = y2;
            } else
                connectors.add(new Connector(source, target, x1, y1, x2, y2));
        }

        public void removeConnector(Component source, Component target) {
            Connector c = findConnector(source, target);
            if (c != null)
                connectors.remove(c);
        }
        
        public Connector findConnector(Component source, Component target) {
            for (Iterator<Connector> i=connectors.iterator(); i.hasNext();) {
                Connector c = i.next();
                if (c.source == source && c.target == target)
                    return c;
            }
            return null;
        }

        public InputStream getStream() {
            BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);

            // Java uses the native drawing functionality of the operating system
            // to draw images using the Graphics class.
            // On Linux server systems that do not have X11 installed, you need to
            // give "-Djava.awt.headless=true" for the application server.
            Graphics drawable = image.getGraphics();
            
            // Background
            drawable.setColor(Color.WHITE);
            drawable.fillRect(0, 0, image.getWidth()-1, image.getHeight()-1);
            
            // Connector lines
            drawable.setColor(Color.BLACK);
            for (Iterator<Connector> i=connectors.iterator(); i.hasNext();) {
                Connector c = i.next();
                drawable.drawLine(c.x1, c.y1, c.x2, c.y2);
            }
            
            try {
                imagebuffer = new ByteArrayOutputStream();
                ImageIO.write(image, "png", imagebuffer);
                return new ByteArrayInputStream(imagebuffer.toByteArray());
            } catch (IOException e) {
                return null;
            }
        }
    }

    // An Embedded graphics canvas that displays the connector image
    Embedded canvas;
    
    // The connector image resource
    StreamResource connectorResource; 
    
    void diagram() {
        // A layout that allows moving its contained components
        // by dragging and dropping them
        final AbsoluteLayout absLayout = new AbsoluteLayout();
        absLayout.setWidth("600px");
        absLayout.setHeight("400px");

        // Image generator for the connectors
        final ConnectorSource connectors = new ConnectorSource();
        connectorResource = new StreamResource(connectors,
                                               makeImageFilename());
        
        // A connector canvas that is beneath everything else
        canvas = new Embedded(null, connectorResource);
        canvas.setWidth("600px");
        canvas.setHeight("400px");
        absLayout.addComponent(canvas, "left: 0px; top: 0px;");

        /** We need to extend the draggable components a bit to store
         *  the coordinates, as they would otherwise be known only by
         *  the AbsoluteLayout. It's also useful to store the dimensions
         *  here.
         */
        class Connectable extends Panel {
            private static final long serialVersionUID = -8061204646531821747L;

            int x, y, width, height;
            
            public Connectable(String caption) {
                super(caption);
            }
            
            public void setSize(int x, int y, int width, int height) {
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                
                setWidth(width + "px");
                setHeight(height + "px");
            }
            
            public void moveTo(int x, int y) {
                this.x = x;
                this.y = y;
            }
            
            int getCenterX() {
                return x+width/2;
            }
            
            int getCenterY() {
                return y+height/2;
            }
        }
        
        // Components to drag
        final Vector<Connectable> connectables = new Vector<Connectable>();
        int positions[][] = {{25, 25},
                             {50, 200},
                             {300, 250},
                             {450, 200},
                             {300, 100}};
        String someContent[] = {"Hello, World!", "Oranges are usually orange",
                "Here I wrote a little text for you", "Me knows it", "Oh my"};
        for (int i=0; i<5; i++) {
            final Connectable c = new Connectable("Note " + (i+1));
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

                public void click(ClickEvent event) {
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
            
            // Set position and size to the component as well (100x100 boxes)
            c.setSize(x, y, 100, 100);
        }

        // Add some connectors
        for (int i = 0; i<8; i++) {
            int src = (int) (Math.random() * 5);
            int trg = (int) (Math.random() * 5);
            if (src != trg)
                connectors.setConnector(connectables.get(src), connectables.get(trg),
                        connectables.get(src).getCenterX(), connectables.get(src).getCenterY(), 
                        connectables.get(trg).getCenterX(), connectables.get(trg).getCenterY()); 
        }
        
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
                
                // Store the new position also in the component
                Connectable c = (Connectable) ((DragAndDropWrapper)t.getSourceComponent()).iterator().next();
                c.moveTo(pos.getLeftValue().intValue(),
                         pos.getTopValue().intValue());
                
                // Change the connections
                for (Iterator<Connectable> i = connectables.iterator(); i.hasNext();) {
                    Connectable other = i.next();
                    
                    // Check for existing connections both ways and if one exists, change it
                    ConnectorSource.Connector con;
                    con = connectors.findConnector(c, other);
                    if (con != null)
                        con.setSourcePos(c.getCenterX(), c.getCenterY());
                    con = connectors.findConnector(other, c);
                    if (con != null)
                        con.setTargetPos(c.getCenterX(), c.getCenterY());
                }
                
                updateConnectors(true);
            }
        }        

        // Handle moving components within the AbsoluteLayout
        layoutWrapper.setDropHandler(new MoveHandler());
        
        // Handle cases where a component is dropped on another
        // component
        for (Iterator<Component> i =
                 absLayout.getComponentIterator(); i.hasNext();) {
            Component c = i.next();
            if (c instanceof DragAndDropWrapper)
                ((DragAndDropWrapper)c).setDropHandler(new MoveHandler());
        }

        // Put the drag area in a layout that has some extra controls
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(layoutWrapper);

        layout.addComponent(new Label("Click on the boxes to edit the content and drag to move them"));
        
        layoutWrapper.setSizeUndefined();
        layoutWrapper.addStyleName("layoutwrapper");
        setSizeFull();
        addStyleName("componentdndexample");

        setCompositionRoot(layout);
    }

    public void updateConnectors(boolean updateAll) {
        // WARNING: This doesn't seem to have any effect alone. (#2470)
        canvas.markAsDirty();
        
        // This is currently the best working way to refresh an image.
        // We have to add a timestamp to the image to prevent annoying
        // flicker (#2471) from previous images cached by the browser.
        // The problem is that this creates a lot of images in the
        // browser's cache.
        connectorResource.setFilename(makeImageFilename());
    }
    
    /**
     * Creates a PNG image file name with a timestamp.
     */
    protected String makeImageFilename() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = df.format(new Date());
        return "connectors-" + timestamp + ".png";
    }
    // END-EXAMPLE: advanced.dragndrop.component.diagram
}
