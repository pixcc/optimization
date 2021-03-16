package ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import methods.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class FXMLController {

    @FXML
    private Label statisticsLabel;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private LineChart<Double, Double> chart;

    @FXML
    private ChoiceBox<String> selectedTestBox;

    @FXML
    private ChoiceBox<String> selectedMethodBox;

    private static final Map<String, TestData> testMapping = Map.of(
            "Test 7", new TestData(x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2), new Segment(6, 9.9)),
            "Test 8", new TestData(x -> -3 * x * Math.sin(0.75 * x) + Math.exp(-2 * x), new Segment(0, Math.PI * 2)));

    public void initialize() {
        TestData testData = testMapping.get(selectedTestBox.getValue());

        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(0.5);
        xAxis.setLowerBound(testData.getSegment().getLeft());
        xAxis.setUpperBound(testData.getSegment().getRight());

        OptimizationMethod optimizationMethod = getOptimizationMethod(testData);
        double minPoint = optimizationMethod.findMin();

        List<Segment> segmentList = optimizationMethod.getIntermediateSegments();
        List<XYChart.Series<Double, Double>> series = prepareSeries(testData.getF(), segmentList);
        chart.getData().addAll(series);


        statisticsLabel.setText(String.format("""
                Minimum point: %f
                Minimum value: %f
                Iterations: %d
                
                """, minPoint,testData.getF().apply(minPoint), segmentList.size()));
    }


    @FXML
    public void buttonClicked(Event e) {
        chart.getData().clear();
        initialize();
    }

    private List<XYChart.Data<Double, Double>> calculateFunctionOnSegment(Function<Double, Double> f, Segment segment) {
        return DoubleStream.iterate(segment.getLeft(), x -> x <= segment.getRight(), x -> x + 0.001).parallel().boxed().map(x -> new XYChart.Data<>(x, f.apply(x))).collect(Collectors.toList());
    }

    private List<XYChart.Series<Double, Double>> prepareSeries(Function<Double, Double> f, List<Segment> segments) {
        List<XYChart.Series<Double, Double>> seriesList = new ArrayList<>();
        for (int i = 0; i + 1 < segments.size(); ++i) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();
            Segment left = new Segment(segments.get(i).getLeft(), segments.get(i + 1).getLeft());
            Segment right = new Segment(segments.get(i + 1).getRight(), segments.get(i).getRight());
            if (left.getLength() >= 1e-9) {
                series.getData().addAll(calculateFunctionOnSegment(f, left));
            }
            if (right.getLength() >= 1e-9) {
                series.getData().addAll(calculateFunctionOnSegment(f, right));
            }
            if (!series.getData().isEmpty()) {
                seriesList.add(series);
            }
        }
        return seriesList;
    }

    private OptimizationMethod getOptimizationMethod(TestData testData) {
        String s = selectedMethodBox.getValue();
        Function<Double, Double> f = testData.getF();
        double left = testData.getSegment().getLeft();
        double right = testData.getSegment().getRight();
        if ("Dichotomy method".equals(s)) {
            return new DichotomyMethod("Метод дихотомии", 1e-6, left, right, f, 1e-6);
        } else if ("Golden ratio method".equals(s)) {
            return new GoldenRatioMethod("Метод золотого сечения", 1e-6, left, right, f);
        } else if ("Fibonacci method".equals(s)) {
            return new FibonacciMethod("Метод фибоначи", 1e-6, left, right, f);
        } else if ("Successive parabolic method".equals(s)) {
            return new SuccessiveParabolicMethod("Метод парабол", 1e-6, left, right, f);
        } else {
            return new BrentMethod("Метод Брента", 1e-6, left, right, f);
        }
    }
}