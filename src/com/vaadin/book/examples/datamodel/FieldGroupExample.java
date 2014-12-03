package com.vaadin.book.examples.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FieldGroupExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = -6235574698344484522L;

    public void init (String context) {
        VerticalLayout layout = new VerticalLayout();

        if ("basic".equals(context))
            basic(layout);
        else if ("buildandbind".equals(context))
            buildandbind(layout);
        else if ("extended".equals(context))
            extended(layout);
        else if ("onetomany".equals(context))
            onetomany(layout);
        else if ("customcomponent".equals(context))
            customcomponent(layout);
        else if ("commit".equals(context))
            commit(layout);
        
        setCompositionRoot(layout);
    }
    
    void basic(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.basic
        // Have an item
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));

        // Have some layout and create the fields
        FormLayout form = new FormLayout();

        TextField nameField = new TextField("Name");
        form.addComponent(nameField);

        TextField ageField = new TextField("Age");
        form.addComponent(ageField);
        
        // Now create the binder and bind the fields
        FieldGroup binder = new FieldGroup(item);
        binder.bind(nameField, "name");
        binder.bind(ageField, "age");
        // END-EXAMPLE: datamodel.itembinding.basic

        layout.addComponent(form);
    }

    void buildandbind(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.buildandbind
        // Have an item
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));

        // Have some layout
        FormLayout form = new FormLayout();
        
        // Now create a binder that can also create the fields
        // using the default field factory
        FieldGroup binder = new FieldGroup(item);
        form.addComponent(binder.buildAndBind("Name", "name"));
        form.addComponent(binder.buildAndBind("Age", "age"));
        // END-EXAMPLE: datamodel.itembinding.buildandbind

        layout.addComponent(form);
    }

    void fieldfactory(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.fieldfactory
        // Have an item
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));

        // Have some layout
        FormLayout form = new FormLayout();
        
        // Now create a binder that can also create the fields
        FieldGroup binder = new FieldGroup(item);
        binder.setFieldFactory(new FieldGroupFieldFactory() {
            private static final long serialVersionUID = -7503214153547969773L;

            @SuppressWarnings("rawtypes")
            @Override
            public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
                TextField field = new TextField();
                
                // The age field needs a conversion
                if (Integer.class == dataType)
                    field.setConverter(dataType);
                return fieldType.cast(field);
            }
        });

        // Build the fields and in the properties bind them
        form.addComponent(binder.buildAndBind("Name", "name"));
        form.addComponent(binder.buildAndBind("Age", "age"));
        // END-EXAMPLE: datamodel.itembinding.fieldfactory
    }

    void extended(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.formclass.extended
        // Have an item
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));

        // Define a form as a class that extends some layout
        class MyForm extends FormLayout {
            private static final long serialVersionUID = 4787596268280857302L;

            // Member that will bind to the "name" property
            TextField name = new TextField("Name");
            
            // Member that will bind to the "age" property 
            @PropertyId("age")
            TextField ageField = new TextField("Age");
            
            public MyForm(Item item) {
                // Add the fields
                addComponent(name);
                addComponent(ageField);

                // Now bind the member fields to the item
                FieldGroup binder = new FieldGroup(item);
                binder.bindMemberFields(this);
            }
        }

        // Create one
        MyForm form = new MyForm(item);

        // And the form can be used in an higher-level layout
        layout.addComponent(form);
        // END-EXAMPLE: datamodel.itembinding.formclass.extended
    }

    // BEGIN-EXAMPLE: datamodel.itembinding.formclass.onetomany
    // Have a bean with a one-to-many relationship
    public class Planet implements Serializable {
        private static final long serialVersionUID = 8351187202041613401L;

        String name;
        List<String> moons = new ArrayList<String>();
        
        public Planet(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public List<String> getMoons() {
            return moons;
        }
        public void setMoons(List<String> moons) {
            this.moons = moons;
        }
    }
    
    // Simple editor for string lists
    class ListEditor extends CustomField<List<String>> {
        private static final long serialVersionUID = 4492189777890493998L;

        VerticalLayout fields = new VerticalLayout();
        
        public ListEditor(String caption) {
            setCaption(caption);
        }

        @Override
        protected Component initContent() {
            GridLayout content = new GridLayout(2,1);
            content.addComponent(fields);
            
            Button add = new Button("+", this::addItem); // Java 8
            content.addComponent(add);
            content.setComponentAlignment(add, Alignment.BOTTOM_CENTER);
            return content;
        }
        
        void addItem(ClickEvent event) {
            List<String> list = getValue();
            if (list == null)
                list = new ArrayList<String>();
            list.add("");
            setValue(list);

            final TextField tf = new TextField();
            tf.addValueChangeListener(valueChange -> { // Java 8
                int index = fields.getComponentIndex(tf);
                List<String> values = getValue();
                values.set(index, tf.getValue());
                setValue(values);
            });
            tf.focus();
            fields.addComponent(tf);
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends List<String>> getType() {
            return (Class<List<String>>) (new ArrayList<String>()).getClass();
        }
        
        @Override
        public List<String> getValue() {
            return super.getValue();
        }
    }
    
    // Define a form as a class that extends some layout
    class MyForm extends Panel {
        private static final long serialVersionUID = 4787596268280857302L;

        // Member that will bind to the "name" property
        private TextField name = new TextField("Name");
        
        // Member that will bind to the "age" property 
        private ListEditor moons = new ListEditor("Moons");

        private FieldGroup binder = new FieldGroup();
        
        private Runnable notifyOk;

        public MyForm(Item item, Runnable notifyOk) {
            setCaption("Planet");
            this.notifyOk = notifyOk;

            VerticalLayout content = new VerticalLayout();
            content.setMargin(true);
            content.setSizeUndefined();
            setContent(content);
            setSizeUndefined();

            FormLayout form = new FormLayout();
            form.addComponents(name, moons);
            content.addComponent(form);
            
            content.addComponent(new Button("Commit", this::commit));

            // Now bind the member fields to the item
            binder.setItemDataSource(item);
            binder.bindMemberFields(this);
        }
        
        void commit(ClickEvent event) {
            try {
                binder.commit();
                notifyOk.run();
            } catch (CommitException e) {
                Notification.show("Commit failed");
            }
        }
    }

    void onetomany(final VerticalLayout layout) {
        Planet planet = new Planet("My Planet");
        BeanItem<Planet> item = new BeanItem<Planet>(planet);
        layout.addComponent(new MyForm(item, () -> { // Java 8
            Notification.show(planet.name,
                planet.moons.toString(),
                Notification.Type.HUMANIZED_MESSAGE);
        }));
        // END-EXAMPLE: datamodel.itembinding.formclass.onetomany
    }

    void multipage(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.formclass.multipage
        // Have an item
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));
        item.addItemProperty("city", new ObjectProperty<String>("Somewhere"));

        class MyTabbedForm extends TabSheet
                           implements TabSheet.SelectedTabChangeListener {
            MyTabbedForm top = this;
        
            class FormPage1 extends FormLayout implements ClickListener {
                private static final long serialVersionUID = 4787596268280857302L;
    
                TextField name = new TextField("Name");
                
                public FieldGroup binder;
                
                public FormPage1(Item item) {
                    addComponent(name);

                    binder = new FieldGroup(item);
                    binder.bindMemberFields(this);
    
                    // Validate the page and allow moving forward only if valid
                    addComponent(new Button("OK", this));
                }
                
                @Override
                public void buttonClick(ClickEvent event) {
                    if (binder.isValid()) {
                        top.getTab(page2).setEnabled(true);
                        top.setSelectedTab(page2);
                    }
                }
            }
    
            // Define a form as a class that extends some layout
            class FormPage2 extends FormLayout implements ClickListener {
                private static final long serialVersionUID = 4787596268280857302L;
    
                TextField age = new TextField("Age");
                TextField city = new TextField("City");
                
                public FieldGroup binder;
                
                public FormPage2(Item item) {
                    addComponent(age);
                    addComponent(city);
    
                    binder = new FieldGroup(item);
                    binder.bindMemberFields(this);

                    // Validate the entire form
                    addComponent(new Button("Finish", this));
                }

                @Override
                public void buttonClick(ClickEvent event) {
                    if (binder.isValid()) {
                        // Both pages are valid, commit them.
                        try {
                            page1.binder.commit();
                            page2.binder.commit();
                        } catch (CommitException e) {
                            // This should not happen
                            Notification.show("Commit failed");
                        }
                        
                        // Finish somehow
                        top.setEnabled(false);
                    } else
                        Notification.show("No good");
                }
            }

            // Create the step forms
            FormPage1 page1;
            FormPage2 page2;
            
            public MyTabbedForm(Item item) {
                page1 = new FormPage1(item);
                page2 = new FormPage2(item);
                addComponents(page1, page2);
            }
            
            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                // Disable the second tab if activating the first one
                if (getSelectedTab() == page1)
                    getTab(page2).setEnabled(false);
            }
        }
        
        MyTabbedForm form = new MyTabbedForm(item);
        layout.addComponent(form);
        // END-EXAMPLE: datamodel.itembinding.formclass.multipage
    }

    void customcomponent(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.formclass.customcomponent
        // Have an item
        PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Zaphod"));
        item.addItemProperty("age", new ObjectProperty<Integer>(42));

        // A form component that allows editing an item
        class MyForm extends CustomComponent {
            private static final long serialVersionUID = -5542085770295768492L;

            // Member that will bind to the "name" property
            TextField name = new TextField("Name");
            
            // Member that will bind to the "age" property 
            @PropertyId("age")
            TextField ageField = new TextField("Age");
            
            public MyForm(Item item) {
                FormLayout layout = new FormLayout();
                layout.addComponent(name);
                layout.addComponent(ageField);
                
                // Now use a binder to bind the members
                FieldGroup binder = new FieldGroup(item);
                binder.bindMemberFields(this);

                setCompositionRoot(layout);
            }
        }
        
        // And the form can be used in an higher-level layout
        layout.addComponent(new MyForm(item));
        // END-EXAMPLE: datamodel.itembinding.formclass.customcomponent
    }

    void validation(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.validation
        // Have an item with couple of properties
        final PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Q"));
        item.addItemProperty("age",  new ObjectProperty<Integer>(42));

        // Have some layout and create the fields
        Panel form = new Panel("Buffered Form");
        FormLayout formLayout = new FormLayout();
        form.setContent(formLayout);

        // Build and bind the fields using the default field factory
        final FieldGroup binder = new FieldGroup(item);
        formLayout.addComponent(binder.buildAndBind("Name", "name"));
        formLayout.addComponent(binder.buildAndBind("Age",  "age"));

        // Enable buffering (actually enabled by default)
        binder.setBuffered(true);
        
        // A button to commit the buffer
        formLayout.addComponent(new Button("OK", new ClickListener() {
            private static final long serialVersionUID = 2928773509123270489L;

            @Override
            public void buttonClick(ClickEvent event) {
                if (binder.isValid())
                    Notification.show("Valid!");
                else
                    Notification.show("You fail!");
            }
        }));
        // END-EXAMPLE: datamodel.itembinding.validation
    }

    void commit(final VerticalLayout layout) {
        // BEGIN-EXAMPLE: datamodel.itembinding.buffering.commit
        // Have an item with couple of properties
        final PropertysetItem item = new PropertysetItem();
        item.addItemProperty("name", new ObjectProperty<String>("Q"));
        item.addItemProperty("age",  new ObjectProperty<Integer>(42));

        // Have some layout and create the fields
        Panel form = new Panel("Buffered Form");
        FormLayout formLayout = new FormLayout();
        form.setContent(formLayout);

        // Build and bind the fields using the default field factory
        final FieldGroup binder = new FieldGroup(item);
        formLayout.addComponent(binder.buildAndBind("Name", "name"));
        formLayout.addComponent(binder.buildAndBind("Age",  "age"));

        // Enable buffering (actually enabled by default)
        binder.setBuffered(true);
        
        // A button to commit the buffer
        formLayout.addComponent(new Button("OK", new ClickListener() {
            private static final long serialVersionUID = 2928773509123270489L;

            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    binder.commit();
                    Notification.show("Thanks!");
                } catch (CommitException e) {
                    Notification.show("You fail!");
                }
            }
        }));

        // A button to discard the buffer
        formLayout.addComponent(new Button("Discard", new ClickListener() {
            private static final long serialVersionUID = 2928773509123270489L;

            @Override
            public void buttonClick(ClickEvent event) {
                binder.discard();
                Notification.show("Discarded!");
            }
        }));
        
        // Have a read-only form to show the item data
        Panel roform = new Panel("Item Data");
        FormLayout roformLayout = new FormLayout();
        roform.setContent(roformLayout);
        final FieldGroup robinder = new FieldGroup(item);
        roformLayout.addComponent(robinder.buildAndBind("Name", "name"));
        roformLayout.addComponent(robinder.buildAndBind("Age",  "age"));
        robinder.setBuffered(false); // Disable buffering
        for (Component c: roformLayout)
            c.setReadOnly(true);

        // Put them in a layout
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.addComponent(form);
        hlayout.addComponent(roform);
        // END-EXAMPLE: datamodel.itembinding.buffering.commit
        
        hlayout.setSpacing(true);
        layout.addComponent(hlayout);
    }
}
