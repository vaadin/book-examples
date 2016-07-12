package com.vaadin.book.examples.addons.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPyramid;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class PyramidExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 1439057228889940743L;

    public void pyramid(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.funnelpyramid
        Chart chart = new Chart(ChartType.PYRAMID);
        chart.setWidth("500px");
        chart.setHeight("350px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Monster Food Pyramid");
        conf.getLegend().setEnabled(false);
        conf.getCredits().setEnabled(false);
        
        // Give more room for the labels
        conf.getChart().setSpacingRight(120);

        // Configure the funnel neck shape 
        PlotOptionsPyramid options = new PlotOptionsPyramid();
        
        options.setDepth(10);
        options.setShadow(true);

        // Style the data labels
        Labels dataLabels = new Labels();
        dataLabels.setFormat("<b>{point.name}</b> ({point.y:,.0f})");
        dataLabels.setSoftConnector(false);
        dataLabels.setColor(SolidColor.BLACK);
        options.setDataLabels(dataLabels);
      
        conf.setPlotOptions(options);

        // Create the range series
        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Grid Bugs", 340));
        series.add(new DataSeriesItem("Newts", 235));
        series.add(new DataSeriesItem("Adventurers", 187));
        series.add(new DataSeriesItem("Trolls", 120));
        series.add(new DataSeriesItem("Wizards", 70));
        series.add(new DataSeriesItem("Mind Flayers", 55));
        conf.addSeries(series);

        // Configure X axis
        XAxis xaxis = new XAxis();
        conf.addxAxis(xaxis);

        // Configure Y axis
        YAxis yaxis = new YAxis();
        conf.addyAxis(yaxis);

        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.funnel.pyramid
    }

    public void both(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.funnel.both
        VerticalLayout layout1 = new VerticalLayout();
        new FunnelExample().funnel(layout1);

        VerticalLayout layout2 = new VerticalLayout();
        pyramid(layout2);
        
        layout.addComponent(new HorizontalLayout(layout1, layout2));
        // END-EXAMPLE: charts.charttype.funnel.both
    }
}
