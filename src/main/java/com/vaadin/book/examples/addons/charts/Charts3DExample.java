package com.vaadin.book.examples.addons.charts;

import java.io.PrintStream;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.DataSeriesItem3d;
import com.vaadin.addon.charts.model.Frame;
import com.vaadin.addon.charts.model.FramePanel;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.Options3d;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.PlotOptionsScatter;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class Charts3DExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = 6077554005967619905L;

    public void basic (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.3d.basic
        Chart chart = new Chart(ChartType.PIE);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Planets – In 3D!");
        conf.setSubTitle("The bigger they are the harder they pull");
        conf.getLegend().setEnabled(false); // Disable legend
        conf.setCredits(null);

        // Pie in 3D!
        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(60);
        conf.getChart().setOptions3d(options3d);           
        
        // Set some plot options
        PlotOptionsPie options = new PlotOptionsPie();
        options.setInnerSize(0); // Non-0 results in a donut
        options.setSize("75%");  // Default
        options.setCenter("50%", "50%"); // Default
        options.setDepth(45); // Pie in 3D! 
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
        // END-EXAMPLE: charts.3d.basic
    }

    public void scatter (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.3d.scatter
        Chart chart = new Chart(ChartType.SCATTER);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Scatter – in 3D!");
        conf.getLegend().setEnabled(false);  // Disable legend
        conf.getCredits().setEnabled(false); // Disable credits
        
        // In 3D!
        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(10);
        options3d.setBeta(30);
        options3d.setDepth(135); // Default is 100
        options3d.setViewDistance(100); // Default
        Frame frame = new Frame();
        frame.setBack(new FramePanel(SolidColor.BEIGE, 1));
        options3d.setFrame(frame);
        conf.getChart().setOptions3d(options3d);
        
        conf.getChart().setMarginTop(100); // Need more space
        conf.getChart().setMarginRight(50);
        
        // Set some plot options
        PlotOptionsScatter options = new PlotOptionsScatter();
        conf.setPlotOptions(options);

        // The data
        double[][] points = {{0.0, 0.0, 0.0}, // x, y, z
                             {1.0, 0.0, 0.0},
                             {0.0, 1.0, 0.0},
                             {0.0, 0.0, 1.0},
                             {-1.0, 0.0, 0.0},
                             {0.0, -1.0, 0.0},
                             {0.0, 0.0, -1.0}};
        DataSeries series = new DataSeries();
        for (int i=0; i<points.length; i++) {
            double x = points[i][0];
            double y = points[i][1];
            double z = points[i][2];

            // Scale the depth coordinate, as the depth axis is not
            // scaled automatically
            DataSeriesItem3d item = new DataSeriesItem3d(x, y,
                z * options3d.getDepth().doubleValue());

            Marker marker = new Marker(true);
            marker.setFillColor(new SolidColor((int) (128 + 127*x), (int) (128+127*y), (int) (128+127*z)));
            item.setMarker(marker);
            
            series.add(item);
        }
        conf.addSeries(series);
        
        XAxis xAxis = new XAxis();
        xAxis.setTitle("X");
        xAxis.setExtremes(-1.0, 1.0);
        xAxis.setTickInterval(0.5);
        xAxis.setGridLineWidth(1);
        xAxis.setGridLineColor(SolidColor.LIGHTGRAY);
        conf.addxAxis(xAxis);
        
        YAxis yAxis = new YAxis();
        yAxis.setTitle("Y");
        yAxis.setExtremes(-1.0, 1.0);
        yAxis.setTickInterval(0.5);
        yAxis.setGridLineWidth(1);
        yAxis.setGridLineColor(SolidColor.LIGHTGRAY);
        conf.addyAxis(yAxis);
        
        layout.addComponent(chart);
        // END-EXAMPLE: charts.3d.scatter
    }

    // UNUSED
    private double gaussrnd (double stdv) {
        if (stdv>0.0) {
            double r = Math.random()-0.5;
            return stdv * Math.log((r+0.5)/(0.5-r));
        } else
            return 0.0;
    }

    // UNUSED
    public static double[][] matmul(double[][] a, double[][] b) {
          double[][] result = new double[a.length][b[0].length];

          for(int i = 0; i < result.length; i++)
              for(int j = 0; j < result[i].length; j++)
                  result[i][j] = 0.0;
          
          for(int i = 0; i < a.length; i++)
              for(int j = 0; j < b[0].length; j++)
                  for(int k = 0; k < a[0].length; k++)
                      result[i][j] += (a[i][k]*b[k][j]);

          return result;
    }
    
    // UNUSED
    private static double[][] Rx(double theta) {
        double[][] result = {{1.0, 0.0, 0.0},
                             {0.0, Math.cos(theta), -Math.sin(theta)},
                             {0.0, Math.sin(theta), Math.cos(theta)}};
        return result;
    }
    
    // UNUSED
    private static double[][] Ry(double theta) {
        double[][] result = {{Math.cos(theta), 0.0, Math.sin(theta)},
                             {0.0, 1.0, 0.0},
                             {-Math.sin(theta), 0.0, Math.cos(theta)}};
        return result;
    }
    
    // UNUSED
    private static double[][] Rz(double theta) {
        double[][] result = {{Math.cos(theta), -Math.sin(theta), 0.0},
                             {Math.sin(theta), Math.cos(theta), 0.0},
                             {0.0, 0.0, 1.0}};
        return result;
    }
    
    // UNUSED
    public static double[] rotate(double[] vector3d, double alpha, double beta, double gamma) {
        // Determine the rotation matrix
        double[][] R = matmul(matmul(Rz(gamma), Ry(beta)), Rx(alpha));
        System.out.println("R = ");
        printMatrix(R, System.out);

        // Transpose the input vector
        double[][] columnvec3d = {{vector3d[0]}, {vector3d[1]}, {vector3d[2]}};
        System.out.print("colvec3d = ");
        printMatrix(columnvec3d, System.out);

        // Apply the rotation to the input vector
        double[][] resultcolumn = matmul(R, columnvec3d);
        System.out.print("resultcol = ");
        printMatrix(resultcolumn, System.out);

        double[] result = {resultcolumn[1][0], resultcolumn[1][0], resultcolumn[2][0]};
        return result;
    }
    
    // UNUSED
    public static void printMatrix(double[][] matrix, PrintStream stream) {
        stream.print("[");
        for (int i=0; i<matrix.length; i++) {
            stream.print("[");
            for (int j=0; j<matrix[i].length; j++) {
                stream.print(matrix[i][j]);
                if (j < matrix[i].length - 1)
                    stream.print(", ");
            }
            stream.print("]");
            if (i < matrix.length - 1)
                stream.println(",");
        }            
        stream.println("]");
        stream.flush();
    }
    
    public double distanceTo(double[] point, double alpha,
                             double beta, double viewDist) {
        final double theta = alpha * Math.PI / 180;
        final double phi   = beta * Math.PI / 180;
        double x = viewDist * Math.sin(theta) * Math.cos(phi);
        double y = viewDist * Math.sin(theta) * Math.sin(phi);
        double z = - viewDist * Math.cos(theta);
        return Math.sqrt(Math.pow(x - point[0], 2) +
                         Math.pow(y - point[1], 2) +
                         Math.pow(z - point[2], 2));
    }

    public void scatterfade (VerticalLayout layout) {
        // BEGIN-EXAMPLE: charts.3d.scatterfade
        Chart chart = new Chart(ChartType.SCATTER);
        chart.setWidth("400px");
        chart.setHeight("400px");
        
        // Modify the default configuration a bit
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Fading in Distance");
        conf.getLegend().setEnabled(false); // Disable legend
        conf.setCredits(new Credits(false));
        
        // Rotation parameters are reused for our own rotation
        final double alpha = 10;
        final double beta  = 30;

        // In 3D!
        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(alpha);
        options3d.setBeta(beta);
        options3d.setDepth(135);
        conf.getChart().setOptions3d(options3d);           
        
        // Need a bit more space
        conf.getChart().setMarginTop(100);
        conf.getChart().setMarginRight(50);

        // Set some plot options
        PlotOptionsScatter options = new PlotOptionsScatter();
        conf.setPlotOptions(options);

        // The data
        DataSeries series = new DataSeries();
        double[][] points = {{0.0, 0.0, 0.0},
                             {1.0, 0.0, 0.0},
                             {0.0, 1.0, 0.0},
                             {0.0, 0.0, 1.0},
                             {-1.0, 0.0, 0.0},
                             {0.0, -1.0, 0.0},
                             {0.0, 0.0, -1.0}};
        for (int i=0; i<points.length; i++) {
            double x = points[i][0];
            double y = points[i][1];
            double z = points[i][2];
            
            DataSeriesItem3d item = new DataSeriesItem3d(x, y,
                z * options3d.getDepth().doubleValue());
            
            double distance = distanceTo(new double[]{x,y,z},
                                         alpha, beta, 2); 
            
            int gr = (int) (distance*75); // Grayness
            Marker marker = new Marker(true);
            marker.setRadius(1 + 10 / distance);
            marker.setFillColor(new SolidColor(gr, gr, gr));
            item.setMarker(marker);
            
            series.add(item);
        }
        conf.addSeries(series);

        XAxis xAxis = new XAxis();
        xAxis.setTitle("X");
        xAxis.setExtremes(-1.0, 1.0);
        xAxis.setTickInterval(0.5);
        xAxis.setGridLineWidth(1);
        xAxis.setGridLineColor(SolidColor.LIGHTGRAY);
        conf.addxAxis(xAxis);
        
        YAxis yAxis = new YAxis();
        yAxis.setTitle("Y");
        yAxis.setExtremes(-1.0, 1.0);
        yAxis.setTickInterval(0.5);
        yAxis.setGridLineWidth(1);
        yAxis.setGridLineColor(SolidColor.LIGHTGRAY);
        conf.addyAxis(yAxis);

        layout.addComponent(chart);
        // END-EXAMPLE: charts.3d.scatterfade
    }
}
