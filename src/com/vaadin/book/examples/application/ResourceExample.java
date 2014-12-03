package com.vaadin.book.examples.application;

import java.io.File;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

public class ResourceExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 2193412887830271569L;
    
    String context;
    VerticalLayout layout = new VerticalLayout();

    public void init (String context) {
        this.context = context;
        setCompositionRoot(layout);
    }
    
    @Override
    public void attach() {
        super.attach();

        if ("fileresource".equals(context))
            fileresource(layout);
        else if ("classresource".equals(context))
            classresource(layout);
        else if ("themeresource".equals(context))
            themeresource(layout);
        else if ("local".equals(context))
            local(layout);
        else if ("relative".equals(context))
            relative(layout);
        else if ("streamresource-basic".equals(context))
            streamresource_basic(layout);
        else if ("streamresource-download".equals(context))
            streamresource_download(layout);
    }
    
    public static String classresourceDescription =
        "<h1>Class Resources</h1>"+
        "<p>Class resources are loaded by a class loader. Typical use is images "+
        "stored in the source tree, which are copied to <tt>WEB-INF/classes/path</tt> "+
        "during compilation.</p> <p>The example image below is of the particular "+
        "package folder in Eclipse.</p>";
    void classresource(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.classresource
        // A class resource in a sub-package. The full package name
        // must be given as a file path with slashes.
        ClassResource resource = new ClassResource(
            "/com/vaadin/book/examples/application/images/image.png");

        Embedded image = new Embedded("My Classy Image", resource);
        // END-EXAMPLE: application.resources.classresource

        layout.addComponent(image);
    }

    void classref(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.classresource.class-ref
        // A class resource in a sub-package. The full package name
        // must be given as a file path with slashes.
        ClassResource resource = new ClassResource(
            "/com/vaadin/book/examples/application/images/image.png");

        // TODO - This used the obsolete method - how to do it now?
        Label label = new Label("Here the image is in the <img src='" +
                "" + // How to get the URL???
                "'/> of this text", ContentMode.HTML);
        // END-EXAMPLE: application.resources.classresource.class-ref
    
        layout.addComponent(label);
    }
    
    public static String themeresourceDescription =
        "<h1>Theme Resources</h1>"+
        "<p>Theme resources are loaded from the current theme. The load path " +
        "is relative to the root of the theme directory. In this example, " +
        "it would be <tt>VAADIN/themes/book-examples</tt>.</p>";
    void themeresource(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.themeresource
        // A theme resource in the current theme ("book-examples")
        // Located in: VAADIN/themes/book-examples/img/themeimage.png
        ThemeResource resource = new ThemeResource("img/themeimage.png");

        Embedded image = new Embedded("My Theme Image", resource);
        // END-EXAMPLE: application.resources.themeresource

        layout.addComponent(image);
    }
    
    public static String fileresourceDescription =
        "<h1>FileResource</h1>"+
        "<p>File resources can be located anywhere in the server. "+
        "They are served by the application servlet, so the application "+
        "object must be given as a parameter.</p>";
    void fileresource(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.fileresource
        // FORUM: http://vaadin.com/forum/-/message_boards/message/252238
        // Find the application directory
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        layout.addComponent(new Label("Base Path: " + basepath));

        // Image as a file resource
        FileResource resource = new FileResource(new File(basepath +
                                "/WEB-INF/images/image.png"));

        // Show the image in the application
        Embedded image = new Embedded("Image from file", resource);
        
        // Let the user view the file in browser or download it
        Link link = new Link("Link to the image file", resource);
        // END-EXAMPLE: application.resources.fileresource

        layout.addComponent(image);
        layout.addComponent(link);
        layout.setSpacing(true);
    }

    public static String localDescription =
        "<h1>Local Statically Served External Resources</h1>"+
        "<p><i>External resources</i> are resources referenced with a URL. "+
        "They can be located anywhere in the Internet or in the same server.</p>"+
        "<p>In this example, we have a static image resource that is located "+
        "in the same server, under the same application, but in a different path. "+
        "In order to access images in the same application, the servlet path "+
        "defined in a mapping in <tt>web.xml</tt> must be a sub-path, "+
        "such as <tt>/book-examples/*</tt>, not <tt>/*</tt> (you also need a "+
        "mapping for the /VAADIN/* context).</p>";

    void local(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.externalresource.local
        // FORUM: http://vaadin.com/forum/-/message_boards/message/252238
        ExternalResource resource =
            new ExternalResource(BookExamplesUI.APPCONTEXT + "/images/image.png");
        
        Embedded image = new Embedded("External Image", resource);
        
        Link link = new Link("Link to the image " + resource.getURL(), resource);
        link.setTargetName("_new");
        // END-EXAMPLE: application.resources.externalresource.local

        layout.addComponent(image);
        layout.addComponent(link);
    }

    public static String relativeDescription =
        "<p>A relative path for an external resource is relative to the " +
        "entered URL. For example, if the application context "+
        "is <tt>book-examples</tt>, the application path is <tt>/book-examples/book</tt> "+
        "and the resource path is <tt>images/image.png</tt>, the absolute path is " +
        "<tt>/book-examples/images/image.png</tt>. To serve such an URI, "+
        "the application URL defined in <tt>web.xml</tt> must be below the "+
        "context, such as <tt>/book/*</tt> (not <tt>/*</tt>), so that the "+
        "absolute URI for the application is <tt>/book-examples/book</tt>.</p>"+
        "<p><b>Warning:</b> <i><font size='+1'>Do not use relative paths for external resources</font>, as the 'current' URL path may be entered in different ways.</i></p>";

    void relative(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.externalresource.relative
        // FORUM: http://vaadin.com/forum/-/message_boards/message/221760
        // An external resource with a relative path
        ExternalResource resource = new ExternalResource("images/image.png");
        
        // Show the resource as an image
        Embedded image = new Embedded("External Image", resource);

        // Show also the image URL as a link
        Link link = new Link("Link to the image " + resource.getURL(), resource);
        link.setTargetName("_new");
        // END-EXAMPLE: application.resources.externalresource.relative

        layout.addComponent(image);
        layout.addComponent(link);
    }
    
    
    void streamresource_basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.externalresource.relative
    }

    void streamresource_download(VerticalLayout layout) {
        // BEGIN-EXAMPLE: application.resources.externalresource.relative
    }
}
