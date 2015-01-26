package com.vaadin.book.examples.component.grid;

import java.io.Serializable;
import java.util.Stack;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GridEditingExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -4292553844521293140L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing.basic
        Grid grid = new Grid(GridExample.exampleDataSource());
        grid.setWidth("400px");
        grid.setHeight("250px");

        // Single-selection mode (default)
        grid.setSelectionMode(SelectionMode.NONE);

        // Enable editing
        grid.setEditorEnabled(true);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.basic
    }

    public void editorfields(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing.editorfields
        Grid grid = new Grid(GridExample.exampleDataSource());
        grid.setWidth("600px");
        grid.setHeight("400px");

        // Enable editing
        grid.setEditorEnabled(true);
        
        TextField nameEditor = new TextField();
        
        // Custom CSS style
        nameEditor.addStyleName("nameeditor");

        // Custom validation
        nameEditor.addValidator(new RegexpValidator(
            "^\\p{Alpha}+ \\p{Alpha}+$",
            "Need first and last name"));

        // Editor fields can have interaction
        // TODO This doesn't have proper effect 
        nameEditor.addBlurListener(blur ->
            nameEditor.validate());
        
        grid.setEditorField("name", nameEditor);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.editorfields

        grid.setSelectionMode(SelectionMode.NONE);
    }

    public void commit(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing.commit
        Grid grid = new Grid(GridExample.exampleDataSource());
        grid.setWidth("600px");
        grid.setHeight("400px");

        // Enable editing
        grid.setEditorEnabled(true);

        // Validation can cause commit to fail
        TextField nameEditor = new TextField();
        nameEditor.addValidator(new RegexpValidator(
            "^\\p{Alpha}+ \\p{Alpha}+$",
            "Need first and last name"));
        grid.setEditorField("name", nameEditor);

        FieldGroup fieldGroup = new FieldGroup();
        grid.setEditorFieldGroup(fieldGroup);
        fieldGroup.addCommitHandler(new CommitHandler() {
            private static final long serialVersionUID = -8378742499490422335L;

            @Override
            public void preCommit(CommitEvent commitEvent)
                   throws CommitException {
                // 
            }
            
            @Override
            public void postCommit(CommitEvent commitEvent)
                   throws CommitException {
                Notification.show("Saved successfully");
                grid.setComponentError(new UserError("This is user error could be empty"));
            }
        });
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.commit
    }
    
    // BEGIN-EXAMPLE: component.grid.editing.fieldgroup
    // BOOK: components.grid
    // A data model
    static public class Person implements Serializable {
        private static final long serialVersionUID = 8877593814795677381L;

        @NotNull
        @Size(min=2, max=10)
        private String name;
        
        @Min(1)
        @Max(130)       
        private int    age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }

    public void fieldgroup(VerticalLayout layout) {
        // Have a grid bound to a People bean data source
        Grid grid = new Grid(exampleBeanDataSource());
        grid.setWidth("600px");
        grid.setHeight("400px");
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setColumnOrder("name", "age");

        grid.setEditorEnabled(true);

        // Enable bean validation for the data
        grid.setEditorFieldGroup(
            new BeanFieldGroup<Person>(Person.class));

        // Have some extra validation in a field
        TextField nameEditor = new TextField();
        nameEditor.addValidator(new RegexpValidator(
            "^\\p{Alpha}+ \\p{Alpha}+$",
            "Need first and last name"));
        grid.setEditorField("name", nameEditor);

        // Give feedback on commit failures
        grid.setErrorHandler(new DefaultErrorHandler() {
            private static final long serialVersionUID = -7301821934017608390L;

            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                if (event.getThrowable() instanceof CommitException) {
                    CommitException e = (CommitException) event.getThrowable();
                    if (e.getCause() instanceof InvalidValueException) {
                        InvalidValueException ives = (InvalidValueException) e.getCause();
                        String description = "";
                        
                        // The exceptions could be in multiple levels,
                        // so have to search recursively
                        Stack<InvalidValueException> stack =
                            new Stack<InvalidValueException>();
                        stack.push(ives);
                        while (!stack.isEmpty()) {
                            InvalidValueException c = stack.pop();
                            if (c.getCause() != null)
                                stack.add((InvalidValueException) c.getCause());
                            for (InvalidValueException i: c.getCauses())
                                stack.add(i);
                            if (c.getMessage() != null)
                                description += c.getMessage() + "<br/>";
                        }

                        new Notification(
                            "Invalid input<br/>", description,
                            Notification.Type.WARNING_MESSAGE, true).
                            show(Page.getCurrent());
                    } else 
                        Notification.show("Commit failed",
                            event.getThrowable().getLocalizedMessage(),
                            Notification.Type.ERROR_MESSAGE);
                } else
                    super.error(event);
            }
        });
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.fieldgroup
    }

    public static BeanItemContainer<Person> exampleBeanDataSource() {
        BeanItemContainer<Person> container =
            new BeanItemContainer<Person>(Person.class);

        // Generate some data
        String[] firstnames = new String[]{"Isaac", "Ada", "Charles", "Douglas"};
        String[] lastnames  = new String[]{"Newton", "Lovelace", "Darwin", "Adams"};
        for (int i=0; i<100; i++) {
            String name = firstnames[(int) (Math.random()*4)] + " " +
                          lastnames[(int) (Math.random()*4)];
            int age = 30 + (int) (Math.random()*100);
            container.addBean(new Person(name, age));
        }

        return container;
    }
    
    public void fieldfactory(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing.fieldfactory
        Grid grid = new Grid(GridExample.exampleDataSource());
        grid.setWidth("600px");
        grid.setHeight("400px");

        // Single-selection mode (default)
        grid.setSelectionMode(SelectionMode.NONE);

        // Enable editing
        grid.setEditorEnabled(true);
        
        grid.setEditorFieldFactory(new FieldGroupFieldFactory() {
            public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {
                return null;
            }
        });
        
        FieldGroup fieldgroup = new FieldGroup();
        grid.setEditorFieldGroup(fieldgroup);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.fieldfactory
    }
}
