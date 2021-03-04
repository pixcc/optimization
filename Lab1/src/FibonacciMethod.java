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
        double test = (end - start) / eps;
        int i = 1;
        while (Fib.get(i) <= test) {
            Fib.add(Fib.get(i) + Fib.get(i - 1));
            i++;
        }

        int n = i;

        double left = start;
        double right = end;
        double x1 = left + Fib.get(n - 2) * (right - left) / Fib.get(n);
        double x2 = left + Fib.get(n - 1) * (right - left) / Fib.get(n);
        counter += 2;
        double f1 = f.apply(x1);
        double f2 = f.apply(x2);
        double len = lenOX(left, right);
        double pred_len = len;
        writeLogInfAboutMethod();
        int ind = 0;
        for (int k = 1; k <= n - 2; k++) {
            if (f1 > f2) {
                /*writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind++, left, right, len, pred_len / len,
                        new Point(x1).toString(), new Point(x2).toString()));*/
                left = x1;
                x1 = x2;
                counter++;
                x2 = left + Fib.get(n - k - 1) * (right - left) / Fib.get(n - k);
                f1 = f2;
                f2 = f.apply(x2);
            } else {
                /*writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind++, left, right, len, pred_len / len,
                        new Point(x1).toString(), new Point(x2).toString()));*/
                right = x2;
                x2 = x1;
                x1 = left + Fib.get(n - k - 2) * (right - left) / Fib.get(n - k);
                f2 = f1;
                counter++;
                f1 = f.apply(x1);
            }
            pred_len = len;
            len = lenOX(left, right);
        }
        /*writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind, left, right, len, pred_len / len,
                new Point(x1).toString(), new Point(x2).toString()));*/
        return (x1 + x2) / 2.0;
    }
}
