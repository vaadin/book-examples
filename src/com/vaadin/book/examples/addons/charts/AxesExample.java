package com.vaadin.book.examples.addons.charts;

import java.util.GregorianCalendar;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DashStyle;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.MarkerSymbolUrl;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class AxesExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 1439057228889940743L;

    public void axes(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.configuration.axes
        Chart chart = new Chart(ChartType.SCATTER);
        chart.setWidth("500px");
        chart.setHeight("500px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Axes");
        conf.getLegend().setEnabled(false); // Disable legend

        PlotOptionsScatter plotOptions = new PlotOptionsScatter();
        String axe = VaadinServlet.getCurrent().getServletContext().getContextPath()
                + "/VAADIN/themes/book-examples/icons/axe-8px.png";
        Marker marker = new Marker();
        marker.setSymbol(new MarkerSymbolUrl(axe));
        plotOptions.setMarker(marker);

        Labels dataLabels = new Labels(true);
        dataLabels.setFormat("{x:%1.0f},{y:%1.0f}");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);

        // Have a data series containing some data
        DataSeries series = new DataSeries();
        for (int i=0; i<10; i++)
            series.add(new DataSeriesItem(
                100 * Math.random(), 100 * Math.random()));
        conf.addSeries(series);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setTitle("X");
        xaxis.setExtremes(0.0, 100.0);
        
        xaxis.setType(AxisType.LINEAR); // Default
        
        // Set up major ticks
        xaxis.setTickColor(SolidColor.GREEN);
        xaxis.setTickInterval(10.0);
        xaxis.setTickLength(8);
        xaxis.setTickWidth(2);
        
        // Set up major grid lines
        xaxis.setGridLineColor(SolidColor.GREEN);
        xaxis.setGridLineDashStyle(DashStyle.SOLID);
        xaxis.setGridLineWidth(2);
        
        // Set up minor ticks
        xaxis.setMinorTickColor(SolidColor.GREEN);
        xaxis.setMinorTickInterval(5.0);
        xaxis.setMinorTickLength(5);
        xaxis.setMinorTickWidth(1);

        // Axis line
        xaxis.setLineColor(SolidColor.GREEN);
        xaxis.setLineWidth(3);
        
        // Set up minor grid lines
        // TODO The API is wrong, update this later
        // xaxis.setMinorGridLineColor(SolidColor.GREEN);
        
        Labels xlabels = xaxis.getLabels();
        xlabels.setAlign(HorizontalAlign.RIGHT);
        xlabels.setBackgroundColor(SolidColor.PALEGREEN);
        xlabels.setBorderWidth(3);
        xlabels.setColor(SolidColor.GREEN);
        xlabels.setRotation(-45);
        xlabels.setStep(2); // Every 2 major tick
        xlabels.setFormat("{value:%02.2f}");
        Style xlabelsstyle = new Style();
        xlabelsstyle.setColor(SolidColor.GREEN);
        xlabels.setStyle(xlabelsstyle);

        // Finally, use it
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Y");
        yaxis.setExtremes(0.0, 100.0);

        yaxis.setType(AxisType.LINEAR); // Default

        // Set up major ticks
        yaxis.setTickColor(SolidColor.RED);
        yaxis.setTickInterval(20.0);
        yaxis.setTickLength(8);
        yaxis.setTickWidth(2);
        
        // Set up major grid lines
        yaxis.setGridLineColor(SolidColor.RED);
        yaxis.setGridLineDashStyle(DashStyle.DASH);
        yaxis.setGridLineWidth(2);
        
        // Set up minor ticks
        yaxis.setMinorTickColor(SolidColor.RED);
        yaxis.setMinorTickInterval(5.0);
        yaxis.setMinorTickLength(5);
        yaxis.setMinorTickWidth(1);
        
        // Set up alternating colors
        yaxis.setAlternateGridColor(SolidColor.AQUA);
        
        // Axis line
        yaxis.setLineColor(SolidColor.RED);
        yaxis.setLineWidth(3);

        Labels ylabels = yaxis.getLabels();
        ylabels.setFormatter("function() {return this.value + ' km';}");
        
        // Set up minor grid lines
        // TODO The API is wrong, update this later
        // yaxis.setMinorGridLineColor(SolidColor.RED);

        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.configuration.axes
    }

    public void timelogarithmic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.configuration.axes
        Chart chart = new Chart(ChartType.SCATTER);
        chart.setWidth("500px");
        chart.setHeight("500px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Axes 2");
        // conf.getLegend().setEnabled(false); // Disable legend

        PlotOptionsScatter plotOptions = new PlotOptionsScatter();
        conf.setPlotOptions(plotOptions);

        // Have a data series containing some data
        DataSeries series = new DataSeries();
        GregorianCalendar cal = new GregorianCalendar();
        for (int i=0; i<300; i++) {
            series.add(new DataSeriesItem(
                cal.getTime(), 100 * Math.random()));
            cal.add(GregorianCalendar.DAY_OF_YEAR, 1);
        }
        conf.addSeries(series);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setTitle("Time (s)");
        // xaxis.setExtremes(0.0, 100.0);
        
        xaxis.setType(AxisType.DATETIME);
        
        // Set up major ticks
        //xaxis.setTickColor(SolidColor.GREEN);
        //xaxis.setTickInterval(20.0);
        //xaxis.setTickLength(5);
        //xaxis.setTickWidth(2);
        
        // Set up major grid lines
        //xaxis.setGridLineColor(SolidColor.GREEN);
        //xaxis.setGridLineDashStyle(DashStyle.SOLID);
        //xaxis.setGridLineWidth(2);
        
        // Set up minor ticks
        //xaxis.setMinorTickColor(SolidColor.GREEN);
        //xaxis.setMinorTickInterval(5.0);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Measurement");
        yaxis.setExtremes(0.1, 100.0);

        yaxis.setType(AxisType.LOGARITHMIC);
        
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.configuration.axes
    }
}
