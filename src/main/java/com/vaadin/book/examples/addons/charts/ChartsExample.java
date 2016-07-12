package com.vaadin.book.examples.addons.charts;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ContainerDataSeries;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Exporting;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.MarkerSymbolEnum;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.Stacking;
import com.vaadin.addon.charts.model.TickPosition;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.FontWeight;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.lib.BookExampleBundle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ChartsExample extends CustomComponent implements BookExampleBundle {
    private static final long serialVersionUID = 97529549237L;
    
    String context;

    public void init(String context) {
        this.context = context;
        setCompositionRoot(new Label("Uninitialized"));
    }
    
    @Override
    public void attach() {
        VerticalLayout layout = new VerticalLayout();
        if ("basic".equals(context))
            basic(layout);
        else if ("basicmixed".equals(context))
            basicmixed(layout);
        else if ("mixedmeter".equals(context))
            mixedmeter(layout);
        else if ("grouped".equals(context))
            grouped(layout);
        else if ("themes".equals(context))
            themes(layout);
        else if ("containerseries".equals(context)) {
            HorizontalLayout hlayout = new HorizontalLayout();
            containerseries(hlayout);
            layout.addComponent(hlayout);
        }
        else if ("exporting".equals(context))
            exporting(layout);
        else
            setCompositionRoot(new Label("Invalid Context"));
        setCompositionRoot(layout);
    }
    
    void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.basic
        Chart chart = new Chart(ChartType.BAR);
        chart.setWidth("400px");
        chart.setHeight("350px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Planets");
        conf.setSubTitle("The bigger they are the harder they pull");
        conf.getLegend().setEnabled(false); // Disable legend

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
          "function() {return Math.floor(this.value/1000) + \' Mm\';}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.basic
    }

    void basicmixed (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.mixed.basicmixed
        Chart chart = new Chart();
        chart.setWidth("400px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Mixed Chart");
        conf.getLegend().setEnabled(false); // Disable legend
        
        // A data series as column graph
        DataSeries series1 = new DataSeries();
        PlotOptionsColumn options1 = new PlotOptionsColumn();
        options1.setColor(SolidColor.BLUE);
        series1.setPlotOptions(options1);
        series1.setData(4900,  12100,  12800,
            6800,  143000, 125000,
            51100, 49500);
        conf.addSeries(series1);

        // A data series as line graph
        ListSeries series2 = new ListSeries("Diameter");
        PlotOptionsLine options2 = new PlotOptionsLine();
        options2.setColor(SolidColor.RED);
        series2.setPlotOptions(options2);
        series2.setData(4900,  12100,  12800,
            6800,  143000, 125000,
            51100, 49500);
        conf.addSeries(series2);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Mercury", "Venus", "Earth",
                            "Mars", "Jupiter", "Saturn",
                            "Uranus", "Neptune");
        xaxis.setTitle((String) null);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Diameter");
        yaxis.getLabels().setFormatter(
            "function() { return Math.floor(this.value/1000) + \'Mm\';}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.mixed.basicmixed
    }

    void mixedmeter(VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.mixed.mixedmeter
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSpacing(true);
        layout.addComponent(hlayout);
        
        final double initialValue      = 42.0;
        
        // This is a bit hack to show the column and the extra
        // indicator in the same chart.
        // How to set these values is a deep mystery.
        final double columnPosition    = 30;
        final double indicatorPosition = 70;
        
        // The chart
        Chart chart = new Chart();
        chart.setWidth("100px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("");
        conf.getLegend().setEnabled(false); // Disable legend
        conf.getChart().setBackgroundColor(new SolidColor("#404040"));
        conf.getChart().setMargin(10, 0, 30, 30);

        // Just to fill the top of the column as white
        final DataSeries fillseries = new DataSeries();
        PlotOptionsColumn filloptions = new PlotOptionsColumn();
        filloptions.setColor(new SolidColor("#e0e0e0"));
        filloptions.setPointWidth(30); // pixels wide column
        filloptions.setBorderWidth(0);
        filloptions.setStacking(Stacking.NORMAL);
        fillseries.setPlotOptions(filloptions);
        fillseries.setData(Arrays.asList(new DataSeriesItem(columnPosition, 100)));
        conf.addSeries(fillseries);
        
        // A data series as column graph
        final DataSeries series1 = new DataSeries();
        PlotOptionsColumn options1 = new PlotOptionsColumn();
        GradientColor bargradient = GradientColor.createLinear(0, 0, 1, 0);
        bargradient.addColorStop(0, SolidColor.WHITE);
        bargradient.addColorStop(1, SolidColor.GRAY);
        options1.setColor(bargradient);
        options1.setPointWidth(30); // pixels wide column
        options1.setBorderWidth(0);
        options1.setStacking(Stacking.NORMAL);
        series1.setPlotOptions(options1);
        final DataSeriesItem valueItem = new DataSeriesItem(columnPosition, initialValue);
        series1.setData(Arrays.asList(valueItem));
        conf.addSeries(series1);

        // Indicator
        final DataSeries indicatorSeries = new DataSeries();
        PlotOptionsScatter options2 = new PlotOptionsScatter();
        options2.setColor(SolidColor.RED);
        Marker marker = new Marker();
        marker.setSymbol(MarkerSymbolEnum.DIAMOND);
        GradientColor markergradient = GradientColor.createLinear(0, 0, 1, 0);
        markergradient.addColorStop(0, SolidColor.GRAY);
        markergradient.addColorStop(1, new SolidColor("#404040"));
        marker.setFillColor(markergradient);
        marker.setRadius(12);
        options2.setMarker(marker);
        indicatorSeries.setPlotOptions(options2);
        final DataSeriesItem indicatorItem = new DataSeriesItem(indicatorPosition, initialValue);
        indicatorSeries.setData(Arrays.asList(indicatorItem));
        conf.addSeries(indicatorSeries);

        // Invisible X axis
        XAxis xaxis = new XAxis();
        xaxis.setMin(0);
        xaxis.setMax(100);
        xaxis.setTitle((String) null);
        // xaxis.setLineColor(SolidColor.WHITE);
        xaxis.getLabels().setEnabled(false);
        xaxis.setStartOnTick(true); // no effect?
        xaxis.setEndOnTick(true);   // no effect?
        xaxis.setMinPadding(50);    // no effect?
        xaxis.setMaxPadding(30);    // no effect?
        xaxis.setLineWidth(0);
        xaxis.setTickWidth(0);
        conf.addxAxis(xaxis);

        // The Y axis
        YAxis yaxis = new YAxis();
        yaxis.setTitle("");
        yaxis.getLabels().setColor(SolidColor.WHITE);
        yaxis.getLabels().getStyle().setFontWeight(FontWeight.BOLD);
        yaxis.setMin(0);
        yaxis.setMax(100);
        yaxis.getLabels().setStep(1);
        yaxis.setTickInterval(10);
        yaxis.setTickPosition(TickPosition.OUTSIDE);
        yaxis.setTickLength(5);
        yaxis.setTickWidth(2);
        yaxis.setTickColor(SolidColor.WHITE);
        yaxis.setGridLineWidth(0);
        yaxis.setMinorTickInterval(2);
        yaxis.setMinorTickPosition(TickPosition.OUTSIDE);
        yaxis.setMinorTickLength(2);
        yaxis.setMinorTickWidth(1);
        yaxis.setMinorGridLineWidth(0);
        yaxis.setMinorTickColor(SolidColor.WHITE);
        yaxis.setLineColor(SolidColor.WHITE);
        conf.addyAxis(yaxis);

        CssLayout chartFrame = new CssLayout();
        chartFrame.addStyleName("meterframe");
        chartFrame.addComponent(chart);
        hlayout.addComponent(chartFrame);
        
        // The control
        Slider slider = new Slider(0, 100);
        slider.setOrientation(SliderOrientation.VERTICAL);
        slider.setHeight("100%");
        slider.setValue(initialValue);
        slider.addValueChangeListener(new ValueChangeListener() {
            private static final long serialVersionUID = -6218060514949845912L;

            @Override
            public void valueChange(ValueChangeEvent event) {
                double value = (Double) event.getProperty().getValue();
                valueItem.setY(value);
                indicatorItem.setY(value);
                series1.update(valueItem);
                indicatorSeries.update(indicatorItem);
            }
        });
        slider.setImmediate(true);
        hlayout.addComponent(slider);
        // END-EXAMPLE: charts.mixed.mixedmeter
    }

    void themes (VerticalLayout rootlayout) {
        // BEGIN-EXAMPLE: charts.themes
        // ChartTheme.get().setTheme(new SkiesTheme());

        Chart chart = new Chart(ChartType.AREA);
        chart.setWidth("400px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Theme...");
        conf.getLegend().setEnabled(false); // Disable legend

        // The data
        ListSeries series = new ListSeries("Diameter");
        series.setData(4900,  12100,  12800,
            6800,  143000, 125000,
            51100, 49500);
        conf.addSeries(series);

        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        xaxis.setCategories("Mercury", "Venus", "Earth",
                            "Mars", "Jupiter", "Saturn",
                            "Uranus", "Neptune");
        xaxis.setTitle((String) null);
        conf.addxAxis(xaxis);

        // Set the Y axis title
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Diameter");
        yaxis.getLabels().setFormatter(
            "function() { return Math.floor(this.value/1000) + \'Mm\';}");
        yaxis.getLabels().setStep(2);
        conf.addyAxis(yaxis);
        
        rootlayout.addComponent(chart);
        // END-EXAMPLE: charts.themes
    }

    void grouped (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.grouped
        Object charttypes[][] = {
                {ChartType.COLUMN, "Column",         false},
                {ChartType.COLUMN, "Stacked Column", true},
                {ChartType.BAR,    "Bar"   ,         false},
                {ChartType.LINE,   "Line"  ,         false},
        };

        GridLayout grid = new GridLayout(2,2);
        for (Object[] charttype: charttypes) {
            Chart chart = new Chart((ChartType) charttype[0]);
            chart.setWidth("400px");
            chart.setHeight("350px");
            
            // Modify the default configuration a bit
            Configuration conf = chart.getConfiguration();
            conf.setTitle((String) charttype[1]);
            conf.setSubTitle("Reindeer Kills by Predators by Counties");
            conf.getCredits().setEnabled(false);
            
            // Use stacked columns
            PlotOptionsColumn plotOptions = new PlotOptionsColumn();
            if ((Boolean) charttype[2])
                plotOptions.setStacking(Stacking.NORMAL);
            Labels labels = new Labels();
            labels.setEnabled(true);
            conf.setPlotOptions(plotOptions);
    
            // The data
            // Source: V. Maijala, H. Norberg, J. Kumpula, M. Nieminen
            // Calf production and mortality in the Finnish
            // reindeer herding area. 2002.
            String predators[] = {"Bear", "Wolf", "Wolverine", "Lynx"};
            int kills[][] = {        // Location:
                    {8,   0,  7, 0}, // Muddusjärvi
                    {30,  1, 30, 2}, // Ivalo
                    {37,  0, 22, 2}, // Oraniemi
                    {13, 23,  4, 1}, // Salla
                    {3,  10,  9, 0}, // Alakitka
            };    
            
            // Create a data series for each numeric column in the table
            for (int predator = 0; predator < 4; predator++) {
                ListSeries series = new ListSeries();
                series.setName(predators[predator]);
                
                // The rows of the table
                for (int location = 0; location < kills.length; location++)
                    series.addData(kills[location][predator]);
                conf.addSeries(series);
            }
    
            // Set the category labels on the axis accordingly
            XAxis xaxis = new XAxis();
            xaxis.setCategories("Muddusjärvi", "Ivalo", "Oraniemi",
                                "Salla", "Alakitka");
            xaxis.setTitle((String) null);
            conf.addxAxis(xaxis);
    
            // Set the Y axis title
            YAxis yaxis = new YAxis();
            yaxis.setTitle("Kills");
            conf.addyAxis(yaxis);

            grid.addComponent(chart);
        }
        layout.addComponent(grid);
        // END-EXAMPLE: charts.grouped
    }
    
    // BEGIN-EXAMPLE: charts.data.containerseries
    // The data model
    public class Planet implements Serializable {
        private static final long serialVersionUID = -4700817425257396005L;

        String name;
        double diameter;
        
        public Planet(String name, double diameter) {
            this.name = name;
            this.diameter = diameter;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setDiameter(double diameter) {
            this.diameter = diameter;
        }
        public double getDiameter() {
            return diameter;
        }
    }

    void containerseries (Layout layout) {
        // The data
        BeanItemContainer<Planet> container =
                new BeanItemContainer<Planet>(Planet.class);
        container.addBean(new Planet("Mercury", 4900));
        container.addBean(new Planet("Venus", 12100));
        container.addBean(new Planet("Earth", 12800));
        container.addBean(new Planet("Mars", 6800));
        container.addBean(new Planet("Jupiter", 143000));
        container.addBean(new Planet("Saturn", 125000));
        container.addBean(new Planet("Uranus", 51100));
        container.addBean(new Planet("Neptune", 49500));
        
        // Display it in a table
        Table table = new Table("Planets", container);
        table.setPageLength(container.size());
        table.setVisibleColumns("name","diameter");
        layout.addComponent(table);
        
        // Display it in a chart
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Planets");
        conf.setSubTitle("The bigger they are the harder they pull");
        conf.getLegend().setEnabled(false); // Disable legend
        conf.getCredits().setEnabled(false);

        // Wrap the container in a data series
        ContainerDataSeries series =
                new ContainerDataSeries(container);
        
        // Set up the name and Y properties
        series.setNamePropertyId("name");
        series.setYPropertyId("diameter");
        conf.addSeries(series);
        
        // Set the category labels on the axis correspondingly
        XAxis xaxis = new XAxis();
        String names[] = new String[container.size()];
        List<Planet> planets = container.getItemIds();
        for (int i=0; i<planets.size(); i++)
            names[i] = planets.get(i).getName();
        xaxis.setCategories(names);
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
        // END-EXAMPLE: charts.data.containerseries
    }

    void exporting(Layout layout) {
        Chart chart = new Chart(ChartType.BAR);
        chart.setWidth("400px");
        chart.setHeight("300px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Planets");
        conf.setSubTitle("The bigger they are the harder they pull");
        conf.getLegend().setEnabled(false); // Disable legend

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

        // BEGIN-EXAMPLE: charts.exporting
        // Create the export configuration
        Exporting exporting = new Exporting(true);
        
        // Customize the file name of the download file
        exporting.setFilename("mychartfile.pdf");

        // Enable export of raster images 
        exporting.setEnableImages(true);
        
        // Exporting is by default done on highcharts public servers, but you
        // can also use your own server
        // exporting.setUrl("http://my.own.server.com");
        
        // Disable the print button
        /*
        ExportButton printButton = new ExportButton();
        printButton.setEnabled(false);
        exporting.setPrintButton(printButton);*/
        
        // Use the exporting configuration in the chart
        chart.getConfiguration().setExporting(exporting);
        // END-EXAMPLE: charts.exporting

        // Simpler boolean API can also be used to just toggle the service
        // on/off
        // chart.getConfiguration().setExporting(true);
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
}
