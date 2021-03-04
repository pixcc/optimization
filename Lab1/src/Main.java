import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    final static double analyticAns_7 = 8.72691;
    final static double analyticAns_x_sinx = 0;
    final static double analyticAns_6 = 0.1099;// хз
    final static double analyticKek = -3.0;// хз

    public static void main(String[] args) {
        test_7();
        //test_6();
        //test_x_sinx();
        // test_kek();
    }

    private static void test_kek() {
        Function<Double, Double> f = x -> 12 * x + 2 * x * x - 5 * x * x * x - (5 * Math.pow(x, 4)) / 4 + (3 * Math.pow(x, 5) / 5) + Math.pow(x, 6) / 6;
        run(f, analyticKek, -3.4, -2);
    }

    private static void test_7() {
        Function<Double, Double> f = x -> Math.pow(Math.log10(x - 2), 2) + Math.pow(Math.log10(10 - x), 2) - Math.pow(x, 0.2);
        run(f, analyticAns_7, 6, 9.9);
    }

    private static void test_6() {
        Function<Double, Double> f = x -> -5 * Math.pow(x, 5) + 4 * Math.pow(x, 4) - 12 * Math.pow(x, 3) + 11 * Math.pow(x, 2) - 2 * x + 1;
        run(f, analyticAns_6, -0.5, 0.5);
    }

    private static void test_x_sinx() {
        Function<Double, Double> f = x -> x * Math.sin(x);
        run(f, analyticAns_x_sinx, -0.5, 0.5);
    }

    private static void run(Function<Double, Double> f, double analyticAns, double left, double right) {
        List<AbstractMethod> methods = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            double eps = Math.pow(10, -i);
            methods.add(new DichotomyMethod("Метод дихотомии", eps, left, right, f, eps / 2));
            methods.add(new GoldenRatioMethod("Метод золотого сечения", eps, left, right, f));
            methods.add(new FibonacciMethod("Метод фибоначи", eps, left, right, f));
            methods.add(new SuccessiveParabolicMethod("Метод парабол", eps, left, right, f));
            methods.add(new BrentMethod("Метод Брента", eps, left, right, f));

            for (AbstractMethod method : methods) {
                double ans = method.findMin();
                System.out.println(i + " " + method.counter);
            }

            methods.clear();
        }
    }

}
