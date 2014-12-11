package com.vaadin.book.examples.addons.charts;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.BoxPlotItem;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DashStyle;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.DataSeriesItem3d;
import com.vaadin.addon.charts.model.Frame;
import com.vaadin.addon.charts.model.FramePanel;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.MarkerSymbolEnum;
import com.vaadin.addon.charts.model.MarkerSymbolUrl;
import com.vaadin.addon.charts.model.Options3d;
import com.vaadin.addon.charts.model.PlotOptionsArea;
import com.vaadin.addon.charts.model.PlotOptionsBoxPlot;
import com.vaadin.addon.charts.model.PlotOptionsBubble;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsErrorBar;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.PlotOptionsWaterfall;
import com.vaadin.addon.charts.model.RangeSeries;
import com.vaadin.addon.charts.model.Stacking;
import com.vaadin.addon.charts.model.TickPosition;
import com.vaadin.addon.charts.model.TickmarkPlacement;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.WaterFallSum;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.FontWeight;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.book.examples.BookExampleBundle;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class ChartTypesExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        if ("all".equals(context))
            charttypes(layout);
        else if ("line".equals(context))
            linechart(layout);
        else if ("area".equals(context))
            areachart(layout);
        else if ("arearange".equals(context))
            arearangechart(layout);
        else if ("column".equals(context))
            columnchart(layout);
        else if ("boxplot".equals(context))
            boxplotchart(layout);
        else if ("scatter".equals(context))
            scatterchart(layout);
        else if ("errorbar".equals(context))
            errorbarchart(layout);
        else if ("bubble".equals(context))
            bubblechart(layout);
        else if ("pie".equals(context))
            piechart(layout);
        else if ("waterfall".equals(context))
            waterfallchart(layout);
        else if ("polar".equals(context))
            polarchart(layout);
        else if ("spiderweb".equals(context))
            spiderwebchart(layout);
        else if ("polarspiderweb".equals(context))
            polarspiderwebchart(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    void linechart (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.line
        Chart chart = new Chart(ChartType.LINE);
        chart.setWidth("400px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Reindeer Population in Finland");
        conf.setSubTitle("Source: Paliskuntain yhdistys");
        conf.getLegend().setEnabled(true);

        // Disable markers from lines
        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setMarker(new Marker(false));
        conf.setPlotOptions(plotOptions);

        // The data with two columns
        int data[][] = reindeerData();
        String columns[] = {"Kept Alive", "Reduce"};

        // Create two series
        for (int s=0; s<2; s++) {
            DataSeries series = new DataSeries();
            series.setName(columns[s]);
            final int startValue = 1959;
            for (int i = 0; i < data[s].length; i++)
                series.add(new DataSeriesItem(startValue + i,
                                              data[s][i]));
        
            // Modify the color of one point
            int maxitem = findMaxItem(data, s);
            DataSeriesItem item = series.get(maxitem);
            Marker maxMarker = new Marker(true);
            maxMarker.setFillColor(SolidColor.RED);
            item.setMarker(maxMarker);

            conf.setSeries(series);
        }
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setTitle("Year");
        xaxis.setMin(1959);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Population");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.line
    }

    void areachart (VerticalLayout rootlayout) {
        HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: charts.charttype.area
        for (boolean stacked: new boolean[] {false, true}) {
            Chart chart = new Chart(ChartType.AREA);
            chart.setWidth("400px");
            chart.setHeight("300px");
            
            // Modify the default configuration a bit
            Configuration conf = chart.getConfiguration();
            conf.setTitle("Reindeer Population in Finland");
            if (stacked)
                conf.setSubTitle("Stacked");
            else
                conf.setSubTitle("Not Stacked");
    
            PlotOptionsArea plotOptions = new PlotOptionsArea();
            if (stacked)
                plotOptions.setStacking(Stacking.NORMAL);
            Marker marker = new Marker();
            marker.setEnabled(true);
            plotOptions.setMarker(marker);
            conf.setPlotOptions(plotOptions);
            conf.getLegend().setEnabled(true);
            plotOptions.setPointStart(1959);
    
            // The data with two columns
            int data[][] = reindeerData();
            String columns[] = {"Reindeer", "Slaughter"};
    
            // Create two series
            for (int s=0; s<2; s++) {
                ListSeries series = new ListSeries();
                series.setName(columns[s]);
                for (int i = 0; i < data[s].length; i++)
                    series.addData(data[s][i]);
                conf.addSeries(series);
            }
            
            // Set the category labels on the axis correspondingly
            XAxis xaxis = new XAxis();
            xaxis.setTitle("Year");
            xaxis.setMin(1959);
            conf.addxAxis(xaxis);
    
            // Set the Y axis title
            YAxis yaxis = new YAxis();
            yaxis.setTitle("Population");
            yaxis.getLabels().setStep(2);
            conf.addyAxis(yaxis);
            
            layout.addComponent(chart);
        }
        // END-EXAMPLE: charts.charttype.area
        rootlayout.addComponent(layout);
    }

    void arearangechart (VerticalLayout rootlayout) {
        HorizontalLayout layout = new HorizontalLayout();
        
        // BEGIN-EXAMPLE: charts.charttype.arearange
        for (ChartType type: new ChartType[]{ChartType.AREARANGE,
                                             ChartType.COLUMNRANGE}) {
            Chart chart = new Chart(type);
            chart.setWidth("400px");
            chart.setHeight("400px");
            
            // Modify the default configuration a bit
            Configuration conf = chart.getConfiguration();
            conf.setTitle("Extreme Temperature Range in Finland");
            conf.setSubTitle("http://ilmatieteenlaitos.fi/lampotilaennatyksia");
            conf.getLegend().setEnabled(false);
    
            // Create the range series
            // Source: http://ilmatieteenlaitos.fi/lampotilaennatyksia
            RangeSeries series = new RangeSeries("Temperature Extremes",
                new Double[]{-51.5,10.9}, // Kittilä, Pokka 28.1.1999 / Maarianhamina 6.1.1973
                new Double[]{-49.0,11.8}, //
                new Double[]{-44.3,17.5}, //
                new Double[]{-36.0,25.5}, //
                new Double[]{-24.6,31.0}, //
                new Double[]{ -7.0,33.8}, //
                new Double[]{ -5.0,37.2}, //
                new Double[]{-10.8,33.8}, //
                new Double[]{-18.7,28.8}, //
                new Double[]{-31.8,19.4}, //
                new Double[]{-42.0,14.1}, //
                new Double[]{-47.0,10.8});//
            conf.addSeries(series);

            RangeSeries series2 = new RangeSeries("Temperature Extremes");
            
            // Give low-high values in constructor
            series2.add(new DataSeriesItem(0,  -51.5, 10.9)); // Kittilä, Pokka 28.1.1999 / Maarianhamina 6.1.1973
            series2.add(new DataSeriesItem(1,  -49.0, 11.8));
            
            // Set low-high values with setters
            DataSeriesItem point2 = new DataSeriesItem();
            point2.setX(2);
            point2.setLow(-44.3);
            point2.setHigh(17.5);
            series2.add(point2);
            
            series2.add(new DataSeriesItem(3,  -36.0, 25.5));
            series2.add(new DataSeriesItem(4,  -24.6, 31.0));
            series2.add(new DataSeriesItem(5,   -7.0, 33.8));
            series2.add(new DataSeriesItem(6,   -5.0, 37.2));
            series2.add(new DataSeriesItem(7,  -10.8, 33.8));
            series2.add(new DataSeriesItem(8,  -18.7, 28.8));
            series2.add(new DataSeriesItem(9,  -31.8, 19.4));
            series2.add(new DataSeriesItem(10, -42.0, 14.1));
            series2.add(new DataSeriesItem(11, -47.0, 10.8));
            conf.addSeries(series2);
    
            // Set the category labels on the axis correspondingly
            XAxis xaxis = new XAxis();
            xaxis.setTitle("Month");
            xaxis.setCategories("Jan", "Feb", "Mar",
                "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec");
            conf.addxAxis(xaxis);
    
            // Set the Y axis title
            YAxis yaxis = new YAxis();
            yaxis.setTitle("Temperature °C");
            yaxis.getLabels().setStep(2);
            conf.addyAxis(yaxis);
            
            layout.addComponent(chart);
        }
        // END-EXAMPLE: charts.charttype.arearange
        rootlayout.addComponent(layout);
    }

    void scatterchart (VerticalLayout layout) {
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

    void bubblechart (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.bubble
        // Create a bubble chart
        Chart chart = new Chart(ChartType.BUBBLE);
        chart.setWidth("640px");
        chart.setHeight("350px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Champagne Consumption by Country");
        conf.getLegend().setEnabled(false); // Disable legend
        conf.getTooltip().setFormatter("this.point.name + ': ' + " +
            "Math.round(100*(this.point.z * this.point.z))/100.0 + " +
            "' M bottles'");
        
        // World map as background
        // TODO Check that this is correct
        String url = VaadinServlet.getCurrent().getServletContext()
            .getRealPath("/VAADIN/themes/book-examples/img/neocreo_Blue_World_Map_640x.png");
        conf.getChart().setPlotBackgroundImage(url);

        // Show more bubbly bubbles with spherical color gradient
        PlotOptionsBubble plotOptions = new PlotOptionsBubble();
        Marker marker = new Marker();
        GradientColor color = GradientColor.createRadial(0.4, 0.3, 0.7);
        color.addColorStop(0.0, new SolidColor(255, 255, 255, 0.5));
        color.addColorStop(1.0, new SolidColor(170, 70, 67, 0.5));
        marker.setFillColor(color);
        plotOptions.setMarker(marker);
        conf.setPlotOptions(plotOptions);
        
        // Source: CIVC - Les expeditions de vins de Champagne en 2011
        DataSeries series = new DataSeries("Countries");
        Object data[][] = {
                {"France",         181.6},
                {"United Kingdom",  34.53},
                {"United States",   19.37},
                {"Germany",         14.2},
                {"Belgium",          9.56},
                {"Japan",            7.9},
                {"Australia",        4.87},
                {"Canada",           1.59},
                {"Russia",           1.34},
                {"China",            1.32},
        };
        for (Object[] country: data) {
            String name = (String) country[0];
            double amount = (Double) country[1];
            Coordinate pos = getCountryCoordinates(name); 

            DataSeriesItem3d item = new DataSeriesItem3d();
            item.setX(pos.longitude * Math.cos(pos.latitude/2.0 * (Math.PI/160)));
            item.setY(pos.latitude * 1.2);
            item.setZ(Math.sqrt(amount));
            item.setName(name);
            series.add(item);
        }
        conf.addSeries(series);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setTitle(new Title(null));
        xaxis.setExtremes(-180, 180);
        xaxis.setLabels(new Labels(false));
        xaxis.setLineWidth(0);
        xaxis.setLineColor(new SolidColor(0,0,0,0.0)); // Invisible
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("");
        yaxis.setExtremes(-90, 90);
        yaxis.setTickPosition(TickPosition.OUTSIDE);
        yaxis.setTickLength(1);
        yaxis.setTickInterval(100);
        yaxis.setTickColor(new SolidColor(0,0,0,0.5)); // Invisible
        yaxis.setTickWidth(0);
        yaxis.setLabels(new Labels(false));
        yaxis.setLineWidth(0);
        yaxis.setLineColor(new SolidColor(0,0,0,0.0)); // Invisible
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.bubble
    }

    void columnchart (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.column
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("400px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Planets");
        conf.setSubTitle("The bigger they are the harder they pull");
        conf.getLegend().setEnabled(false); // Disable legend
        
        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setPointWidth(5);
        conf.setPlotOptions(plotOptions);
        
        // The data
        ListSeries series = new ListSeries("Diameter");
        series.setData(4900,  12100,  12800,
                       6800,  143000, 125000,
                       51100, 49500);
        conf.addSeries(series);

        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Mercury", "Venus",   "Earth",
                            "Mars",    "Jupiter", "Saturn",
                            "Uranus",  "Neptune");
        xaxis.setTitle("Planet");
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Diameter");
        yaxis.getLabels().setFormatter(
          "function() {return Math.floor(this.value/1000) + \'Mm\';}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.column
    }

    void piechart (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.pie
        Chart chart = new Chart(ChartType.PIE);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Planets");
        conf.setSubTitle("The bigger they are the harder they pull");
        conf.getLegend().setEnabled(false); // Disable legend
        conf.setCredits(null);
        
        // Set some plot options
        PlotOptionsPie options = new PlotOptionsPie();
        options.setInnerSize(0); // Non-0 results in a donut
        options.setSize("75%");  // Default
        options.setCenter("50%", "50%"); // Default
        conf.setPlotOptions(options);

        // The data
        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Mercury", 4900));
        series.add(new DataSeriesItem("Venus", 12100));
        
        // Slice one sector out
        DataSeriesItem earth = new DataSeriesItem("Earth", 12800);
        earth.setSliced(true);
        series.add(earth);
        
        series.add(new DataSeriesItem("Mars", 6800));
        series.add(new DataSeriesItem("Jupiter", 143000));
        series.add(new DataSeriesItem("Saturn", 125000));
        series.add(new DataSeriesItem("Uranus", 51100));
        series.add(new DataSeriesItem("Neptune", 49500));
        conf.addSeries(series);

        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.pie
    }

    void errorbarchart (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.errorbar
        // Create a chart of some primary type
        Chart chart = new Chart(ChartType.SCATTER);
        chart.setWidth("600px");
        chart.setHeight("400px");

        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average and Extreme Temperatures in Turku");
        conf.getLegend().setEnabled(false);

        // The primary data series
        ListSeries averages = new ListSeries(
            -6, -6.5, -4, 3, 9, 14, 17, 16, 11, 6, 2, -2.5);
        
        // Error bar data series with low and high values
        DataSeries errors = new DataSeries();
        errors.add(new DataSeriesItem(0,  -9, -3));
        errors.add(new DataSeriesItem(1, -10, -3));
        errors.add(new DataSeriesItem(2,  -8,  1));
        errors.add(new DataSeriesItem(3,  -2,  7));
        errors.add(new DataSeriesItem(4,   3, 14));
        errors.add(new DataSeriesItem(5,   8, 19));
        errors.add(new DataSeriesItem(6,  12, 22));
        errors.add(new DataSeriesItem(7,  11, 21));
        errors.add(new DataSeriesItem(8,   7, 15));
        errors.add(new DataSeriesItem(9,   2,  9));
        errors.add(new DataSeriesItem(10, -1,  4));
        errors.add(new DataSeriesItem(11, -5,  0));

        // Configure the stem and whiskers in error bars 
        PlotOptionsErrorBar barOptions = new PlotOptionsErrorBar();
        barOptions.setStemColor(SolidColor.GREY);
        barOptions.setStemWidth(2);
        barOptions.setStemDashStyle(DashStyle.DASH);
        barOptions.setWhiskerColor(SolidColor.BROWN);
        barOptions.setWhiskerLength(80); // 80% of category width
        barOptions.setWhiskerWidth(2); // Pixels
        errors.setPlotOptions(barOptions);
        
        // The errors should be drawn lower
        conf.addSeries(errors);
        conf.addSeries(averages);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Jan", "Feb", "Mar", "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        xaxis.setTitle("Month");
        xaxis.setTickInterval(1);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Temperature °C");
        conf.addyAxis(yaxis);

        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.errorbar
    }
    
    void boxplotchart (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.charttype.boxplot
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
        
        // Orienteering control point times for runners
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
    
    double[][] orienteeringdata() {
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
    // END-EXAMPLE: charts.charttype.boxplot

    
    void waterfallchart (Layout layout) {
        // BEGIN-EXAMPLE: charts.charttype.waterfall
        Chart chart = new Chart(ChartType.WATERFALL);
        chart.setWidth("550px");
        chart.setHeight("350px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Changes in Reindeer Population in 2011");
        conf.getLegend().setEnabled(false);
        conf.getChart().setAlignTicks(true);
        
        // Define the colors
        final Color balanceColor = SolidColor.BLACK;
        final Color positiveColor = SolidColor.BLUE;
        final Color negativeColor = SolidColor.RED;
        
        // Configure the colors 
        PlotOptionsWaterfall options = new PlotOptionsWaterfall();
        options.setUpColor(positiveColor);
        options.setNegativeColor(negativeColor);

        // Configure the labels
        Labels labels = new Labels(true);
        labels.setVerticalAlign(VerticalAlign.TOP);
        labels.setY(-20);
        labels.setFormatter("Math.floor(this.y/1000) + 'k'");
        Style style = new Style();
        style.setColor(SolidColor.BLACK);
        style.setFontWeight(FontWeight.BOLD);
        labels.setStyle(style);
        options.setDataLabels(labels);
        options.setPointPadding(0);
        conf.setPlotOptions(options);
        
        // The data
        DataSeries series = new DataSeries();
        
        // The beginning balance
        DataSeriesItem start = new DataSeriesItem("Start", 306503);
        start.setColor(balanceColor);
        series.add(start);

        // Deltas
        series.add(new DataSeriesItem("Predators", -3330));
        series.add(new DataSeriesItem("Slaughter", -103332));
        series.add(new DataSeriesItem("Reproduction", +104052));
        
        WaterFallSum end = new WaterFallSum("End");
        end.setColor(balanceColor);
        end.setIntermediate(false);
        series.add(end);

        conf.addSeries(series);

        // Configure X axis
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Start", "Predators", "Slaughter",
            "Reproduction", "End");
        conf.addxAxis(xaxis);

        // Configure Y axis
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Population (thousands)");
        yaxis.setMax(330000);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.waterfall
    }
    
    void charttypes (VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: charts.charttypes
        Object charttypes[][] = {
          {ChartType.BAR,        "Bar"},
          {ChartType.COLUMN,     "3D Column"},
          {ChartType.LINE,       "Line"},
          {ChartType.PIE,        "Pie"},
          {ChartType.SPLINE,     "Spline"},
          {ChartType.AREA,       "Area"},
          {ChartType.AREASPLINE, "Area Spline"},
          {ChartType.SCATTER,    "Scatter"},
        };
        
        GridLayout grid = new GridLayout(2,2);
        for (Object[] charttype: charttypes) {
            Chart chart = new Chart((ChartType) charttype[0]);
            chart.setWidth("400px");
            chart.setHeight("300px");
            
            // Modify the default configuration a bit
            Configuration conf = chart.getConfiguration();
            conf.setTitle((String) charttype[1]);
            conf.getLegend().setEnabled(false); // Disable legend
            conf.getCredits().setEnabled(false);
            
            // Have the column chart as 3D
            if (charttype[0] == ChartType.COLUMN) {
                Options3d options3d = new Options3d();
                options3d.setEnabled(true);
                options3d.setAlpha(15);
                options3d.setBeta(15);
                options3d.setDepth(50);
                options3d.setViewDistance(200);
                Frame frame = new Frame();
                frame.setBack(new FramePanel(SolidColor.GREY, 5));
                frame.setBottom(new FramePanel(SolidColor.GREY, 10));
                frame.setSide(new FramePanel(SolidColor.DARKGRAY, 5));
                options3d.setFrame(frame);
                conf.getChart().setOptions3d(options3d);           
            }
            
            // The data
            String planets[] = {"Mercury", "Venus", "Earth",
                "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
            int diameters[] = {4900,  12100,  12800,
                6800,  143000, 125000, 51100, 49500};
            DataSeries series = new DataSeries("Diameter");
            for (int i=0; i<planets.length; i++)
                series.add(new DataSeriesItem(planets[i], diameters[i]));
            conf.addSeries(series);
    
            // Set the category labels on the axis correspondingly
            XAxis xaxis = new XAxis();
            xaxis.setCategories(planets);
            xaxis.setTitle((String) null);
            conf.addxAxis(xaxis);
    
            // Set the Y axis title
            YAxis yaxis = new YAxis();
            yaxis.setTitle("Diameter");
            yaxis.getLabels().setFormatter(
                "function() { return Math.floor(this.value/1000) + \'Mm\';}");
            yaxis.getLabels().setStep(2);
            conf.addyAxis(yaxis);
            
            grid.addComponent(chart);
        }
        // END-EXAMPLE: charts.charttypes
        rootlayout.addComponent(grid);
    }
    
    void polarspiderwebchart (VerticalLayout layout) {
        HorizontalLayout hlayout = new HorizontalLayout();
        polarchart(hlayout);
        spiderwebchart(hlayout);
        layout.addComponent(hlayout);
    }

    void polarchart (Layout layout) {
        // BEGIN-EXAMPLE: charts.charttype.polar
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.getChart().setPolar(true);
        conf.setTitle("Extreme Temperature Range in Finland");
        conf.setSubTitle("http://ilmatieteenlaitos.fi/lampotilaennatyksia");
        conf.getLegend().setEnabled(false);
        conf.getCredits().setEnabled(false);

        // Create the range series
        // Source: http://ilmatieteenlaitos.fi/lampotilaennatyksia
        ListSeries series = new ListSeries("Temperature Extremes",
            10.9, 11.8, 17.5, 25.5, 31.0, 33.8,
            37.2, 33.8, 28.8, 19.4, 14.1, 10.8);
        conf.addSeries(series);

        // Configure the X axis
        XAxis xaxis = new XAxis();
        conf.addxAxis(xaxis);

        // Configure the Y axis
        YAxis yaxis = new YAxis();
        yaxis.setTickInterval(10);
        yaxis.getLabels().setStep(1);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.polar
    }

    void spiderwebchart (Layout layout) {
        // BEGIN-EXAMPLE: charts.charttype.spiderweb
        Chart chart = new Chart(ChartType.LINE);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.getChart().setPolar(true);
        conf.setTitle("Extreme Temperature Range in Finland");
        conf.setSubTitle("http://ilmatieteenlaitos.fi/lampotilaennatyksia");
        conf.getLegend().setEnabled(false);
        conf.getCredits().setEnabled(false);

        // Create the range series
        // Source: http://ilmatieteenlaitos.fi/lampotilaennatyksia
        ListSeries series = new ListSeries("Temperature Extremes",
            10.9, 11.8, 17.5, 25.5, 31.0, 33.8,
            37.2, 33.8, 28.8, 19.4, 14.1, 10.8);
        conf.addSeries(series);

        // Set the category labels on the X axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Jan", "Feb", "Mar",
            "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec");
        xaxis.setTickmarkPlacement(TickmarkPlacement.ON);
        xaxis.setLineWidth(0);
        conf.addxAxis(xaxis);

        // Configure the Y axis
        YAxis yaxis = new YAxis();
        yaxis.setGridLineInterpolation("polygon"); // Webby look
        yaxis.setMin(0);
        yaxis.setTickInterval(10);
        yaxis.getLabels().setStep(1);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.charttype.spiderweb
    }

    static int[][] reindeerData() {
        // Source: Poromaarien kehitys elo teuras 1959-2010. Paliskuntain yhdistys.
        // Columns: Year, preserve, slaughter
        int data[][] = {
            {1959,146486,34605},
            {1960,158014,43471},
            {1961,150454,37651},
            {1962,136781,40425},
            {1963,155660,42913},
            {1964,152913,40908},
            {1965,132790,38332},
            {1966,137787,43401},
            {1967,137704,50121},
            {1968,149720,58767},
            {1969,118007,32118},
            {1970,137506,36118},
            {1971,142525,42590},
            {1972,145286,50362},
            {1973,104627,35352},
            {1974,124490,30010},
            {1975,138674,33607},
            {1976,158960,41114},
            {1977,158414,42873},
            {1978,153430,51947},
            {1979,187289,58458},
            {1980,177683,58534},
            {1981,184368,60906},
            {1982,204427,71342},
            {1983,222908,83401},
            {1984,220673,95382},
            {1985,229673,103045},
            {1986,229136,133140},
            {1987,226990,133974},
            {1988,256180,142044},
            {1989,238069,124647},
            {1990,259612,169023},
            {1991,231637,181979},
            {1992,215364,129154},
            {1993,209317,129769},
            {1994,206850,124799},
            {1995,208961,120730},
            {1996,200605,88328},
            {1997,194395,89680},
            {1998,195476,96260},
            {1999,203424,92895},
            {2000,185731,87397},
            {2001,199708,97571},
            {2002,196727,106355},
            {2003,201058,106318},
            {2004,209365,116657},
            {2005,200196,124153},
            {2006,196567,117206},
            {2007,198015,103020},
            {2008,195501,102330},
            {2009,196492,104970},
            {2010,199841,106662},
            {2011,196084,107809}};
        
        // Organize it to two arrays
        int result[][] = new int[2][data.length];
        for (int i=0; i<data.length; i++) {
            result[0][i] = data[i][1];
            result[1][i] = data[i][2];
        }
        
        return result;
    }

    int findMaxItem(int data[][], int series) {
        int maxval = 0;
        int maxitem = 0;
        for (int i=0; i < data[0].length; i++) {
            int val = (series == -1)? data[0][i] + data[1][i]: data[series][i];
            if (val > maxval) {
                maxval = val;
                maxitem = i;
            }
        }
        return maxitem;
    }

    /**
     * Returns an array of monthly minimum-maximum temperature records in Finland.
     * 
     * Source: http://ilmatieteenlaitos.fi/lampotilaennatyksia
     * 
     * @return
     */
    double[][] extremeTemperatures() {
        double data[][] = {
                {-51.5,10.9}, // Kittilä, Pokka 28.1.1999 / Maarianhamina 6.1.1973
                {-49.0,11.8}, //
                {-44.3,17.5}, //
                {-36.0,25.5}, //
                {-24.6,31.0}, //
                {-7.0,33.8}, //
                {-5.0,37.2}, //
                {-10.8,33.8}, //
                {-18.7,28.8}, //
                {-31.8,19.4}, //
                {-42.0,14.1}, //
                {-47.0,10.8}, //
        };
        return data;
    }
    
    class Coordinate implements Serializable {
        private static final long serialVersionUID = 1747750690210931825L;

        public double longitude;
        public double latitude;
        
        public Coordinate(double lat, double lng) {
            this.longitude = -lng;
            this.latitude = lat;
        }
    }
    
    static Map<String, Coordinate> countryCoordinates;
    
    Coordinate getCountryCoordinates(String name) {
        if (countryCoordinates == null)
            countryCoordinates = createCountryCoordinates();
        return countryCoordinates.get(name);
    }
    
    public Map<String, Coordinate> createCountryCoordinates() {
        HashMap<String, Coordinate> coords = new HashMap<String, Coordinate>();
        coords.put("Afghanistan", new Coordinate(33.00, -65.00));
        coords.put("Akrotiri", new Coordinate(34.62, -32.97));
        coords.put("Albania", new Coordinate(41.00, -20.00));
        coords.put("Algeria", new Coordinate(28.00, -3.00));
        coords.put("American Samoa", new Coordinate(-14.33, 170.00));
        coords.put("Andorra", new Coordinate(42.50, -1.50));
        coords.put("Angola", new Coordinate(-12.50, -18.50));
        coords.put("Anguilla", new Coordinate(18.25, 63.17));
        coords.put("Antarctica", new Coordinate(-90.00, -0.00));
        coords.put("Antigua and Barbuda", new Coordinate(17.05, 61.80));
        coords.put("Arctic Ocean", new Coordinate(90.00, -0.00));
        coords.put("Argentina", new Coordinate(-34.00, 64.00));
        coords.put("Armenia", new Coordinate(40.00, -45.00));
        coords.put("Aruba", new Coordinate(12.50, 69.97));
        coords.put("Ashmore and Cartier Islands", new Coordinate(-12.23, -123.08));
        coords.put("Atlantic Ocean", new Coordinate(0.00, 25.00));
        coords.put("Australia", new Coordinate(-27.00, -133.00));
        coords.put("Austria", new Coordinate(47.33, -13.33));
        coords.put("Azerbaijan", new Coordinate(40.50, -47.50));
        coords.put("Bahamas, The", new Coordinate(24.25, 76.00));
        coords.put("Bahrain", new Coordinate(26.00, -50.55));
        coords.put("Baker Island", new Coordinate(0.22, 176.47));
        coords.put("Bangladesh", new Coordinate(24.00, -90.00));
        coords.put("Barbados", new Coordinate(13.17, 59.53));
        coords.put("Bassas da India", new Coordinate(-21.50, -39.83));
        coords.put("Belarus", new Coordinate(53.00, -28.00));
        coords.put("Belgium", new Coordinate(50.83, -4.00));
        coords.put("Belize", new Coordinate(17.25, 88.75));
        coords.put("Benin", new Coordinate(9.50, -2.25));
        coords.put("Bermuda", new Coordinate(32.33, 64.75));
        coords.put("Bhutan", new Coordinate(27.50, -90.50));
        coords.put("Bolivia", new Coordinate(-17.00, 65.00));
        coords.put("Bosnia and Herzegovina", new Coordinate(44.00, -18.00));
        coords.put("Botswana", new Coordinate(-22.00, -24.00));
        coords.put("Bouvet Island", new Coordinate(-54.43, -3.40));
        coords.put("Brazil", new Coordinate(-10.00, 55.00));
        coords.put("British Virgin Islands", new Coordinate(18.50, 64.50));
        coords.put("Brunei", new Coordinate(4.50, -114.67));
        coords.put("Bulgaria", new Coordinate(43.00, -25.00));
        coords.put("Burkina Faso", new Coordinate(13.00, 2.00));
        coords.put("Burma", new Coordinate(22.00, -98.00));
        coords.put("Burundi", new Coordinate(-3.50, -30.00));
        coords.put("Cambodia", new Coordinate(13.00, -105.00));
        coords.put("Cameroon", new Coordinate(6.00, -12.00));
        coords.put("Canada", new Coordinate(60.00, 95.00));
        coords.put("Cape Verde", new Coordinate(16.00, 24.00));
        coords.put("Cayman Islands", new Coordinate(19.50, 80.50));
        coords.put("Central African Republic", new Coordinate(7.00, -21.00));
        coords.put("Chad", new Coordinate(15.00, -19.00));
        coords.put("Chile", new Coordinate(-30.00, 71.00));
        coords.put("China", new Coordinate(35.00, -105.00));
        coords.put("Christmas Island", new Coordinate(-10.50, -105.67));
        coords.put("Clipperton Island", new Coordinate(10.28, 109.22));
        coords.put("Cocos (Keeling) Islands", new Coordinate(-12.50, -96.83));
        coords.put("Colombia", new Coordinate(4.00, 72.00));
        coords.put("Comoros", new Coordinate(-12.17, -44.25));
        coords.put("Congo, Democratic Republic of the", new Coordinate(0.00, -25.00));
        coords.put("Congo, Republic of the", new Coordinate(-1.00, -15.00));
        coords.put("Cook Islands", new Coordinate(-21.23, 159.77));
        coords.put("Coral Sea Islands", new Coordinate(-18.00, -152.00));
        coords.put("Costa Rica", new Coordinate(10.00, 84.00));
        coords.put("Croatia", new Coordinate(45.17, -15.50));
        coords.put("Cuba", new Coordinate(21.50, 80.00));
        coords.put("Cyprus", new Coordinate(35.00, -33.00));
        coords.put("Czech Republic", new Coordinate(49.75, -15.50));
        coords.put("Côte d'Ivoire", new Coordinate(8.00, 5.00));
        coords.put("Denmark", new Coordinate(56.00, -10.00));
        coords.put("Dhekelia", new Coordinate(34.98, -33.75));
        coords.put("Djibouti", new Coordinate(11.50, -43.00));
        coords.put("Dominica", new Coordinate(15.42, 61.33));
        coords.put("Dominican Republic", new Coordinate(19.00, 70.67));
        coords.put("East Timor", new Coordinate(-8.83, -125.92));
        coords.put("Ecuador", new Coordinate(-2.00, 77.50));
        coords.put("Egypt", new Coordinate(27.00, -30.00));
        coords.put("El Salvador", new Coordinate(13.83, 88.92));
        coords.put("Equatorial Guinea", new Coordinate(2.00, -10.00));
        coords.put("Eritrea", new Coordinate(15.00, -39.00));
        coords.put("Estonia", new Coordinate(59.00, -26.00));
        coords.put("Ethiopia", new Coordinate(8.00, -38.00));
        coords.put("Europa Island", new Coordinate(-22.33, -40.37));
        coords.put("Falkland Islands (Islas Malvinas)", new Coordinate(-51.75, 59.00));
        coords.put("Faroe Islands", new Coordinate(62.00, 7.00));
        coords.put("Fiji", new Coordinate(-18.00, -175.00));
        coords.put("Finland", new Coordinate(64.00, -26.00));
        coords.put("France", new Coordinate(46.00, -2.00));
        coords.put("French Guiana", new Coordinate(4.00, 53.00));
        coords.put("French Polynesia", new Coordinate(-15.00, 140.00));
        coords.put("Gabon", new Coordinate(-1.00, -11.75));
        coords.put("Gambia, The", new Coordinate(13.47, 16.57));
        coords.put("Gaza Strip", new Coordinate(31.42, -34.33));
        coords.put("Georgia", new Coordinate(42.00, -43.50));
        coords.put("Germany", new Coordinate(51.00, -9.00));
        coords.put("Ghana", new Coordinate(8.00, 2.00));
        coords.put("Gibraltar", new Coordinate(36.13, 5.35));
        coords.put("Glorioso Islands", new Coordinate(-11.50, -47.33));
        coords.put("Greece", new Coordinate(39.00, -22.00));
        coords.put("Greenland", new Coordinate(72.00, 40.00));
        coords.put("Grenada", new Coordinate(12.12, 61.67));
        coords.put("Guadeloupe", new Coordinate(16.25, 61.58));
        coords.put("Guam", new Coordinate(13.47, -144.78));
        coords.put("Guatemala", new Coordinate(15.50, 90.25));
        coords.put("Guernsey", new Coordinate(49.47, 2.58));
        coords.put("Guinea", new Coordinate(11.00, 10.00));
        coords.put("Guinea-Bissau", new Coordinate(12.00, 15.00));
        coords.put("Guyana", new Coordinate(5.00, 59.00));
        coords.put("Haiti", new Coordinate(19.00, 72.42));
        coords.put("Heard Island and McDonald Islands", new Coordinate(-53.10, -72.52));
        coords.put("Holy See (Vatican City)", new Coordinate(41.90, -12.45));
        coords.put("Honduras", new Coordinate(15.00, 86.50));
        coords.put("Hong Kong", new Coordinate(22.25, -114.17));
        coords.put("Howland Island", new Coordinate(0.80, 176.63));
        coords.put("Hungary", new Coordinate(47.00, -20.00));
        coords.put("Iceland", new Coordinate(65.00, 18.00));
        coords.put("India", new Coordinate(20.00, -77.00));
        coords.put("Indian Ocean", new Coordinate(-20.00, -80.00));
        coords.put("Indonesia", new Coordinate(-5.00, -120.00));
        coords.put("Iran", new Coordinate(32.00, -53.00));
        coords.put("Iraq", new Coordinate(33.00, -44.00));
        coords.put("Ireland", new Coordinate(53.00, 8.00));
        coords.put("Israel", new Coordinate(31.50, -34.75));
        coords.put("Italy", new Coordinate(42.83, -12.83));
        coords.put("Jamaica", new Coordinate(18.25, 77.50));
        coords.put("Jan Mayen", new Coordinate(71.00, 8.00));
        coords.put("Japan", new Coordinate(36.00, -138.00));
        coords.put("Jarvis Island", new Coordinate(-0.38, 160.02));
        coords.put("Jersey", new Coordinate(49.25, 2.17));
        coords.put("Johnston Atoll", new Coordinate(16.75, 169.52));
        coords.put("Jordan", new Coordinate(31.00, -36.00));
        coords.put("Juan de Nova Island", new Coordinate(-17.05, -42.75));
        coords.put("Kazakhstan", new Coordinate(48.00, -68.00));
        coords.put("Kenya", new Coordinate(1.00, -38.00));
        coords.put("Kingman Reef", new Coordinate(6.38, 162.42));
        coords.put("Kiribati", new Coordinate(1.42, -173.00));
        coords.put("Korea, North", new Coordinate(40.00, -127.00));
        coords.put("Korea, South", new Coordinate(37.00, -127.50));
        coords.put("Kuwait", new Coordinate(29.50, -45.75));
        coords.put("Kyrgyzstan", new Coordinate(41.00, -75.00));
        coords.put("Laos", new Coordinate(18.00, -105.00));
        coords.put("Latvia", new Coordinate(57.00, -25.00));
        coords.put("Lebanon", new Coordinate(33.83, -35.83));
        coords.put("Lesotho", new Coordinate(-29.50, -28.50));
        coords.put("Liberia", new Coordinate(6.50, 9.50));
        coords.put("Libya", new Coordinate(25.00, -17.00));
        coords.put("Liechtenstein", new Coordinate(47.27, -9.53));
        coords.put("Lithuania", new Coordinate(56.00, -24.00));
        coords.put("Luxembourg", new Coordinate(49.75, -6.17));
        coords.put("Macau", new Coordinate(22.17, -113.55));
        coords.put("Macedonia, Republic of", new Coordinate(41.83, -22.00));
        coords.put("Madagascar", new Coordinate(-20.00, -47.00));
        coords.put("Malawi", new Coordinate(-13.50, -34.00));
        coords.put("Malaysia", new Coordinate(2.50, -112.50));
        coords.put("Maldives", new Coordinate(3.25, -73.00));
        coords.put("Mali", new Coordinate(17.00, 4.00));
        coords.put("Malta", new Coordinate(35.83, -14.58));
        coords.put("Man, Isle of", new Coordinate(54.25, 4.50));
        coords.put("Marshall Islands", new Coordinate(9.00, -168.00));
        coords.put("Martinique", new Coordinate(14.67, 61.00));
        coords.put("Mauritania", new Coordinate(20.00, 12.00));
        coords.put("Mauritius", new Coordinate(-20.28, -57.55));
        coords.put("Mayotte", new Coordinate(-12.83, -45.17));
        coords.put("Mexico", new Coordinate(23.00, 102.00));
        coords.put("Micronesia, Federated States of", new Coordinate(6.92, -158.25));
        coords.put("Midway Islands", new Coordinate(28.20, 177.37));
        coords.put("Moldova", new Coordinate(47.00, -29.00));
        coords.put("Monaco", new Coordinate(43.73, -7.40));
        coords.put("Mongolia", new Coordinate(46.00, -105.00));
        coords.put("Montenegro", new Coordinate(42.50, -19.30));
        coords.put("Montserrat", new Coordinate(16.75, 62.20));
        coords.put("Morocco", new Coordinate(32.00, 5.00));
        coords.put("Mozambique", new Coordinate(-18.25, -35.00));
        coords.put("Namibia", new Coordinate(-22.00, -17.00));
        coords.put("Nauru", new Coordinate(-0.53, -166.92));
        coords.put("Navassa Island", new Coordinate(18.42, 75.03));
        coords.put("Nepal", new Coordinate(28.00, -84.00));
        coords.put("Netherlands", new Coordinate(52.50, -5.75));
        coords.put("Netherlands Antilles", new Coordinate(12.25, 68.75));
        coords.put("New Caledonia", new Coordinate(-21.50, -165.50));
        coords.put("New Zealand", new Coordinate(-41.00, -174.00));
        coords.put("Nicaragua", new Coordinate(13.00, 85.00));
        coords.put("Niger", new Coordinate(16.00, -8.00));
        coords.put("Nigeria", new Coordinate(10.00, -8.00));
        coords.put("Niue", new Coordinate(-19.03, 169.87));
        coords.put("Norfolk Island", new Coordinate(-29.03, -167.95));
        coords.put("Northern Mariana Islands", new Coordinate(15.20, -145.75));
        coords.put("Norway", new Coordinate(62.00, -10.00));
        coords.put("Oman", new Coordinate(21.00, -57.00));
        coords.put("Pacific Ocean", new Coordinate(0.00, 160.00));
        coords.put("Pakistan", new Coordinate(30.00, -70.00));
        coords.put("Palau", new Coordinate(7.50, -134.50));
        coords.put("Palmyra Atoll", new Coordinate(5.88, 162.08));
        coords.put("Panama", new Coordinate(9.00, 80.00));
        coords.put("Papua New Guinea", new Coordinate(-6.00, -147.00));
        coords.put("Paracel Islands", new Coordinate(16.50, -112.00));
        coords.put("Paraguay", new Coordinate(-23.00, 58.00));
        coords.put("Peru", new Coordinate(-10.00, 76.00));
        coords.put("Philippines", new Coordinate(13.00, -122.00));
        coords.put("Pitcairn Islands", new Coordinate(-25.07, 130.10));
        coords.put("Poland", new Coordinate(52.00, -20.00));
        coords.put("Portugal", new Coordinate(39.50, 8.00));
        coords.put("Puerto Rico", new Coordinate(18.25, 66.50));
        coords.put("Qatar", new Coordinate(25.50, -51.25));
        coords.put("Romania", new Coordinate(46.00, -25.00));
        coords.put("Russia", new Coordinate(60.00, -100.00));
        coords.put("Rwanda", new Coordinate(-2.00, -30.00));
        coords.put("Réunion", new Coordinate(-21.10, -55.60));
        coords.put("Saint Barthelemy", new Coordinate(18.50, 63.42));
        coords.put("Saint Helena", new Coordinate(-15.93, 5.70));
        coords.put("Saint Kitts and Nevis", new Coordinate(17.33, 62.75));
        coords.put("Saint Lucia", new Coordinate(13.88, 60.97));
        coords.put("Saint Martin", new Coordinate(18.08, 63.95));
        coords.put("Saint Pierre and Miquelon", new Coordinate(46.83, 56.33));
        coords.put("Saint Vincent and the Grenadines", new Coordinate(13.25, 61.20));
        coords.put("Samoa", new Coordinate(-13.58, 172.33));
        coords.put("San Marino", new Coordinate(43.77, -12.42));
        coords.put("Saudi Arabia", new Coordinate(25.00, -45.00));
        coords.put("Senegal", new Coordinate(14.00, 14.00));
        coords.put("Serbia and Montenegro", new Coordinate(44.00, -21.00));
        coords.put("Seychelles", new Coordinate(-4.58, -55.67));
        coords.put("Sierra Leone", new Coordinate(8.50, 11.50));
        coords.put("Singapore", new Coordinate(1.37, -103.80));
        coords.put("Slovakia", new Coordinate(48.67, -19.50));
        coords.put("Slovenia", new Coordinate(46.12, -14.82));
        coords.put("Solomon Islands", new Coordinate(-8.00, -159.00));
        coords.put("Somalia", new Coordinate(10.00, -49.00));
        coords.put("South Africa", new Coordinate(-29.00, -24.00));
        coords.put("South Georgia and the South Sandwich Islands", new Coordinate(-54.50, 37.00));
        coords.put("Spain", new Coordinate(40.00, 4.00));
        coords.put("Spratly Islands", new Coordinate(8.63, -111.92));
        coords.put("Sri Lanka", new Coordinate(7.00, -81.00));
        coords.put("Sudan", new Coordinate(15.00, -30.00));
        coords.put("Suriname", new Coordinate(4.00, 56.00));
        coords.put("Svalbard", new Coordinate(78.00, -20.00));
        coords.put("Swaziland", new Coordinate(-26.50, -31.50));
        coords.put("Sweden", new Coordinate(62.00, -15.00));
        coords.put("Switzerland", new Coordinate(47.00, -8.00));
        coords.put("Syria", new Coordinate(35.00, -38.00));
        coords.put("São Tomé and Príncipe", new Coordinate(1.00, -7.00));
        coords.put("Taiwan", new Coordinate(23.50, -121.00));
        coords.put("Tajikistan", new Coordinate(39.00, -71.00));
        coords.put("Tanzania", new Coordinate(-6.00, -35.00));
        coords.put("Thailand", new Coordinate(15.00, -100.00));
        coords.put("Togo", new Coordinate(8.00, -1.17));
        coords.put("Tokelau", new Coordinate(-9.00, 172.00));
        coords.put("Tonga", new Coordinate(-20.00, 175.00));
        coords.put("Trinidad and Tobago", new Coordinate(11.00, 61.00));
        coords.put("Tromelin Island", new Coordinate(-15.87, -54.42));
        coords.put("Tunisia", new Coordinate(34.00, -9.00));
        coords.put("Turkey", new Coordinate(39.00, -35.00));
        coords.put("Turkmenistan", new Coordinate(40.00, -60.00));
        coords.put("Turks and Caicos Islands", new Coordinate(21.75, 71.58));
        coords.put("Tuvalu", new Coordinate(-8.00, -178.00));
        coords.put("Uganda", new Coordinate(1.00, -32.00));
        coords.put("Ukraine", new Coordinate(49.00, -32.00));
        coords.put("United Arab Emirates", new Coordinate(24.00, -54.00));
        coords.put("United Kingdom", new Coordinate(54.00, 2.00));
        coords.put("United States", new Coordinate(38.00, 97.00));
        coords.put("Uruguay", new Coordinate(-33.00, 56.00));
        coords.put("Uzbekistan", new Coordinate(41.00, -64.00));
        coords.put("Vanuatu", new Coordinate(-16.00, -167.00));
        coords.put("Venezuela", new Coordinate(8.00, 66.00));
        coords.put("Vietnam", new Coordinate(16.00, -106.00));
        coords.put("Virgin Islands", new Coordinate(18.33, 64.83));
        coords.put("Wake Island", new Coordinate(19.28, -166.65));
        coords.put("Wallis and Futuna", new Coordinate(-13.30, 176.20));
        coords.put("West Bank", new Coordinate(32.00, -35.25));
        coords.put("Western Sahara", new Coordinate(24.50, 13.00));
        coords.put("Yemen", new Coordinate(15.00, -48.00));
        coords.put("Zambia", new Coordinate(-15.00, -30.00));
        coords.put("Zimbabwe", new Coordinate(-20.00, -30.00));
        return coords;
    }
}
