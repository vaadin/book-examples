package com.vaadin.book.examples.addons.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Pane;
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.PlotOptionsGauge;
import com.vaadin.addon.charts.model.PlotOptionsSolidGauge;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.YAxis.Stop;
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
        yaxis.setTickLength(10);
        yaxis.setTickWidth(1);
        yaxis.setMinorTickInterval(1);
        yaxis.setMinorTickLength(5);
        yaxis.setMinorTickWidth(1);
        yaxis.setPlotBands(new PlotBand[]{
                new PlotBand(0,  60,  SolidColor.GREEN),
                new PlotBand(60, 80,  SolidColor.YELLOW),
                new PlotBand(80, 100, SolidColor.RED)});
        yaxis.setGridLineWidth(0); // Disable grid
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
        conf.setTitle("Solid Gauge");

        Pane pane = conf.getPane();
        pane.setSize("125%");           // For positioning tick labels
        pane.setCenterXY("50%", "70%"); // Move center lower
        pane.setStartAngle(-90);        // Make semi-circle
        pane.setEndAngle(90);           // Make semi-circle
        
        Background bkg = new Background();
        bkg.setBackgroundColor(new SolidColor("#eeeeee")); // Gray
        bkg.setInnerRadius("60%");
        bkg.setOuterRadius("100%");
        bkg.setShape("arc"); // solid or arc
        pane.setBackground(bkg);
        
        // Solid gauge doesn't currently have any special options
        PlotOptionsSolidGauge options = new PlotOptionsSolidGauge();

        // Move the value display box at the center a bit higher
        Labels dataLabels = new Labels();
        dataLabels.setY(-20);
        options.setDataLabels(dataLabels);
        conf.setPlotOptions(options);

        // The data with an initial value
        ListSeries series = new ListSeries("Pressure MPa", 80);
        conf.addSeries(series);

        // Set up the value axis
        YAxis yaxis = conf.getyAxis();
        yaxis.setTitle("Pressure GPa");
        yaxis.getTitle().setY(-80); // Move 80 px upwards from center

        // The limits are mandatory
        yaxis.setMin(0);
        yaxis.setMax(200);
        
        // Configure ticks and labels
        yaxis.setTickInterval(100); // At 0, 100, and 200
        yaxis.getLabels().setY(16); // Move 16 px downwards
        // yaxis.getLabels().setRotationPerpendicular();
        yaxis.setGridLineWidth(0); // Disable grid

        // Configure color change stops
        yaxis.setStops(new Stop(0.1f, SolidColor.GREEN),
                       new Stop(0.5f, SolidColor.YELLOW),
                       new Stop(0.9f, SolidColor.RED));

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
