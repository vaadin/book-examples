package com.vaadin.book.examples.addons.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HeatSeries;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsHeatMap;
import com.vaadin.addon.charts.model.RangeSeries;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class HeatMapExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 867925990997057087L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.heatmap.basic
        Chart chart = new Chart(ChartType.HEATMAP);
        chart.setWidth("600px");
        chart.setHeight("300px");
        
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Heat Data");

        // Set max and min values manually
        conf.getColorAxis().setMin(-51.5);
        conf.getColorAxis().setMax(+37.2);

        // Set colors for the extremes
        conf.getColorAxis().setMinColor(new SolidColor("#8080ff"));
        conf.getColorAxis().setMaxColor(SolidColor.RED);

        // TODO This doesn't work to define multiple color stops
        //conf.getColorAxis().setPlotBands(new PlotBand[]{
        //        new PlotBand(0,  60,  SolidColor.GREEN),
        //        new PlotBand(60, 80,  SolidColor.YELLOW),
        //        new PlotBand(80, 100, SolidColor.RED)});
        
        PlotOptionsHeatMap plotOptions = new PlotOptionsHeatMap();
        plotOptions.setBorderColor(SolidColor.WHITE);
        plotOptions.setBorderWidth(2);
        plotOptions.setDataLabels(new Labels(true));
        conf.setPlotOptions(plotOptions);

        // Create some data
        HeatSeries series = new HeatSeries();
        series.addHeatPoint( 0, 0,  10.9); // Jan High
        series.addHeatPoint( 0, 1, -51.5); // Jan Low
        series.addHeatPoint( 1, 0,  11.8); // Feb High
        series.addHeatPoint( 1, 1, -49.0); // Feb Low
        series.addHeatPoint( 2, 0,  17.5); // Mar High
        series.addHeatPoint( 2, 1, -44.3); // Mar Low
        series.addHeatPoint( 3, 0,  25.5); // Apr High
        series.addHeatPoint( 3, 1, -36.0); // Apr Low
        series.addHeatPoint( 4, 0,  31.0); // May High
        series.addHeatPoint( 4, 1, -24.6); // May Low
        series.addHeatPoint( 5, 0,  33.8); // Jun High
        series.addHeatPoint( 5, 1, -07.0); // Jun Low
        series.addHeatPoint( 6, 0,  37.2); // Jul High
        series.addHeatPoint( 6, 1, -05.0); // Jul Low
        series.addHeatPoint( 7, 0,  33.8); // Aug High
        series.addHeatPoint( 7, 1, -10.8); // Aug Low
        series.addHeatPoint( 8, 0,  28.8); // Sep High
        series.addHeatPoint( 8, 1, -18.7); // Sep Low
        series.addHeatPoint( 9, 0,  19.4); // Oct High
        series.addHeatPoint( 9, 1, -31.8); // Oct Low
        series.addHeatPoint(10, 0,  14.1); // Nov High
        series.addHeatPoint(10, 1, -42.0); // Nov Low
        series.addHeatPoint(11, 0,  10.8); // Dec High
        series.addHeatPoint(11, 1, -47.0); // Dec Low
        conf.addSeries(series);

        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setTitle("Month");
        xaxis.setCategories("Jan", "Feb", "Mar",
            "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec");
        conf.addxAxis(xaxis);

        // Set the category labels on the axis correspondingly
        YAxis yaxis = new YAxis();
        yaxis.setTitle("");
        yaxis.setCategories("High °C", "Low °C");
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.heatmap.basic
    }

    public void basic2(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.heatmap.basic
        Chart chart = new Chart(ChartType.HEATMAP);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Heat Data");

        PlotOptionsHeatMap plotOptions = new PlotOptionsHeatMap();
        conf.setPlotOptions(plotOptions);

        // Set up color axis range
        conf.getColorAxis().setMin(0.0);
        conf.getColorAxis().setMax(1.0);

        // Create some data
        Number[][] data = new Number[100][3];
        for (int i=0; i < 10*10; i++)
            data[i] = new Number[]{i/10, i%10, // Row and column
                Math.sin(0.5 * (i/10)) * Math.cos(0.5 * (i%10))};
        conf.setSeries(new RangeSeries(data));
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.heatmap.basic
    }

    public void simulation(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.heatmap.simulation
        Chart chart = new Chart(ChartType.HEATMAP);
        chart.setWidth("450px");
        chart.setHeight("500px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Heat Transfer Simulation");

        PlotOptionsHeatMap plotOptions = new PlotOptionsHeatMap();
        conf.setPlotOptions(plotOptions);
        
        double[][] heatmapState = createHeatMap(50, 50);
        Number[][] heatmapData = convertMatrixToHeatMapData(heatmapState);
        RangeSeries heatmapSeries = new RangeSeries("Heatmap State", heatmapData);
        conf.setSeries(heatmapSeries);
        
        conf.getColorAxis().setMin(0.0);
        conf.getColorAxis().setMax(1.0);

        // Configure the grid axes
        YAxis yaxis = new YAxis();
        yaxis.setExtremes(0, 50);
        conf.addyAxis(yaxis);

        XAxis xaxis = new XAxis();
        xaxis.setExtremes(0, 50);
        conf.addxAxis(xaxis);

        layout.addComponent(chart);
        
        layout.addComponent(new Button("Start", e -> {
            new FeederThread(chart, heatmapState,
                () -> e.getButton().setEnabled(true)).start();
            e.getButton().setEnabled(false);
        }));
    }

    class FeederThread extends Thread {
        int x = 0;
        
        Chart chart;
        double[][] heatmapState;
        Runnable finished;
        
        public FeederThread(Chart chart, double[][] heatmapState, Runnable finished) {
            this.chart = chart;
            this.heatmapState = heatmapState;
            this.finished = finished;
        }
        
        @Override
        public void run() {
            try {
                // Update the data for a while
                while (x++ < 100) {
                    Thread.sleep(1000);
                
                    UI.getCurrent().access(() -> {
                        evolveHeatMap(heatmapState);

                        // Entirely replace the data series
                        Number[][] newData = convertMatrixToHeatMapData(heatmapState);
                        RangeSeries newSeries = new RangeSeries("Heatmap State", newData);
                        chart.getConfiguration().setSeries(newSeries);
                        chart.drawChart();
                    });
                    
                    System.out.println("Step " + x);
                }

                // Inform that we have stopped running
                getUI().access(finished);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private Number[][] convertMatrixToHeatMapData(double[][] heatmap) {
        Number[][] heatMapData = new Number[heatmap.length*heatmap[0].length][3];
        int pos = 0;
        for (int row=0; row < heatmap.length; row++)
            for (int col=0; col < heatmap[row].length; col++) {
                heatMapData[pos][0] = row;
                heatMapData[pos][1] = col;
                heatMapData[pos++][2] = heatmap[row][col];
            }
        return heatMapData;
    }

    private void updateDataSeriesFromMatrix(DataSeries series, double[][] heatmap) {
        int pos = 0;
        for (int row=0; row < heatmap.length; row++)
            for (int col=0; col < heatmap[row].length; col++) {
                DataSeriesItem item = series.get(pos++);
                item.setX(row);
                item.setLow(col);
                item.setHigh(heatmap[row][col]);
                series.update(item);
            }
    }

    private void evolveHeatMap(double[][] heatmap) {
        // Input heat data
        inputHeatMap(heatmap);
        
        double[][] next = heatmap.clone();
        
        for (int row=0; row < heatmap.length; row++)
            for (int col=0; col < heatmap[row].length; col++) {
                Double left = (col > 0)? heatmap[row][col-1] : null;
                Double right = (col < heatmap[row].length - 1)? heatmap[row][col+1] : null;
                Double up = (row > 0)? heatmap[row-1][col] : null;
                Double down = (row < heatmap.length - 1)? heatmap[row+1][col] : null;
                next[row][col] = cellFunction(heatmap[row][col], left, right, up, down);
            }

        // Write the buffer back to data
        for (int row=0; row < heatmap.length; row++) {
            for (int col=0; col < heatmap[row].length; col++)
                heatmap[row][col] = next[row][col];
        }
    }

    /** Inputs hot or cold spots in the heat map */
    private void inputHeatMap(double[][] heatmap) {
        heatmap[0][heatmap[0].length/2] = 1.0;
    }

    private double cellFunction(double current, Double left, Double right, Double up, Double down) {
        double value = 0.99 * current;
        if (left != null)
            value += 0.1 * left; 
        if (right != null)
            value += 0.1 * right; 
        if (up != null)
            value += 0.02 * up; 
        if (down != null)
            value += 0.2 * down; 
        return value;
    }
    
    private double[][] createHeatMap(int rows, int columns) {
        double[][] heatmap = new double[rows][columns];
        randomizeHeatMap(heatmap, 0.0, 1.0);
        return heatmap;
    }

    private double gaussrnd (double stdv) {
        if (stdv>0.0) {
            double r = Math.random()-0.5;
            return stdv * Math.log((r+0.5)/(0.5-r));
        } else
            return 0.0;
    }

    private void randomizeHeatMap(double[][] heatmap, double mean, double stddev) {
        for (int row=0; row < heatmap.length; row++)
            for (int col=0; col < heatmap[row].length; col++)
                heatmap[row][col] = mean + gaussrnd(stddev);
    }
    // END-EXAMPLE: charts.charttype.heatmap.simulation
}
