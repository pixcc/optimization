import java.util.function.Function;

public class FibonacciMethod extends AbstractMethod {

    protected FibonacciMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        super(name, eps, start, end, f);
    }

    @Override
    public double findMin() {
        int n = (int) (Math.log(Math.ceil(Math.sqrt(5) * (end - start) / eps)) / Math.log((1 + Math.sqrt(5)) / 2.0) + 1);
        int[] Fib = new int[n + 1];
        Fib[0] = 0;
        Fib[1] = 1;

        for (int i = 2; i < n + 1; i++) {
            Fib[i] = Fib[i - 2] + Fib[i - 1];
        }

        double left = start;
        double right = end;
        double x1 = left + Fib[n - 2] * (right - left) / Fib[n];
        double x2 = left + Fib[n - 1] * (right - left) / Fib[n];
        double f1 = f.apply(x1);
        double f2 = f.apply(x2);
        int ind = 0;
        for (int k = 1; k <= n - 2; k++) {
            if (f1 > f2) {
                //writeLog(right, left, x1, f.apply(x1), ind++);
                left = x1;
                x1 = x2;
                x2 = left + Fib[n - k - 1] * (right - left) / Fib[n - k];
                f1 = f2;
                f2 = f.apply(x2);
            } else {
                //writeLog(right, left, x1, f.apply(x1), ind++);
                right = x2;
                x2 = x1;
                x1 = left + Fib[n - k - 2] * (right - left) / Fib[n - k];
                f2 = f1;
                f1 = f.apply(x1);
            }
        }

        return (x1 + x2) / 2.0;
    }
}
