package com.vaadin.book.examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.vaadin.book.examples.EmboExample.EmbeddingType;
import com.vaadin.book.examples.addons.AddonTests;
import com.vaadin.book.examples.addons.charts.AxesExample;
import com.vaadin.book.examples.addons.charts.BoxPlotExample;
import com.vaadin.book.examples.addons.charts.ChartTypesExample;
import com.vaadin.book.examples.addons.charts.Charts3DExample;
import com.vaadin.book.examples.addons.charts.ChartsExample;
import com.vaadin.book.examples.addons.charts.FunnelExample;
import com.vaadin.book.examples.addons.charts.GaugeExample;
import com.vaadin.book.examples.addons.charts.HeatMapExample;
import com.vaadin.book.examples.addons.charts.PyramidExample;
import com.vaadin.book.examples.addons.charts.ScatterExample;
import com.vaadin.book.examples.addons.charts.TimelineExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAContainerExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAFieldFactoryExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAFilteringExample;
import com.vaadin.book.examples.addons.jpacontainer.JPAHierarhicalExample;
import com.vaadin.book.examples.addons.spreadsheet.SpreadsheetComponentsExample;
import com.vaadin.book.examples.addons.spreadsheet.SpreadsheetExample;
import com.vaadin.book.examples.addons.spreadsheet.SpreadsheetManagementExample;
import com.vaadin.book.examples.addons.spreadsheet.SpreadsheetTablesExample;
import com.vaadin.book.examples.advanced.BrowserInfoExample;
import com.vaadin.book.examples.advanced.DebugWindowExample;
import com.vaadin.book.examples.advanced.EmbeddingExample;
import com.vaadin.book.examples.advanced.FontIconExample;
import com.vaadin.book.examples.advanced.I18NExample;
import com.vaadin.book.examples.advanced.JSAPIExample;
import com.vaadin.book.examples.advanced.LoggingExample;
import com.vaadin.book.examples.advanced.NavigatorExample;
import com.vaadin.book.examples.advanced.PopupWindowExample;
import com.vaadin.book.examples.advanced.PrintingExample;
import com.vaadin.book.examples.advanced.RequestHandlerExample;
import com.vaadin.book.examples.advanced.ServletRequestListenerExample;
import com.vaadin.book.examples.advanced.ShortcutExample;
import com.vaadin.book.examples.advanced.UriFragmentExample;
import com.vaadin.book.examples.advanced.cdi.CDIExample;
import com.vaadin.book.examples.advanced.dd.ComponentDnDExample;
import com.vaadin.book.examples.advanced.dd.DiagramDnDExample;
import com.vaadin.book.examples.advanced.dd.DragNDropTreeExample;
import com.vaadin.book.examples.advanced.dd.TreeAndTableExample;
import com.vaadin.book.examples.advanced.push.PushExample;
import com.vaadin.book.examples.advanced.spring.MySpringUI;
import com.vaadin.book.examples.application.ArchitectureExample;
import com.vaadin.book.examples.application.BuildingUIExample;
import com.vaadin.book.examples.application.ErrorIndicatorExample;
import com.vaadin.book.examples.application.EventListenerExample;
import com.vaadin.book.examples.application.LayoutClickListenerExample;
import com.vaadin.book.examples.application.LifecycleExample;
import com.vaadin.book.examples.application.NotificationExample;
import com.vaadin.book.examples.application.ResourceExample;
import com.vaadin.book.examples.application.calc.MVPCalculator;
import com.vaadin.book.examples.application.declarative.DeclarativeUIExample;
import com.vaadin.book.examples.client.ColorpickerExample;
import com.vaadin.book.examples.client.MyComponentExample;
import com.vaadin.book.examples.client.js.JSIntegrationExample;
import com.vaadin.book.examples.component.BufferingExample;
import com.vaadin.book.examples.component.ButtonExample;
import com.vaadin.book.examples.component.CalendarExample;
import com.vaadin.book.examples.component.CheckBoxExample;
import com.vaadin.book.examples.component.ColorPickerExample;
import com.vaadin.book.examples.component.ComboBoxExample;
import com.vaadin.book.examples.component.CustomComponentExample;
import com.vaadin.book.examples.component.CustomFieldExample;
import com.vaadin.book.examples.component.DateFieldExample;
import com.vaadin.book.examples.component.EmbeddedExample;
import com.vaadin.book.examples.component.ImageExample;
import com.vaadin.book.examples.component.LabelExample;
import com.vaadin.book.examples.component.LinkExample;
import com.vaadin.book.examples.component.ListSelectExample;
import com.vaadin.book.examples.component.MenuBarExample;
import com.vaadin.book.examples.component.NativeSelectExample;
import com.vaadin.book.examples.component.OptionGroupExample;
import com.vaadin.book.examples.component.PasswordFieldExample;
import com.vaadin.book.examples.component.ProgressBarExample;
import com.vaadin.book.examples.component.RichTextAreaExample;
import com.vaadin.book.examples.component.SelectExample;
import com.vaadin.book.examples.component.SliderExample;
import com.vaadin.book.examples.component.TextAreaExample;
import com.vaadin.book.examples.component.TextChangeEventsExample;
import com.vaadin.book.examples.component.TextFieldExample;
import com.vaadin.book.examples.component.TreeExample;
import com.vaadin.book.examples.component.TreeTableExample;
import com.vaadin.book.examples.component.TwinColSelectExample;
import com.vaadin.book.examples.component.UploadExample;
import com.vaadin.book.examples.component.grid.ColumnsExample;
import com.vaadin.book.examples.component.grid.GridDataBindingExample;
import com.vaadin.book.examples.component.grid.GridEditingExample;
import com.vaadin.book.examples.component.grid.GridExample;
import com.vaadin.book.examples.component.grid.GridStyleExample;
import com.vaadin.book.examples.component.grid.HierarchicalGridExample;
import com.vaadin.book.examples.component.grid.RendererExample;
import com.vaadin.book.examples.component.properties.CaptionExample;
import com.vaadin.book.examples.component.properties.DescriptionExample;
import com.vaadin.book.examples.component.properties.EnabledExample;
import com.vaadin.book.examples.component.properties.FocusExample;
import com.vaadin.book.examples.component.properties.IconExample;
import com.vaadin.book.examples.component.properties.ListenerExample;
import com.vaadin.book.examples.component.properties.LocaleExample;
import com.vaadin.book.examples.component.properties.ReadOnlyExample;
import com.vaadin.book.examples.component.properties.RequiredExample;
import com.vaadin.book.examples.component.properties.StyleNameExample;
import com.vaadin.book.examples.component.properties.ValidationExample;
import com.vaadin.book.examples.component.properties.VisibleExample;
import com.vaadin.book.examples.component.table.GeneratedColumnExample;
import com.vaadin.book.examples.component.table.TableEditingExample;
import com.vaadin.book.examples.component.table.TableExample;
import com.vaadin.book.examples.datamodel.BeanContainerExample;
import com.vaadin.book.examples.datamodel.BeanItemContainerExample;
import com.vaadin.book.examples.datamodel.BeanValidationExample;
import com.vaadin.book.examples.datamodel.ContainerFilterExample;
import com.vaadin.book.examples.datamodel.ConverterExample;
import com.vaadin.book.examples.datamodel.FieldGroupExample;
import com.vaadin.book.examples.datamodel.FilesystemContainerExample;
import com.vaadin.book.examples.datamodel.HierarchicalExample;
import com.vaadin.book.examples.datamodel.IndexedContainerExample;
import com.vaadin.book.examples.datamodel.ItemExample;
import com.vaadin.book.examples.datamodel.PropertyExample;
import com.vaadin.book.examples.gettingstarted.NewProjectExample;
import com.vaadin.book.examples.intro.HelloWorldExample;
import com.vaadin.book.examples.layout.AbsoluteLayoutExample;
import com.vaadin.book.examples.layout.AccordionExample;
import com.vaadin.book.examples.layout.AlignmentExample;
import com.vaadin.book.examples.layout.CssLayoutExample;
import com.vaadin.book.examples.layout.CustomLayoutExample;
import com.vaadin.book.examples.layout.ExpandRatioExample;
import com.vaadin.book.examples.layout.FormLayoutExample;
import com.vaadin.book.examples.layout.GridLayoutExample;
import com.vaadin.book.examples.layout.LayoutExample;
import com.vaadin.book.examples.layout.LayoutFeaturesExample;
import com.vaadin.book.examples.layout.MarginExample;
import com.vaadin.book.examples.layout.OrderedLayoutExample;
import com.vaadin.book.examples.layout.PanelExample;
import com.vaadin.book.examples.layout.PopupViewExample;
import com.vaadin.book.examples.layout.SpacingExample;
import com.vaadin.book.examples.layout.SplitPanelExample;
import com.vaadin.book.examples.layout.SubWindowExample;
import com.vaadin.book.examples.layout.TabSheetExample;
import com.vaadin.book.examples.misc.ExceptionExamples;
import com.vaadin.book.examples.misc.I18nPrototype;
import com.vaadin.book.examples.misc.SerializationExample;
import com.vaadin.book.examples.themes.BuiltInThemeExample;
import com.vaadin.book.examples.themes.ResponsiveExample;
import com.vaadin.book.examples.themes.ScssThemeExample;
import com.vaadin.book.examples.themes.ThemeExample;
import com.vaadin.book.examples.themes.ThemeTricksExample;

public class BookExampleLibrary {
    private static BookExampleLibrary instance = null;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * Gets the Book example library singleton instance.
     * 
     * @return the singleton instance
     */
    synchronized public static BookExampleLibrary getInstance(File baseDirectory) {
        if (instance == null)
            instance = new BookExampleLibrary(baseDirectory);
        return instance;
    }

    /**
     * Returns all example items, including redirection items.
     * 
     * @return array of example objects
     */
    public AbstractExampleItem[] getAllExamples() {
        return examples;
    }

    /**
     * Returns only actual example objects, no redirection items.
     * 
     * @return list of BookExample objects
     */
    public List<BookExample> getExamples() {
        ArrayList<BookExample> exampleList = new ArrayList<BookExample>();
        for (int i=0; i<examples.length; i++)
            if (examples[i] instanceof BookExample)
            exampleList.add((BookExample) examples[i]);
        return exampleList;
    }

    /** Constructor. */
    private BookExampleLibrary(File baseDirectory) {
        logger.info("book-examples INFO: Loading example data...");

        for (BookExample e: getExamples()) {
            e.loadExample(baseDirectory);
            // BookExamplesUI.getLogger().info("book-examples INFO: " + e.getExampleId());
        }

        logger.info("book-examples INFO: Loaded " + getExamples().size() + " examples.");
    }

    final AbstractExampleItem examples[] = {
            new ExampleCtgr("intro", "Chapter 1. Introduction"),
            new ExampleCtgr("intro.walkthrough-", "Example Application Walkthrough"),
            new BookExample("intro.walkthrough.helloworld", "Hello World", HelloWorldExample.class),
            new ExampleCtgr("gettingstarted", "Chapter 2. Getting Started"),
            new ExampleCtgr("gettingstarted.firstproject-", "First Project"),
            new BookExample("gettingstarted.firstproject.newproject", "Initial New Project", NewProjectExample.class),
            // new BookExample("gettingstarted.scala", "Scala UI", NewProjectExample.class),
            new ExampleCtgr("application", "Chapter 4. Writing a Web Application"),
            new ExampleCtgr("application.architecture-", "Building the UI"),
            new BookExample("application.architecture.hierarchical", "Hierarchical Composition", BuildingUIExample.class),
            new ExampleCtgr("application.architecture.composition", "Compositing Components"),
            new BookExample("application.architecture.composition.layout", "With a Layout", ArchitectureExample.class),
            new BookExample("application.architecture.composition.customcomponent", "With a CustomComponent", ArchitectureExample.class),
            new BookExample("application.architecture.globalaccess", "Accessing UI, Page, and Session", ArchitectureExample.class),
            new ExampleCtgr("application.declarative-", "Designing UIs Declaratively"),
            new BookExample("application.declarative.basic", "Basic Use", DeclarativeUIExample.class),
            new BookExample("application.declarative.context", "Design Context", DeclarativeUIExample.class),
            new BookExample("application.declarative.designroot", "Basic Use of Design Root", DeclarativeUIExample.class),
            new BookExample("application.declarative.customcomponent", "Custom Components", DeclarativeUIExample.class),
            new BookExample("application.declarative.inline", "Inline Content and Data", DeclarativeUIExample.class),
            new BookExample("application.declarative.writing", "Writing a Design", DeclarativeUIExample.class),
            new BookExample("application.declarative.writingdesignroot", "Writing a Design Root", DeclarativeUIExample.class),
            new ExampleCtgr("application.eventlistener-", "Handling Events with Listeners"),
            new BookExample("application.eventlistener.anonymous", "Anonymous Class", EventListenerExample.class),
            new BookExample("application.eventlistener.constructor", "Defining in Constructor", EventListenerExample.class),
            new BookExample("application.eventlistener.java8", "Lambda Expressions in Java 8", EventListenerExample.class),
            new BookExample("application.eventlistener.java8differentiation", "Differentiating in Java 8", EventListenerExample.class),
            new BookExample("application.eventlistener.classlistener", "A Class Listener", EventListenerExample.class),
            new BookExample("application.eventlistener.differentiation", "Differentiating Between Sources", EventListenerExample.class),
            new BookExample("application.eventlistener.another", "Yet Another Typical Case", EventListenerExample.class),
            new ExampleCtgr("application.eventlistener.clicklistener-", "Layout Click Listener"),
            new BookExample("application.eventlistener.clicklistener.basic", "Basic Use", LayoutClickListenerExample.class),
            new BookExample("application.eventlistener.clicklistener.doubleclick", "Double Click", LayoutClickListenerExample.class),
            new ExampleCtgr("application.resources-", "Referencing Resources"),
            new BookExample("application.resources.fileresource", "File Resources", ResourceExample.class),
            new BookExample("application.resources.classresource", "Class Resources", ResourceExample.class),
            //new BookExample("application.resources.classresource.class-ref", "Getting the Reference", ResourceExample.class),
            new BookExample("application.resources.themeresource", "Theme Resources", ResourceExample.class),
            new ExampleCtgr("application.resources.externalresource-", "External Resources"),
            new BookExample("application.resources.externalresource.local", "Local Static Resources", ResourceExample.class),
            new BookExample("application.resources.externalresource.relative", "Relative External Resources", ResourceExample.class),
            new ExampleCtgr("application.errors-", "Handling Errors"),
            new ExampleCtgr("application.errors.error-indicator-", "Error Indicator and Message"),
            new BookExample("application.errors.error-indicator.basic", "Basic Use", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.setting", "Setting", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.clearing", "Clearing", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.form", "Form Error", ErrorIndicatorExample.class),
            new BookExample("application.errors.error-indicator.errorhandler", "Error Handler", ErrorIndicatorExample.class),
            new ExampleCtgr("application.errors.notification-", "Notifications"),
            new BookExample("application.errors.notification.shorthand", "Shorthand Use", NotificationExample.class),
            new BookExample("application.errors.notification.normal", "Normal Use", NotificationExample.class),
            new BookExample("application.errors.notification.error", "Error Notification", NotificationExample.class),
            new BookExample("application.errors.notification.customization", "Customization", NotificationExample.class),
            new ExampleCtgr("application.lifecycle-", "Application Lifecycle"),
            new BookExample("application.lifecycle.expiration", "UI and Session Expiration", LifecycleExample.class),
            new BookExample("application.lifecycle.closing", "Closing the Session", LifecycleExample.class),
            new BookExample("application.lifecycle.closingall", "Closing Session and All UIs", LifecycleExample.class),
            new BookExample("application.lifecycle.uiclosing", "Closing a UI", LifecycleExample.class),
            new ExampleCtgr("component.features-", "Common Component Features"),
            new ExampleCtgr("component", "Chapter 5. User Interface Components"),
            new ExampleCtgr("component.features-", "Common Component Features"),
            new ExampleCtgr("component.features.caption-", "Caption"),
            new BookExample("component.features.caption.overview", "Overview", CaptionExample.class),
            new BookExample("component.features.caption.layout", "Management by Layout", CaptionExample.class),
            new BookExample("component.features.caption.self", "Management by the Component", CaptionExample.class),
            new BookExample("component.features.caption.special", "Unicode Characters", CaptionExample.class),
            new BookExample("component.features.caption.styling", "Styling with CSS", CaptionExample.class),
            new ExampleCtgr("component.features.description-", "Description & Tooltips"),
            new BookExample("component.features.description.plain", "Plain Text", DescriptionExample.class),
            new BookExample("component.features.description.richtext", "Rich Text", DescriptionExample.class),
            new BookExample("component.features.description.customization", "Tooltip Parameters", DescriptionExample.class),
            new ExampleCtgr("component.features.enabled-", "Enabled/Disabled"),
            new BookExample("component.features.enabled.simple", "Basic use", EnabledExample.class),
            new BookExample("component.features.enabled.styling", "Styling with CSS", EnabledExample.class),
            new ExampleCtgr("component.features.focusable-", "Focus and Tabulator Index"),
            new BookExample("component.features.focusable.focus", "Setting focus", FocusExample.class),
            new BookExample("component.features.focusable.tabindex", "Setting tab index", FocusExample.class),
            new BookExample("component.features.focusable.focusevent", "Focus and Blur Events", FocusExample.class),
            new BookExample("component.features.focusable.alternatingfocus", "Focus Alternation", FocusExample.class),
            new BookExample("component.features.focusable.css", "Styling with CSS", FocusExample.class),
            new ExampleCtgr("component.features.icon-", "Icon"),
            new BookExample("component.features.icon.basic", "Basic Use", IconExample.class),
            new BookExample("component.features.listener", "Component Listener Interface", ListenerExample.class),
            new ExampleCtgr("component.features.locale-", "Locale"),
            new BookExample("component.features.locale.simple", "Setting locale", LocaleExample.class),
            new BookExample("component.features.locale.get-attach", "Getting Locale in attach()", LocaleExample.class),
            new BookExample("component.features.locale.get-ui", "Getting the UI locale", LocaleExample.class),
            new BookExample("component.features.locale.selection", "Language selection", LocaleExample.class),
            new ExampleCtgr("component.features.readonly-", "Read-Only"),
            new BookExample("component.features.readonly.simple", "Basic use", ReadOnlyExample.class),
            new BookExample("component.features.readonly.layouts", "Layouts", ReadOnlyExample.class),
            new BookExample("component.features.readonly.styling", "Styling with CSS", ReadOnlyExample.class),
            new ExampleCtgr("component.features.stylename-", "Style Name"),
            new BookExample("component.features.stylename.add", "Adding", StyleNameExample.class),
            new BookExample("component.features.stylename.set", "Setting I", StyleNameExample.class),
            new BookExample("component.features.stylename.set-changing", "Setting II", StyleNameExample.class),
            new ExampleCtgr("component.features.visible-", "Visible/Invisible"),
            new BookExample("component.features.visible.simple", "Basic use", VisibleExample.class),
            new BookExample("component.features.visible.inlayout", "Invisible in Layout", VisibleExample.class),
            new BookExample("component.features.visible.styling", "Styling with CSS", VisibleExample.class),
            new ExampleCtgr("component.field-", "Field Component Features"),
            new ExampleCtgr("component.field.required-", "Required"),
            new BookExample("component.field.required.basic", "Basic use", RequiredExample.class),
            new BookExample("component.field.required.beanfields", "Required Bean Fields", RequiredExample.class),
            new BookExample("component.field.required.caption", "With Caption", RequiredExample.class),
            new BookExample("component.field.required.nocaption", "Required but no Caption", RequiredExample.class),
            // new BookExample("component.field.required.styling", "Styling with CSS", RequiredExample.class),
            new ExampleCtgr("component.field.buffering-", "Buffering"),
            new BookExample("component.field.buffering.basic", "Basic Use", BufferingExample.class),
            new ExampleCtgr("component.field.validation-", "Validation"),
            new BookExample("component.field.validation.basic", "Basic Use", ValidationExample.class),
            new BookExample("component.field.validation.explicit", "Explicit Validation", ValidationExample.class),
            new BookExample("component.field.validation.automatic", "Automatic vs Non-Automatic", ValidationExample.class),
            new BookExample("component.field.validation.integer", "Integer Range", ValidationExample.class),
            new BookExample("component.field.validation.customvalidator", "Custom Validator", ValidationExample.class),
            new ExampleCtgr("component.label-", "Label"),
            new BookExample("component.label.basic", "Basic Use", LabelExample.class),
            new BookExample("component.label.getset", "Getting and Setting Value", LabelExample.class),
            new BookExample("component.label.wrap", "Line Wrap", LabelExample.class),
            new ExampleCtgr("component.label.content-modes-", "Content Modes"),
            new BookExample("component.label.content-modes.contentmodes", "Available Content Modes", LabelExample.class),
            new BookExample("component.label.content-modes.html", "HTML mode", LabelExample.class),
            new BookExample("component.label.content-modes.javascript", "JavaScript in HTML mode", LabelExample.class),
            new BookExample("component.label.binding", "Binding to an Editor", LabelExample.class),
            new BookExample("component.label.delegation", "Delegation is Unsupported", LabelExample.class),
            new ExampleCtgr("component.label.spacing-", "Spacing with Label"),
            new BookExample("component.label.spacing.non-breaking", "Non-Breaking Space", LabelExample.class),
            new BookExample("component.label.spacing.preformatted", "Preformatted Text", LabelExample.class),
            new BookExample("component.label.spacing.adjustable", "Adjustable Spacing", LabelExample.class),
            new BookExample("component.label.spacing.expanding", "Expanding Spacer", LabelExample.class),
            new ExampleCtgr("component.label.styling-", "Styling with CSS"),
            new BookExample("component.label.styling.css", "Basic Styling", LabelExample.class),
            new BookExample("component.label.styling.predefinedstyles", "Predefined Styles", LabelExample.class),
            new BookExample("component.label.styling.switchbutton", "Switch Button", LabelExample.class),
            new ExampleCtgr("component.link-", "Link"),
            new BookExample("component.link.basic", "Basic Use", LinkExample.class),
            new BookExample("component.link.target", "Hyperlink Target", LinkExample.class),
            new BookExample("component.link.popup", "Open in Popup Window", LinkExample.class),
            new BookExample("component.link.css", "Styling with CSS", LinkExample.class),
            new ExampleCtgr("component.textfield-", "TextField"),
            new BookExample("component.textfield.basic", "Basic Use", TextFieldExample.class),
            new BookExample("component.textfield.inputhandling", "Handling Input", TextFieldExample.class),
            new BookExample("component.textfield.valuetype", "Value Type", TextFieldExample.class),
            new BookExample("component.textfield.databinding", "Binding to an Object", TextFieldExample.class),
            new BookExample("component.textfield.beanbinding", "Binding to a Bean Property", TextFieldExample.class),
            new BookExample("component.textfield.nullvaluerepresentation", "Null Value Representation", TextFieldExample.class),
            new BookExample("component.textfield.widthfitting", "Fitting Width", TextFieldExample.class),
            new ExampleCtgr("component.textfield.textchangeevents-", "Text Change Events"),
            new BookExample("component.textfield.textchangeevents.counter", "Counter", TextChangeEventsExample.class),
            new BookExample("component.textfield.textchangeevents.filtering", "Filtering", TextChangeEventsExample.class),
            new BookExample("component.textfield.selectall", "Selecting All Text", TextFieldExample.class),
            new BookExample("component.textfield.cursorposition", "Setting Cursor Position", TextFieldExample.class),
            new BookExample("component.textfield.css", "CSS Styling", TextFieldExample.class),
            new BookExample("component.textfield.styles", "Component Styles", TextFieldExample.class),
            new ExampleCtgr("component.textarea-", "TextArea"),
            new BookExample("component.textarea.basic", "Basic Use", TextAreaExample.class),
            new BookExample("component.textarea.wordwrap", "Word Wrap", TextAreaExample.class),
            new BookExample("component.textarea.css", "CSS Styling", TextAreaExample.class),
            new ExampleCtgr("component.passwordfield-", "PasswordField"),
            new BookExample("component.passwordfield.basic", "Basic Use", PasswordFieldExample.class),
            new BookExample("component.passwordfield.css", "CSS Styling", PasswordFieldExample.class),
            new ExampleCtgr("component.richtextarea-", "RichTextArea"),
            new BookExample("component.richtextarea.basic", "Basic Use", RichTextAreaExample.class),
            new ExampleCtgr("component.datefield-", "Date and Time Input"),
            new BookExample("component.datefield.basic", "Basic Use of DateField", DateFieldExample.class),
            new ExampleCtgr("component.datefield.popupdatefield", "PopupDateField"),
            new BookExample("component.datefield.popupdatefield.basicpopup", "Basic Use", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.formatting", "Formatting", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.customerror", "Customizing the Error", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.parsing", "Parsing a Date", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.validation", "Validating a Date", DateFieldExample.class),
            new BookExample("component.datefield.popupdatefield.inputprompt", "Input Prompt", DateFieldExample.class),
            new BookExample("component.datefield.inlinedatefield", "InlineDateField", DateFieldExample.class),
            new BookExample("component.datefield.resolution", "Time Resolution", DateFieldExample.class),
            new BookExample("component.datefield.weeknumbers", "Week Numbers", DateFieldExample.class),
            new ExampleCtgr("component.calendar-", "Calendar"),
            new BookExample("component.calendar.basic", "Basic Use", CalendarExample.class),
            new BookExample("component.calendar.rangeselect", "Selecting a range", CalendarExample.class),
            new BookExample("component.calendar.contextmenu", "Context Menu", CalendarExample.class),
            new BookExample("component.calendar.monthlyview", "Monthly View", CalendarExample.class),
            new BookExample("component.calendar.beanitemcontainer", "Binding to BeanItemContainer", CalendarExample.class),
            new BookExample("component.calendar.jpacontainer", "Binding to JPAContainer", CalendarExample.class),
            new ExampleCtgr("component.button-", "Button"),
            new BookExample("component.button.basic", "Basic Use", ButtonExample.class),
            new BookExample("component.button.html", "Caption in HTML", ButtonExample.class),
            new BookExample("component.button.link", "Button Looks Like a Link", ButtonExample.class),
            new BookExample("component.button.styles", "Component Styles", ButtonExample.class),
            new ExampleCtgr("component.checkbox-", "CheckBox"),
            new BookExample("component.checkbox.basic", "Basic Use", CheckBoxExample.class),
            new BookExample("component.checkbox.switching", "Switching", CheckBoxExample.class),
            new BookExample("component.checkbox.beanbinding", "Bean Binding", CheckBoxExample.class),

            new ExampleCtgr("component.select", "Selecting Items"),
            new ExampleCtgr("component.select.common-", "Basic Selection Features"),
            new BookExample("component.select.common.basic", "Basic Use", SelectExample.class),
            new ExampleCtgr("component.select.common.adding", "Adding Items"),
            new BookExample("component.select.common.adding.givencaption", "Use Given Item ID as Caption", SelectExample.class),
            new BookExample("component.select.common.adding.givenitemid", "Given Item ID", SelectExample.class),
            new BookExample("component.select.common.adding.generateditemid", "Generated Item ID", SelectExample.class),
            new BookExample("component.select.common.container", "Binding to a Container", SelectExample.class),
            new ExampleCtgr("component.select.common.captions", "Caption Modes"),
            new BookExample("component.select.common.captions.explicitdefaultsid", "Explicit Caption (Defaults to ID)", SelectExample.class),
            new BookExample("component.select.common.captions.captionmodeid", "Use ID as Caption", SelectExample.class),
            new BookExample("component.select.common.captions.captionproperty", "Use Property for Caption", SelectExample.class),
            new BookExample("component.select.common.preselecting", "Preselecting an Item", SelectExample.class),
            new BookExample("component.select.common.multiselect", "Multiple Selection", SelectExample.class),
            new BookExample("component.select.common.icons", "Item Icons", SelectExample.class),
            new ExampleCtgr("component.select.listselect-", "ListSelect"),
            new BookExample("component.select.listselect.basic", "Basic Use", ListSelectExample.class),
            new BookExample("component.select.listselect.multiselect", "Multiple Selection Mode", ListSelectExample.class),
            new ExampleCtgr("component.select.nativeselect-", "NativeSelect"),
            new BookExample("component.select.nativeselect.basic", "Basic Use", NativeSelectExample.class),
            new ExampleCtgr("component.select.combobox-", "ComboBox"),
            new BookExample("component.select.combobox.basic", "Basic Use", ComboBoxExample.class),
            new BookExample("component.select.combobox.filtering", "Filtering", ComboBoxExample.class),
            new BookExample("component.select.combobox.enumtype", "Using an Enum Type", ComboBoxExample.class),
            new BookExample("component.select.combobox.preselecting", "Preselecting an Item", ComboBoxExample.class),
            new BookExample("component.select.combobox.newitemsallowed", "Adding New Items", ComboBoxExample.class),
            new BookExample("component.select.combobox.newitemhandler", "Handling New Items", ComboBoxExample.class),
            new BookExample("component.select.combobox.nullselection", "Null Selection", ComboBoxExample.class),
            new BookExample("component.select.combobox.resetselection", "Resetting Selection", ComboBoxExample.class),
            new BookExample("component.select.combobox.styles", "Component Styles", ComboBoxExample.class),
            //new ExampleItem("component.select.hierarchical", "Hierarchical selection", SelectExample.class),
            new ExampleCtgr("component.select.optiongroup-", "OptionGroup"),
            new BookExample("component.select.optiongroup.basic", "Basic Use", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.singlemultiple", "Single and Multiple Selection", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.icons", "Icons", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.disabling", "Disabling Items", OptionGroupExample.class),
            new BookExample("component.select.optiongroup.styling", "Styling", OptionGroupExample.class),
            new ExampleCtgr("component.select.twincolselect-", "TwinColSelect"),
            new BookExample("component.select.twincolselect.basic", "Basic Use", TwinColSelectExample.class),
            new BookExample("component.select.twincolselect.captions", "Column Captions", TwinColSelectExample.class),
            // new BookExample("component.select.twincolselect.icons", "Item Icons", TwinColSelectExample.class),
            new BookExample("component.select.twincolselect.css", "Styling with CSS", TwinColSelectExample.class),
            new ExampleCtgr("component.table-", "Table"),
            //new ExampleItem("component.table.overview-", "Overview"),
            //new ExampleItem("component.table.overview.layouts", "Layouts in Table", TableExample.class),
            new RedirctItem("component.table.overview.component", "component.table.components.components"),
            new BookExample("component.table.basic", "Basic Use", TableExample.class),
            new ExampleCtgr("component.table.selecting-", "Selecting Items"),
            new BookExample("component.table.selecting.singleselect", "Single Selection Mode", TableExample.class),
            new BookExample("component.table.selecting.multiselect", "Multiple Selection Mode", TableExample.class),
            new ExampleCtgr("component.table.features-", "Table Features"),
            new BookExample("component.table.features.columnresize", "Resizing Columns", TableExample.class),
            new BookExample("component.table.features.columnreordering", "Reordering Columns", TableExample.class),
            new BookExample("component.table.features.columncollapsing", "Collapsing (Hiding) Columns", TableExample.class),
            new RedirctItem("component.table.features.varyingrows", "component.table.components.varyingrows"),
            new RedirctItem("component.table.features.cellrenderer", "component.table.components.cellrenderer"),
            new BookExample("component.table.features.scrolltoitem", "Scrolling to Specific Item", TableExample.class),
            new BookExample("component.table.features.removeallitems", "Removing All Items", TableExample.class),
            new BookExample("component.table.features.detailsshrink", "Shrinking to Show Details", TableExample.class),
            new ExampleCtgr("component.table.components-", "Components in Table"),
            new BookExample("component.table.components.components", "Basic Use", TableExample.class),
            new BookExample("component.table.components.components2", "Another Example", TableExample.class),
            new BookExample("component.table.components.nestedtables", "Nested Tables", TableExample.class),
            new BookExample("component.table.components.interactingcomponents", "Interaction within row", TableExample.class),
            new BookExample("component.table.components.beancomponents", "Bean Components", TableExample.class),
            new BookExample("component.table.components.varyingrows", "Varying Row Heights", TableExample.class),
            new BookExample("component.table.components.varyingheightlabels", "Multi-row Labels", TableExample.class),
            new BookExample("component.table.components.cellrenderer", "Pattern: Cell Renderer", TableExample.class),
            new ExampleCtgr("component.table.headersfooters-", "Column Headers and Footers"),
            new BookExample("component.table.headersfooters.headers", "Headers", TableExample.class),
            new BookExample("component.table.headersfooters.fakeheaders", "Fake Headers", TableExample.class),
            new BookExample("component.table.headersfooters.htmlheaders", "HTML in Headers", TableExample.class),
            new ExampleCtgr("component.table.headersfooters.footers-", "Footers"),
            new BookExample("component.table.headersfooters.footers.footer-basic", "Calculating Sum", TableExample.class),
            new BookExample("component.table.headersfooters.footers.footer-sum", "Interactive Calculation", TableExample.class),
            new BookExample("component.table.headersfooters.headerclick", "Handling Clicks on Headers and Footers", TableExample.class),
            new BookExample("component.table.rowheaders", "Row Headers", TableExample.class),
            new BookExample("component.table.contextmenu", "Context Menu", TableExample.class),
            new BookExample("component.table.binding", "Data Binding", TableExample.class),
            new BookExample("component.table.binding.editorform", "Item Editor Form", TableExample.class),
            new BookExample("component.table.sorting-", "Sorting", TableExample.class),
            new BookExample("component.table.sorting.reversebyindex", "Show in reverse indexed order", TableExample.class),
            new BookExample("component.table.editable-", "Editable Mode", TableExample.class),
            new BookExample("component.table.editable.editable", "Table in Editable Mode", TableEditingExample.class),
            new BookExample("component.table.editable.buffering", "Buffering", TableEditingExample.class),
            new BookExample("component.table.editable.spreadsheet", "Editing on Demand", TableEditingExample.class),
            new BookExample("component.table.editable.editableheights", "Height of Editables", TableEditingExample.class),
            new BookExample("component.table.editable.adding", "Adding New Items", TableEditingExample.class),
            new BookExample("component.table.editable.longtable", "Editing a Long Table", TableEditingExample.class),
            //new ExampleItem("component.table.editable.combobox", "ComboBoxes in Table (test)", TableExample.class),
            new BookExample("component.table.ratios", "Column Expand Ratios", TableExample.class),
            // new ExampleItem("component.table.filtering", "Filtering", TableExample.class),
            new ExampleCtgr("component.table.generatedcolumns-", "Generated Columns"),
            new BookExample("component.table.generatedcolumns.extended", "Extended Example", GeneratedColumnExample.class),
            new BookExample("component.table.generatedcolumns.accessing", "Accessing Generated Components", GeneratedColumnExample.class),
            new BookExample("component.table.generatedcolumns.layoutclick", "Layout Click Problem", GeneratedColumnExample.class),
            new ExampleCtgr("component.table.columnformatting-", "Formatting Table Columns"),
            new BookExample("component.table.columnformatting.columnformatting-simple", "Simple Case", TableExample.class),
            new BookExample("component.table.columnformatting.columnformatting-extended", "Extended Example", TableExample.class),
            // new ExampleItem("component.table.columnformatting.columnformatting-component", "Formatting Value as Component", TableExample.class),
            new ExampleCtgr("component.table.styling-", "Styling with CSS"),
            new BookExample("component.table.styling.cellstylegenerator", "Cell Style Generator", TableExample.class),
            new BookExample("component.table.styling.cssinjection", "CSS Injection", TableExample.class),
            // new ExampleItem("component.table.propertyformatter", "Formatting Columns with PropertyFormatter", TableExample.class),
            new ExampleCtgr("component.tree-", "Tree"),
            new BookExample("component.tree.basic", "Basic Use", TreeExample.class),
            new BookExample("component.tree.expanding", "Expanding Nodes", TreeExample.class),
            new BookExample("component.tree.expandlistener", "Node Expand Events", TreeExample.class),
            new BookExample("component.tree.collapselistener", "Node Collapse Events", TreeExample.class),
            new BookExample("component.tree.disable", "Disabling items", TreeExample.class),
            new BookExample("component.tree.itemclicklistener", "Item Click Events", TreeExample.class),
            new BookExample("component.tree.itemstylegenerator", "Item Style Generator", TreeExample.class),
            new ExampleCtgr("component.treetable-", "TreeTable"),
            new BookExample("component.treetable.basic", "Basic Use", TreeTableExample.class),
            new BookExample("component.treetable.components", "Components in Tree", TreeTableExample.class),
            new BookExample("component.treetable.editable", "Editable TreeTable", TreeTableExample.class),
            new BookExample("component.treetable.additemafter", "Adding Item After Another", TreeTableExample.class),
            new BookExample("component.treetable.draganddrop", "Drag and Drop", TreeTableExample.class),
            new BookExample("component.treetable.big", "Lots of Data", TreeTableExample.class),
            new BookExample("component.treetable.itemstylegenerator", "Cell Style for Tree Column", TreeTableExample.class),
            new ExampleCtgr("component.grid-", "Grid"),
            new BookExample("component.grid.basic", "Basic Use", GridExample.class),
            new BookExample("component.grid.features", "Summary of Features", GridExample.class),
            new ExampleCtgr("component.grid.databinding-", "Data Binding"),
            new BookExample("component.grid.databinding.array", "Input from Array", GridDataBindingExample.class),
            new BookExample("component.grid.databinding.collection", "Binding to a Collection", GridDataBindingExample.class),
            new BookExample("component.grid.databinding.hierarchical", "Hierarchical Data", GridDataBindingExample.class),
            new ExampleCtgr("component.grid.selection-", "Handling Selection"),
            new BookExample("component.grid.selection.multi", "Multi-Select Mode", GridExample.class),
            new ExampleCtgr("component.grid.columns-", "Columns"),
            new BookExample("component.grid.columns.summary", "Summary", ColumnsExample.class),
            new BookExample("component.grid.columns.expandratio", "Expand Ratios", ColumnsExample.class),
            new ExampleCtgr("component.grid.renderer-", "Renderers"),
            new BookExample("component.grid.renderer.summary", "Summary", RendererExample.class),
            new BookExample("component.grid.renderer.button", "Button Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.image", "Image Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.imagebyname", "Image Renderer By Filename", RendererExample.class),
            new BookExample("component.grid.renderer.date", "Date Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.html", "HTML Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.number", "Number Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.progressbar", "Progress Bar Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.text", "Text Renderer", RendererExample.class),
            new BookExample("component.grid.renderer.custom", "Custom Renderer", RendererExample.class),
            new BookExample("component.grid.filtering", "Filtering", GridExample.class),
            new ExampleCtgr("component.grid.sorting-", "Sorting"),
            new BookExample("component.grid.sorting.sort", "Basic Use", GridExample.class),
            new BookExample("component.grid.sorting.sortdirection", "Sort Direction", GridExample.class),
            new ExampleCtgr("component.grid.editing-", "Editing"),
            new BookExample("component.grid.editing.basic", "Basic Use", GridEditingExample.class),
            new BookExample("component.grid.editing.editorfields", "Setting Editor Fields", GridEditingExample.class),
            new BookExample("component.grid.editing.commit", "Handling Commit", GridEditingExample.class),
            new BookExample("component.grid.editing.entersave", "Save on Enter", GridEditingExample.class),
            new BookExample("component.grid.editing.fieldgroup", "FieldGroup", GridEditingExample.class),
            new ExampleCtgr("component.grid.stylegeneration-", "Style Generators"),
            new BookExample("component.grid.stylegeneration.rowstyle", "Row Styles", GridStyleExample.class),
            new BookExample("component.grid.stylegeneration.cellstyle", "Cell Styles", GridStyleExample.class),
            new ExampleCtgr("component.grid.tricks-", "Tricks"),
            new BookExample("component.grid.tricks.hierarchical", "Hierarchical Grid", HierarchicalGridExample.class),
            new ExampleCtgr("component.menubar-", "MenuBar"),
            new BookExample("component.menubar.basic", "Basic Use", MenuBarExample.class),
            new BookExample("component.menubar.keep", "Keep Selection", MenuBarExample.class),
            new BookExample("component.menubar.navigator", "Using with Navigator", MenuBarExample.class),
            new BookExample("component.menubar.bigger", "More Items Than Fits", MenuBarExample.class),
            new ExampleCtgr("component.embedded-", "Embedded Resources"),
            new RedirctItem("component.embedded.basic", "component.embedded.image"),
            new ExampleCtgr("component.embedded.image-", "Image"),
            new BookExample("component.embedded.image.basic", "Basic Use", ImageExample.class),
            new RedirctItem("component.embedded.image.editable", "component.customfield.imagefield"),
            new BookExample("component.embedded.flash", "Adobe Flash", EmbeddedExample.class),
            new BookExample("component.embedded.browserframe", "Browser Frame", EmbeddedExample.class),
            new BookExample("component.embedded.embedded", "Generic Embedded Object", EmbeddedExample.class),
            new BookExample("component.embedded.svg", "SVG Image", EmbeddedExample.class),
            new BookExample("component.embedded.pdf", "PDF Document", EmbeddedExample.class),
            new BookExample("component.embedded.scrolling-css", "Scrolling with CSS", ImageExample.class),
            new ExampleCtgr("component.upload-", "Upload"),
            new BookExample("component.upload.basic", "Basic Use", UploadExample.class),
            new BookExample("component.upload.advanced", "Advanced Use", UploadExample.class),
            // TODO new ExampleCtgr("component.form-", "Form"),
            // TODO new BookExample("component.form.visibleproperties", "Visible Item Properties", FormExample.class),
            // TODO new BookExample("component.form.buffering", "Buffering", FormExample.class),
            // TODO new BookExample("component.form.select", "Field interaction", FormExample.class),
            // TODO new BookExample("component.form.attachfield", "The attachField() method I", FormExample.class),
            // TODO new BookExample("component.form.attachfield2", "The attachField() method II", FormExample.class),
            // TODO new BookExample("component.form.propertyfiltering", "Filtering Properties", FormExample.class),
            // TODO new RedirctItem("component.form.nested", "component.form.subform.nestedtable"),
            // TODO new BookExample("component.form.layout-", "Alternative Layouts for Form", FormExample.class),
            // TODO new BookExample("component.form.layout.customlayout", "CustomLayout", FormExample.class),
            // TODO new BookExample("component.form.layout.gridlayout", "GridLayout", FormExample.class),
            // TODO new ExampleCtgr("component.form.subform-", "Nested Beans and Subforms"),
            // TODO new BookExample("component.form.subform.boundselect", "Property Bound to a Select", FormExample.class),
            // TODO new BookExample("component.form.subform.nestedtable", "Property Bound to a Nested Table", FormExample.class),
            // TODO new BookExample("component.form.subform.nestedforms", "Subforms in a Nested Table", FormExample.class),
            // TODO new ExampleCtgr("component.form.styling-", "Styling with CSS"),
            // TODO new BookExample("component.form.styling.wrapcaptions", "Wrapping Captions", FormExample.class),
            new ExampleCtgr("component.progressbar-", "ProgressBar"),
            new BookExample("component.progressbar.basic", "Basic Use", ProgressBarExample.class),
            new BookExample("component.progressbar.thread", "Worker Thread", ProgressBarExample.class),
            new BookExample("component.progressbar.indeterminate", "Indeterminate", ProgressBarExample.class),
            //new ExampleCtgr("component.progressindicator-", "ProgressIndicator"),
            //new BookExample("component.progressindicator.simple", "Basic Use", ProgressIndicatorExample.class),
            //new BookExample("component.progressindicator.thread", "Worker Thread", ProgressIndicatorExample.class),
            new BookExample("component.slider", "Slider", SliderExample.class),
            new ExampleCtgr("component.colorpicker-", "ColorPicker"),
            new BookExample("component.colorpicker.colorpicker", "Popup ColorPicker", ColorPickerExample.class),
            new BookExample("component.colorpicker.colorpickerarea", "ColorPickerArea", ColorPickerExample.class),
            new ExampleCtgr("component.customcomponent-", "CustomComponent"),
            new BookExample("component.customcomponent.basic", "Basic Use", CustomComponentExample.class),
            new BookExample("component.customcomponent.joining", "Joining Components", CustomComponentExample.class),
            new BookExample("component.customcomponent.customfield", "CustomField", CustomComponentExample.class),
            new ExampleCtgr("component.customfield-", "CustomField"),
            new BookExample("component.customfield.basic", "Basic Use", CustomFieldExample.class),
            new BookExample("component.customfield.complex", "Custom Complex Value Type", CustomFieldExample.class),
            new BookExample("component.customfield.calendar", "Calendar Navigation", CustomFieldExample.class),
            new BookExample("component.customfield.imagefield", "Editable Image Field", CustomFieldExample.class),
            new ExampleCtgr("layout", "Chapter 6. Managing Layout"),
            new ExampleCtgr("layout.overview-", "Overview"),
            new BookExample("layout.overview.catfinder", "Cat Finder", LayoutExample.class),
            new BrknExample("layout.overview.traversal", "Traversal", LayoutExample.class),
            new ExampleCtgr("layout.layoutfeatures-", "Layout Features"),
            new BookExample("layout.layoutfeatures.layoutclick", "Layout Click Events", LayoutFeaturesExample.class),
            new ExampleCtgr("layout.window-", "Window"),
            new BookExample("layout.window.mainwindowsize", "Main Window Size", PopupWindowExample.class),
            new ExampleCtgr("layout.orderedlayout-", "VerticalLayout and HorizontalLayout"),
            new BookExample("layout.orderedlayout.basic", "Basic Use", OrderedLayoutExample.class),
            new BookExample("layout.orderedlayout.adjustments", "Layout Adjustments", OrderedLayoutExample.class),
            new ExampleCtgr("layout.orderedlayout.sizing", "Sizing Contained Components"),
            new BookExample("layout.orderedlayout.sizing.undefineddefiningsize", "Defining with Contained Size", OrderedLayoutExample.class),
            new BookExample("layout.orderedlayout.sizing.relativesize", "Relative Size", OrderedLayoutExample.class),
            new ExampleCtgr("layout.formlayout-", "FormLayout"),
            new BookExample("layout.formlayout.basic", "Basic Use", FormLayoutExample.class),
            new ExampleCtgr("layout.gridlayout-", "GridLayout"),
            new BookExample("layout.gridlayout.basic", "Basic", GridLayoutExample.class),
            new BookExample("layout.gridlayout.expandratio", "Expand ratio", GridLayoutExample.class),
            new ExampleCtgr("layout.panel-", "Panel"),
            new BookExample("layout.panel.basic", "Basic Use", PanelExample.class),
            new BookExample("layout.panel.complex", "Basic Use II", PanelExample.class),
            new BookExample("layout.panel.scrollbars", "Automatic Scrollbars", PanelExample.class),
            new BookExample("layout.panel.scroll", "Programmatic Scrolling", PanelExample.class),
            new BrknExample("layout.panel.styling", "Styling with CSS", PanelExample.class),
            new BookExample("layout.panel.light", "Light Style", PanelExample.class),
            new ExampleCtgr("layout.sub-window-", "Sub-Window"),
            new BookExample("layout.sub-window.basic", "Basic Use", SubWindowExample.class),
            new BookExample("layout.sub-window.inheritance", "Inheriting Window", SubWindowExample.class),
            new BookExample("layout.sub-window.close", "Closing a Child Window", SubWindowExample.class),
            new BookExample("layout.sub-window.modal", "Modal Sub-Windows", SubWindowExample.class),
            new BookExample("layout.sub-window.scrolling", "Scroll Bars", SubWindowExample.class),
            new BrknExample("layout.sub-window.noscroll", "Scroll Prevention", SubWindowExample.class),
            new BookExample("layout.sub-window.positioning", "Positioning", SubWindowExample.class),
            new BookExample("layout.sub-window.styling", "Styling with CSS", SubWindowExample.class),
            new ExampleCtgr("layout.absolutelayout-", "AbsoluteLayout"),
            new BookExample("layout.absolutelayout.basic", "Basic Use", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.bottomright", "Bottom-Right Relative Coordinates", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.area", "Area", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.zindex", "Z-index", AbsoluteLayoutExample.class),
            new BookExample("layout.absolutelayout.proportional", "Proportional Coordinates", AbsoluteLayoutExample.class),
            new ExampleCtgr("layout.csslayout-", "CssLayout"),
            new BookExample("layout.csslayout.basic", "Basic Use", CssLayoutExample.class),
            new BookExample("layout.csslayout.styling", "Styling", CssLayoutExample.class),
            new BookExample("layout.csslayout.flow", "Flow Layout", CssLayoutExample.class),
            new ExampleCtgr("layout.splitpanel-", "SplitPanel"),
            new BookExample("layout.splitpanel.basic", "Basic Use", SplitPanelExample.class),
            new BookExample("layout.splitpanel.splitposition", "Split Position and Locking", SplitPanelExample.class),
            new BookExample("layout.splitpanel.small", "Small Style", SplitPanelExample.class),
            new BookExample("layout.splitpanel.styling", "Styling", SplitPanelExample.class),
            new ExampleCtgr("layout.tabsheet-", "TabSheet"),
            new BookExample("layout.tabsheet.basic", "Basic Use", TabSheetExample.class),
            new BookExample("layout.tabsheet.tabchange", "Handling Tab Changes", TabSheetExample.class),
            new BookExample("layout.tabsheet.preventtabchange", "Reverting Tab Changes", TabSheetExample.class),
            new BookExample("layout.tabsheet.tabclose", "Closing Tabs", TabSheetExample.class),
            new BookExample("layout.tabsheet.leveling", "Leveling tab heights", TabSheetExample.class),
            new BookExample("layout.tabsheet.styling", "Styling with CSS", TabSheetExample.class),
            new ExampleCtgr("layout.accordion-", "Accordion"),
            new BookExample("layout.accordion.basic", "Basic Use", AccordionExample.class),
            new BookExample("layout.accordion.tabchange", "Handling Tab Changes", AccordionExample.class),
            new BookExample("layout.accordion.preventtabchange", "Reverting Tab Changes", AccordionExample.class),
            new BookExample("layout.accordion.styling", "Styling with CSS", AccordionExample.class),
            new ExampleCtgr("layout.popupview-", "PopupView"),
            new BookExample("layout.popupview.basic", "Basic Use", PopupViewExample.class),
            new BookExample("layout.popupview.programmatic", "Opening Programmatically", PopupViewExample.class),
            new BookExample("layout.popupview.visibilitylistener", "Popup Visibility Listener", PopupViewExample.class),
            new BookExample("layout.popupview.subwindow", "Inside a Sub-Window", PopupViewExample.class),
            new ExampleCtgr("layout.formatting-", "Layout Formatting"),
            new BookExample("layout.formatting.margin", "Margin", MarginExample.class),
            new ExampleCtgr("layout.formatting.spacing", "Spacing"),
            new BookExample("layout.formatting.spacing.vertical", "Vertical", SpacingExample.class),
            new BookExample("layout.formatting.spacing.horizontal", "Horizontal", SpacingExample.class),
            new BookExample("layout.formatting.spacing.grid", "Grid", SpacingExample.class),
            new ExampleCtgr("layout.formatting.alignment-", "Alignment"),
            new BookExample("layout.formatting.alignment.gridlayout", "In GridLayout", AlignmentExample.class),
            new BookExample("layout.formatting.alignment.verticallayout", "In VerticalLayout", AlignmentExample.class),
            new BookExample("layout.formatting.alignment.maxwidth", "Taking Maximum Width", AlignmentExample.class),
            new ExampleCtgr("layout.formatting.expandratio-", "Expand Ratios"),
            new BookExample("layout.formatting.expandratio.basic", "Basic Use", ExpandRatioExample.class),
            new BookExample("layout.formatting.expandratio.horizontal", "In a Horizontal Layout", ExpandRatioExample.class),
            new BookExample("layout.formatting.expandratio.summary", "Summary", ExpandRatioExample.class),
            new ExampleCtgr("layout.customlayout-", "CustomLayout"),
            new BookExample("layout.customlayout.basic", "Basic Use", CustomLayoutExample.class),
            new BookExample("layout.customlayout.stream", "Template from Stream", CustomLayoutExample.class),
            new BookExample("layout.customlayout.maxwidth", "Taking Maximum Width", CustomLayoutExample.class),
            new BookExample("layout.customlayout.styling", "Styling", CustomLayoutExample.class),
            new ExampleCtgr("themes", "Chapter 8. Themes"),
            new ExampleCtgr("themes.using-", "Using Themes"),
            new ExampleCtgr("themes.using.default-", "Built-in Themes"),
            new BookExample("themes.using.default.runo", "Runo Theme", BuiltInThemeExample.class),
            new BookExample("themes.using.default.valo", "Valo Theme", ThemeExample.class),
            new ExampleCtgr("themes.scss-", "SCSS Themes"),
            new BookExample("themes.scss.basic", "Basic Use", ScssThemeExample.class),
            new BookExample("themes.scss.switching", "Switching Themes", ScssThemeExample.class),
            new ExampleCtgr("themes.misc-", "Miscellaneous Problems"),
            new BookExample("themes.misc.pointertypes", "Pointer Types", ThemeTricksExample.class),
            new BookExample("themes.misc.cssinjection", "CSS Injection", ThemeTricksExample.class),
            new BookExample("themes.misc.webfonts", "Google Web Fonts", ThemeTricksExample.class),
            new ExampleCtgr("themes.responsive-", "Responsive Themes"),
            new BookExample("themes.responsive.basic", "Basic Use", ResponsiveExample.class),
            new BookExample("themes.responsive.flexwrap", "Flexible Wrapping", ResponsiveExample.class),
            new BookExample("themes.responsive.wrapgrid", "Flexible Wrap Grid", ResponsiveExample.class),
            new BookExample("themes.responsive.display", "Toggling the Display Property", ResponsiveExample.class),
            new BookExample("themes.responsive.complex", "Yet Another Example", ResponsiveExample.class),
            new ExampleCtgr("themes.fonticon-", "Font Icons"),
            new BookExample("themes.fonticon.basic", "Basic Use", FontIconExample.class),
            new BookExample("themes.fonticon.html", "Using in HTML", FontIconExample.class),
            new BookExample("themes.fonticon.all", "All FontIcons", FontIconExample.class),
            new BookExample("themes.fonticon.intext", "FontIcon in any text", FontIconExample.class),
            new BookExample("themes.fonticon.custom", "Custom FontIcons", FontIconExample.class),
            new ExampleCtgr("datamodel", "Chapter 9. Binding Components to Data"),
            new ExampleCtgr("datamodel.overview", "Overview"),
            new ExampleCtgr("datamodel.properties-", "Properties"),
            new BookExample("datamodel.properties.basic", "Setting and Getting Property Values", PropertyExample.class),
            new BookExample("datamodel.properties.propertyviewer", "Property Viewer", PropertyExample.class),
            new BookExample("datamodel.properties.propertyeditor", "Property Editor", PropertyExample.class),
            new BookExample("datamodel.properties.objectproperty", "ObjectProperty Implementation", PropertyExample.class),
            new BookExample("datamodel.properties.implementation", "Implementing Property Interface", PropertyExample.class),
            new BookExample("datamodel.properties.valuechangenotifier", "Implementing Value Change Notifier", PropertyExample.class),
            new ExampleCtgr("datamodel.properties.converter-", "Converting Between Model and Representation"),
            new BookExample("datamodel.properties.converter.basic", "Converting Basic Types", ConverterExample.class),
            new BookExample("datamodel.properties.converter.customconverter", "Converting Custom Types", ConverterExample.class),
            new BookExample("datamodel.properties.converter.converterfactory", "Custom Converter Factory", ConverterExample.class),
            new BookExample("datamodel.properties.converter.beanbinding", "Conversion in Bean Binding", ConverterExample.class),
            new ExampleCtgr("datamodel.items-", "Holding Properties in Items"),
            new BookExample("datamodel.items.propertysetitem", "PropertysetItem Implementation", ItemExample.class),
            new BookExample("datamodel.items.beanitem", "BeanItem", ItemExample.class),
            new BookExample("datamodel.items.beanitem.beanitem-basic", "Basic Use", ItemExample.class),
            new BookExample("datamodel.items.beanitem.nestedbean", "Nested Beans", ItemExample.class),
            new BookExample("datamodel.items.beanitem.doublebinding", "Multiple Binding", ItemExample.class),
            new BookExample("datamodel.items.beanitem.valuechangenotifier", "Notifying Value Changes in Bean", ItemExample.class),
            new BookExample("datamodel.items.implementing", "Implementing the Item Interface", ItemExample.class),
            new ExampleCtgr("datamodel.itembinding-", "Binding Fields to Items"),
            new BookExample("datamodel.itembinding.basic", "Basic use of FieldGroup", FieldGroupExample.class),
            new BookExample("datamodel.itembinding.buildandbind", "Using a FieldFactory", FieldGroupExample.class),
            new ExampleCtgr("datamodel.itembinding.formclass-", "Binding a Form Class"),
            new BookExample("datamodel.itembinding.formclass.extended", "An Extended Layout", FieldGroupExample.class),
            new BookExample("datamodel.itembinding.formclass.onetomany", "One-to-Many Relationships", FieldGroupExample.class),
            new BookExample("datamodel.itembinding.formclass.customcomponent", "Encapsulating in CustomComponent", FieldGroupExample.class),
            new BookExample("datamodel.itembinding.formclass.multipage", "Multiple-Step Forms", FieldGroupExample.class),
            new ExampleCtgr("datamodel.itembinding.buffering-", "Buffering Forms"),
            new BookExample("datamodel.itembinding.buffering.commit", "Handling a Commit", FieldGroupExample.class),
            new ExampleCtgr("datamodel.itembinding.beanvalidation-", "Bean Validation"),
            new BookExample("datamodel.itembinding.beanvalidation.basic", "Basic Bean Validation", BeanValidationExample.class),
            new BookExample("datamodel.itembinding.beanvalidation.fieldgroup", "Validating a Form", BeanValidationExample.class),
            new ExampleCtgr("datamodel.container-", "Collecting Items in Containers"),
            new ExampleCtgr("datamodel.container.indexedcontainer-", "IndexedContainer"),
            new BookExample("datamodel.container.indexedcontainer.basic", "Basic Use", IndexedContainerExample.class),
            new ExampleCtgr("datamodel.container.hierarchical-", "Hierarchical"),
            new BookExample("datamodel.container.hierarchical.implementing", "Implementing Hierarchical", HierarchicalExample.class),
            new ExampleCtgr("datamodel.container.beancontainer-", "BeanContainer"),
            new BookExample("datamodel.container.beancontainer.basic", "Basic Use", BeanContainerExample.class),
            new ExampleCtgr("datamodel.container.beanitemcontainer-", "BeanItemContainer"),
            new BookExample("datamodel.container.beanitemcontainer.basic", "Basic Use", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.select", "Using in a Selection Component", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.dualbinding", "Binding to Table and Form", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.nestedbean", "Nested Beans", BeanItemContainerExample.class),
            new BookExample("datamodel.container.beanitemcontainer.addall", "Add All From Collection", BeanItemContainerExample.class),
            new ExampleCtgr("datamodel.container.filesystemcontainer-", "FileSystemContainer"),
            new BookExample("datamodel.container.filesystemcontainer.basic", "Basic Use", FilesystemContainerExample.class),
            new ExampleCtgr("datamodel.container.filter-", "Filtering Containers"),
            new BookExample("datamodel.container.filter.basic", "Basic Use", ContainerFilterExample.class),
            new BookExample("datamodel.container.filter.like", "Like filter", ContainerFilterExample.class),
            new BookExample("datamodel.container.filter.tree", "Filtered Tree", ContainerFilterExample.class),
            new BookExample("datamodel.container.filter.custom", "Custom Filter", ContainerFilterExample.class),
            new ExampleCtgr("jpacontainer-", "Chapter 18. Vaadin JPAContainer Add-on"),
            new BookExample("jpacontainer.nonpersistent", "Non-persistent Binding", JPAContainerExample.class),
            new BookExample("jpacontainer.basic", "Basic Use", JPAContainerExample.class),
            new BookExample("jpacontainer.thehardway", "The Hard Way", JPAContainerExample.class),
            new BookExample("jpacontainer.nested", "Nested Properties", JPAContainerExample.class),
            new BookExample("jpacontainer.buffering", "Buffering", JPAContainerExample.class),
            new BookExample("jpacontainer.hierarchical", "Hierarchical Support", JPAHierarhicalExample.class),
            new BookExample("jpacontainer.hierarchical.basic", "Basic Use", JPAHierarhicalExample.class),
            new BookExample("jpacontainer.hierarchical.childrenallowed", "Disabling Node Expansion", JPAHierarhicalExample.class),
            new ExampleCtgr("jpacontainer.filtering", "Filtering"),
            new BookExample("jpacontainer.filtering.basic", "Basic Filtering", JPAFilteringExample.class),
            new BookExample("jpacontainer.filtering.entity", "Filtering by Entity", JPAFilteringExample.class),
            new ExampleCtgr("jpacontainer.criteria", "Querying with the Criteria API"),
            new BookExample("jpacontainer.criteria.querymodification", "Simple Criteria Query", JPAFilteringExample.class),
            new ExampleCtgr("jpacontainer.fieldfactory", "FieldFactory"),
            new BookExample("jpacontainer.fieldfactory.formonetomany", "Form with One-to-Many Relationship", JPAFieldFactoryExample.class),
            new BookExample("jpacontainer.fieldfactory.tableonetomany", "Table with One-to-Many Relationship I", JPAFieldFactoryExample.class),
            new BookExample("jpacontainer.fieldfactory.tableonetomany2", "Table with One-to-Many Relationship II", JPAFieldFactoryExample.class),
            new BookExample("jpacontainer.fieldfactory.masterdetail", "Master-Detail Editor Manually", JPAFieldFactoryExample.class),
            new BookExample("jpacontainer.fieldfactory.manytoone", "Form with Many-to-One Relationship", JPAFieldFactoryExample.class),
            new ExampleCtgr("advanced", "Chapter 12. Advanced Web Application Topics"),
            new ExampleCtgr("advanced.windows-", "Handling Browser Windows"),
            new BookExample("advanced.windows.popup", "Popup Windows", PopupWindowExample.class),
            new BookExample("advanced.windows.tab", "Opening as Tab", PopupWindowExample.class),
            new BookExample("advanced.windows.closing", "Close Event", PopupWindowExample.class),
            new BookExample("advanced.windows.dynamic", "Dynamic Creation of Windows", PopupWindowExample.class),
            new BookExample("advanced.windows.automatic", "Multiple Book Example Windows", PopupWindowExample.class),
            new ExampleCtgr("advanced.embedding-", "Embedding Applications"),
            new BookExample("advanced.embedding.div", "Embedding in <div>", EmbeddingExample.class),
            new ExampleCtgr("advanced.debug-", "Debug Mode and Window"),
            new BookExample("advanced.debug.analyze1", "Analyze Layouts 1", DebugWindowExample.class),
            new BookExample("advanced.debug.analyze2", "Analyze Layouts 2", DebugWindowExample.class),
            new ExampleCtgr("advanced.shortcut-", "Shortcut Keys"),
            new BookExample("advanced.shortcut.defaultbutton", "Default Button", ShortcutExample.class),
            new BookExample("advanced.shortcut.focus", "Focus Shortcuts", ShortcutExample.class),
            new BookExample("advanced.shortcut.modifier", "Modifier Keys", ShortcutExample.class),
            new BookExample("advanced.shortcut.scope", "Shortcut Scope", ShortcutExample.class),
            new ExampleCtgr("advanced.printing-", "Printing"),
            new BookExample("advanced.printing.this", "Print This Page", PrintingExample.class),
            new BookExample("advanced.printing.open", "Open Page to Print", PrintingExample.class),
            new BookExample("advanced.printing.pdfgeneration", "PDF Generation", PrintingExample.class),
            new ExampleCtgr("advanced.urifragment-", "URI Fragment Management"),
            new BookExample("advanced.urifragment.basic", "Basic Use", UriFragmentExample.class),
            new RedirctItem("advanced.urifragmentutility.uriexample", "advanced.urifragment.basic"),
            new RedirctItem("advanced.urifragmentutility.indexing", "advanced.urifragment.basic"),
            //new BookExample("advanced.urifragmentutility.indexing", "Seach Engine Indexing Support", UriFragmentExample.class),
            new ExampleCtgr("advanced.navigator-", "Navigation with a Navigator"),
            new BookExample("advanced.navigator.basic", "Basic Use", NavigatorExample.class),
            new BookExample("advanced.navigator.customviewprovider", "Custom View Provider", NavigatorExample.class),
            new ExampleCtgr("advanced.servletrequestlistener-", "Listening for Server Requests"),
            new BookExample("advanced.servletrequestlistener.introduction", "Introduction", ServletRequestListenerExample.class),
            new BookExample("advanced.servletrequestlistener.cookies", "Managing Cookies", ServletRequestListenerExample.class),
            new ExampleCtgr("advanced.dragndrop-", "Drag & Drop"),
            new ExampleCtgr("advanced.dragndrop.tree-", "Dropping Items On a Tree"),
            new BookExample("advanced.dragndrop.tree.treedropcriterion", "Tree Drop Criteria", DragNDropTreeExample.class),
            new ExampleCtgr("advanced.dragndrop.table-", "Dropping Items On a Table"),
            new BookExample("advanced.dragndrop.table.treeandtable", "Tree and Table", TreeAndTableExample.class),
            new ExampleCtgr("advanced.dragndrop.accept-", "Accepting Drops"),
            new BookExample("advanced.dragndrop.accept.serverside", "Server-Side Criteria", DragNDropTreeExample.class),
            new ExampleCtgr("advanced.dragndrop.component-", "Dropping on a Component"),
            new BookExample("advanced.dragndrop.component.basic", "Basic Use", ComponentDnDExample.class),
            new BookExample("advanced.dragndrop.component.absolute", "Absolute Drop Position", ComponentDnDExample.class),
            new BookExample("advanced.dragndrop.component.resize", "Resizing Components", ComponentDnDExample.class),
            new BookExample("advanced.dragndrop.component.diagram", "Moving Connected Components", DiagramDnDExample.class),
            new ExampleCtgr("advanced.requesthandler-", "Request Handler"),
            new BookExample("advanced.requesthandler.basic", "Basic Use", RequestHandlerExample.class),
            new BookExample("advanced.requesthandler.staticlogin", "Login from an External Page", RequestHandlerExample.class),
            new ExampleCtgr("advanced.logging-", "Logging"),
            new BookExample("advanced.logging.basic", "Basic Use", LoggingExample.class),
            new ExampleCtgr("advanced.browserinfo-", "Browser Info"),
            new BookExample("advanced.browserinfo.basic", "Basic", BrowserInfoExample.class),
            new ExampleCtgr("advanced.i18n-", "Internationalization"),
            new BookExample("advanced.i18n.bundles", "Basic i18n Using Bundles", I18NExample.class),
            new BookExample("advanced.i18n.rtl", "Right-to-Left Languages", I18NExample.class),
            new ExampleCtgr("advanced.jsapi-", "JavaScript API"),
            new BookExample("advanced.jsapi.basic", "Basic JavaScript Calling", JSAPIExample.class),
            new BookExample("advanced.jsapi.status", "Setting Status Message", JSAPIExample.class),
            new BookExample("advanced.jsapi.callbackbasic", "Calling Callbacks from JavaScript", JSAPIExample.class),
            new BookExample("advanced.jsapi.callbackparameters", "Callback Parameters", JSAPIExample.class),
            new BookExample("advanced.jsapi.screendump", "Dumping the Screen", JSAPIExample.class),
            //new ExampleCtgr("advanced.global-", "Accessing Session Data Globally"),
            //new BrknExample("advanced.global.threadlocal", "ThreadLocal Pattern", GlobalAccessExample.class),
            new ExampleCtgr("advanced.architecture-", "Application Architecture"),
            new BookExample("advanced.architecture.mvp", "Model-View-Presenter Pattern", MVPCalculator.class),
            new ExampleCtgr("advanced.push-", "Server Push"),
            new BookExample("advanced.push.basic", "Basic Use", PushExample.class),
            new BookExample("advanced.push.pusharound", "Pushing to Other UIs", PushExample.class),
            new ExampleCtgr("advanced.cdi-", "Vaadin CDI"),
            new BookExample("advanced.cdi.navigation", "Navigation", CDIExample.class),
            new BookExample("advanced.cdi.events", "CDI Events", CDIExample.class),
            new BookExample("advanced.cdi.broadcasting", "Broadcasting to UIs", CDIExample.class),
            new BookExample("advanced.cdi.producers", "Producing Objects", CDIExample.class),
            new ExampleCtgr("advanced.spring-", "Vaadin Spring"),
            //new BookExample("advanced.spring.navigation", "Navigation", SpringExample.class),
            new EmboExample("advanced.spring.navigation", "Navigation", MySpringUI.class, "myspringuis", EmbeddingType.FRAME, 570, 300),
            new ExampleCtgr("gwt-", "Chapter 16. Integrating with the Server-Side"),
            new ExampleCtgr("gwt.eclipse-", "Creating Widgets with Eclipse"),
            new BookExample("gwt.eclipse.basic", "Simple Custom Widget", MyComponentExample.class),
            new ExampleCtgr("gwt.integration-", "Widget Integration"),
            new BookExample("gwt.integration.basic", "Basic Use", ColorpickerExample.class),
            new BookExample("gwt.integration.resource", "Resources", MyComponentExample.class),
            new ExampleCtgr("gwt.javascript-", "Integrating JavaScript Components"),
            new BookExample("gwt.javascript.basic", "Basic Integration", JSIntegrationExample.class),
            new ExampleCtgr("charts-", "Chapter 16. Vaadin Charts Add-on"),
            new BookExample("charts.basic", "Basic Use", ChartsExample.class),
            new ExampleCtgr("charts.charttype", "Chart Types"),
            new BookExample("charts.charttype.all", "All", ChartTypesExample.class),
            new BookExample("charts.charttype.line", "Line Chart", ChartTypesExample.class),
            new BookExample("charts.charttype.area", "Area Chart", ChartTypesExample.class),
            new BookExample("charts.charttype.arearange", "Area Range Chart", ChartTypesExample.class),
            new BookExample("charts.charttype.scatter", "Scatter Chart", ScatterExample.class),
            new BookExample("charts.charttype.bubble", "Bubble Chart", ChartTypesExample.class),
            new BookExample("charts.charttype.column", "Column Chart", ChartTypesExample.class),
            new BookExample("charts.charttype.errorbar", "Error Bar Chart", ChartTypesExample.class),
            new ExampleCtgr("charts.charttype.boxplot-", "Box Plot Chart"),
            new BookExample("charts.charttype.boxplot.basic", "Basic Use", BoxPlotExample.class),
            new BookExample("charts.charttype.pie", "Pie Chart", ChartTypesExample.class),
            new ExampleCtgr("charts.charttype.funnel-", "Funnel and Pyramid Charts"),
            new BookExample("charts.charttype.funnel.funnel", "Funnel Chart", FunnelExample.class),
            new BookExample("charts.charttype.funnel.pyramid", "Pyramid Chart", PyramidExample.class),
            new BookExample("charts.charttype.funnel.both", "Both Funnel and Pyramid", PyramidExample.class),
            new BookExample("charts.charttype.waterfall", "Waterfall Chart", ChartTypesExample.class),
            new ExampleCtgr("charts.charttype.gauge-", "Gauge Charts"),
            new BookExample("charts.charttype.gauge.basic", "Basic Regular Gauge", GaugeExample.class),
            new BookExample("charts.charttype.gauge.solid", "Solid Gauge", GaugeExample.class),
            new BookExample("charts.charttype.gauge.both", "Both Regular and Solid Gauge", GaugeExample.class),
            new BookExample("charts.charttype.polar", "Polar Projection", ChartTypesExample.class),
            new BookExample("charts.charttype.spiderweb", "Spiderweb", ChartTypesExample.class),
            new BookExample("charts.charttype.polarspiderweb", "Polar and Spiderweb Summary", ChartTypesExample.class),
            new ExampleCtgr("charts.charttype.heatmap-", "Heat Map"),
            new BookExample("charts.charttype.heatmap.basic", "Basic Use", HeatMapExample.class),
            new BookExample("charts.charttype.heatmap.simulation", "Heat Transfer Simulation", HeatMapExample.class),
            new BookExample("charts.grouped", "Grouped Data Series", ChartsExample.class),
            new ExampleCtgr("charts.mixed-", "Mixed Charts"),
            new BookExample("charts.mixed.basicmixed", "Mixed Chart Type", ChartsExample.class),
            new BookExample("charts.mixed.mixedmeter", "Styled mixed meter", ChartsExample.class),
            new ExampleCtgr("charts.3d-", "3D Charts"),
            new BookExample("charts.3d.basic", "Basic Use", Charts3DExample.class),
            new BookExample("charts.3d.scatter", "Scatter with 3D Data", Charts3DExample.class),
            new BookExample("charts.3d.scatterfade", "Scatter with Distance Fade (test)", Charts3DExample.class),
            new BookExample("charts.themes", "Themes", ChartsExample.class),
            new ExampleCtgr("charts.data-", "Data Series"),
            new BookExample("charts.data.containerseries", "Container Data Series", ChartsExample.class),
            new ExampleCtgr("charts.configuration-", "Configuration"),
            new BookExample("charts.configuration.axes", "Axes", AxesExample.class),
            new BookExample("charts.exporting", "Exporting", ChartsExample.class),
            new ExampleCtgr("charts.timeline-", "Timeline"),
            new BookExample("charts.timeline.basic", "Basic Use", TimelineExample.class),
            new RedirctItem("calendar.basic", "component.calendar.basic"),
            new RedirctItem("calendar.monthlyview", "component.calendar.monthlyview"),
            new RedirctItem("calendar.contextmenu", "component.calendar.contextmenu"),
            new RedirctItem("calendar.beanitemcontainer", "component.calendar.beanitemcontainer"),
            new RedirctItem("calendar.jpacontainer", "component.calendar.jpacontainer"),
            new ExampleCtgr("spreadsheet-", "Chapter 17. Vaadin Spreadsheet Add-on"),
            new BookExample("spreadsheet.basic", "Basic Use", SpreadsheetExample.class),
            new ExampleCtgr("spreadsheet.load-", "Loading and Saving"),
            new BookExample("spreadsheet.load.upload", "Document Management", SpreadsheetManagementExample.class),
            new BookExample("spreadsheet.empty", "Empty Sheet", SpreadsheetExample.class),
            new BookExample("spreadsheet.cellvalues", "A more complex sheet", SpreadsheetExample.class),
            new BookExample("spreadsheet.components", "Components", SpreadsheetComponentsExample.class),
            new BookExample("spreadsheet.freezepane", "Frozen Row and Col Pane", SpreadsheetExample.class),
            new ExampleCtgr("spreadsheet.contextmenu-", "Context Menu"),
            new BookExample("spreadsheet.contextmenu.defaultcontextmenu", "Default", SpreadsheetExample.class),
            new BookExample("spreadsheet.contextmenu.customcontextmenu", "Custom", SpreadsheetExample.class),
            new ExampleCtgr("spreadsheet.tables-", "Table Regions"),
            new BookExample("spreadsheet.tables.basic", "Basic Use", SpreadsheetTablesExample.class),
            new BookExample("spreadsheet.tables.filter", "Filter Table", SpreadsheetTablesExample.class),
            new ExampleCtgr("misc-", "x. Miscellaneous Examples"),
            new ExampleCtgr("misc.exception-", "Exceptions"),
            new BookExample("misc.exception.concurrentmodification", "Concurrent Modification", ExceptionExamples.class),
            new ExampleCtgr("misc.prototypes-", "Prototypes"),
            new ExampleCtgr("misc.prototypes.i18n-", "Internationalization"),
            new BookExample("misc.prototypes.i18n.basic", "Basic Prototype", I18nPrototype.class),
            new ExampleCtgr("misc.serialization-", "Serialization"),
            new BookExample("misc.serialization.basic", "Basic Serialization", SerializationExample.class),
            new ExampleCtgr("misc.addons-", "Add-on Tests"),
            new BookExample("misc.addons.csvalidation", "Client-Side Validation", AddonTests.class),
            new BookExample("misc.addons.arraycontainer", "ArrayContainer", AddonTests.class),
            new BookExample("misc.addons.gridview", "GridView", AddonTests.class),
            new BookExample("misc.addons.questiontree", "QuestionTree", AddonTests.class),
            new BookExample("misc.addons.minichat", "MiniChat", AddonTests.class),
            //new ExampleItem("misc.itemcontainer-", "x.x. ItemContainer"),
            //new ExampleItem("misc.itemcontainer.basic", "Basic Use", ItemContainerExample.class),
        };
}
