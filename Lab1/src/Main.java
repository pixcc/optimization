import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    final static double analyticAns = 8.72691;
    public static void main(String[] args) {
        Function<Double, Double> f = x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2);
        test_7();
        test_6();
        test_x_sinx();
    }

    private static void test_7() {
        Function<Double, Double> f = x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2);
        run(f);
    }

    private static void test_6() {
        Function<Double, Double> f = x -> -5 * Math.pow(x, 5) + 4 * Math.pow(x, 4) - 12 * Math.pow(x, 3) + 11 * Math.pow(x, 2) - 2 * x + 1;
        run(f);
    }

    private static void test_x_sinx() {
        Function<Double, Double> f = x -> (x - 100) * (x - 100);
        run(f);
    }

    private static void run(Function<Double, Double> f) {
        List<OptimizationMethod> methods = new ArrayList<>();

        methods.add(new DichotomyMethod("Метод дихотомии", 1e-6, 6, 9.9, f, 1e-6));
        methods.add(new GoldenRatioMethod( "Метод золотого сечения", 1e-9, 6, 9.9, f));
        methods.add(new FibonacciMethod("Метод фибоначи", 1e-9, 6, 9.9, f));
        methods.add(new SuccessiveParabolicMethod("Метод парфбол", 1e-9, 6, 9.9, f));
        methods.add(new BrentMethod("Метод Брента",1e-9, 6, 9.9, f));

        for (OptimizationMethod method : methods) {
            System.out.println(method.toString());
            double ans = method.findMin();
            System.out.format("ans = %.4f Δ = %.4f \n", ans, Math.abs(ans - analyticAns) );
        }
    }
}
