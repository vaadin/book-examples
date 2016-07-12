package com.vaadin.book.examples.component.properties;

import java.io.Serializable;
import java.util.Iterator;

import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RequiredExample extends CustomComponent implements BookExampleBundle {
	private static final long serialVersionUID = -1770451668233870037L;

	public void init (String context) {
	    VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic();
        else if ("beanfields".equals(context))
            beanfields();
        else if ("caption".equals(context))
            caption(layout);
        else if ("nocaption".equals(context))
            nocaption(layout);
        if (getCompositionRoot() == null)
            setCompositionRoot(layout);
	}
	
	public static final String basicDescription =
	    "<h1>Basic Use of Required Field Property</h1>"+
	    "<p></p>";
	
    void basic() {
        // BEGIN-EXAMPLE: component.field.required.basic
        // Some data source
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("firstName",
                new ObjectProperty<String>(""));
        item.addItemProperty("lastName",
                new ObjectProperty<String>(""));

        // A buffered form bound to a data source
        class MyForm extends VerticalLayout {
            FormLayout formLayout = new FormLayout();
            TextField firstName = new TextField("First Name");
            TextField lastName  = new TextField("Last Name");
            
            public MyForm(Item item) {
                addComponent(formLayout);

                // A required field
                firstName.setRequired(true);
                formLayout.addComponent(firstName);

                // A required field with an error message
                lastName.setRequired(true);
                lastName.setRequiredError("Give last name");
                lastName.setImmediate(true);
                lastName.setValidationVisible(true);
                lastName.addValidator(new StringLengthValidator(
                        "Must not be empty", 1, 100, false));
                formLayout.addComponent(lastName);
                
                final FieldGroup binder = new FieldGroup(item);
                binder.bindMemberFields(this);

                // Footer
                addComponent(new Button("OK",
                    new Button.ClickListener() {
                    private static final long serialVersionUID = -4459717321759951123L;

                    public void buttonClick(ClickEvent event) {
                        try {
                            binder.commit();
                        } catch (EmptyValueException e) {
                            // A required value was missing
                        } catch (CommitException e) {
                            for (Component c: formLayout)
                                ((AbstractField)c).setValidationVisible(true);
                        }
                    }
                }));
                
            }
        };
        MyForm     form   = new MyForm(item);

        // Have a read-only form that displays the content of the first form
        MyForm roform = new MyForm(item);
        roform.setReadOnly(true);
        // END-EXAMPLE: component.field.required.basic
        
        form.setCaption("Form with Required Fields");
        form.addStyleName("bordered");
        roform.setCaption("Buffered Data Source Value");
        roform.addStyleName("bordered");
        roform.setHeight("100%");
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        hlayout.setWidth("600px");
        hlayout.addComponent(form);
        hlayout.addComponent(roform);

        setCompositionRoot(hlayout);
    }

    void formclass() {
        // BEGIN-EXAMPLE: component.field.required.formclass
        // Some data source
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("firstName",
                new ObjectProperty<String>(""));
        item.addItemProperty("lastName",
                new ObjectProperty<String>(""));

        // A buffered form bound to a data source
        class MyForm extends VerticalLayout {
            FormLayout formLayout = new FormLayout();
            TextField firstName = new TextField("First Name");
            TextField lastName  = new TextField("Last Name");
            
            public MyForm(Item item) {
                addComponent(formLayout);

                // A required field
                firstName.setRequired(true);
                formLayout.addComponent(firstName);

                // A required field with an error message
                lastName.setRequired(true);
                lastName.setRequiredError("Give last name");
                lastName.setImmediate(true);
                lastName.setValidationVisible(true);
                lastName.addValidator(new StringLengthValidator(
                        "Must not be empty", 1, 100, false));
                formLayout.addComponent(lastName);
                
                final FieldGroup binder = new FieldGroup(item);
                binder.bindMemberFields(this);

                // Footer
                addComponent(new Button("OK",
                    new Button.ClickListener() {
                    private static final long serialVersionUID = -4459717321759951123L;

                    public void buttonClick(ClickEvent event) {
                        try {
                            binder.commit();
                        } catch (EmptyValueException e) {
                            // A required value was missing
                        } catch (CommitException e) {
                            for (Component c: formLayout)
                                ((AbstractField)c).setValidationVisible(true);
                        }
                    }
                }));
                
            }
        };
        MyForm     form   = new MyForm(item);

        // Have a read-only form that displays the content of the first form
        MyForm roform = new MyForm(item);
        roform.setReadOnly(true);
        // END-EXAMPLE: component.field.required.formclass
        
        form.setCaption("Form with Required Fields");
        form.addStyleName("bordered");
        roform.setCaption("Buffered Data Source Value");
        roform.addStyleName("bordered");
        roform.setHeight("100%");
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        hlayout.setWidth("600px");
        hlayout.addComponent(form);
        hlayout.addComponent(roform);

        setCompositionRoot(hlayout);
    }

    void caption(VerticalLayout root) {
        // BEGIN-EXAMPLE: component.field.required.caption
        GridLayout grid = new GridLayout(2,1);
        grid.addStyleName("blackborder");
        grid.setSpacing(true);
        grid.addComponent(new Label("\u00a0 <b>Layout</b>", ContentMode.HTML));
        grid.addComponent(new Label("\u00a0 <b>Example</b>", ContentMode.HTML));
        
        for (Layout layout: new Layout[]{
                new VerticalLayout(),
                new HorizontalLayout(),
                new GridLayout(),
                new FormLayout(),
                new CssLayout()}) {
            layout.setWidth("-1px"); // Must be undefined

            // Caption
            VerticalLayout captionLayout = new VerticalLayout();
            captionLayout.setWidth("-1px");
            captionLayout.setHeight("100%");
            captionLayout.addComponent(new Label(layout.getClass().getSimpleName()));
            captionLayout.setMargin(true);
            grid.addComponent(captionLayout);

            // The required TextField
            TextField tf = new TextField("Caption");
            tf.setRequired(true);
            layout.addComponent(tf);

            grid.addComponent(layout);
        }
        // END-EXAMPLE: component.field.required.caption
        root.addComponent(grid);
    }    
    
    void nocaption(VerticalLayout root) {
        // BEGIN-EXAMPLE: component.field.required.nocaption
        GridLayout grid = new GridLayout(2,1);
        grid.addStyleName("blackborder");
        grid.setSpacing(true);
        grid.addComponent(new Label("\u00a0 <b>Layout</b>", ContentMode.HTML));
        grid.addComponent(new Label("\u00a0 <b>Example</b>", ContentMode.HTML));
        
        for (Layout layout: new Layout[]{
                new VerticalLayout(),
                new HorizontalLayout(),
                new GridLayout(),
                new FormLayout(),
                new CssLayout()}) {
            layout.setWidth("-1px"); // Must be undefined

            // Caption
            VerticalLayout captionLayout = new VerticalLayout();
            captionLayout.setWidth("-1px");
            captionLayout.setHeight("100%");
            captionLayout.addComponent(new Label(layout.getClass().getSimpleName()));
            captionLayout.setMargin(true);
            grid.addComponent(captionLayout);

            // The required TextField
            TextField tf = new TextField();
            tf.setRequired(true);
            layout.addComponent(tf);

            grid.addComponent(layout);
        }
        // END-EXAMPLE: component.field.required.nocaption
        root.addComponent(grid);
    }    
    
    // BEGIN-EXAMPLE: component.field.required.beanfields
    public class Person implements Serializable {
        private static final long serialVersionUID = 5867086003810011772L;

        String firstName;
        String lastName;
        
        public String getFirstName() {
            return firstName;
        }
        
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    // TODO Vaadin 7: Update Form
    void beanfields() {
	    // A data source
	    BeanItem<Person> item = new BeanItem<Person>(new Person());
	    
	    // A buffered form bound to a bean item data source
	    final Form form = new Form();
	    // TODO form.setWriteThrough(false);
	    
	    form.setFormFieldFactory(new FormFieldFactory() {
            private static final long serialVersionUID = 8753017413275541461L;

            public Field createField(Item item, Object propertyId, Component uiContext) {
                TextField field = null;
                if ("firstName".equals(((String)propertyId))) {
                    field = new TextField("First Name");
                    field.setRequired(true);
                    field.addValidator(new StringLengthValidator("Must...give...name", 1, 999, false));
                } else if ("lastName".equals(((String)propertyId))) {
                    field = new TextField("Last Name");
                    field.setRequired(true);
                    field.setRequiredError("You didn't give last name");
                }
                
                // This causes running validation immediately when the
                // value changes.
                field.setImmediate(true);
                
                // When set to false, tooltips are not shown
                // for empty required fields.
                field.setValidationVisible(false);
                
                // Empty text value shows as empty
                field.setNullRepresentation("");
                return field;
            }
        });
        form.setItemDataSource(item);
        ((AbstractComponent) ((Component)form.getField("lastName"))).setComponentError(null);
        
        Button validate = new Button("Validate");
        form.getFooter().addComponent(validate);
	    
        // Commit the buffered form data to the data source
        Button commit = new Button("Commit");
        commit.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = -4459717321759951123L;

            public void buttonClick(ClickEvent event) {
                try {
                    form.commit();
                } catch (EmptyValueException e) {
                    // A required value was missing.
                    // Enable tooltips to show this.
                    for (Iterator<Component> i = form.getLayout().getComponentIterator();
                         i.hasNext();)
                        ((AbstractField) i.next()).setValidationVisible(true);
                }
            }
        });
        form.getFooter().addComponent(commit);
        
        // Have a read-only form that displays the content of the first form
        Form roform = new Form();
        roform.setItemDataSource(form);
        roform.setReadOnly(true);
	    // END-EXAMPLE: component.field.required.beanfields
        
        form.setCaption("Form with Required Fields");
        form.addStyleName("bordered");
        roform.setCaption("Buffered Data Source Value");
        roform.addStyleName("bordered");
        roform.setHeight("100%");
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        hlayout.setWidth("600px");
        hlayout.addComponent(form);
        hlayout.addComponent(roform);

        setCompositionRoot(hlayout);
    }
}
