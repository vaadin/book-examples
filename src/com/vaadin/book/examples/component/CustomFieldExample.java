package com.vaadin.book.examples.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.ChangeEvent;
import com.vaadin.ui.Upload.ChangeListener;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class CustomFieldExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -2893838661604268626L;
    
    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("complex".equals(context))
            complex(layout);
        else if ("calendar".equals(context))
            calendar(layout);
        else if ("imagefield".equals(context))
            imagefield(layout);
        else
            layout.addComponent(new Label("Invalid context"));
        setCompositionRoot(layout);
    }
  
    // BEGIN-EXAMPLE: component.customfield.basic
    public class BooleanField extends CustomField<Boolean> {
        private static final long serialVersionUID = 8869338016971784657L;

        Button button = new Button();
        
        @Override
        protected Component initContent() {
            // Flip the field value on click
            button.addClickListener(event -> // Java 8
                    setValue(! (Boolean) getValue()));

            return new VerticalLayout(
                new Label("Click the button"), button);
        }

        @Override
        public Class<Boolean> getType() {
            return Boolean.class;
        }
        
        @Override
        public void setValue(Boolean newFieldValue)
            throws com.vaadin.data.Property.ReadOnlyException,
            ConversionException {
            button.setCaption(newFieldValue? "On" : "Off");
            super.setValue(newFieldValue);
        }
    }
  
    void basic(VerticalLayout layout) {
        BooleanField field = new BooleanField();
        field.focus();
        
        // It's a field so we can set its value
        field.setValue(new Boolean(true));
        
        // ...and read the value
        Label value = new Label();
        value.setValue(field.getValue()?
                "Initially on" : "Initially off");
        
        // ...and handle value changes
        field.addValueChangeListener(event -> // Java 8
                value.setValue(field.getValue()?
                        "It's now on" : "It's now off"));
        
        layout.addComponents(field, value);
        // END-EXAMPLE: component.customfield.basic
        
        layout.setSpacing(true);
    }
    
    /** Have some complex type **/
    public class Complex implements Serializable {
        private static final long serialVersionUID = 2530296188068983536L;

        double real;
        double imag;
        
        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }
        
        public double getReal() {
            return real;
        }
        
        public double getImag() {
            return imag;
        }
    }

    // BEGIN-EXAMPLE: component.customfield.complex
    class ComplexField extends CustomField<Complex> {
        private static final long serialVersionUID = 2653495643907891234L;

        TextField real = new TextField();
        TextField imag = new TextField();

        @Override
        protected Component initContent() {
            HorizontalLayout content = new HorizontalLayout();
            content.addComponent(real);
            content.addComponent(new Label(" + "));
            content.addComponent(imag);
            content.addComponent(new Label("i"));
            
            // Handle editing either part of the value
            ValueChangeListener listener =
                    new ValueChangeListener() {
                private static final long serialVersionUID = 7342288705079773186L;

                @Override
                public void valueChange(
                    Property.ValueChangeEvent event) {
                    Complex newValue = new Complex(
                        Double.parseDouble(real.getValue()),
                        Double.parseDouble(imag.getValue()));

                    setValue(newValue);
                }
            };
            real.addValueChangeListener(listener);
            
            return content;
        }

        @Override
        public Class<? extends Complex> getType() {
            return Complex.class;
        }
        
        @Override
        public void focus() {
            real.focus();
        }
    }

    void complex(VerticalLayout layout) {
        ComplexField field = new ComplexField();
        
        // It's a field so we can set its value
        field.setValue(new Complex(2.0, 3.0));
        
        field.focus();
        layout.addComponent(field);
        // END-EXAMPLE: component.customfield.complex
    }
    
    // BEGIN-EXAMPLE: component.customfield.calendar
    class QuickCalendar extends CustomField<Date> {
        private static final long serialVersionUID = -1184991761711858405L;
        
        NativeSelect year; 
        NativeSelect month;
        InlineDateField df;
        
        Date date;
        
        public QuickCalendar(Date date) {
            this.date = date;

            // Year box
            year = new NativeSelect("Year");
            for (int i=2014; i >= 1900; i--)
                year.addItem(new Integer(i));
            year.setNullSelectionAllowed(false);

            // Month box
            month = new NativeSelect("Month");
            for (int i=1; i<=12; i++)
                month.addItem(new Integer(i));
            month.setNullSelectionAllowed(false);

            // Calendar box
            df = new InlineDateField("Day");
            df.setValue(date);
            df.setResolution(Resolution.DAY);
            df.addStyleName("no-year-or-month");
            df.setImmediate(true);
        }

        @Override
        public Class<Date> getType() {
            return Date.class;
        }
        
        @Override
        public void focus() {
            year.focus();
        }

        @Override
        protected Component initContent() {
            FormLayout layout = new FormLayout();
            layout.addComponents(year, month, df);
         
            // Date in text format
            final Label value = new Label();
            value.setCaption("Date");
            value.setPropertyDataSource(df);
            layout.addComponent(value);

            // Set the initial year and month
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            year.select(new Integer(cal.get(Calendar.YEAR)));
            month.select(new Integer(cal.get(Calendar.MONTH) + 1));
            
            year.addValueChangeListener(new ValueChangeListener() {
                private static final long serialVersionUID = -524636182634311831L;

                public void valueChange(Property.ValueChangeEvent event) {
                    int newyear = (Integer) year.getValue();
                    Date current = df.getValue();
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(current);
                    cal.set(newyear,
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE));
                    df.setValue(cal.getTime());
                }
            });
            year.setImmediate(true);

            month.addValueChangeListener(new ValueChangeListener() {
                private static final long serialVersionUID = -8778775984954765735L;

                public void valueChange(Property.ValueChangeEvent event) {
                    int newmonth = (Integer) month.getValue();
                    Date current = df.getValue();
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(current);
                    cal.set(cal.get(Calendar.YEAR),
                            newmonth - 1,
                            cal.get(Calendar.DATE));
                    df.setValue(cal.getTime());
                    
                    // TODO Changing to a month with less days than
                    // previous month can cause date to wrap to next
                    // month, which leads to invalid month selection.
                }
            });
            month.setImmediate(true);
            
            return layout;
        }
    }
    
    void calendar(VerticalLayout layout) {
        QuickCalendar calendar = new QuickCalendar(new Date());
        calendar.focus();
        layout.addComponent(calendar);
        // END-EXAMPLE: component.customfield.calendar
    }


    public void imagefield(VerticalLayout vlayout) { 
        // BEGIN-EXAMPLE: component.customfield.imagefield
        class ImageData implements Serializable {
            private static final long serialVersionUID = 7590792385834419260L;

            private String filename;
            private String mimetype;
            private byte[] imagedata;
            
            public ImageData(String filename, String mimetype, byte[] imagedata) {
                this.filename = filename;
                this.mimetype = mimetype;
                this.imagedata = imagedata;
            }
            
            public String getFilename() {
                return filename;
            }
            public String getMimetype() {
                return mimetype;
            }
            public byte[] getImagedata() {
                return imagedata;
            }
        }
        
        class EditableImage extends CustomField<ImageData>
            implements Receiver, ProgressListener,
                       FailedListener, SucceededListener {
            private static final long serialVersionUID = -4520882923454628875L;

            // Put upload in this memory buffer that grows automatically
            ByteArrayOutputStream os =
                new ByteArrayOutputStream(10240);

            VerticalLayout content;
            Image image;
            Upload upload;
            ProgressBar progress = new ProgressBar(0.0f);
            
            ImageData newimage = null;
            
            public EditableImage(String caption, ImageData imagedata) {
                setCaption(caption);

                content = new VerticalLayout();
                image = new Image();
                content.addComponent(image);

                // These also set the image so can't call before it's set
                setValue(imagedata);
                setInternalValue(imagedata);
                
                content.addComponent(progress);
                progress.setVisible(false);
                
                // Create the upload component and handle all its events
                upload = new Upload("Upload new image here", this);
                upload.setButtonCaption(null);
                upload.addProgressListener(this);
                upload.addFailedListener(this);
                upload.addSucceededListener(this);
                content.addComponent(upload);
                
                upload.addChangeListener(new ChangeListener() {
                    private static final long serialVersionUID = 8712035630286804938L;

                    @Override
                    public void filenameChanged(ChangeEvent event) {
                        upload.submitUpload();
                    }
                });
            }
            
            /* This should be static method */
            StreamSource createStreamSource(final ImageData imageData) {
                return new StreamSource() {
                    private static final long serialVersionUID = -4905654404647215809L;

                    public InputStream getStream() {
                        return new ByteArrayInputStream(imageData.getImagedata());
                    }
                };
            }
            
            protected void setInternalValue(final ImageData newValue) {
                super.setInternalValue(newValue);
                
                if (newValue == null)
                    return;
                
                StreamSource source = createStreamSource(newValue);

                if (image.getSource() == null)
                    // Create a new stream resource
                    image.setSource(new StreamResource(source, newValue.getFilename()));
                else { // Reuse the old resource
                    StreamResource resource =
                            (StreamResource) image.getSource();
                    resource.setStreamSource(source);
                    resource.setFilename(newValue.getFilename());
                }

                image.setVisible(true);
                image.markAsDirty();
            }
            
            @Override
            public void setReadOnly(boolean readOnly) {
                super.setReadOnly(readOnly);
                
                upload.setVisible(!readOnly);
            }
            
            @Override
            protected Component initContent() {
                return content;
            }

            @Override
            public Class<? extends ImageData> getType() {
                return ImageData.class;
            }

            public OutputStream receiveUpload(String filename, String mimeType) {
                newimage = new ImageData(filename, mimeType, null);
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

            public void uploadSucceeded(SucceededEvent event) {
                newimage.imagedata = os.toByteArray();
                setValue(newimage);
                progress.setVisible(false);
            }

            @Override
            public void uploadFailed(FailedEvent event) {
                Notification.show("Upload failed",
                                  Notification.Type.ERROR_MESSAGE);
            }
        }

        // Load an initial image
        File baseDir = VaadinService.getCurrent().getBaseDirectory();
        Path initialpath = Paths.get(baseDir.getAbsolutePath() + "/VAADIN/themes/book-examples/img/reindeer-256px.png");
        ImageData initialimage = null;
        try {
            initialimage = new ImageData(initialpath.toString(), "image/png", Files.readAllBytes(initialpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        vlayout.addComponent(hlayout);

        // Use the field to edit the image
        final EditableImage image = new EditableImage("Editable", initialimage);
        hlayout.addComponent(image);

        // A copy just to show how the property value can be used
        final Image copy = new Image("Copy in ordinary Image");
        hlayout.addComponent(copy);
        
        image.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = -6025055567242090598L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                StreamSource source = image.createStreamSource(image.getValue());
                copy.setSource(new StreamResource(source, image.getValue().filename));
            }
        });

        // Use the image as a data source
        final EditableImage copy2 = new EditableImage("Read-only Copy", null);
        copy2.setReadOnly(true);
        copy2.setPropertyDataSource(image);
        hlayout.addComponent(copy2);
        // END-EXAMPLE: component.embedded.image.editable
    }
}
