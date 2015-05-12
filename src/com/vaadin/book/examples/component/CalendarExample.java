package com.vaadin.book.examples.component;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.EntityManager;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.book.examples.Description;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.calendar.DateConstants;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.ContainerEventProvider;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

public class CalendarExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -3205020480634478985L;
    String context;

    @Description("<h1>Basic Use of Calendar</h1>")
    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.basic
        // Create the calendar
        Calendar calendar = new Calendar("My Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("300px"); // Undefined by default

        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));
        
        // We use a fixed date range in this example
        calendar.setStartDate(new GregorianCalendar(
            2014, 0, 6, 13, 00, 00).getTime());
        calendar.setEndDate(new GregorianCalendar(
            2014, 0, 12, 13, 00, 00).getTime());

        // Set daily time range
        calendar.setFirstVisibleHourOfDay(9);
        calendar.setLastVisibleHourOfDay(17);

        // Add a two-hour event
        GregorianCalendar eventStart =
                new GregorianCalendar(2014, 0, 7, 13, 00, 00);
        GregorianCalendar eventEnd =
                new GregorianCalendar(2014, 0, 7, 16, 00, 00);
        calendar.addEvent(new BasicEvent("Calendar study",
                "Learning how to use Vaadin Calendar",
                eventStart.getTime(), eventEnd.getTime()));

        // A two-day event
        GregorianCalendar alldayStart =
                new GregorianCalendar(2014, 0, 8, 0, 00, 00);
        GregorianCalendar alldayEnd =
                new GregorianCalendar(2014, 0, 9, 0, 00, 00);
        BasicEvent allday = new BasicEvent("Study all day",
            "Study how to create all-day events",
            alldayStart.getTime(), alldayEnd.getTime());
        allday.setAllDay(true);
        calendar.addEvent(allday);

        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.basic
    }

    @Description("<p>Select a range by dragging.</p>")
    public void rangeselect(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.rangeselect
        // Create the calendar
        final Calendar calendar = new Calendar("My Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("300px"); // Undefined by default
        
        // Set daily time range
        calendar.setFirstVisibleHourOfDay(9);
        calendar.setLastVisibleHourOfDay(17);

        // Add events on range selection
        calendar.setHandler(new RangeSelectHandler() {
            private static final long serialVersionUID = 7932267063732178541L;

            @Override
            public void rangeSelect(RangeSelectEvent event) {
                calendar.addEvent(new BasicEvent("Calendar study",
                    "Learning how to use Vaadin Calendar",
                    event.getStart(),
                    event.getEnd()));
            }
        });
        
        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.rangeselect
    }

    public static final String monthlyviewDescription =
            "<h1>Monthly View</h1>";

    public void monthlyview(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.monthlyview
        // Create the calendar
        Calendar calendar = new Calendar("My Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("400px"); // Undefined by default

        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));

        // Set start date to first date in this month
        GregorianCalendar calStart = new GregorianCalendar();
        calendar.setStartDate(calStart.getTime());

        // Set end date to last day of this month
        GregorianCalendar calEnd = new GregorianCalendar();
        calEnd.set(java.util.Calendar.DATE, 1);
        calEnd.roll(java.util.Calendar.DATE, -1);
        calendar.setEndDate(calEnd.getTime());
        
        // Add a short event today
        GregorianCalendar start = new GregorianCalendar();
        start.set(java.util.Calendar.HOUR, 14);
        start.set(java.util.Calendar.MINUTE, 00);
        start.set(java.util.Calendar.SECOND, 00);
        GregorianCalendar end   = new GregorianCalendar();
        end.set(java.util.Calendar.HOUR, 18);
        end.set(java.util.Calendar.MINUTE, 00);
        end.set(java.util.Calendar.SECOND, 00);
        calendar.addEvent(new BasicEvent("Calendar study",
                "Learning how to use Vaadin Calendar",
                start.getTime(), end.getTime()));
               
        // Add an all-day event
        GregorianCalendar today = new GregorianCalendar();
        BasicEvent dayEvent = new BasicEvent("All-day Long",
                "This is the Day",
                today.getTime(), today.getTime());
        dayEvent.setAllDay(true);
        calendar.addEvent(dayEvent);

        // Add an all-week event
        GregorianCalendar weekstart = new GregorianCalendar();
        GregorianCalendar weekend   = new GregorianCalendar();
        weekstart.setFirstDayOfWeek(java.util.Calendar.SUNDAY);
        weekstart.set(java.util.Calendar.HOUR_OF_DAY, 0);
        weekstart.set(java.util.Calendar.DAY_OF_WEEK,
                     java.util.Calendar.SUNDAY);
        weekend.set(java.util.Calendar.HOUR_OF_DAY, 23);
        weekend.set(java.util.Calendar.DAY_OF_WEEK,
                     java.util.Calendar.SATURDAY);
        BasicEvent weekEvent = new BasicEvent("A long week",
                "This is a long long week",
                weekstart.getTime(), weekend.getTime());
        //        weekEvent.setAllDay(true);
        calendar.addEvent(weekEvent);
        
        // Handle clicks on dates
        calendar.setHandler(new BasicDateClickHandler() {
            private static final long serialVersionUID = 1763979724318467578L;

            public void dateClick(DateClickEvent event) {
              Calendar cal = event.getComponent();
              
              // Check if the current range is already one day long
              long currentCalDateRange = cal.getEndDate().getTime() -
                                         cal.getStartDate().getTime();

              // From one-day view, zoom out to week view
              if (currentCalDateRange <= DateConstants.DAYINMILLIS) {
                  // Change the date range to the current week
                  GregorianCalendar weekstart = new GregorianCalendar();
                  GregorianCalendar weekend   = new GregorianCalendar();
                  weekstart.setTime(event.getDate());
                  weekend.setTime(event.getDate());
                  weekstart.setFirstDayOfWeek(java.util.Calendar.SUNDAY);
                  weekstart.set(java.util.Calendar.HOUR_OF_DAY, 0);
                  weekstart.set(java.util.Calendar.DAY_OF_WEEK,
                               java.util.Calendar.SUNDAY);
                  weekend.set(java.util.Calendar.HOUR_OF_DAY, 23);
                  weekend.set(java.util.Calendar.DAY_OF_WEEK,
                               java.util.Calendar.SATURDAY);
                  cal.setStartDate(weekstart.getTime());
                  cal.setEndDate(weekend.getTime());

                  Notification.show("Custom zoom to week");
              } else {
                // Default behavior, change date range to one day
                super.dateClick(event);
              }
            }
          });       
        
        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.monthlyview
    }

    public static final String contextmenuDescription =
            "<h1>Context Menu</h1>";

    public void contextmenu(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.contextmenu
        // Create the calendar
        Calendar calendar = new Calendar("My Contextual Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("300px"); // Undefined by default

        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Handle the context menu selection
        Action.Handler actionHandler = new Action.Handler() {
            private static final long serialVersionUID = -306196319123409692L;

            Action addEventAction    = new Action("Add Event");
            Action deleteEventAction = new Action("Delete Event");

            @Override
            public Action[] getActions(Object target, Object sender) {
                // The target should be a CalendarDateRage for the
                // entire day from midnight to midnight.
                if (! (target instanceof CalendarDateRange))
                    return null;
                CalendarDateRange dateRange = (CalendarDateRange) target;

                // The sender is the Calendar object
                if (! (sender instanceof Calendar))
                    return null;
                Calendar calendar = (Calendar) sender;
                
                // List all the events on the requested day
                List<CalendarEvent> events =
                        calendar.getEvents(dateRange.getStart(),
                                           dateRange.getEnd());
                
                // You can have some logic here, using the date
                // information.
                if (events.size() == 0)
                    return new Action[] {addEventAction};
                else
                    return new Action[] {addEventAction, deleteEventAction};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                // The sender is the Calendar object
                Calendar calendar = (Calendar) sender;
                
                if (action == addEventAction) {
                    // Check that the click was not done on an event
                    if (target instanceof Date) {
                        Date date = (Date) target;
                        // Add an event from now to plus one hour
                        GregorianCalendar start = new GregorianCalendar();
                        start.setTime(date);
                        GregorianCalendar end   = new GregorianCalendar();
                        end.setTime(date);
                        end.add(java.util.Calendar.HOUR, 7);
                        calendar.addEvent(new BasicEvent("Calendar study",
                                "Learning how to use Vaadin Calendar",
                                start.getTime(), end.getTime()));
                    } else
                        Notification.show("Can't add on an event");
                } else if (action == deleteEventAction) {
                    // Check if the action was clicked on top of an event
                    if (target instanceof CalendarEvent) {
                        CalendarEvent event = (CalendarEvent) target;
                        calendar.removeEvent(event);
                    } else
                        Notification.show("No event to delete");
                }
            }
        };
        calendar.addActionHandler(actionHandler);
        
        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.contextmenu
    }

    public static final String navigationDescription =
            "<h1>Calendar Navigation</h1>" +
            "<p>Vaadin Calendar has no built-in support for navigation as it can be implemented in so many different ways. " +
            "This example demonstrates one implementation of navigation.</p>";

    public void navigation(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.navigation
        // Create the calendar
        Calendar calendar = new Calendar("My Calendar");
        calendar.setWidth("300px");  // Undefined by default
        calendar.setHeight("300px"); // Undefined by default

        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));

        // Set start date to first date in this month
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.set(java.util.Calendar.MONTH, 1);
        startDate.set(java.util.Calendar.DATE, 1);
        calendar.setStartDate(startDate.getTime());

        // Set end date to last day of the month
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.set(java.util.Calendar.DATE, 1);
        endDate.roll(java.util.Calendar.DATE, -1);
        calendar.setEndDate(endDate.getTime());
        
        // Add a short event
        GregorianCalendar start = new GregorianCalendar();
        GregorianCalendar end   = new GregorianCalendar();
        end.add(java.util.Calendar.HOUR, 2);
        calendar.addEvent(new BasicEvent("Calendar study",
                "Learning how to use Vaadin Calendar",
                start.getTime(), end.getTime()));
        
        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.navigation
    }
    
    public static final String beanitemcontainerDescription =
        "<h1>Binding to a BeanItemContainer</h1>" +
        "<p>You can bind calendar to a BeanItemContainer with properties required for events.</p>" +
        "<ul>"+
        "  <li>You must call <i>sort()</i> with the start date/time property after you add any events!</li>"+
        "</ul>";

    public void beanitemcontainer(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.beanitemcontainer
        // Create the calendar
        Calendar calendar = new Calendar("Bound Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("400px"); // Undefined by default
    
        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));
    
        // Use the weekly view
        calendar.setStartDate(
            new GregorianCalendar(2012, 1, 12).getTime());
        calendar.setEndDate(
            new GregorianCalendar(2012, 1, 18).getTime());
        calendar.setFirstVisibleHourOfDay(8);
        calendar.setLastVisibleHourOfDay(21);

        // Use a container of built-in BasicEvents
        final BeanItemContainer<BasicEvent> container =
            new BeanItemContainer<BasicEvent>(BasicEvent.class);        

        // Create a meeting in the container
        container.addBean(new BasicEvent("The Event", "Single Event",
                    new GregorianCalendar(2012,1,14,12,00).getTime(),
                    new GregorianCalendar(2012,1,14,20,00).getTime()));

        calendar.setContainerDataSource(container, "caption",
            "description", "start", "end", "styleName");
        
        // We can also add events to the container through the calendar
        BasicEvent event = new BasicEvent("Wednesday Wonder",
            "Wonderful Event",
            new GregorianCalendar(2012,1,15,15,00).getTime(),
            new GregorianCalendar(2012,1,15,19,00).getTime());
        event.setStyleName("blue");
        calendar.addEvent(event);

        // An all-day event
        BasicEvent allday = new BasicEvent("Thursday Thunder",
            "Big Event",
            new GregorianCalendar(2012,1,16,00,00).getTime(),
            new GregorianCalendar(2012,1,16,00,00).getTime());
        allday.setAllDay(true);
        calendar.addEvent(allday);

        // The container MUST be ordered by the start time. You
        // have to sort the BIC every time after you have added items.        
        container.sort(new Object[]{"start"}, new boolean[]{true});

        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.beanitemcontainer
    }

    public static final String beanitemcontainer2Description =
            "<h1>Binding to a BeanItemContainer</h1>" +
            "<p>You can bind calendar to a BeanItemContainer with properties required for events.</p>" +
            "<ul>"+
            "  <li>You must call <i>sort()</i> with the start date/time property after you add any events!</li>"+
            "</ul>";

    public void beanitemcontainer2(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.beanitemcontainer2
        // Create the calendar
        Calendar calendar = new Calendar("Bound Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("400px"); // Undefined by default
    
        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));
    
        // Set month view
        GregorianCalendar startDate =
            new GregorianCalendar(2012, 1, 1);
        calendar.setStartDate(startDate.getTime());
        GregorianCalendar endDate =
            new GregorianCalendar(2012, 1, 29);
        calendar.setEndDate(endDate.getTime());

        final BeanItemContainer<MyCalendarEvent> container =
            new BeanItemContainer<MyCalendarEvent>(MyCalendarEvent.class);        

        // Meeting every Monday morning
        GregorianCalendar startTime =
            new GregorianCalendar(2012, 1, 1, 9, 0);
        startTime.set(java.util.Calendar.DAY_OF_WEEK,
                      java.util.Calendar.MONDAY);
        startTime.set(java.util.Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        for (int i=0; i<5; i++) {
            GregorianCalendar endTime =
                (GregorianCalendar) startTime.clone();
            endTime.add(java.util.Calendar.HOUR, 1);
            MyCalendarEvent event =
                new MyCalendarEvent("Monday Meeting",
                    "The same meeting every Monday",
                    startTime.getTime(), endTime.getTime(), "red");
            container.addBean(event);
            startTime.add(java.util.Calendar.WEEK_OF_YEAR, 1);
        }

        calendar.setContainerDataSource(container, "caption",
            "description", "startDate", "endDate", "styleName");
        
        // Now we can add events to the container through the calendar
        BasicEvent event = new BasicEvent("Wednesday Wonder",
            "Wonderful Event",
            new GregorianCalendar(2012,1,15,12,00).getTime(),
            new GregorianCalendar(2012,1,15,14,00).getTime());
        event.setStyleName("blue");
        calendar.addEvent(event);

        // The container must be ordered by the start time. We
        // have to sort the BIC every time after we add items.        
        container.sort(new Object[]{"start"}, new boolean[]{true});

        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.beanitemcontainer2
    }

    public static final String jpacontainerDescription =
        "<h1>JPAContainer Binding</h1>" +
        "<p>Binding to a JPAContainer is really easy.</p>"+
        "<ul>"+
        "  <li>You must set up <i>sort()</i> with the start date/time property!</li>"+
        "</ul>";

    public void jpacontainer(VerticalLayout layout) {
        // BEGIN-EXAMPLE: component.calendar.jpacontainer
        // Create the calendar
        Calendar calendar = new Calendar("Bound Calendar");
        calendar.setWidth("600px");  // Undefined by default
        calendar.setHeight("400px"); // Undefined by default
    
        // Use US English for date/time representation 
        calendar.setLocale(new Locale("en", "US"));
    
        // Set month view
        GregorianCalendar startDate =
            new GregorianCalendar(2012, 1, 1);
        calendar.setStartDate(startDate.getTime());
        GregorianCalendar endDate =
            new GregorianCalendar(2012, 1, 29);
        calendar.setEndDate(endDate.getTime());

        try {
            // Generate example data if none already exists
            EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit("book-examples");
            em.getTransaction().begin();
            em.createQuery("DELETE FROM MyCalendarEvent mce").executeUpdate();
    
            // Meeting every Monday morning
            GregorianCalendar startTime = new GregorianCalendar(2012, 1, 1, 9, 0);
            startTime.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
            startTime.set(java.util.Calendar.DAY_OF_WEEK_IN_MONTH, 1);
            for (int i=0; i<5; i++) {
                GregorianCalendar endTime = (GregorianCalendar) startTime.clone();
                endTime.add(java.util.Calendar.HOUR, 1);
                MyCalendarEvent event = new MyCalendarEvent("Monday Meeting",
                    "The same meeting every Monday",
                    startTime.getTime(), endTime.getTime(), "red");
                em.persist(event);
                startTime.add(java.util.Calendar.WEEK_OF_YEAR, 1);
            }
            em.getTransaction().commit();
    
            // Create a JPAContainer
            final JPAContainer<MyCalendarEvent> container =
                JPAContainerFactory.make(MyCalendarEvent.class,
                                         "book-examples");
    
            // The container must be ordered by start date. For JPAContainer
            // we can just set up sorting once and it will stay ordered.
            container.sort(new String[]{"startDate"}, new boolean[]{true});
            
            // Customize the event provider for adding events
            // as entities
            ContainerEventProvider cep =
                    new ContainerEventProvider(container) {
                private static final long serialVersionUID = 4985603843578816634L;
    
                @Override
                public void addEvent(CalendarEvent event) {
                    MyCalendarEvent entity = new MyCalendarEvent(
                        event.getCaption(), event.getDescription(),
                        event.getStart(), event.getEnd(),
                        event.getStyleName());
                    container.addEntity(entity);
                }
            };
            cep.setStartDateProperty("startDate");
            cep.setEndDateProperty("endDate");
            
            // Set the container as the data source
            calendar.setEventProvider(cep);
            
            // Now we can add events to the database through the calendar
            BasicEvent event = new BasicEvent("The Event", "Single Event",
                new GregorianCalendar(2012,1,15,12,00).getTime(),
                new GregorianCalendar(2012,1,15,14,00).getTime());
            event.setStyleName("blue");
            calendar.addEvent(event);
        } catch (Exception e) {
            layout.addComponent(new Label(e.getClass().getName() + ": " + e.getMessage()));
        }
        layout.addComponent(calendar);
        // END-EXAMPLE: component.calendar.jpacontainer
    }
}
