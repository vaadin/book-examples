package com.vaadin.book.examples.advanced.push;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

//BEGIN-EXAMPLE: advanced.push.basic
@Push(PushMode.AUTOMATIC)
public class PushyUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;

    DataSeries series = new DataSeries();
    
    @Override
    protected void init(VaadinRequest request) {
        // Display some data in a chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        chart.setSizeFull();
        setContent(chart);
        
        // Prepare the data display
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Hot New Data");
        conf.setSeries(series);
        
        // Start the data feed thread
        new FeederThread().start();
    }
    
    class FeederThread extends Thread {
        int x = 0;
        
        @Override
        public void run() {
            try {
                // Update the data for a while
                while (x < 100) {
                    Thread.sleep(1000);
                
                    access(new Runnable() {
                        @Override
                        public void run() {
                            // Some value
                            double y = Math.random();
                            
                            // Add the data point, and drop the
                            // first one if there's already
                            // more than 10 points.
                            series.add(new DataSeriesItem(x++, y),
                                true, x > 10);
                        }
                    });
                }

                // Inform that we have stopped running
                access(new Runnable() {
                    @Override
                    public void run() {
                        setContent(new Label("Done!"));
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// END-EXAMPLE: advanced.push.basic
