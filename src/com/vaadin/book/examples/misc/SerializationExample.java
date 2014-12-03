package com.vaadin.book.examples.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class SerializationExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: misc.serialization.basic
    // Here is a bean
    public class Bean implements Serializable {
        private static final long serialVersionUID = -1520923107014804137L;

        String name;
        double energy; // Energy content in kJ/100g
        
        public Bean(String name, double energy) {
            this.name   = name;
            this.energy = energy;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public double getEnergy() {
            return energy;
        }
        
        public void setEnergy(double energy) {
            this.energy = energy;
        }
    }
    
    void basic(Layout layout) {
        HorizontalLayout hlayout = new HorizontalLayout();
        
        final Bean bean = new Bean("Mung bean", 1452.0);
        BeanItem<Bean> item = new BeanItem<Bean>(bean);

        // Bind a form to it
        final Form form1 = new Form();
        form1.setCaption("The Original Bean");
        form1.setItemDataSource(item);
        hlayout.addComponent(form1);
        
        // Have a box for serialized representation
        final Label serialized = new Label();
        serialized.setCaption("Serialized Bean");
        serialized.setWidth("200px");
        hlayout.addComponent(serialized);
        
        // Serialization
        Button serialize = new Button("Serialize");
        serialize.addListener(new ClickListener() {
            private static final long serialVersionUID = -3659227959623977486L;

            @Override
            public void buttonClick(ClickEvent event) {
                // Serialize
                ByteArrayOutputStream baostr = new ByteArrayOutputStream();
                ObjectOutputStream oostr;
                try {
                    oostr = new ObjectOutputStream(baostr);
                    oostr.writeObject(bean);
                    oostr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                // Encode
                BASE64Encoder encoder = new BASE64Encoder();
                String encoded = encoder.encode(baostr.toByteArray());

                serialized.setValue(encoded);
            }
        });
        form1.getFooter().addComponent(serialize);

        // A form to hold the deserialized object
        final Form form2 = new Form();
        form2.setCaption("The Unoriginal Bean");
        hlayout.addComponent(form2);
        
        // Deserialization
        Button deserialize = new Button("Deserialize");
        deserialize.addListener(new ClickListener() {
            private static final long serialVersionUID = -3659227959623977486L;

            @Override
            public void buttonClick(ClickEvent event) {
                String encoded = (String) serialized.getValue();
                
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    byte[] data = decoder.decodeBuffer(encoded);
                    ObjectInputStream ois =
                            new ObjectInputStream( 
                                    new ByteArrayInputStream(data));

                    // The deserialized bean
                    Bean unbean = (Bean) ois.readObject();
                    ois.close();
                    
                    BeanItem<Bean> unitem = new BeanItem<Bean>(unbean);
                    form2.setItemDataSource(unitem);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        form2.getFooter().addComponent(deserialize);

        layout.addComponent(hlayout);
        // END-EXAMPLE: misc.serialization.basic
    }
}
