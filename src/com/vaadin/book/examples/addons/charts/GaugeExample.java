package com.vaadin.book.examples.addons.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.PlotOptionsGauge;
import com.vaadin.addon.charts.model.PlotOptionsSolidGauge;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GaugeExample extends CustomComponent implements AnyBookExampleBundle {
    public void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.gauge.basic
        Chart chart = new Chart(ChartType.GAUGE);
        chart.setWidth("300px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Speedometer");
        conf.getPane().setStartAngle(-135);
        conf.getPane().setEndAngle(135);
        
        PlotOptionsGauge options = new PlotOptionsGauge();
        // Default are OK
        conf.setPlotOptions(options);

        // The data
        ListSeries series = new ListSeries("Speed");
        series.setData(80); // Initial value
        conf.addSeries(series);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("km/h");
        yaxis.setMin(0);
        yaxis.setMax(100);
        yaxis.getLabels().setStep(1);
        yaxis.setTickInterval(10);
        yaxis.setPlotBands(new PlotBand[]{
                new PlotBand(0,  60,  SolidColor.GREEN),
                new PlotBand(60, 80,  SolidColor.YELLOW),
                new PlotBand(80, 100, SolidColor.RED)});
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);

        TextField tf = new TextField("Enter a new value");
        layout.addComponent(tf);

        Button update = new Button("Update", e -> { // Java 8
            Integer newValue = new Integer((String)tf.getValue());
            series.updatePoint(0, newValue);
        }); 
        layout.addComponent(update);
        // END-EXAMPLE: charts.charttype.gauge.basic
    }

    public void solid(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.gauge.solid
        Chart chart = new Chart(ChartType.SOLIDGAUGE);
        chart.setWidth("300px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Speedometer");
        conf.getPane().setStartAngle(-135);
        conf.getPane().setEndAngle(135);
        
        PlotOptionsSolidGauge options = new PlotOptionsSolidGauge();
        // Solid gauge doesn't currently have any special options
        conf.setPlotOptions(options);

        // The data
        ListSeries series = new ListSeries("Speed");
        series.setData(80); // Initial value
        conf.addSeries(series);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("km/h");
        yaxis.setMin(0);
        yaxis.setMax(100);
        yaxis.getLabels().setStep(1);
        yaxis.setTickInterval(10);
        yaxis.setPlotBands(new PlotBand[]{
                new PlotBand(0,  60,  SolidColor.GREEN),
                new PlotBand(60, 80,  SolidColor.YELLOW),
                new PlotBand(80, 100, SolidColor.RED)});
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);

        TextField tf = new TextField("Enter a new value");
        layout.addComponent(tf);

        Button update = new Button("Update", e -> { // Java 8
            Integer newValue = new Integer((String)tf.getValue());
            series.updatePoint(0, newValue);
        }); 
        layout.addComponent(update);
        // END-EXAMPLE: charts.charttype.gauge.solid
    }

    public void both(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.gauge.both
        VerticalLayout layout1 = new VerticalLayout();
        basic(layout1);

        VerticalLayout layout2 = new VerticalLayout();
        solid(layout2);
        
        layout.addComponent(new HorizontalLayout(layout1, layout2));
        // END-EXAMPLE: charts.charttype.gauge.both
    }
}
