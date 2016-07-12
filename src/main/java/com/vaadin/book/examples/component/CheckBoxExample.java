package com.vaadin.book.examples.component;

import java.io.Serializable;

import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class CheckBoxExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.checkbox.basic
        CheckBox checkbox = new CheckBox("Box with a Check");
        
        checkbox.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6857112166321059475L;

            public void valueChange(ValueChangeEvent event) {
                boolean value = (Boolean) event.getProperty().getValue();

                Notification.show("Check: " + value);
            }
        });
        checkbox.setImmediate(true);
        // END-EXAMPLE: component.checkbox.basic

        layout.addComponent(checkbox);
    }

    public void switching(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.checkbox.switching
        CheckBox checkbox1 = new CheckBox("Box with no Check");
        CheckBox checkbox2 = new CheckBox("Box with a Check");

        checkbox2.setValue(true);
        
        checkbox1.addValueChangeListener(event -> // Java 8
            checkbox2.setValue(! checkbox1.getValue()));

        checkbox2.addValueChangeListener(event -> // Java 8
            checkbox1.setValue(! checkbox2.getValue()));
        // END-EXAMPLE: component.checkbox.switching

        layout.addComponents(checkbox1, checkbox2);
    }
    
    public static final String beanbindingDescription =
        "<h1>Binding CheckBox to a Bean</h1>" +
        "<p>You can bind either primitive <b>boolean</b> or object <b>Boolean</b> type to " +
        "a <b>CheckBox</b>. If the type is a <b>Boolean</b> object, you must return it with a <tt>get</tt>-ter, " +
        "not with <tt>is</tt> method. The object must not be null, although you can circumvent the limitation by " +
        "overriding the <tt>setInternalValue()</tt> method and handle the null case.</p>";

    // BEGIN-EXAMPLE: component.checkbox.beanbinding
    public class TruthAndDare implements Serializable {
        private static final long serialVersionUID = 4276850089892850078L;

        boolean truth = true; // Primitive boolean type
        Boolean dare  = null; // Object type - can be null
        
        public boolean isTruth() {
            return truth;
        }
        public void setTruth(boolean truth) {
            this.truth = truth;
        }
        public Boolean getDare() {
            return dare;
        }
        public void setDare(Boolean dare) {
            this.dare = dare;
        }
    };

    /** CheckBox extension that allows null values */
    class CheckBoxWithNull extends CheckBox {
        private static final long serialVersionUID = -6208978581410404086L;
        
        public CheckBoxWithNull(String caption) {
            super(caption);
        }
        
        @Override
        protected void setInternalValue(Boolean newValue) {
            // Allows null values for Boolean properties
            if (newValue == null)
                newValue = false; // Handle it somehow

            super.setInternalValue(newValue); 
        }
    }    
    
    public void beanbinding(VerticalLayout layout) {
        final TruthAndDare bean = new TruthAndDare();
        
        BeanItem<TruthAndDare> beanitem =
            new BeanItem<TruthAndDare>(bean);
        
        Form form = new Form();
        form.setCaption("Here is a form");
        form.setFormFieldFactory(new FormFieldFactory() {
            private static final long serialVersionUID = 3927943308003136706L;

            public Field createField(Item item, Object propertyId, Component uiContext) {
                if ("truth".equals(propertyId))
                    return new CheckBox("boolean truth");
                else if ("dare".equals(propertyId))
                    return new CheckBoxWithNull("Boolean dare");
                return null;
            }
        });
        form.setItemDataSource(beanitem);
        
        Button ok = new Button("OK");
        ok.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 3454155914766972071L;

            public void buttonClick(ClickEvent event) {
                Notification.show("There is " +
                        (bean.isTruth()? "truth" : "untruth") +
                        " and " +
                        (bean.getDare()!=null? // Must check here
                                (bean.getDare()? "daring" : "cowardness")
                                : "unknown"));
            }
        });
        form.getFooter().addComponent(ok);
        // END-EXAMPLE: component.checkbox.beanbinding

        form.setVisibleItemProperties(new Object[] {"truth", "dare"});
        layout.addComponent(form);
    }
}
