package com.vaadin.book.examples.datamodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

public class ConverterExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("customconverter".equals(context))
            customconverter(layout);
        else if ("converterfactory".equals(context))
            converterfactory(layout);
        else if ("beanbinding".equals(context))
            beanbinding(layout);
        
        setCompositionRoot(layout);
    }

    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.converter.basic
        // Have an integer property
        final ObjectProperty<Integer> property =
                new ObjectProperty<Integer>(42); 
        
        // Create a TextField, which edits Strings
        final TextField tf = new TextField("Name");

        // Use a converter between String and Integer
        tf.setConverter(new StringToIntegerConverter());

        // And bind the field
        tf.setPropertyDataSource(property);
        
        // When the field value is edited by the user
        Button parse = new Button("Parse");
        parse.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -3001067516231106227L;

            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Value = " +
                                              property.getValue()));
            }
        });
        // END-EXAMPLE: datamodel.properties.converter.basic

        layout.addComponent(tf);
        layout.addComponent(parse);
    }

    // BEGIN-EXAMPLE: datamodel.properties.converter.customconverter
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
    
    public class ComplexConverter
           implements Converter<String, Complex> {
        private static final long serialVersionUID = -220989690852205509L;

        @Override
        public Complex convertToModel(String value,
                Class<? extends Complex> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
            String parts[] = value.replaceAll("[\\(\\)]", "").split(",");
            if (parts.length != 2)
                throw new ConversionException(
                        "Unable to parse String to Complex");
            return new Complex(Double.parseDouble(parts[0]),
                               Double.parseDouble(parts[1]));
        }

        @Override
        public String convertToPresentation(Complex value,
                Class<? extends String> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
            return "("+value.getReal()+","+value.getImag()+")";
        }
        

        @Override
        public Class<Complex> getModelType() {
            return Complex.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }
    }

    void customconverter(final VerticalLayout layout) {
        // Have a property of the custom type
        final ObjectProperty<Complex> property =
                new ObjectProperty<Complex>(new Complex(4,2)); 
        
        // A TextField allows editing String representation
        final TextField tf = new TextField("Complex value");

        // Use the custom converter
        tf.setConverter(new ComplexConverter());

        // Bind to the property after setting the converter
        tf.setPropertyDataSource(property);
        
        // When the field value is edited by the user
        Button show = new Button("Show");
        show.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -3001067516231106227L;

            @Override
            public void buttonClick(ClickEvent event) {
                Complex complex = property.getValue();
                layout.addComponent(new Label("Value = " +
                    complex.getReal() + " + " +
                    complex.getImag() + "i"));
            }
        });
        // END-EXAMPLE: datamodel.properties.converter.customconverter

        layout.addComponent(tf);
        layout.addComponent(show);
    }

    // BEGIN-EXAMPLE: datamodel.properties.converter.beanbinding
    public class MyBean implements Serializable {
        private static final long serialVersionUID = 584774795830739333L;
        
        String items[] = null;
        
        public String[] getItems() {
            return items;
        }
        
        public void setItems(String[] items) {
            this.items = items;
        }
    }
    
    public static class ArrayToSetConverter
           implements Converter<Object, String[]> {
        private static final long serialVersionUID = -7461326886197704330L;

        @Override
        public String[] convertToModel(Object value,
            Class<? extends String[]> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
            Set<String> pres = ((Set<String>)value);
            String[] model = new String[pres.size()];
            int i=0;
            for (String str: pres)
                model[i++] = str;
            return model;
        }

        @Override
        public Collection convertToPresentation(String[] value,
            Class<? extends Object> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
            HashSet<String> set = new HashSet<String>(value.length);
            for (String str: value)
                set.add(str);
            return set;
        }

        @Override
        public Class<String[]> getModelType() {
            return String[].class;
        }

        @Override
        public Class<Object> getPresentationType() {
            return Object.class;
        }
    }

    void beanbinding(final VerticalLayout layout) {
        class MyForm extends VerticalLayout {
            private static final long serialVersionUID = 6497270368660378975L;

            TwinColSelect items = new TwinColSelect();
            
            public MyForm(final MyBean bean, Item item) {
                items.addItem("Mercury");
                items.addItem("Venus");
                items.addItem("Earth");
                items.addItem("Mars");
                items.setConverter(new ArrayToSetConverter());
                addComponent(items);
                
                final FieldGroup fieldGroup = new FieldGroup(item);
                fieldGroup.setBuffered(false);
                fieldGroup.bindMemberFields(this);

                items.addValueChangeListener(new ValueChangeListener() {
                    private static final long serialVersionUID = 6565924888520834388L;

                    @Override
                    public void valueChange(ValueChangeEvent event) {
                        String str = "";
                        for (String itemId: bean.getItems())
                            str += itemId + " ";
                        addComponent(new Label("Selected: " + str));
                    }
                });
                items.setImmediate(true);

            }
        }
        
        MyBean bean = new MyBean();
        bean.setItems(new String[]{"Mercury", "Venus"});
        BeanItem<MyBean> item = new BeanItem<MyBean>(bean);
        MyForm form = new MyForm(bean, item);
        layout.addComponent(form);
        // END-EXAMPLE: datamodel.properties.converter.beanbinding
    }
    
    @SuppressWarnings("unchecked")
    void converterfactory(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.properties.converter.converterfactory
        class MyConverterFactory extends DefaultConverterFactory {
            private static final long serialVersionUID = 8417113314826736235L;

            @Override
            public <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL>
                    createConverter(Class<PRESENTATION> presentationType,
                                    Class<MODEL> modelType) {
                // Handle one particular type conversion
                if (String.class == presentationType &&
                    Complex.class == modelType)
                    return (Converter<PRESENTATION, MODEL>)
                           new ComplexConverter();

                // Default to the supertype
                return super.createConverter(presentationType,
                                             modelType);
            }
            
        }

        // Use the factory globally in the application
        VaadinSession.getCurrent().setConverterFactory(
            new MyConverterFactory());
        
        // Have an integer property
        final ObjectProperty<Complex> property =
            new ObjectProperty<Complex>(new Complex(1,1)); 
        
        // Create and bind a text field to it
        final TextField tf = new TextField("Complex value");

        // Bind to the property
        // - the correct converter is used automatically
        tf.setPropertyDataSource(property);
        
        // When the field value is edited by the user
        Button parse = new Button("Parse");
        parse.addClickListener(new ClickListener() {
            private static final long serialVersionUID = -3001067516231106227L;

            @Override
            public void buttonClick(ClickEvent event) {
                Complex complex = property.getValue();
                layout.addComponent(new Label("Value = " +
                    complex.getReal() + " + " +
                    complex.getImag() + "i"));
            }
        });
        // END-EXAMPLE: datamodel.properties.converter.converterfactory

        layout.addComponent(tf);
        layout.addComponent(parse);
    }
}
