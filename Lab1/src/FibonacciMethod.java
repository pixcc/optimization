import java.util.ArrayList;
import java.util.function.Function;

public class FibonacciMethod extends AbstractMethod {

    protected FibonacciMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        super(name, eps, start, end, f);
    }

    @Override
    public double findMin() {

        ArrayList<Long> Fib = new ArrayList<>();
        Fib.add(0L);
        Fib.add(1L);
        double maxFib = (end - start) / eps;
        int n = 1;
        while (Fib.get(n) <= maxFib) {
            Fib.add(Fib.get(n) + Fib.get(n - 1));
            n++;
        }

        Point left = new Point(start);
        Point right = new Point(end);
        Point x1 = new Point(left.getX() + Fib.get(n - 2) * (right.getX() - left.getX()) / Fib.get(n));
        Point x2 = new Point(left.getX() + Fib.get(n - 1) * (right.getX() - left.getX()) / Fib.get(n));
        for (int k = 1; k <= n - 2; k++) {
            if (x1.getY() > x2.getY()) {
                left = x1;
                x1 = x2;
                x2 = new Point(left.getX() + Fib.get(n - k - 1) * (right.getX() - left.getX()) / Fib.get(n - k));
            } else {
                right = x2;
                x2 = x1;
                x1 = new Point(left.getX() + Fib.get(n - k - 2) * (right.getX() - left.getX()) / Fib.get(n - k));
            }
        }
        return (x1.getX() + x2.getX()) / 2.0;
    }
}
