package com.vaadin.book.examples.addons.charts;

import java.util.Arrays;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.BoxPlotItem;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.PlotOptionsBoxPlot;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class BoxPlotExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 1439057228889940743L;

    public void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.boxplot.basic
        Chart chart = new Chart(ChartType.BOXPLOT);
        chart.setWidth("600px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Orienteering Split Times");
        conf.getLegend().setEnabled(false);

        // Set median line color and width
        PlotOptionsBoxPlot plotOptions = new PlotOptionsBoxPlot();
        plotOptions.setMedianColor(SolidColor.BLUE);
        plotOptions.setMedianWidth(3);
        // plotOptions.setWhiskerLength(whiskerLength)
        conf.setPlotOptions(plotOptions);
        
        // Example: orienteering control point times for runners
        double data[][] = orienteeringdata(); 

        DataSeries series = new DataSeries();
        for (double cpointtimes[]: data) {
            StatAnalysis analysis = new StatAnalysis(cpointtimes);
            series.add(new BoxPlotItem(analysis.low(),
                                       analysis.firstQuartile(),
                                       analysis.median(),
                                       analysis.thirdQuartile(),
                                       analysis.high()));
        }
        conf.setSeries(series);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("S-1", "1-2", "2-3", "3-4", "4-5",
                            "5-6", "6-7", "7-8");
        xaxis.setTitle("Check Point");
        xaxis.setTickInterval(1);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Time (s)");
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
    }

    static public double[][] orienteeringdata() {
        double data[][] = {
            {65,63,71,86,115,117,106,122,144,320,164,230,128,170,184,463,194,215,194},
            {117,107,115,114,135,194,191,270,253,441,462,515,293,258,340,313,348,359,520},
            {56,58,65,237,151,98,152,108,124,140,142,157,369,217,158,146,483,449,286},
            {198,194,333,276,318,325,309,326,376,357,409,366,465,516,530,820,584,583,885},
            {63,65,69,69,125,112,106,100,122,160,167,152,187,255,163,159,357,371,192},
            {80,78,77,72,87,120,140,121,133,129,129,159,192,191,185,146,216,204,217},
            {160,175,184,158,209,242,271,284,395,287,321,283,359,450,492,360,501,535,733},
            {80,89,109,87,81,118,107,142,149,111,156,122,105,160,238,145,114,216,240}};
        return data;
    }    
    
    class StatAnalysis {
        private double orderedData[];

        public StatAnalysis(double data[]) {
            orderedData = data.clone();
            Arrays.sort(orderedData);
        }
        
        public double low() {
            return orderedData[0];
        }

        public double high() {
            return orderedData[orderedData.length - 1];
        }

        public double median() {
            if ((orderedData.length % 2) == 1)
                return (orderedData[orderedData.length/2] + orderedData[orderedData.length/2 + 1])/2;
            else
                return orderedData[orderedData.length/2];
        }

        public double firstQuartile() {
            switch (orderedData.length % 4) {
                case 0: return (orderedData[orderedData.length/4] + orderedData[orderedData.length/4 + 1])/2;
                case 1: return orderedData[orderedData.length/4]*0.25 + orderedData[orderedData.length/4 + 1]*0.75;
                case 2: return orderedData[orderedData.length/4];
                case 3: return orderedData[orderedData.length/4]*0.75 + orderedData[orderedData.length/4 + 1]*0.25;
            }
            return 0.0;
        }

        public double thirdQuartile() {
            switch (orderedData.length % 4) {
                case 0: return (orderedData[orderedData.length/4] + orderedData[orderedData.length/4 - 1])/2;
                case 1: return orderedData[orderedData.length/4*3]*0.25 + orderedData[orderedData.length/4*3 - 1]*0.75;
                case 2: return orderedData[orderedData.length/4*3];
                case 3: return orderedData[orderedData.length/4*3]*0.75 + orderedData[orderedData.length/4*3 - 1]*0.25;
            }
            return 0.0;
        }
    }
    // END-EXAMPLE: charts.charttype.boxplot.basic
}
