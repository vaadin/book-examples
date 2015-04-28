package com.vaadin.book.examples.addons.spreadsheet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SpreadsheetManagementExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 6075597050447328626L;

    @SuppressWarnings("unchecked")
    public void upload(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.load.upload
        // BOOK: spreadsheet.using.creating
        class UploadBox extends CustomComponent
              implements Receiver, ProgressListener,
                         FailedListener, SucceededListener {
            private static final long serialVersionUID = -46336015006190050L;

            // Receive upload in this memory buffer that grows automatically
            private ByteArrayOutputStream os =
                new ByteArrayOutputStream(10240);

            private Upload upload = new Upload();

            private String filename;
            
            private ProgressBar progress = new ProgressBar(0.0f);
            
            public UploadBox() {
                upload.setReceiver(this);
                upload.addProgressListener(this);
                upload.addFailedListener(this);
                
                // Put the upload and image display in a panel
                Panel panel = new Panel();
                panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
                HorizontalLayout panelContent = new HorizontalLayout();
                panelContent.setSpacing(true);
                panel.setContent(panelContent);
                panelContent.addComponents(upload, progress);
                panelContent.setComponentAlignment(progress, Alignment.MIDDLE_CENTER);
                
                progress.setVisible(false);
                
                setCompositionRoot(panel);
            }            
            
            public OutputStream receiveUpload(String filename, String mimeType) {
                this.filename = filename;
                os.reset(); // Needed to allow re-uploading
                return os;
            }

            @Override
            public void updateProgress(long readBytes, long contentLength) {
                progress.setVisible(true);
                if (contentLength == -1)
                    progress.setIndeterminate(true);
                else {
                    progress.setIndeterminate(false);
                    progress.setValue(((float)readBytes) /
                                      ((float)contentLength));
                }
            }

            @Override
            public void uploadFailed(FailedEvent event) {
                Notification.show("Upload failed",
                                  Notification.Type.ERROR_MESSAGE);
            }
            
            public byte[] getReceivedContent() {
                return os.toByteArray();
            }

            @Override
            public void uploadSucceeded(SucceededEvent event) {
                progress.setVisible(false);
            }
        }

        // Ultra-simple document-management system
        TreeTable doctree = new TreeTable("Documents");
        doctree.setWidth("700px");
        doctree.setHeight("250px");
        doctree.addContainerProperty("filename", String.class, null, "Name", null, null);
        doctree.addContainerProperty("size", Long.class, 0, "Size", null, null);
        doctree.addContainerProperty("modified", Date.class, null, "Modified", null, null);
        doctree.addContainerProperty("content", ByteBuffer.class, null, "Content", null, null);
        doctree.addGeneratedColumn("download", new ColumnGenerator() {
            private static final long serialVersionUID = 6395928869250163617L;
            
            int resourceCounter = 0; // Make use they change

            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Button download = new Button("Download " + resourceCounter);
                Item item = source.getItem(itemId);
                String resourceId = "" + resourceCounter++ + "-" + ((String) itemId);

                System.out.println("Generatic download for " + resourceId);
                
                ByteBuffer buffer = (ByteBuffer) item.getItemProperty("content").getValue();
                if (buffer == null)
                    return null;
                
                StreamResource resource = new StreamResource(() -> {
                    return new ByteArrayInputStream(buffer.array());
                }, resourceId);

                FileDownloader downloader = new FileDownloader(resource);
                downloader.extend(download);
                
                return download;
            }
        });
        doctree.setVisibleColumns("filename", "size", "modified", "download");
        layout.addComponent(doctree);
        
        Item root = doctree.addItem("root");
        root.getItemProperty("filename").setValue("My Documents");
        doctree.setCollapsed("root", false);
        
        UploadBox uploadbox = new UploadBox();
        uploadbox.upload.addSucceededListener(succeed -> {
            Item newItem = doctree.addItem(uploadbox.filename);
            if (newItem == null) {
                Notification.show("Adding file failed, must not have same filename");
                return;
            }
            
            newItem.getItemProperty("filename").setValue(
                uploadbox.filename);
            newItem.getItemProperty("size").setValue(
                new Long(succeed.getLength()));
            newItem.getItemProperty("modified").setValue(
                new Date());
            newItem.getItemProperty("content").setValue(
                ByteBuffer.wrap(uploadbox.getReceivedContent()));
            doctree.setChildrenAllowed(uploadbox.filename, false);
            doctree.setParent(uploadbox.filename, "root");
        });
        layout.addComponent(uploadbox);
        
        class SheetEditor extends CustomComponent {
            private static final long serialVersionUID = 1L;
            Spreadsheet sheet = null;
            Object itemId;
            Panel sheetPanel = new Panel();
            
            public SheetEditor(Consumer<SheetEditor> saveListener) {
                setWidth("700px");
                setHeight("300px");
                
                sheetPanel.setSizeFull();
                sheetPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);

                Button save = new Button("Save", click ->
                    saveListener.accept(this));

                VerticalLayout layout = new VerticalLayout(sheetPanel, save);
                layout.setExpandRatio(sheetPanel, 1.0f);
                layout.setSizeFull();
                setCompositionRoot(layout);
            }
            
            public void open(Object itemId, byte[] content) throws IOException {
                this.itemId = itemId;
                sheet = new Spreadsheet(new ByteArrayInputStream(content));
                sheet.setSizeFull();
                sheetPanel.setContent(sheet);
            }
            
            public ByteBuffer getContent() throws IOException {
                ByteArrayOutputStream outs = new ByteArrayOutputStream();
                sheet.write(outs);
                return ByteBuffer.wrap(outs.toByteArray());
            }
            
            public Object getItemId() {
                return itemId;
            }
        }
        
        SheetEditor sheetEditor = new SheetEditor(editor -> {
            try {
                ByteBuffer content = editor.getContent();
                Item editedItem = doctree.getItem(editor.getItemId());
                editedItem.getItemProperty("size").setValue(
                    new Long(content.array().length));
                editedItem.getItemProperty("modified").setValue(
                    new Date());
                editedItem.getItemProperty("content").setValue(
                    content);
                Notification.show("Modifications saved");
                editor.setVisible(false);
                uploadbox.setVisible(true);
                doctree.refreshRowCache();
            } catch (Exception e) {
                Notification.show("Saving file failed");
            }
        });
        sheetEditor.setVisible(false);
        layout.addComponent(sheetEditor);
        
        doctree.addItemClickListener(click -> {
            Item editItem = click.getItem();
            if (editItem.getItemProperty("content").getValue() == null) {
                Notification.show("Not editable item");
                return;
            }
            
            // Get the spreadsheet content from the container
            byte[] content = ((ByteBuffer) click.getItem().
                getItemProperty("content").getValue()).array();
            
            // Create the spreadsheet
            try {
                sheetEditor.open(click.getItemId(), content);
                sheetEditor.setVisible(true);
                uploadbox.setVisible(false);
            } catch (Exception e) {
                Notification.show("Opening file failed");
                uploadbox.setVisible(true);
                sheetEditor.setVisible(false);
            }
        });
        loadSaveWithPOI();
        loadSaveWithVaadinSpreadsheet();
        // END-EXAMPLE: spreadsheet.load.upload
    }
    
    String inputFilename = "/home/magi/spreadsheet-test-1.xlsx";
    String outputFilenamePOI = "/home/magi/spreadsheet-test-1-poi.xlsx";
    String outputFilenameSS = "/home/magi/spreadsheet-test-1-ss.xlsx";
    
    private void loadSaveWithPOI() {
        try {
            //filename : libreOffice xlsx document

            System.err.println("Loading with POI");
            FileInputStream fis = new FileInputStream(inputFilename);
            XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis);
            fis.close();

            //dont make any modification

            FileOutputStream fos = new FileOutputStream(outputFilenamePOI);
            workbook.write(fos);
            fos.close();

            //no file format corruption. can be reopened with vaadin spreadsheet

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        System.err.println("Loaded with POI");
    }


    private void loadSaveWithVaadinSpreadsheet() {
        try {
            //filename : libreOffice xlsx document

            System.err.println("Loading with SS");
            Spreadsheet sheet = new Spreadsheet(new FileInputStream(new File(inputFilename)));

            //dont make any modification

            sheet.write(outputFilenameSS);

            //file Format gets corrupted after saving. can not be reopened with vaadin spreadsheet

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        System.err.println("Loaded with SS");
    }    
}
