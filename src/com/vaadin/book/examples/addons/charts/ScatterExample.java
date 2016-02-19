package com.vaadin.book.examples.addons.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.MarkerSymbolEnum;
import com.vaadin.addon.charts.model.MarkerSymbolUrl;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class ScatterExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 6699462235800845680L;

    public void scatter (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.scatter
        Chart chart = new Chart(ChartType.SCATTER);
        chart.setWidth("500px");
        chart.setHeight("500px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Random Sphere");
        conf.getLegend().setEnabled(false); // Disable legend

        PlotOptionsScatter plotOptions = new PlotOptionsScatter();
        conf.setPlotOptions(plotOptions);
        
        DataSeries series = new DataSeries();
        for (int i=0; i<300; i++) {
            double lng = Math.random() * 2 * Math.PI;
            double lat = Math.random() * Math.PI - Math.PI/2;
            double x   = Math.cos(lat) * Math.sin(lng);
            double y   = Math.sin(lat);
            double z   = Math.cos(lng) * Math.cos(lat);
            
            DataSeriesItem point = new DataSeriesItem(x,y);
            Marker marker = new Marker();
            
            // Set a radius
            marker.setRadius((z+1)*5);

            // Use an alternative symbol for some points
            if (Math.random() > 0.9)
                marker.setSymbol(MarkerSymbolEnum.DIAMOND);
            else if (Math.random() > 0.95) {
                // TODO Check that this is correct
                String url = VaadinServlet.getCurrent().getServletContext()
                    .getRealPath("/VAADIN/themes/book-examples/img/smiley2-20px.png");
                marker.setSymbol(new MarkerSymbolUrl(url));
            }

            // Set line width and color
            marker.setLineWidth(1); // Normally zero width
            marker.setLineColor(SolidColor.BLACK);
            
            // Set RGB fill color
            int level = (int) Math.round((1-z)*127);
            marker.setFillColor(new SolidColor(255-level, 0, level));
            point.setMarker(marker);
            series.add(point);
        }
        conf.addSeries(series);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setTitle("X");
        xaxis.setExtremes(-1.0, 1.0);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Y");
        yaxis.setExtremes(-1.0, 1.0);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.scatter
    }

}
