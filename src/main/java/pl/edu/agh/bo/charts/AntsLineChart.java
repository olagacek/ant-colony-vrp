package pl.edu.agh.bo.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.IntegerDocument;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;

public class AntsLineChart extends ApplicationFrame {

    private Map<Integer, Double> entriesMap;

    public AntsLineChart( String applicationTitle , String chartTitle,  Map<Integer, Double> entriesMap) {
        super(applicationTitle);
        this.entriesMap = entriesMap;
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Iteracje","Funkcja celu",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        XYPlot plot = lineChart.getXYPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(100));

//        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
//        yAxis.setTickUnit(new NumberTickUnit(1));

//        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
//        yAxis.setTickUnit(new NumberTickUnit(10));

        ChartPanel chartPanel = new ChartPanel( lineChart );
        //chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );

    }

    private XYDataset createDataset( ) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("funkcja celu");
        entriesMap.entrySet().stream().forEach(entry -> {
            series.add(entry.getKey(), entry.getValue());
        });
        dataset.addSeries(series);
        return dataset;
    }

    private static Map<Integer, Double> parseFile(Path path) throws IOException {
        Map<Integer, Double> functionPerIteration = new HashMap<>();
        Files.lines(path)
                .map(line -> line.split(";"))
                .forEach(numbers -> functionPerIteration.put(
                        Integer.parseInt(numbers[0]), Double.parseDouble(numbers[numbers.length - 1].split(" ")[1])));

        return functionPerIteration;
    }


    public static void showGraph(String p) {

        (new Thread(() -> {
            Path path = Paths.get(p);
            AntsLineChart chart = null;
            try {
                Map<Integer, Double> functionPerIteration = parseFile(path);
                chart = new AntsLineChart(
                        p ,
                        "Funkcja celu od iteracji", functionPerIteration);
            } catch (IOException e) {
                e.printStackTrace();
            }

            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );
        })).start();

    }
}