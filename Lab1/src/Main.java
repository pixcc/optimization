import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Double, Double> f = x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2);

        OptimizationMethod dichotomyMethod = new DichotomyMethod(1e-6, 6, 9.9, f, 1e-6);
        System.out.println(dichotomyMethod.findMin());

        OptimizationMethod goldenMethod = new GoldenRatioMethod(1e-9, 6, 9.9, f);
        System.out.println(goldenMethod.findMin());

        OptimizationMethod fibonacciMethod = new FibonacciMethod(1e-9, 6, 9.9, f);
        System.out.println(fibonacciMethod.findMin());

        OptimizationMethod successiveParabolicMethod = new SuccessiveParabolicMethod(1e-9, 6, 9.9, f);
        System.out.println(successiveParabolicMethod.findMin());

        OptimizationMethod brentMethod = new BrentMethod(1e-9, 6, 9.9, f);
        System.out.println(brentMethod.findMin());
        test_6();
        test_x_sinx();
    }

    private static void test_6() {
        Function<Double, Double> f = x -> -5 * Math.pow(x, 5) + 4 * Math.pow(x, 4) - 12 * Math.pow(x, 3) + 11 * Math.pow(x, 2) - 2 * x + 1;

        OptimizationMethod dichotomyMethod = new DichotomyMethod(1e-6, -0.5, 0.5, f, 1e-6);
        System.out.println(dichotomyMethod.findMin());

        OptimizationMethod goldenMethod = new GoldenRatioMethod(1e-9, -0.5, 0.5, f);
        System.out.println(goldenMethod.findMin());

        OptimizationMethod fibonacciMethod = new FibonacciMethod(1e-9, -0.5, 0.5, f);
        System.out.println(fibonacciMethod.findMin());

        OptimizationMethod SuccessiveParabolicMethod = new SuccessiveParabolicMethod(1e-9, -0.5, 0.5, f);
        System.out.println(SuccessiveParabolicMethod.findMin());

        OptimizationMethod brentMethod = new BrentMethod(1e-9, -0.5, 0.5, f);
        System.out.println(brentMethod.findMin());
    }

    private static void test_x_sinx() {
        Function<Double, Double> f = x -> x * Math.sin(x);

        OptimizationMethod dichotomyMethod = new DichotomyMethod(1e-6, -1, 1, f, 1e-6);
        System.out.println(dichotomyMethod.findMin());

        OptimizationMethod goldenMethod = new GoldenRatioMethod(1e-9, -1, 1,  f);
        System.out.println(goldenMethod.findMin());

        OptimizationMethod fibonacciMethod = new FibonacciMethod(1e-9, -1, 1,  f);
        System.out.println(fibonacciMethod.findMin());

        OptimizationMethod SuccessiveParabolicMethod = new SuccessiveParabolicMethod(1e-9, -1, 1,  f);
        System.out.println(SuccessiveParabolicMethod.findMin());

        OptimizationMethod brentMethod = new BrentMethod(1e-9, -1, 1,  f);
        System.out.println(brentMethod.findMin());
    }
}
