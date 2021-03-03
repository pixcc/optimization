import java.util.function.Function;

public class GoldenRatioMethod extends AbstractMethod {
    private static final double INV_GOLDEN_RATIO = (Math.sqrt(5) - 1) / 2;

    public GoldenRatioMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        super(name, eps, start, end, f);
    }

    @Override
    public double findMin() {
        double l = start;
        double r = end;
        double x1 = l + (1 - INV_GOLDEN_RATIO) * (r - l);
        double x2 = l + INV_GOLDEN_RATIO * (r - l);
        double fx1 = f.apply(x1);
        double fx2 = f.apply(x2);
        int ind = 0;
        while (compare(r - l, eps) > 0) {
            if (compare(fx1, fx2) <= 0) {
                //writeLog(r, l, x1, f.apply(x1), ind++);
                r = x2;
                x2 = x1;
                x1 = r - INV_GOLDEN_RATIO * (r - l);
                fx2 = fx1;
                fx1 = f.apply(x1);
            } else {
                //writeLog(r, l, x1, f.apply(x1), ind++);
                l = x1;
                x1 = x2;
                x2 = l + INV_GOLDEN_RATIO * (r - l);
                fx1 = fx2;
                fx2 = f.apply(x2);
            }
        }
        return (x1 + x2) / 2;
    }
}
