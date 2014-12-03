    package com.vaadin.book.examples.datamodel;

import java.io.File;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

public class FilesystemContainerExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 6337856314788300891L;

    String context;

    public void init (String context) {
        this.context = context;
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.container.filesystemcontainer.basic
        // Find the root folder of the web application
        File folder = VaadinService.getCurrent().getBaseDirectory();

        // Create the file system container 
        FilesystemContainer container = new FilesystemContainer(folder);

        // Create a TreeTable bound to the container
        TreeTable treetable = new TreeTable("Here's my File System");
        treetable.setContainerDataSource(container);
        
        // Set the row header icon by the file type
        treetable.setItemIconPropertyId("Icon");

        // Do not show the Icon column
        treetable.setVisibleColumns(new Object[]{"Name", "Size",
                                                 "Last Modified"});
        // END-EXAMPLE: datamodel.container.filesystemcontainer.basic

        layout.addComponent(treetable);
    }
}
