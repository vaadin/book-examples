package com.vaadin.book.examples.addons.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsFunnel;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

public class FunnelExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 1439057228889940743L;

    public void funnel (Layout layout) {
        // BEGIN-EXAMPLE: charts.charttype.funnel.funnel
        Chart chart = new Chart(ChartType.FUNNEL);
        chart.setWidth("500px");
        chart.setHeight("350px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Monster Funnel");
        conf.getLegend().setEnabled(false);
        conf.getCredits().setEnabled(false);
        
        // Give more room for the labels
        conf.getChart().setSpacingRight(120);

        // Configure the funnel neck shape 
        PlotOptionsFunnel options = new PlotOptionsFunnel();
        options.setNeckHeightPercentage(20);
        options.setNeckWidthPercentage(20);

        // Style the data labels
        Labels dataLabels = new Labels();
        dataLabels.setFormat("<b>{point.name}</b> ({point.y:,.0f})");
        dataLabels.setSoftConnector(false);
        dataLabels.setColor(SolidColor.BLACK);
        options.setDataLabels(dataLabels);
      
        conf.setPlotOptions(options);

        // Create the range series
        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Monsters Met", 340));
        series.add(new DataSeriesItem("Engaged", 235));
        series.add(new DataSeriesItem("Killed", 187));
        series.add(new DataSeriesItem("Tinned", 70));
        series.add(new DataSeriesItem("Eaten", 55));
        conf.addSeries(series);

        // Configure X axis
        XAxis xaxis = new XAxis();
        conf.addxAxis(xaxis);

        // Configure Y axis
        YAxis yaxis = new YAxis();
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.funnel.funnel
    }
}
