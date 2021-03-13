package ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import methods.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class FXMLController {

    @FXML
    private ScatterChart<Double, Double> chart;

    @FXML
    private ChoiceBox<String> selectedTestBox;

    @FXML
    private ChoiceBox<String> selectedMethodBox;

    private static final Map<String, TestData> testMapping = Map.of(
            "Test 7", new TestData(x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2), new Segment(6, 9.9)),
            "Test 8", new TestData(x -> -3 * x * Math.sin(0.75 * x) + Math.exp(-2 * x), new Segment(0, Math.PI * 2)));

    public void initialize() {
        TestData testData = testMapping.get(selectedTestBox.getValue());
        OptimizationMethod optimizationMethod = getOptimizationMethod(testData);
        /*for (Segment segment : optimizationMethod.getIntermediateSegments()) {
            showFunctionSegment(testData.getF(), segment);
        }*/
        showFunctionSegment(testData.getF(), testData.getSegment());
    }

    @FXML
    public void buttonClicked(Event e) {
        chart.getData().clear();
        initialize();
    }

    private List<XYChart.Data<Double, Double>> calculateFunctionOnSegment(Function<Double, Double> f, Segment segment, double step) {
        return DoubleStream.iterate(segment.getLeft(), x -> x <= segment.getRight(), x -> x + step).parallel().boxed().map(x -> new XYChart.Data<>(x, f.apply(x))).collect(Collectors.toList());
    }

    private void showFunctionSegment(Function<Double, Double> f, Segment segment) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.getData().addAll(calculateFunctionOnSegment(f, segment, 0.1));
        chart.getData().add(series);
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
        } else if ("FibonacciMethod".equals(s)) {
            return new FibonacciMethod("Метод фибоначи", 1e-6, left, right, f);
        } else if ("Successive parabolic method".equals(s)) {
            return new SuccessiveParabolicMethod("Метод парабол", 1e-6, left, right, f);
        } else {
            return new BrentMethod("Метод Брента", 1e-6, left, right, f);
        }
    }
}