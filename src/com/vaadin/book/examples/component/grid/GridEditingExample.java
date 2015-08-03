package com.vaadin.book.examples.component.grid;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.Description;
import com.vaadin.book.examples.advanced.MyAppCaptions;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
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
        grid.setWidth("400px");
        grid.setHeight("300px");

        // Enable editing
        grid.setEditorEnabled(true);
        
        TextField nameEditor = new TextField();
        
        // Custom CSS style
        nameEditor.addStyleName("nameeditor");

        // Custom validation
        nameEditor.addValidator(new RegexpValidator(
            "^\\p{Alpha}+ \\p{Alpha}+$",
            "Need first and last name"));

        grid.getColumn("name").setEditorField(nameEditor);
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.editorfields

        grid.setSelectionMode(SelectionMode.NONE);
    }

    public void captions(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing.captions
        Grid grid = new Grid(GridExample.exampleDataSource());
        grid.setWidth("400px");
        grid.setHeight("300px");

        // Enable editing
        grid.setEditorEnabled(true);
        
        // Captions are stored in a resource bundle
        ResourceBundle bundle = ResourceBundle.getBundle(
            MyAppCaptions.class.getName(),
            Locale.forLanguageTag("fi")); // Finnish

        // Localize the editor button captions
        grid.setEditorSaveCaption(
            bundle.getString(MyAppCaptions.SaveKey));
        grid.setEditorCancelCaption(
            bundle.getString(MyAppCaptions.CancelKey));
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.captions

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
        grid.getColumn("name").setEditorField(nameEditor);

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

    @Description("This is a test for a workaround for issue #16842 to handle Enter key to save editor")
    public void entersave(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.grid.editing.entersave
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
        grid.getColumn("name").setEditorField(nameEditor);

        grid.addShortcutListener(new ShortcutListener("ENTER",
                                 ShortcutAction.KeyCode.ENTER, null) {
            private static final long serialVersionUID = -5750865238489191851L;

            @Override
            public void handleAction(Object sender, Object target) {
                Object targetParent = ((AbstractComponent) target).getParent();
                if ((targetParent != null) && (targetParent instanceof Grid)) {
                    Grid targetGrid = (Grid) targetParent;
    
                    if (targetGrid.isEditorActive()) {
                        try {
                            targetGrid.saveEditor();
                            targetGrid.cancelEditor();
                        } catch (CommitException e) {
                            Notification.show("Validation failed");
                        }
                    }
                }
            }
        });        
        
        layout.addComponent(grid);
        // END-EXAMPLE: component.grid.editing.entersave
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
        grid.getColumn("name").setEditorField(nameEditor);
        
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
