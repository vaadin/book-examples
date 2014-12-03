package com.vaadin.book.examples.addons.charts;

import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.addon.timeline.Timeline;
import com.vaadin.addon.timeline.Timeline.ChartMode;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TimelineExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    @SuppressWarnings("unchecked")
    void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.timeline.basic
        Timeline timeline = new Timeline("Reindeer Population");
        timeline.setChartMode(ChartMode.LINE); // The default
        timeline.setWidth("600px");
        timeline.setHeight("400px");
        timeline.setZoomLevelsVisible(false);
        timeline.setDateSelectVisible(false);
        timeline.setGraphLegendVisible(false);
        
        // A time series as a Vaadin container
        IndexedContainer data = new IndexedContainer();
        data.addContainerProperty("time", Date.class, null);
        data.addContainerProperty("value", Integer.class, null);
        
        // Add some data converted from another representation
        int rawdata[] = ChartsExample.reindeerData()[0];
        for (int i=0; i < rawdata.length; i++) {
            Item item = data.getItem(data.addItem());
            Date year = new GregorianCalendar(1959 + i, 1, 1).getTime();
            item.getItemProperty("time").setValue(year);
            item.getItemProperty("value").setValue(rawdata[i]);
        }
        
        timeline.addGraphDataSource(data, "time", "value");

        // TODO Doesn't work with Charts 2.0
        // timeline.setGraphOutlineColor(data, new Color(255, 0,0));
        
        layout.addComponent(timeline);
        // END-EXAMPLE: charts.timeline.basic
    }
}
