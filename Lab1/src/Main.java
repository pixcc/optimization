import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Double, Double> f = x -> Math.pow(Math.log(x - 2), 2) + Math.pow(Math.log(10 - x), 2) - Math.pow(x, 0.2);
        OptimizationMethod method = new DichotomyMethod(1e-6, 6, 9.9, f, 1e-6);
        System.out.println(method.findMin());

    }
}
