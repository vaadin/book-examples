package com.vaadin.book.examples.advanced;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import com.vaadin.annotations.Theme;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PrintingExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;

    public void init(String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("this".equals(context))
            printThisPage();
        else if ("open".equals(context))
            printOpenedPage();
        else if ("nonblocking".equals(context))
            printNonblockingPage();
        else if ("pdfgeneration".equals(context))
            pdfgeneration(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
    }
    
    void printThisPage () {
        // BEGIN-EXAMPLE: advanced.printing.this
        Button print = new Button("Print This Page");
        print.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 15335453452L;

            public void buttonClick(ClickEvent event) {
                // Print the current page
                JavaScript.getCurrent().execute("print();");
            }
        });
        // END-EXAMPLE: advanced.printing.this
        
        setCompositionRoot(print);
    }
    
    @Theme("valo")
    // BEGIN-EXAMPLE: advanced.printing.open
    public static class PrintUI extends UI {
        private static final long serialVersionUID = -4265213983602980250L;
    
        @Override
        protected void init(VaadinRequest request) {
            // Have some content to print
            setContent(new Label(
                "<h1>Here's some dynamic content</h1>\n" +
                "<p>This is to be printed.</p>",
                ContentMode.HTML));
            
            // Print automatically when the window opens
            JavaScript.getCurrent().execute(
                "setTimeout(function() {" +
                "  print(); self.close();}, 2000);");
        }
    }

    void printOpenedPage () {
        // Create an opener extension
        BrowserWindowOpener opener =
                new BrowserWindowOpener(PrintUI.class);
        opener.setFeatures("height=200,width=400,resizable");
        
        // A button to open the printer-friendly page.
        Button print = new Button("Click to Print");
        opener.extend(print);
        // END-EXAMPLE: advanced.printing.open
        
        setCompositionRoot(print);
    }

    // TODO: This actually blocks also.
    void printNonblockingPage () {
        // A button to open the printer-friendly page.
        final Button print = new Button("Click to Print");

        print.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 349852897523897L;

            public void buttonClick(ClickEvent event) {
                // Content to be printed. Must double-quote newlines.
                String content = "<h1>Stuff to Print</h1>\\n" +
                                 "<p>Important stuff</p>\\n";

                // The code to print and close the window
                content += "<SCRIPT language=\"JavaScript\">" +
                           "  print();" +
                           "  close();" +
                           "</SCRIPT>";
                
                // Open the print window
                String js = "popup = window.open('', 'mywindow','status=1,width=350,height=150');\n" +
                            "popup.document.write('"+content+"');\n";
                JavaScript.getCurrent().execute(js);
            }
        });
        
        setCompositionRoot(print);
    }
    
    public final static String pdfgenerationDescription =
        "<h1>Generating a Printable PDF</h1>" +
        "<p>You can generate a PDF file dynamically using a <b>StreamResource</b>. The following example does it using the Apache FOP.</p>";

    // BEGIN-EXAMPLE: advanced.printing.pdfgeneration
    /** Generates the PDF dynamically when requested by HTTP. */
    class MyPdfSource implements StreamSource {
        private static final long serialVersionUID = 6580720404794033932L;

        String name; // A trivial content data model
        
        /** Constructor gets a content data model as parameter */
        public MyPdfSource(String name) {
            this.name = name;
        }
        
        @Override
        public InputStream getStream() {
            // Generate the FO content. You could use the Java DOM API
            // here as well and pass the DOM to the transformer.
            String fo = "<?xml version='1.0' encoding='ISO-8859-1'?>\n"+
            "<fo:root xmlns:fo='http://www.w3.org/1999/XSL/Format'>\n"+
            "<fo:layout-master-set>"+
            "  <fo:simple-page-master master-name='A4' margin='2cm'>"+
            "    <fo:region-body />"+
            "  </fo:simple-page-master>"+
            "</fo:layout-master-set>"+
            "<fo:page-sequence master-reference='A4'>"+
            "    <fo:flow flow-name='xsl-region-body'>"+
            "    <fo:block text-align='center'>"+
            "Hello There, "+ name + "!</fo:block>"+
            "  </fo:flow>"+
            "</fo:page-sequence>"+
            "</fo:root>\n";
            ByteArrayInputStream foStream =
                new ByteArrayInputStream(fo.getBytes());
            
            // Basic FOP configuration. You could create this object
            // just once and keep it.
            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setStrictValidation(false); // For an example
            
            // Configuration for this PDF document - mainly metadata
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            userAgent.setProducer("My Vaadin Application");
            userAgent.setCreator("Me, Myself and I");
            userAgent.setAuthor("Da Author");
            userAgent.setCreationDate(new Date());
            userAgent.setTitle("Hello to " + name);
            userAgent.setKeywords("PDF Vaadin example");
            userAgent.setTargetResolution(300); // DPI

            // Transform to PDF
            ByteArrayOutputStream fopOut = new ByteArrayOutputStream();
            try {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
                                            userAgent, fopOut);
                TransformerFactory factory =
                    TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                Source src = new
                    javax.xml.transform.stream.StreamSource(foStream);
                Result res = new SAXResult(fop.getDefaultHandler());
                transformer.transform(src, res);
                fopOut.close();
                return new ByteArrayInputStream(fopOut.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
        }
    }
    
    void pdfgeneration(VerticalLayout layout) {
        // A user interface for a (trivial) data model from which
        // the PDF is generated
        final TextField name = new TextField("Name");
        name.setValue("Slartibartfast");

        // This has to be clicked first to create the
        // stream resource
        final Button ok = new Button("OK");
        
        // This actually opens the stream resource
        final Button print = new Button("Open PDF");
        print.setEnabled(false);
        
        ok.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -2989367937320174397L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Create the PDF source and pass the data model to it
                StreamSource source =
                    new MyPdfSource((String) name.getValue());
                
                // Create the stream resource and give it a file name
                String filename = "pdf_printing_example.pdf";
                StreamResource resource =
                        new StreamResource(source, filename);
                
                // These settings are not usually necessary. MIME type
                // is detected automatically from the file name, but
                // setting it explicitly may be necessary if the file
                // suffix is not ".pdf".
                resource.setMIMEType("application/pdf");
                resource.getStream().setParameter("Content-Disposition",
                        "attachment; filename="+filename);

                // Extend the print button with an opener
                // for the PDF resource
                BrowserWindowOpener opener =
                        new BrowserWindowOpener(resource);
                opener.extend(print);
              
                name.setEnabled(false);
                ok.setEnabled(false);
                print.setEnabled(true);
            }
        });

        // Re-enable editing after printing
        print.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -5413419737626607326L;

            @Override
            public void buttonClick(ClickEvent event) {
                name.setEnabled(true);
                ok.setEnabled(true);
                print.setEnabled(false);
            }
        });
        
        layout.addComponent(name);
        layout.addComponent(ok);
        layout.addComponent(print);
    }
    // END-EXAMPLE: advanced.printing.pdfgeneration
}
