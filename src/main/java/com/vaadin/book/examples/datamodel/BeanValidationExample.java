package com.vaadin.book.examples.datamodel;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vaadin.book.BookExamplesUI;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.ErrorMessage;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BeanValidationExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("fieldgroup".equals(context))
            fieldgroup(layout);
        else
            layout.addComponent(new Label("Invalid context: " + context));
        
        setCompositionRoot(layout);
    }
    
    // BEGIN-EXAMPLE: datamodel.itembinding.beanvalidation.basic
    // Here is a bean
    public class Person implements Serializable {
        private static final long serialVersionUID = -1520923107014804137L;

        @NotNull
        @javax.validation.constraints.Size(min=2, max=10)
        String name;
        
        @Min(1)
        @Max(130)
        int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age  = age;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }
    }
    
    void basic(VerticalLayout layout) {
        Person bean = new Person("Mr Bean", 100);
        BeanItem<Person> item = new BeanItem<Person> (bean);
        
        // Create an editor bound to a bean field
        TextField firstName = new TextField("First Name",
                item.getItemProperty("name"));
        
        // Add the bean validator
        firstName.addValidator(new BeanValidator(Person.class, "name"));
        
        firstName.setImmediate(true);
        layout.addComponent(firstName);
        // END-EXAMPLE: datamodel.itembinding.beanvalidation.basic
    }

    void fieldgroup(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.beanvalidation.fieldgroup
        // Have a bean
        Person bean = new Person("Mung bean", 100);
        
        // Form for editing the bean
        final BeanFieldGroup<Person> form =
                new BeanFieldGroup<Person>(Person.class);
        form.setItemDataSource(bean);
        layout.addComponent(form.buildAndBind("Name", "name"));
        layout.addComponent(form.buildAndBind("Age", "age"));
        
        final Label error = new Label("", ContentMode.HTML);
        error.setVisible(false);
        layout.addComponent(error);

        // Buffer the form content
        form.setBuffered(true);
        layout.addComponent(new Button("OK", new ClickListener() {
            private static final long serialVersionUID = 8273374540088290859L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    form.commit();
                    Notification.show("OK!");
                    error.setVisible(false);
                } catch (CommitException e) {
                    for (Field<?> field: form.getFields()) {
                        ErrorMessage errMsg = ((AbstractField<?>)field).getErrorMessage();
                        if (errMsg != null) {
                            error.setValue("Error in " +
                                field.getCaption() + ": " +
                                errMsg.getFormattedHtmlMessage());
                            error.setVisible(true);
                            break;
                        }
                    }
                }
            }
        }));
        // END-EXAMPLE: datamodel.itembinding.beanvalidation.fieldgroup
        BookExamplesUI.getLogger().info("This is a logging test");
    }
}
